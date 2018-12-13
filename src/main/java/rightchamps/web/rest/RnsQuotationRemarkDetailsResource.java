package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.*;

import rightchamps.modal.RnsQuotationFullDetails;
import rightchamps.modal.RnsQuotationRemarkDetailsModal;
import rightchamps.repository.*;
import rightchamps.service.MailService;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * REST controller for managing RnsQuotationRemarkDetails.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationRemarkDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationRemarkDetailsResource.class);

    private static final String ENTITY_NAME = "Quotation Remark Details";

    private final RnsQuotationRemarkDetailsRepository rnsQuotationRemarkDetailsRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    @Inject
    private EmailTemplateBodyRepository emailTemplateBodyRepository;

    @Inject
    private ApplicationProperties applicationProperties;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private MailService mailService;

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private AuctionVrntRepository auctionVrntRepository;

    @Inject
    private MessageBodyRepository messageBodyRepository;

    public RnsQuotationRemarkDetailsResource(RnsQuotationRemarkDetailsRepository rnsQuotationRemarkDetailsRepository) {
        this.rnsQuotationRemarkDetailsRepository = rnsQuotationRemarkDetailsRepository;
    }

    /**
     * POST  /rns-quotation-remark-details : Create a new rnsQuotationRemarkDetails.
     *
     * @param rnsQuotationRemarkDetails the rnsQuotationRemarkDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotationRemarkDetails, or with status 400 (Bad Request) if the rnsQuotationRemarkDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotation-remark-details")
    @Timed
    public ResponseEntity<RnsQuotationRemarkDetailsModal> createRnsQuotationRemarkDetails(@Valid @RequestBody RnsQuotationRemarkDetails rnsQuotationRemarkDetails) throws URISyntaxException {
        rnsQuotationRemarkDetails.setAuthDate(Instant.now());
        log.debug("REST request to save RnsQuotationRemarkDetails : {}", rnsQuotationRemarkDetails);
        if (rnsQuotationRemarkDetails.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotationRemarkDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rnsQuotationRemarkDetails.setId(null);
        RnsQuotationRemarkDetails result = rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetails);
        RnsQuotationRemarkDetails rnsQuotationRemarkDetailsnew = rnsQuotationRemarkDetails;
        rnsQuotationRemarkDetailsnew.setEmpCode(rnsQuotationRemarkDetailsnew.getForwardCode());
        rnsQuotationRemarkDetailsnew.setForwardCode(null);
        rnsQuotationRemarkDetailsnew.setAuthType(null);
        rnsQuotationRemarkDetailsnew.setAuthDate(null);
        RnsQuotationRemarkDetails result1 = rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetailsnew);
        RnsQuotationRemarkDetailsModal rnsQuotationRemarkDetailsModal = new RnsQuotationRemarkDetailsModal();
        rnsQuotationRemarkDetailsModal.setQuoteId(result1.getQuoteId());
            rnsQuotationRemarkDetailsModal.setId(result1.getId());
            rnsQuotationRemarkDetailsModal.setEmpCode(userRepository.findByLogin(getCurrentUserLogin()));
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationRemarkDetailsModal.getId().toString()))
                .body(rnsQuotationRemarkDetailsModal);

    }

    @PutMapping("/rns-quotation-remark-details-reopen")
    @Timed
    public ResponseEntity<RnsQuotationRemarkDetails> reOpenRnsQuotationRemarkDetails(@Valid @RequestBody RnsQuotationRemarkDetails rnsQuotationRemarkDetails) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationRemarkDetails : {}", rnsQuotationRemarkDetails);
        if (rnsQuotationRemarkDetails.getId() == 0) {
            rnsQuotationRemarkDetails.setId(null);
        }
        rnsQuotationRemarkDetails.setAuthDate(Instant.now());
        RnsQuotationRemarkDetails result = rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotation-remark-details : Updates an existing rnsQuotationRemarkDetails.
     *
     * @param rnsQuotationRemarkDetails the rnsQuotationRemarkDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationRemarkDetails,
     * or with status 400 (Bad Request) if the rnsQuotationRemarkDetails is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationRemarkDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-remark-details")
    @Timed
    public ResponseEntity<RnsQuotationRemarkDetails> updateRnsQuotationRemarkDetails(@Valid @RequestBody RnsQuotationRemarkDetails rnsQuotationRemarkDetails) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationRemarkDetails : {}", rnsQuotationRemarkDetails);
        String emailTemplateCode = null;
        if (rnsQuotationRemarkDetails.getFlowType() != null && rnsQuotationRemarkDetails.getFlowType().equalsIgnoreCase("A")) {
            emailTemplateCode = "approvalFlow";
        } else if (rnsQuotationRemarkDetails.getFlowType() != null && rnsQuotationRemarkDetails.getFlowType().equalsIgnoreCase("W")) {
            emailTemplateCode = "workFlowProcess";
        }
        if (rnsQuotationRemarkDetails.getId() == 0) {
            rnsQuotationRemarkDetails.setId(null);
            rnsQuotationRemarkDetails.setAuthDate(Instant.now());
            RnsQuotationRemarkDetails result = rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetails);

            RnsQuotationRemarkDetails rnsQuotationRemarkDetailsnew = new RnsQuotationRemarkDetails();
            rnsQuotationRemarkDetailsnew.setId(null);
            rnsQuotationRemarkDetailsnew.setQuoteId(result.getQuoteId());
            rnsQuotationRemarkDetailsnew.setEmpCode(result.getForwardCode());
            rnsQuotationRemarkDetailsnew.setFlowType(result.getFlowType());
            rnsQuotationRemarkDetailsnew.setApprovalType(result.getApprovalType());
            rnsQuotationRemarkDetailsnew.setRemarks("");
            rnsQuotationRemarkDetailsnew.setForwardCode(null);
            rnsQuotationRemarkDetailsnew.setAuthType("");
            rnsQuotationRemarkDetailsnew.setAuthDate(Instant.now());
            RnsQuotationRemarkDetails result1 = rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetailsnew);
            if (rnsQuotationRemarkDetails.getFlowType() != null && rnsQuotationRemarkDetails.getFlowType().equalsIgnoreCase("W")) {
                rnsQuotationRemarkDetailsRepository.updateRnsQuotationWorkflowStatus("F", result.getQuoteId());
            }

            if (result != null) {
                EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateCode(emailTemplateCode);
                List<EmailTemplateBody> emailTemplateBodies = emailTemplateBodyRepository.findAllByTemplateCode(emailTemplateCode);
                if (emailTemplateBodies != null && emailTemplateBodies.size() > 0) {
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
                        String PATH = applicationProperties.getTemplatePath() + "templates/mail";
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Locale locale = Locale.forLanguageTag(result.getForwardCode().getLangKey());
                        Context context = new Context(locale);
                        context.setVariable("empCode", result.getEmpCode());
                        context.setVariable("forwardCode", result.getForwardCode());
                        context.setVariable("quoteId", result.getQuoteId());
                        context.setVariable("applicationProperties", applicationProperties);
                        String content = templateEngine.process("mail/" + emailTemplateCode, context);
                        subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);

                        if (emailTemplate.isEmail() != null && emailTemplate.isEmail() == true) {
                            try {
                                mailService.sendEmail(result.getForwardCode().getEmail(), subject, content, false, true);
                            } catch (Exception e) {
                                System.out.println("RnsQuotationRemarksDetailsResource " + e.getMessage());
                            }
                        }

                        if (emailTemplate.isNotification() != null && emailTemplate.isNotification() == true) {
                            Message message = new Message();
                            message.setFromMail(result.getEmpCode().getLogin());
                            message.setToMail(result.getForwardCode().getLogin());
                            message.setSubject(subject);
                            message.setQuotationId(result.getId());
                            message.setMessageBody(content);
                            if (message.getCreatedBy() == null) {
                                message.setCreatedBy(result.getEmpCode().getLogin());
                            }
                            message.setCreatedDate(Instant.now());
                            String mailBody = message.getMessageBody();
                            message.setMessageBody(" ");
                            Message message1 = messageRepository.save(message);
                            List<String> strings = new ArrayList<String>();
                            int index = 0;
                            while (index < mailBody.length()) {
                                strings.add(mailBody.substring(index, Math.min(index + 2000, mailBody.length())));
                                index += 2000;
                            }
                            for (String body : strings) {
                                MessageBody messageBody = new MessageBody();
                                messageBody.setMessageId(message1.getId());
                                messageBody.setMessageBody(body);
                                messageBody.setCreatedBy(result.getEmpCode().getLogin());
                                messageBody.setCreatedDate(LocalDate.now());
                                messageBodyRepository.save(messageBody);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("RnsQuotationRemarksDetailsResource " + e.getMessage());
                    }
                }
            }
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);

        } else {
            rnsQuotationRemarkDetails.setAuthDate(Instant.now());
            RnsQuotationRemarkDetails result = rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetails);
            if(result!=null && result.getAuthType()!=null && (result.getAuthType().equalsIgnoreCase("C") || result.getAuthType().equalsIgnoreCase("R"))){
                if(result.getFlowType()!=null && result.getFlowType().equalsIgnoreCase("W")) {
                    rnsQuotationRemarkDetailsRepository.updateRnsQuotationWorkflowStatus(result.getAuthType(), result.getQuoteId());
                } else if(result.getFlowType()!=null && result.getFlowType().equalsIgnoreCase("A")){
                    RnsQuotation rnsQuotation = rnsQuotationRepository.findById(result.getQuoteId()).orElse(null);
                    rnsQuotation.setApprovedDate(Instant.now());
                    rnsQuotation.setApprovedBy(getCurrentUserLogin());
                    rnsQuotation.setApprovedFlag(result.getAuthType());
                    if(result.getAuthType().equalsIgnoreCase("C")) {
                        if(result.getApprovalType() !=null && result.getApprovalType().equalsIgnoreCase("A")) {
                            rnsQuotation.setRfqApplicable(true);
                            rnsQuotation.setAuctionApplicable(true);
                        } else if(result.getApprovalType() !=null && result.getApprovalType().equalsIgnoreCase("R")) {
                            rnsQuotation.setRfqApplicable(true);
                        } else if(result.getApprovalType() !=null && result.getApprovalType().equalsIgnoreCase("B")) {
                            rnsQuotation.setAuctionApplicable(true);
                        }
                    }
                    rnsQuotationRepository.save(rnsQuotation);
                }
            }
            else {
                RnsQuotationRemarkDetails rnsQuotationRemarkDetailsnew = new RnsQuotationRemarkDetails();
                rnsQuotationRemarkDetailsnew.setId(null);
                rnsQuotationRemarkDetailsnew.setQuoteId(result.getQuoteId());
                rnsQuotationRemarkDetailsnew.setEmpCode(result.getForwardCode());
                rnsQuotationRemarkDetailsnew.setFlowType(result.getFlowType());
                rnsQuotationRemarkDetailsnew.setApprovalType(result.getApprovalType());
                rnsQuotationRemarkDetailsnew.setRemarks("");
                rnsQuotationRemarkDetailsnew.setForwardCode(null);
                rnsQuotationRemarkDetailsnew.setAuthType("");
                rnsQuotationRemarkDetailsnew.setAuthDate(Instant.now());
                RnsQuotationRemarkDetails result1 = rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetailsnew);
                if(result.getFlowType()!=null && result.getFlowType().equalsIgnoreCase("W")) {
                    rnsQuotationRemarkDetailsRepository.updateRnsQuotationWorkflowStatus("F", result.getQuoteId());
                }
            }
            if (result != null) {
                EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateCode(emailTemplateCode);
                List<EmailTemplateBody> emailTemplateBodies = emailTemplateBodyRepository.findAllByTemplateCode(emailTemplateCode);
                if (emailTemplateBodies != null && emailTemplateBodies.size() > 0) {
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
                        String PATH = applicationProperties.getTemplatePath() + "templates/mail";
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (result != null && result.getAuthType() != null && (result.getAuthType().equalsIgnoreCase("C") || result.getAuthType().equalsIgnoreCase("R"))) {
                        List<RnsQuotationRemarkDetails> rnsQuotationRemarkDetailsList = rnsQuotationRemarkDetailsRepository.getRnsQuotationRemarkDetailsByQuoteId(result.getQuoteId(), result.getFlowType());
                        List<String> sentList = new ArrayList<>();
                        for(RnsQuotationRemarkDetails quotationRemarkDetails : rnsQuotationRemarkDetailsList) {
                            if(sentList.contains(quotationRemarkDetails.getEmpCode().getLogin())){}
                            else {
                                try {
                                    Locale locale = Locale.forLanguageTag(quotationRemarkDetails.getForwardCode().getLangKey());
                                    Context context = new Context(locale);
                                    context.setVariable("empCode", result.getEmpCode());
                                    context.setVariable("forwardCode", quotationRemarkDetails.getEmpCode());
                                    context.setVariable("quoteId", result.getQuoteId());
                                    context.setVariable("applicationProperties", applicationProperties);
                                    String content = templateEngine.process("mail/" + emailTemplateCode, context);
                                    subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);

                                    if (emailTemplate.isEmail() != null && emailTemplate.isEmail() == true) {
                                        try {
                                            mailService.sendEmail(quotationRemarkDetails.getEmpCode().getEmail(), subject, content, false, true);
                                        } catch (Exception e) {
                                            System.out.println("RnsQuotationRemarksDetailsResource " + e.getMessage());
                                        }
                                    }

                                    if (emailTemplate.isNotification() != null && emailTemplate.isNotification() == true) {
                                        Message message = new Message();
                                        message.setFromMail(result.getEmpCode().getLogin());
                                        message.setToMail(quotationRemarkDetails.getEmpCode().getLogin());
                                        message.setSubject(subject);
                                        message.setQuotationId(result.getId());
                                        message.setMessageBody(content);
                                        if (message.getCreatedBy() == null) {
                                            message.setCreatedBy(result.getEmpCode().getLogin());
                                        }
                                        message.setCreatedDate(Instant.now());
                                        String mailBody = message.getMessageBody();
                                        message.setMessageBody(" ");
                                        Message message1 = messageRepository.save(message);
                                        List<String> strings = new ArrayList<String>();
                                        int index = 0;
                                        while (index < mailBody.length()) {
                                            strings.add(mailBody.substring(index, Math.min(index + 2000, mailBody.length())));
                                            index += 2000;
                                        }
                                        for (String body : strings) {
                                            MessageBody messageBody = new MessageBody();
                                            messageBody.setMessageId(message1.getId());
                                            messageBody.setMessageBody(body);
                                            messageBody.setCreatedBy(result.getEmpCode().getLogin());
                                            messageBody.setCreatedDate(LocalDate.now());
                                            messageBodyRepository.save(messageBody);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("RnsQuotationRemarksDetailsResource " + e.getMessage());
                                }
                            }
                        }
                    } else {
                        try {
                            Locale locale = Locale.forLanguageTag(result.getForwardCode().getLangKey());
                            Context context = new Context(locale);
                            context.setVariable("empCode", result.getEmpCode());
                            context.setVariable("forwardCode", result.getForwardCode());
                            context.setVariable("quoteId", result.getQuoteId());
                            context.setVariable("applicationProperties", applicationProperties);
                            String content = templateEngine.process("mail/" + emailTemplateCode, context);
                            subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);

                            if (emailTemplate.isEmail() != null && emailTemplate.isEmail() == true) {
                                try {
                                    mailService.sendEmail(result.getForwardCode().getEmail(), subject, content, false, true);
                                } catch (Exception e) {
                                    System.out.println("RnsQuotationRemarksDetailsResource " + e.getMessage());
                                }
                            }

                            if (emailTemplate.isNotification() != null && emailTemplate.isNotification() == true) {
                                Message message = new Message();
                                message.setFromMail(result.getEmpCode().getLogin());
                                message.setToMail(result.getForwardCode().getLogin());
                                message.setSubject(subject);
                                message.setQuotationId(result.getId());
                                message.setMessageBody(content);
                                if (message.getCreatedBy() == null) {
                                    message.setCreatedBy(result.getEmpCode().getLogin());
                                }
                                message.setCreatedDate(Instant.now());
                                String mailBody = message.getMessageBody();
                                message.setMessageBody(" ");
                                Message message1 = messageRepository.save(message);
                                List<String> strings = new ArrayList<String>();
                                int index = 0;
                                while (index < mailBody.length()) {
                                    strings.add(mailBody.substring(index, Math.min(index + 2000, mailBody.length())));
                                    index += 2000;
                                }
                                for (String body : strings) {
                                    MessageBody messageBody = new MessageBody();
                                    messageBody.setMessageId(message1.getId());
                                    messageBody.setMessageBody(body);
                                    messageBody.setCreatedBy(result.getEmpCode().getLogin());
                                    messageBody.setCreatedDate(LocalDate.now());
                                    messageBodyRepository.save(messageBody);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("RnsQuotationRemarksDetailsResource " + e.getMessage());
                        }
                    }
                }
            }
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }

    }

    /**
     * GET  /rns-quotation-remark-details : get all the rnsQuotationRemarkDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotationRemarkDetails in body
     */
    @GetMapping("/rns-quotation-remark-details")
    @Timed
    public List<RnsQuotationRemarkDetails> getAllRnsQuotationRemarkDetails() {
        log.debug("REST request to get all RnsQuotationRemarkDetails");
        return rnsQuotationRemarkDetailsRepository.findAll();
        }

    /**
     * GET  /rns-quotation-remark-details/:id : get the "id" rnsQuotationRemarkDetails.
     *
     * @param id the id of the rnsQuotationRemarkDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationRemarkDetails, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-remark-details/{id}")
    @Timed
    public ResponseEntity<RnsQuotationRemarkDetails> getRnsQuotationRemarkDetails(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationRemarkDetails : {}", id);
        Optional<RnsQuotationRemarkDetails> rnsQuotationRemarkDetails = rnsQuotationRemarkDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsQuotationRemarkDetails);
    }

    /**
     * GET  /rns-quotation-remark-details/:id : get the "id" rnsQuotationRemarkDetails.
     *
     * @param id the id of the rnsQuotationRemarkDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationRemarkDetails, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-remark-details-call-quote/{id}/{flowType}")
    @Timed
    public ResponseEntity<RnsQuotationRemarkDetailsModal> getRnsQuotationRemarkDetailsByQuoteNumber(@PathVariable Long id, @PathVariable String flowType) {
        log.debug("REST request to get RnsQuotationRemarkDetails : {}", id);
        List<RnsQuotationRemarkDetails> rnsQuotationRemarkDetails = rnsQuotationRemarkDetailsRepository.getRnsQuotationRemarkDetailsByQuoteId(id, flowType);
        RnsQuotation rnsQuotation = rnsQuotationRemarkDetailsRepository.getRnsQuotationById(id);
        RnsQuotationRemarkDetails rnsQuotationRemarkDetail = rnsQuotationRemarkDetailsRepository.getEntryRnsQuotationRemarkDetailsByQuoteId(id, flowType);
        RnsQuotationRemarkDetailsModal rnsQuotationRemarkDetailsModal = new RnsQuotationRemarkDetailsModal();
        if(rnsQuotationRemarkDetails!=null && rnsQuotationRemarkDetails.size()>0) {
            rnsQuotationRemarkDetailsModal.setRnsQuotationRemarkDetailsList(rnsQuotationRemarkDetails);
            if(rnsQuotationRemarkDetail!=null && rnsQuotationRemarkDetail.getId()!=null && rnsQuotationRemarkDetail.getId()>0){
                rnsQuotationRemarkDetailsModal.setQuoteId(id);
                rnsQuotationRemarkDetailsModal.setId(rnsQuotationRemarkDetail.getId());
                rnsQuotationRemarkDetailsModal.setApprovalType(rnsQuotationRemarkDetail.getApprovalType());
                if(getCurrentUserLogin()!=null && getCurrentUserLogin().equalsIgnoreCase(rnsQuotationRemarkDetail.getEmpCode().getLogin())){
                    rnsQuotationRemarkDetailsModal.setEmpCode(rnsQuotationRemarkDetail.getEmpCode());
                    rnsQuotationRemarkDetailsModal.setAllowEntry(true);
                }
                else{
                    rnsQuotationRemarkDetailsModal.setEmpCode(rnsQuotationRemarkDetail.getEmpCode());
                    rnsQuotationRemarkDetailsModal.setAllowEntry(false);
                }
            } else{
                rnsQuotationRemarkDetailsModal.setQuoteId(id);
                rnsQuotationRemarkDetailsModal.setId(new Long(0));
                rnsQuotationRemarkDetailsModal.setEmpCode(userRepository.findByLogin(getCurrentUserLogin()));
                rnsQuotationRemarkDetailsModal.setAllowEntry(false);
            }
            if(flowType!= null && flowType.equals("W") && rnsQuotation!=null && rnsQuotation.getWorkflowStatus()!=null && (rnsQuotation.getWorkflowStatus().equalsIgnoreCase("C") || rnsQuotation.getWorkflowStatus().equalsIgnoreCase("R"))){
                rnsQuotationRemarkDetailsModal.setAllowWorkFlow(rnsQuotation.getWorkflowStatus());
            } else if(flowType!= null && flowType.equals("A") && rnsQuotation!=null && rnsQuotation.getApprovedFlag()!=null && rnsQuotation.getApprovedFlag().length()>0){
                rnsQuotationRemarkDetailsModal.setAllowWorkFlow(rnsQuotation.getApprovedFlag());
            }

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationRemarkDetailsModal.getId().toString()))
                .body(rnsQuotationRemarkDetailsModal);
        } else{
            if(flowType != null && flowType.equalsIgnoreCase("A")) {
                List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(id);
                for(RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants){
                    if(rnsQuotationVariant.getOpenCosting()!=null && rnsQuotationVariant.getOpenCosting().equalsIgnoreCase("Required")){
                        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariant.getId());
                        if(auctionVrnt!=null && auctionVrnt.isApplicable()!=null && auctionVrnt.isApplicable()==true){}
                        else{
                            rnsQuotationRemarkDetailsModal.setId(new Long(0));
                            rnsQuotationRemarkDetailsModal.setAllowEntry(false);
                            rnsQuotationRemarkDetailsModal.setErrorMessage("Open costing required. Please fill in the open costing element information for "+rnsQuotationVariant.getTitle().replace("Variant", "Lot"));
                        }
                    }
                }
            }
            if(rnsQuotationRemarkDetailsModal.getErrorMessage() !=null && rnsQuotationRemarkDetailsModal.getErrorMessage().length()>0){ } else {
                if (rnsQuotationRemarkDetail != null && rnsQuotationRemarkDetail.getId() > 0) {
                    rnsQuotationRemarkDetailsModal.setQuoteId(id);
                    rnsQuotationRemarkDetailsModal.setId(new Long(rnsQuotationRemarkDetail.getId()));
                    rnsQuotationRemarkDetailsModal.setEmpCode(rnsQuotationRemarkDetail.getEmpCode());
                    if (getCurrentUserLogin() != null && getCurrentUserLogin().equalsIgnoreCase(rnsQuotationRemarkDetail.getEmpCode().getLogin())) {
                        rnsQuotationRemarkDetailsModal.setAllowEntry(true);
                    } else {
                        rnsQuotationRemarkDetailsModal.setAllowEntry(true);
                    }
                } else if (rnsQuotation.getUser() != null && rnsQuotation.getUser().getLogin().equalsIgnoreCase(getCurrentUserLogin())) {
                    rnsQuotationRemarkDetailsModal.setQuoteId(id);
                    rnsQuotationRemarkDetailsModal.setId(new Long(0));
                    rnsQuotationRemarkDetailsModal.setEmpCode(userRepository.findByLogin(getCurrentUserLogin().toLowerCase()));
                    rnsQuotationRemarkDetailsModal.setAllowEntry(true);
                } else {
                    rnsQuotationRemarkDetailsModal.setQuoteId(id);
                    rnsQuotationRemarkDetailsModal.setId(new Long(0));
                    rnsQuotationRemarkDetailsModal.setEmpCode(rnsQuotation.getUser());
                    rnsQuotationRemarkDetailsModal.setAllowEntry(false);
                }
                if (rnsQuotation != null && rnsQuotation.getWorkflowStatus() != null && rnsQuotation.getWorkflowStatus().equalsIgnoreCase("C")) {
                    rnsQuotationRemarkDetailsModal.setAllowWorkFlow("C");
                } else if (rnsQuotation != null && rnsQuotation.getWorkflowStatus() != null && rnsQuotation.getWorkflowStatus().equalsIgnoreCase("R")) {
                    rnsQuotationRemarkDetailsModal.setAllowWorkFlow("R");
                }
            }
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationRemarkDetailsModal.getId().toString()))
                .body(rnsQuotationRemarkDetailsModal);
        }
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsQuotationRemarkDetails));
    }

    /**
     * DELETE  /rns-quotation-remark-details/:id : delete the "id" rnsQuotationRemarkDetails.
     *
     * @param id the id of the rnsQuotationRemarkDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotation-remark-details/{id}")
    @Timed
    public ResponseEntity<RnsQuotationRemarkDetailsModal> deleteRnsQuotationRemarkDetails(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotationRemarkDetails : {}", id);
        RnsQuotationRemarkDetails rnsQuotationRemarkDetails = rnsQuotationRemarkDetailsRepository.getEntryRnsQuotationRemarkDetailsById(id);
        long prevId=rnsQuotationRemarkDetailsRepository.getMaxRnsQuotationRemarkDetailsByQuoteId(rnsQuotationRemarkDetails.getQuoteId(), rnsQuotationRemarkDetails.getFlowType());
        rnsQuotationRemarkDetailsRepository.deleteById(id);

        RnsQuotationRemarkDetails rnsQuotationRemarkDetailsprev = rnsQuotationRemarkDetailsRepository.getEntryRnsQuotationRemarkDetailsById(prevId);
        rnsQuotationRemarkDetailsprev.setRemarks("");
        rnsQuotationRemarkDetailsprev.setForwardCode(null);
        rnsQuotationRemarkDetailsprev.setAuthType("");
        rnsQuotationRemarkDetailsRepository.save(rnsQuotationRemarkDetailsprev);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(null);
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
