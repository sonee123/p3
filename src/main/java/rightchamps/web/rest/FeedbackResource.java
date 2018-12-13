package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.EmailTemplate;
import rightchamps.domain.EmailTemplateBody;
import rightchamps.domain.Feedback;

import rightchamps.domain.UserAuthority;
import rightchamps.repository.EmailTemplateBodyRepository;
import rightchamps.repository.EmailTemplateRepository;
import rightchamps.repository.FeedbackRepository;
import rightchamps.repository.UserRepository;
import rightchamps.service.MailService;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.AbstractResource;


import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.io.*;
import java.net.*;

/**
 * REST controller for managing Feedback.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "Feedback";

    private final FeedbackRepository feedbackRepository;

    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    @Inject
    private EmailTemplateBodyRepository emailTemplateBodyRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ApplicationProperties applicationProperties;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    MailService mailService;

    public FeedbackResource(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * POST  /feedbacks : Create a new feedback.
     *
     * @param @feedback the feedback to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feedback, or with status 400 (Bad Request) if the feedback has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/feedbacks" , consumes = {"multipart/form-data"})
    @Timed
    public ResponseEntity<Feedback> createFeedback(@RequestParam(required = false) MultipartFile file, String email, String message) throws URISyntaxException, IOException {
        /*log.debug("REST request to save Feedback : {}", feedback);
        if (feedback.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        String UPLOADED_FOLDER = applicationProperties.getUploadPath();
        Feedback feedback = new Feedback();

        feedback.setYourEmailId(email);
        feedback.setRemarks(message);
        feedback.setCreatedDate(LocalDate.now());
        feedback.setCreatedBy(getCurrentUserLogin());

        Feedback result = feedbackRepository.save(feedback);
        try {
            if (file != null) {
                String extn = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
                result.setAttachFile(result.getId() + extn);
                result.setDisplayAttachFile(file.getOriginalFilename());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + "feedback/" + result.getId() + extn);
                Files.write(path, bytes);
                result = feedbackRepository.save(result);
            }
        } catch(IOException e){
            System.out.println("FeedbackResource createFeedback() "+ e.getMessage());
        } catch(Exception e){
            System.out.println("FeedbackResource createFeedback() "+ e.getMessage());
        }


        //Send Mail

        String emailTemplateCode="feedbackMail";
        EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateCode(emailTemplateCode);
        List<EmailTemplateBody> emailTemplateBodies = emailTemplateBodyRepository.findAllByTemplateCode(emailTemplateCode);
        if(emailTemplateBodies!=null && emailTemplateBodies.size()>0) {
            String emailBody = "";
            for (EmailTemplateBody body : emailTemplateBodies) {
                emailBody += body.getMailBody();
            }

            emailBody = StringEscapeUtils.unescapeHtml3(emailBody);

            emailBody = emailBody.replaceAll("<<", "<span th:text=\"\\${");
            emailBody = emailBody.replaceAll(">>", "}\" th:remove=\"tag\"/>");

            String subject = emailTemplate.getMailSubject();
            subject = subject.replaceAll("<<", "<span th:text=\"\\${");
            subject = subject.replaceAll(">>", "}\" th:remove=\"tag\"/>");

            PrintWriter writer = null;
            try {
                String PATH = applicationProperties.getTemplatePath()+"templates/mail";
                //String PATH = "E:\\Project\\rns\\target\\classes\\mails";
                File f = new File(PATH + "/" + emailTemplateCode + ".html");
                File f1 = new File(PATH + "/" + emailTemplateCode + "Subject.html");
                if (f.exists()) {
                } else {
                    f.createNewFile();
                }
                if (f1.exists()) {
                } else {
                    f1.createNewFile();
                }
                writer = new PrintWriter(PATH + "/" + emailTemplateCode + ".html", "UTF-8");
                writer.println(emailBody);
                writer.close();

                writer = new PrintWriter(PATH + "/" + emailTemplateCode + "Subject.html", "UTF-8");
                writer.println(subject);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch(Exception e){
                System.out.println("FeedbackResource createFeedback() "+ e.getMessage());
            }

            try {
                if (result != null) {
                    Locale locale = Locale.forLanguageTag("en");
                    Context context = new Context(locale);
                    context.setVariable("feedback", result);
                    context.setVariable("applicationProperties", applicationProperties);
                    String content = templateEngine.process("mail/" + emailTemplateCode, context);
                    subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);
                    if (file != null) {
                        File file1 = new File(UPLOADED_FOLDER + "feedback/" + result.getAttachFile());
                        mailService.sendEmail("sheetal@fulcrumconsultancy.com", subject, content, true, true, file1, result.getAttachFile());
                    } else {
                        mailService.sendEmail("sheetal@fulcrumconsultancy.com", subject, content, false, true);
                    }
                }
            } catch(Exception e){
                System.out.println("FeedbackResource createFeedback() "+ e.getMessage());
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feedbacks : Updates an existing feedback.
     *
     * @param feedback the feedback to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feedback,
     * or with status 400 (Bad Request) if the feedback is not valid,
     * or with status 500 (Internal Server Error) if the feedback couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feedbacks")
    @Timed
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedback);
        if (feedback.getId() == null) {
            //return createFeedback(null,feedback);
        }

        feedback.setCcdResponseDate(LocalDate.now());
        Feedback result = feedbackRepository.save(feedback);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feedback.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feedbacks : get all the feedbacks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feedbacks in body
     */
    @GetMapping("/feedbacks")
    @Timed
    public List<Feedback> getAllFeedbacks() {
        log.debug("REST request to get all Feedbacks");
        List<UserAuthority> userAuthorities = userRepository.findAllAuthorityByLogin(getCurrentUserLogin());
        if (userAuthorities != null && userAuthorities.get(0).getAuthorityName().equalsIgnoreCase("ROLE_ADMIN")) {
            return feedbackRepository.findAll();
        } else {
            return feedbackRepository.findAllByCreatedBy(getCurrentUserLogin());
        }
    }


       /* @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        // Load file from database
       // Feedback feedback = DBFileStorageService.getFile(fileId);
       Feedback feedback =  feedbackRepository.getOne(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(feedback.getAttachFile()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + feedback.getRemarks() + "\"")
               // .body(new ByteArrayResource(feedback.getAttachFile().toString));
                .body(new ByteArrayResource(feedback.getAttachFile()));
    }*/




    /**
     * GET  /feedbacks/:id : get the "id" feedback.
     *
     * @param id the id of the feedback to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedback, or with status 404 (Not Found)
     */
    @GetMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(feedback);
    }

    /**
     * GET  /feedbacks/:id : get the "id" feedback.
     *
     * @param id the id of the feedback to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedback, or with status 404 (Not Found)
     */
    @GetMapping("/feedbacks-download/{id}")
    @Timed
    public ResponseEntity<Object> getFeedbackDownload(@PathVariable Long id) throws FileNotFoundException, IOException{
        log.debug("REST request to get Feedback : {}", id);
        String UPLOADED_FOLDER = applicationProperties.getUploadPath();
        Feedback feedback = feedbackRepository.findById(id).orElse(null);;
        File file = new File(UPLOADED_FOLDER + "feedback/"+feedback.getAttachFile());
        Path path = Paths.get(UPLOADED_FOLDER + "feedback/" + feedback.getAttachFile());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        String mimeType = Files.probeContentType(path);
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType(mimeType)).body(resource);
        return responseEntity;
    }

    /**
     * DELETE  /feedbacks/:id : delete the "id" feedback.
     *
     * @param id the id of the feedback to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }
}
