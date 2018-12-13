package rightchamps.web.websocket;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.*;
import rightchamps.modal.AuctionPauseDetailsBean;
import rightchamps.repository.*;
import rightchamps.service.MailService;
import rightchamps.web.rest.util.HeaderUtil;

import javax.inject.Inject;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * REST controller for managing VndrPrice.
 */
@Controller
@RequestMapping("/api")
public class AuctionPausedWS {
    private static final String ENTITY_NAME = "auctionPauseDetails";
    private final Logger log = LoggerFactory.getLogger(AuctionPausedWS.class);

    @Inject
    private AuctionPauseDetailsRepository auctionPauseDetailsRepository;

    @Inject
    private AuctionVarDetailsRepository auctionVarDetailsRepository;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    @Inject
    private RnsQuotationVendorsRepository rnsQuotationVendorsRepository;

    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    @Inject
    private EmailTemplateBodyRepository emailTemplateBodyRepository;

    @Inject
    private UserRepository userRepository;

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
    /**
     * POST  /vndr-prices : Create a new vndrPrice.
     *
     * @param //vndrPrice the vndrPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vndrPrice, or with status 400 (Bad Request) if the vndrPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @MessageMapping("/auction-paused-ws/{quotationId}")
    @SendTo("/topic/auction-paused-ws/{quotationId}")
    @Timed
    public ResponseEntity<AuctionPauseDetails> createAuctionPaused(@DestinationVariable Long quotationId, @RequestBody AuctionPauseDetailsBean auctionPauseDetailsBean) throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        log.debug("REST request to save VndrPrice : {}");
        AuctionPauseDetails auctionPauseDetails = new AuctionPauseDetails();
        BeanUtils.copyProperties(auctionPauseDetails, auctionPauseDetailsBean);
        if (auctionPauseDetails.getId() != null) {
            auctionPauseDetails = auctionPauseDetailsRepository.findById(auctionPauseDetails.getId()).orElse(null);
            Instant instant = Instant.now();
            auctionPauseDetails.setPauseEndDate(instant);
            auctionPauseDetails.setUpdatedDate(instant);
            auctionPauseDetails.setUpdatedBy(getCurrentUserLogin());
            AuctionPauseDetails result = auctionPauseDetailsRepository.save(auctionPauseDetails);
            if(result!=null && auctionPauseDetailsBean.getTimeAllow() == null){
                Duration betweenCurrent = Duration.between(result.getPauseStartDate(), instant);
                Long minutesDifference = betweenCurrent.toMinutes();
                List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotationAndVariant(result.getQuotationId(), result.getVariantId());
                for (AuctionVarDetails auctionVarDetails : auctionVarDetailsList) {
                    if (result.getVariantId().longValue() == auctionVarDetails.getVariantId().longValue()) {
                        Instant lotEndTime = auctionVarDetails.getLotEndTime();
                        lotEndTime = lotEndTime.plus(minutesDifference, ChronoUnit.MINUTES);
                        auctionVarDetails.setLotEndTime(lotEndTime);
                        auctionVarDetailsRepository.save(auctionVarDetails);
                    } else {
                        Instant lotStartTime = auctionVarDetails.getLotStartTime();
                        lotStartTime = lotStartTime.plus(minutesDifference, ChronoUnit.MINUTES);
                        auctionVarDetails.setLotStartTime(lotStartTime);

                        Instant lotEndTime = auctionVarDetails.getLotEndTime();
                        lotEndTime = lotEndTime.plus(minutesDifference, ChronoUnit.MINUTES);
                        auctionVarDetails.setLotEndTime(lotEndTime);
                        auctionVarDetailsRepository.save(auctionVarDetails);
                    }
                }
            }
            // Bidding Resumed Mail
            if(result!=null){
                RnsQuotation rnsQuotation = rnsQuotationRepository.findById(result.getQuotationId()).orElse(null);
                User currentUser = userRepository.findByLogin(getCurrentUserLogin());
                try {
                    List<RnsQuotationVendors> rnsQuotationVendorss = rnsQuotationVendorsRepository.findByAcceptedVendorsByQuotationId(result.getQuotationId());
                    List<String> vendors = new ArrayList<String>();
                    for (RnsQuotationVendors rnsQuotationVendors : rnsQuotationVendorss) {
                        if (vendors.contains(rnsQuotationVendors.getVendorCode())) {
                        } else {
                            vendors.add(rnsQuotationVendors.getVendorCode());
                        }
                    }
                    String emailTemplateCode = "biddingResumed";
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
                                    Message message = new Message();
                                    message.setFromMail(getCurrentUserLogin());
                                    message.setToMail(user.getLogin());
                                    message.setSubject(subject);
                                    message.setQuotationId(rnsQuotation.getId());
                                    message.setMessageBody(content);
                                    if (message.getCreatedBy() == null) {
                                        message.setCreatedBy(getCurrentUserLogin());
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
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auctionPauseDetails.getId().toString()))
                .body(result);
        } else{
            auctionPauseDetails.setPauseStartDate(Instant.now());
            auctionPauseDetails.setCreatedDate(Instant.now());
            auctionPauseDetails.setCreatedBy(getCurrentUserLogin());
            AuctionPauseDetails result = auctionPauseDetailsRepository.save(auctionPauseDetails);

            // Bidding Paused Mail
            if(result!=null){
                RnsQuotation rnsQuotation = rnsQuotationRepository.findById(result.getQuotationId()).orElse(null);
                User currentUser = userRepository.findByLogin(getCurrentUserLogin());
                try {
                    List<RnsQuotationVendors> rnsQuotationVendorss = rnsQuotationVendorsRepository.findByAcceptedVendorsByQuotationId(result.getQuotationId());
                    List<String> vendors = new ArrayList<String>();
                    for (RnsQuotationVendors rnsQuotationVendors : rnsQuotationVendorss) {
                        if (vendors.contains(rnsQuotationVendors.getVendorCode())) {
                        } else {
                            vendors.add(rnsQuotationVendors.getVendorCode());
                        }
                    }
                    String emailTemplateCode = "biddingPaused";
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
                                    Message message = new Message();
                                    message.setFromMail(getCurrentUserLogin());
                                    message.setToMail(user.getLogin());
                                    message.setSubject(subject);
                                    message.setQuotationId(rnsQuotation.getId());
                                    message.setMessageBody(content);
                                    if (message.getCreatedBy() == null) {
                                        message.setCreatedBy(getCurrentUserLogin());
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
            return ResponseEntity.created(new URI("/api/auction-pause-details/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
    }

    @MessageMapping("/auction-revised-ws/{quotationId}")
    @SendTo("/topic/auction-paused-ws/{quotationId}")
    @Timed
    public ResponseEntity<AuctionPauseDetails> auctionTimeRevised(@DestinationVariable Long quotationId, @RequestBody AuctionPauseDetailsBean auctionPauseDetailsBean) throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotationId.toString()))
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
