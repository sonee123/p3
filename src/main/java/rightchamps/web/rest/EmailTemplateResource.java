package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.EmailTemplate;

import rightchamps.domain.EmailTemplateBody;
import rightchamps.repository.EmailTemplateBodyRepository;
import rightchamps.repository.EmailTemplateRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmailTemplate.
 */
@RestController
@RequestMapping("/api")
public class EmailTemplateResource {

    private final Logger log = LoggerFactory.getLogger(EmailTemplateResource.class);

    private static final String ENTITY_NAME = "Email Template";

    private final EmailTemplateRepository emailTemplateRepository;

    @Inject
    private EmailTemplateBodyRepository emailTemplateBodyRepository;

    @Autowired
    private ServletContext servletContext;

    public EmailTemplateResource(EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    /**
     * POST  /email-templates : Create a new emailTemplate.
     *
     * @param emailTemplate the emailTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailTemplate, or with status 400 (Bad Request) if the emailTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplate> createEmailTemplate(@Valid @RequestBody EmailTemplate emailTemplate) throws URISyntaxException {
        log.debug("REST request to save EmailTemplate : {}", emailTemplate);
        if (emailTemplate.getId() != null) {
            throw new BadRequestAlertException("A new emailTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }

        EmailTemplate emailTemplate1 = emailTemplateRepository.findByTemplateCode(emailTemplate.getTemplateCode());
        if(emailTemplate1!=null){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, emailTemplate1.getId().toString(),"Template Code already exist"))
                .body(emailTemplate1);
        } else {
            if (emailTemplate.getCreatedBy() == null) {
                emailTemplate.setCreatedBy(getCurrentUserLogin());
            }
            emailTemplate.setCreatedDate(LocalDate.now());
            String mailBody = emailTemplate.getMailBody();
            emailTemplate.setMailBody("");
            EmailTemplate result = emailTemplateRepository.save(emailTemplate);
            List<String> strings = new ArrayList<String>();
            int index = 0;
            while (index < mailBody.length()) {
                strings.add(mailBody.substring(index, Math.min(index + 1000, mailBody.length())));
                index += 1000;
            }
            for(String body : strings){
                EmailTemplateBody emailTemplateBody = new EmailTemplateBody();
                emailTemplateBody.setTemplateCode(result.getTemplateCode());
                emailTemplateBody.setMailBody(body);
                emailTemplateBody.setCreatedBy(getCurrentUserLogin());
                emailTemplateBody.setCreatedDate(LocalDate.now());
                emailTemplateBodyRepository.save(emailTemplateBody);
            }
            return ResponseEntity.created(new URI("/api/email-templates/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
    }

    /**
     * PUT  /email-templates : Updates an existing emailTemplate.
     *
     * @param emailTemplate the emailTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailTemplate,
     * or with status 400 (Bad Request) if the emailTemplate is not valid,
     * or with status 500 (Internal Server Error) if the emailTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplate> updateEmailTemplate(@Valid @RequestBody EmailTemplate emailTemplate) throws URISyntaxException {
        log.debug("REST request to update EmailTemplate : {}", emailTemplate);
        if (emailTemplate.getId() == null) {
            return createEmailTemplate(emailTemplate);
        }
        if(emailTemplate.getLastUpdatedBy()==null){
            emailTemplate.setLastUpdatedBy(getCurrentUserLogin());
        }
        emailTemplate.setLastUpdatedDate(LocalDate.now());

        String mailBody = emailTemplate.getMailBody();
        emailTemplate.setMailBody("");
        EmailTemplate result = emailTemplateRepository.save(emailTemplate);

        List<String> strings = new ArrayList<String>();
        int index = 0;
        while (index < mailBody.length()) {
            strings.add(mailBody.substring(index, Math.min(index + 1000, mailBody.length())));
            index += 1000;
        }
        emailTemplateBodyRepository.deleteByTemplateCode(result.getTemplateCode());
        for(String body : strings){
            EmailTemplateBody emailTemplateBody = new EmailTemplateBody();
            emailTemplateBody.setTemplateCode(result.getTemplateCode());
            emailTemplateBody.setMailBody(body);
            emailTemplateBody.setCreatedBy(getCurrentUserLogin());
            emailTemplateBody.setCreatedDate(LocalDate.now());
            emailTemplateBodyRepository.save(emailTemplateBody);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-templates : get all the emailTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emailTemplates in body
     */
    @GetMapping("/email-templates")
    @Timed
    public List<EmailTemplate> getAllEmailTemplates() {
        log.debug("REST request to get all EmailTemplates");
        List<EmailTemplate> emailTemplatesTemp = emailTemplateRepository.findAll();
        List<EmailTemplate> emailTemplates = new ArrayList<EmailTemplate>();
        for(EmailTemplate emailTemplate : emailTemplatesTemp) {
            List<EmailTemplateBody> emailTemplateBodies = emailTemplateBodyRepository.findAllByTemplateCode(emailTemplate.getTemplateCode());
            String emailBody = "";
            for (EmailTemplateBody body : emailTemplateBodies) {
                emailBody += body.getMailBody();
            }
            emailTemplate.setMailBody(emailBody);
            emailTemplates.add(emailTemplate);
        }
        return emailTemplates;
    }

    /**
     * GET  /email-templates/:id : get the "id" emailTemplate.
     *
     * @param id the id of the emailTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<EmailTemplate> getEmailTemplate(@PathVariable Long id) {
        log.debug("REST request to get EmailTemplate : {}", id);
        EmailTemplate emailTemplate  = emailTemplateRepository.findById(id).orElse(null);
        List<EmailTemplateBody> emailTemplateBodies = emailTemplateBodyRepository.findAllByTemplateCode(emailTemplate.getTemplateCode());
        String emailBody="";
        for(EmailTemplateBody body: emailTemplateBodies){
            emailBody+=body.getMailBody();
        }
        emailTemplate.setMailBody(emailBody);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emailTemplate));
    }

    /**
     * DELETE  /email-templates/:id : delete the "id" emailTemplate.
     *
     * @param id the id of the emailTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailTemplate(@PathVariable Long id) {
        log.debug("REST request to delete EmailTemplate : {}", id);
        emailTemplateRepository.deleteById(id);
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
