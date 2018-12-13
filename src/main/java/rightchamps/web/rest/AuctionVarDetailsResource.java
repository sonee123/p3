package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.*;

import rightchamps.modal.AuctionVarDetailsBean;
import rightchamps.repository.*;
import rightchamps.service.MailService;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * REST controller for managing AuctionVarDetails.
 */
@RestController
@RequestMapping("/api")
public class AuctionVarDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AuctionVarDetailsResource.class);

    private static final String ENTITY_NAME = "Auction Variant Details";

    private final AuctionVarDetailsRepository auctionVarDetailsRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    @Inject
    private EmailTemplateBodyRepository emailTemplateBodyRepository;

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private MessageBodyRepository messageBodyRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailService mailService;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private RnsQuotationVendorsRepository rnsQuotationVendorsRepository;

    public AuctionVarDetailsResource(AuctionVarDetailsRepository auctionVarDetailsRepository) {
        this.auctionVarDetailsRepository = auctionVarDetailsRepository;
    }

    /**
     * POST  /auction-var-details : Create a new auctionVarDetails.
     *
     * @param auctionVarDetails the auctionVarDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auctionVarDetails, or with status 400 (Bad Request) if the auctionVarDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auction-var-details")
    @Timed
    public ResponseEntity<AuctionVarDetails> createAuctionVarDetails(@Valid @RequestBody AuctionVarDetails auctionVarDetails) throws URISyntaxException {
        log.debug("REST request to save AuctionVarDetails : {}", auctionVarDetails);
        if (auctionVarDetails.getId() != null) {
            throw new BadRequestAlertException("A new auctionVarDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuctionVarDetails result = auctionVarDetailsRepository.save(auctionVarDetails);
        return ResponseEntity.created(new URI("/api/auction-var-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auction-var-details : Updates an existing auctionVarDetails.
     *
     * @param auctionVarDetails the auctionVarDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auctionVarDetails,
     * or with status 400 (Bad Request) if the auctionVarDetails is not valid,
     * or with status 500 (Internal Server Error) if the auctionVarDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auction-var-details")
    @Timed
    public ResponseEntity<AuctionVarDetails> updateAuctionVarDetails(@Valid @RequestBody AuctionVarDetails auctionVarDetails) throws URISyntaxException {
        log.debug("REST request to update AuctionVarDetails : {}", auctionVarDetails);
        if (auctionVarDetails.getId() == null) {
            return createAuctionVarDetails(auctionVarDetails);
        }
        AuctionVarDetails result = auctionVarDetailsRepository.save(auctionVarDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auctionVarDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auction-var-details : get all the auctionVarDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auctionVarDetails in body
     */
    @GetMapping("/auction-var-details")
    @Timed
    public List<AuctionVarDetails> getAllAuctionVarDetails() {
        log.debug("REST request to get all AuctionVarDetails");
        return auctionVarDetailsRepository.findAll();
        }

    /**
     * GET  /auction-var-details/:id : get the "id" auctionVarDetails.
     *
     * @param id the id of the auctionVarDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionVarDetails, or with status 404 (Not Found)
     */
    @GetMapping("/auction-var-details/{id}")
    @Timed
    public List<AuctionVarDetailsBean> getAuctionVarDetails(@PathVariable Long id) throws IllegalAccessException, InvocationTargetException {
        log.debug("REST request to get AuctionVarDetails : {}", id);
        List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotation(id);
        List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(id);
        Map<Long, String> variantMap = new HashMap<Long, String>();
        for(RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants){
            variantMap.put(rnsQuotationVariant.getId(), rnsQuotationVariant.getTitle().replace("Variant", "Lot"));
        }
        List<AuctionVarDetailsBean> auctionVarDetailsBeans = new ArrayList<AuctionVarDetailsBean>();

        if(auctionVarDetailsList==null){} else{
            for(AuctionVarDetails auctionVarDetails : auctionVarDetailsList){
                AuctionVarDetailsBean auctionVarDetailsBean = new AuctionVarDetailsBean();
                BeanUtils.copyProperties(auctionVarDetailsBean, auctionVarDetails);
                auctionVarDetailsBean.setLabelName(variantMap.get(auctionVarDetailsBean.getVariantId()));
                auctionVarDetailsBeans.add(auctionVarDetailsBean);
            }
        }
        return auctionVarDetailsBeans;
    }

    /**
     * GET  /auction-var-details/:id : get the "id" auctionVarDetails.
     *
     * @param id the id of the auctionVarDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionVarDetails, or with status 404 (Not Found)
     */
    @GetMapping("/auction-var-details-by-variant/{id}")
    @Timed
    public AuctionVarDetailsBean getAuctionVarDetailsByVariantId(@PathVariable Long id) throws IllegalAccessException, InvocationTargetException {
        log.debug("REST request to get AuctionVarDetails : {}", id);
        AuctionVarDetails auctionVarDetails = auctionVarDetailsRepository.getAuctionVarDetailsByVariant(id);
        RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(id).orElse(null);

        AuctionVarDetailsBean auctionVarDetailsBean = new AuctionVarDetailsBean();
        BeanUtils.copyProperties(auctionVarDetailsBean, auctionVarDetails);
        auctionVarDetailsBean.setLabelName(rnsQuotationVariant.getTitle().replace("Variant", "Lot"));
        return auctionVarDetailsBean;
    }

    /**
     * DELETE  /auction-var-details/:id : delete the "id" auctionVarDetails.
     *
     * @param id the id of the auctionVarDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auction-var-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuctionVarDetails(@PathVariable Long id) {
        log.debug("REST request to delete AuctionVarDetails : {}", id);
        auctionVarDetailsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/auction-var-details-quotation")
    @Timed
    public ResponseEntity<AuctionVarDetails> createAuctionPaused(@RequestBody AuctionVarDetailsBean auctionVarDetailsBean) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        log.debug("REST request to save VndrPrice : {}");
        //Old Time
        AuctionVarDetails auctionVarDetails1 = auctionVarDetailsRepository.findById(auctionVarDetailsBean.getId()).orElse(null);
        Duration betweenCurrentOld = Duration.between(auctionVarDetails1.getLotStartTime(), auctionVarDetails1.getLotEndTime());
        Long minutesDifferenceOld = betweenCurrentOld.toMinutes();

        if(auctionVarDetails1.getLotEndTime().isBefore(Instant.now())) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, auctionVarDetails1.getId().toString(),"Sorry! The lot has closed."))
                .body(auctionVarDetails1);
        }

        // New Time
        Duration betweenCurrent = Duration.between(auctionVarDetailsBean.getLotStartTime(), auctionVarDetailsBean.getLotEndTime());
        Long minutesDifference = betweenCurrent.toMinutes();

        Long differenceMinute = minutesDifference - minutesDifferenceOld;

        AuctionVarDetails auctionVarDetails = new AuctionVarDetails();
        BeanUtils.copyProperties(auctionVarDetails, auctionVarDetailsBean);
        AuctionVarDetails result = auctionVarDetailsRepository.save(auctionVarDetails);
        if (result != null) {
            List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotationAndVariant(result.getQuotationId(), result.getVariantId());
            for (AuctionVarDetails auctionVarDetailsloop : auctionVarDetailsList) {
                if (result.getVariantId().longValue() == auctionVarDetailsloop.getVariantId().longValue()) {
                } else {
                    Instant lotStartTime = auctionVarDetailsloop.getLotStartTime();
                    lotStartTime = lotStartTime.plus(differenceMinute, ChronoUnit.MINUTES);
                    auctionVarDetailsloop.setLotStartTime(lotStartTime);

                    Instant lotEndTime = auctionVarDetailsloop.getLotEndTime();
                    lotEndTime = lotEndTime.plus(differenceMinute, ChronoUnit.MINUTES);
                    auctionVarDetailsloop.setLotEndTime(lotEndTime);

                    auctionVarDetailsRepository.save(auctionVarDetailsloop);
                }
            }
            if(auctionVarDetailsBean.getType() != null && auctionVarDetailsBean.getType().equalsIgnoreCase("E") && auctionVarDetailsBean.getLotMinutes() !=null && auctionVarDetailsBean.getLotMinutes()>0) {
                RnsQuotation rnsQuotation = rnsQuotationRepository.findById(result.getQuotationId()).orElse(null);
                RnsQuotationVariant variant = rnsQuotationVariantRepository.findById(result.getVariantId()).orElse(null);
                User currentUser = userRepository.findByLogin(getCurrentUserLogin());
                List<RnsQuotationVendors> rnsQuotationVendorss = rnsQuotationVendorsRepository.findByAcceptedVendorsByQuotationId(result.getQuotationId());
                List<String> vendors = new ArrayList<String>();
                for (RnsQuotationVendors rnsQuotationVendors : rnsQuotationVendorss) {
                    if (vendors.contains(rnsQuotationVendors.getVendorCode())) {
                    } else {
                        vendors.add(rnsQuotationVendors.getVendorCode());
                    }
                }
                try {
                    String emailTemplateCode = "biddingTimeExtend";
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
                            //String PATH="E:\\Project\\rns\\target\\classes\\mails";
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
                        }
                        if (vendors != null && vendors.size() > 0) {
                            List<User> users = userRepository.findAllByLogins(vendors);
                            for (User user : users) {
                                Locale locale = Locale.forLanguageTag(user.getLangKey());
                                Context context = new Context(locale);
                                context.setVariable("vendor", user);
                                context.setVariable("rnsQuotation", rnsQuotation);
                                context.setVariable("applicationProperties", applicationProperties);
                                context.setVariable("minutes", auctionVarDetailsBean.getLotMinutes());
                                context.setVariable("lotTitle", variant.getTitle().replace("Variant", "Lot"));
                                context.setVariable("user", currentUser);
                                String content = templateEngine.process("mail/" + emailTemplateCode, context);
                                subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);
                                //mailService.sendEmail("sudeep@fulcrumconsultancy.com", subject, content, false, true);
                                //mailService.sendEmail("sheetal@fulcrumconsultancy.com", subject, content, false, true);
                                if (emailTemplate.isEmail() != null && emailTemplate.isEmail() == true) {
                                    try {
                                        //mailService.sendEmail("vivek@fulcrumconsultancy.com", subject, content, false, true);
                                        mailService.sendEmail(user.getEmail(), subject, content, false, true);
                                    } catch (Exception e) {
                                    }
                                }
                                //Notification
                                if (emailTemplate.isNotification() != null && emailTemplate.isNotification() == true) {
                                    Message messageNotifier = new Message();
                                    messageNotifier.setFromMail(getCurrentUserLogin());
                                    messageNotifier.setToMail(user.getLogin());
                                    messageNotifier.setSubject(subject);
                                    messageNotifier.setQuotationId(rnsQuotation.getId());
                                    messageNotifier.setMessageBody(content);
                                    if (messageNotifier.getCreatedBy() == null) {
                                        messageNotifier.setCreatedBy(getCurrentUserLogin());
                                    }
                                    messageNotifier.setCreatedDate(Instant.now());
                                    String mailBody = messageNotifier.getMessageBody();
                                    messageNotifier.setMessageBody(" ");
                                    Message message1 = messageRepository.save(messageNotifier);
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
                                        messageBody.setCreatedBy(getCurrentUserLogin());
                                        messageBody.setCreatedDate(LocalDate.now());
                                        messageBodyRepository.save(messageBody);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("RnsQuotationResource updateAuction()" + e.getMessage());
                }
            } else if(auctionVarDetailsBean.getType() != null && auctionVarDetailsBean.getType().equalsIgnoreCase("R") && auctionVarDetailsBean.getLotMinutes() !=null && auctionVarDetailsBean.getLotMinutes()>0) {
                RnsQuotation rnsQuotation = rnsQuotationRepository.findById(result.getQuotationId()).orElse(null);
                RnsQuotationVariant variant = rnsQuotationVariantRepository.findById(result.getVariantId()).orElse(null);
                User currentUser = userRepository.findByLogin(getCurrentUserLogin());
                List<RnsQuotationVendors> rnsQuotationVendorss = rnsQuotationVendorsRepository.findByAcceptedVendorsByQuotationId(result.getQuotationId());
                List<String> vendors = new ArrayList<String>();
                for (RnsQuotationVendors rnsQuotationVendors : rnsQuotationVendorss) {
                    if (vendors.contains(rnsQuotationVendors.getVendorCode())) {
                    } else {
                        vendors.add(rnsQuotationVendors.getVendorCode());
                    }
                }
                try {
                    String emailTemplateCode = "biddingTimeReduce";
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
                            //String PATH="E:\\Project\\rns\\target\\classes\\mails";
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
                        }
                        if (vendors != null && vendors.size() > 0) {
                            List<User> users = userRepository.findAllByLogins(vendors);
                            for (User user : users) {
                                Locale locale = Locale.forLanguageTag(user.getLangKey());
                                Context context = new Context(locale);
                                context.setVariable("vendor", user);
                                context.setVariable("rnsQuotation", rnsQuotation);
                                context.setVariable("applicationProperties", applicationProperties);
                                context.setVariable("lotTitle", variant.getTitle().replace("Variant", "Lot"));
                                context.setVariable("minutes", auctionVarDetailsBean.getLotMinutes());
                                context.setVariable("user", currentUser);
                                String content = templateEngine.process("mail/" + emailTemplateCode, context);
                                subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);
                                //mailService.sendEmail("sudeep@fulcrumconsultancy.com", subject, content, false, true);
                                //mailService.sendEmail("sheetal@fulcrumconsultancy.com", subject, content, false, true);
                                if (emailTemplate.isEmail() != null && emailTemplate.isEmail() == true) {
                                    try {
                                        //mailService.sendEmail("vivek@fulcrumconsultancy.com", subject, content, false, true);
                                        mailService.sendEmail(user.getEmail(), subject, content, false, true);
                                    } catch (Exception e) {
                                    }
                                }
                                //Notification
                                if (emailTemplate.isNotification() != null && emailTemplate.isNotification() == true) {
                                    Message messageNotifier = new Message();
                                    messageNotifier.setFromMail(getCurrentUserLogin());
                                    messageNotifier.setToMail(user.getLogin());
                                    messageNotifier.setSubject(subject);
                                    messageNotifier.setQuotationId(rnsQuotation.getId());
                                    messageNotifier.setMessageBody(content);
                                    if (messageNotifier.getCreatedBy() == null) {
                                        messageNotifier.setCreatedBy(getCurrentUserLogin());
                                    }
                                    messageNotifier.setCreatedDate(Instant.now());
                                    String mailBody = messageNotifier.getMessageBody();
                                    messageNotifier.setMessageBody(" ");
                                    Message message1 = messageRepository.save(messageNotifier);
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
                                        messageBody.setCreatedBy(getCurrentUserLogin());
                                        messageBody.setCreatedDate(LocalDate.now());
                                        messageBodyRepository.save(messageBody);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("RnsQuotationResource updateAuction()" + e.getMessage());
                }
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
