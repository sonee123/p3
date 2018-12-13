package rightchamps.scheduler;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.*;
import rightchamps.repository.*;
import rightchamps.service.MailService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@EnableScheduling
public class BiddingCloseScheduleTask {

    @Autowired
    private EntityManager entityManager;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    @Inject
    private RnsQuotationVendorsRepository rnsQuotationVendorsRepository;

    @Inject
    private AuctionPauseDetailsRepository auctionPauseDetailsRepository;

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

    @Scheduled(cron = "0 0/5 * * * ?")
    public void publishUpdates(){
        Query query = entityManager.createNativeQuery("SELECT QUOTATION_ID FROM AUCTION_VAR_DETAILS WHERE VARIANT_ID IN(SELECT MAX(B.VARIANT_ID) FROM RNS_QUOTATION A, AUCTION_VAR_DETAILS B  WHERE A.ID = B.QUOTATION_ID"
            +" AND (A.AUCTION_CLOSE is null or A.AUCTION_CLOSE=0)"
            +" GROUP BY B.QUOTATION_ID) AND lot_end_time<=SYSDATE");

        List<Object> objectList = query.getResultList();
        if(objectList!=null && objectList.size()>0){
            for(Object o : objectList){
                AuctionPauseDetails auctionPauseDetails = auctionPauseDetailsRepository.findAuctionPauseDetailsByQuotationId(Long.parseLong(o.toString()));
                if(auctionPauseDetails != null) {
                } else {
                    RnsQuotation rnsQuotation = rnsQuotationRepository.findById(Long.parseLong(o.toString())).orElse(null);
                    if (rnsQuotation != null) {
                        rnsQuotation.setAuctionClose(true);
                        RnsQuotation result = rnsQuotationRepository.save(rnsQuotation);
                        // Bidding closed Mail
                        if(result!=null){
                            User currentUser = result.getUser();
                            try {
                                List<RnsQuotationVendors> rnsQuotationVendorss = rnsQuotationVendorsRepository.findByAcceptedVendorsByQuotationId(result.getId());
                                List<String> vendors = new ArrayList<String>();
                                for (RnsQuotationVendors rnsQuotationVendors : rnsQuotationVendorss) {
                                    if (vendors.contains(rnsQuotationVendors.getVendorCode())) {
                                    } else {
                                        vendors.add(rnsQuotationVendors.getVendorCode());
                                    }
                                }
                                String emailTemplateCode = "biddingClosed";
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
                                            context.setVariable("rnsQuotation", result);
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
                                                message.setQuotationId(result.getId());
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
                        System.out.println("Bidding has been closed for quotation# " + result.getId() + "");
                    }
                }
            }
        }
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
