package rightchamps.web.websocket;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.*;

import rightchamps.modal.VariantOverTime;
import rightchamps.modal.VndrPriceCustom;

import rightchamps.modal.ValueComparator;
import rightchamps.modal.VndrRank;
import rightchamps.repository.*;
import rightchamps.service.MailService;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


/**
 * REST controller for managing VndrPrice.
 */
@Controller
@RequestMapping("/api")
public class VndrPriceWS {

    private static final String ENTITY_NAME = "vndrPrice";
    private final Logger log = LoggerFactory.getLogger(VndrPriceWS.class);
    private final VndrPriceRepository vndrPriceRepository;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private AuctionVrntRepository auctionVrntRepository;

    @Inject
    private AuctionVarDetailsRepository auctionVarDetailsRepository;

    @Inject
    private VndrPriceDeleteRepository vndrPriceDeleteRepository;

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


    public VndrPriceWS(VndrPriceRepository vndrPriceRepository) {
        this.vndrPriceRepository = vndrPriceRepository;
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

    /**
     * POST  /vndr-prices : Create a new vndrPrice.
     *
     * @param vndrPrice the vndrPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vndrPrice, or with status 400 (Bad Request) if the vndrPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @MessageMapping("/vndr-prices/{variantId}")
    @SendTo("/topic/vndr-prices/get-top-price/{variantId}")
    @Timed
    public ResponseEntity<List<VndrRank>> createVndrPrice(@DestinationVariable Long variantId, @RequestBody VndrPrice vndrPrice) throws URISyntaxException {
        log.debug("REST request to save VndrPrice : {}");
        if (vndrPrice.getId() != null) {
            vndrPrice.setId(null);
        }
        String vendorCode = getCurrentUserLogin();
        String message = null;
        Float MyPrice = 0.0f;
        if (vndrPrice.getPriceOne() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceOne();
        }
        if (vndrPrice.getPriceTwo() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceTwo();
        }
        if (vndrPrice.getPriceThree() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceThree();
        }
        if (vndrPrice.getPriceFour() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceFour();
        }
        if (vndrPrice.getPriceFive() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceFive();
        }
        if (vndrPrice.getPriceSix() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceSix();
        }
        if (vndrPrice.getPriceSeven() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceSeven();
        }
        if (vndrPrice.getPriceEight() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceEight();
        }
        if (vndrPrice.getPriceNine() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceNine();
        }
        if (vndrPrice.getPriceTen() != null) {
            MyPrice = MyPrice + vndrPrice.getPriceTen();
        }
        if (MyPrice == 0) {
            message = "Bidding value must be greator than 0.";
            List<VndrRank> vprice = getHighestPrice(variantId, message, vendorCode);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, variantId.toString()))
                .body(vprice);
        }
        RnsQuotation rnsQuotation = rnsQuotationVariantRepository.getRnsQuotationByRnsQuotationVariant(vndrPrice.getVariant().getId());
        RnsQuotationVariant variant = rnsQuotationVariantRepository.findById(vndrPrice.getVariant().getId()).orElse(null);
        boolean errorBid = false;
        if (variant.getBidStartPrice() != null && variant.getBidStartPrice() > 0) {
            if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().equals("AUCTION")) {
                if (MyPrice < variant.getBidStartPrice()) {
                    message = "My bid value must not be less than "+variant.getBidStartPrice();
                    errorBid = true;
                }
            } else if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().equals("REVERSE_AUCTION")) {
                if (MyPrice > variant.getBidStartPrice()) {
                    message = "My bid value must not be greator than "+variant.getBidStartPrice();
                    errorBid = true;
                }
            }
        }

        if( errorBid == true) {
            List<VndrRank> vprice = getHighestPrice(vndrPrice.getVariant().getId(), message, vndrPrice.getVendorCode());
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, variantId.toString()))
                .body(vprice);
        }

        if (vndrPrice != null && vndrPrice.getVendorCode() != null) {
            boolean error = false;
            VndrPrice vndrPrice1 = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(variantId, vndrPrice.getVendorCode());
            if (vndrPrice1 != null) {
                Float myPricePrev = 0.0f;
                if (vndrPrice.getPriceOne() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceOne();
                }
                if (vndrPrice1.getPriceTwo() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceTwo();
                }
                if (vndrPrice1.getPriceThree() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceThree();
                }
                if (vndrPrice1.getPriceFour() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceFour();
                }
                if (vndrPrice1.getPriceFive() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceFive();
                }
                if (vndrPrice1.getPriceSix() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceSix();
                }
                if (vndrPrice1.getPriceSeven() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceSeven();
                }
                if (vndrPrice1.getPriceEight() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceEight();
                }
                if (vndrPrice1.getPriceNine() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceNine();
                }
                if (vndrPrice1.getPriceTen() != null) {
                    myPricePrev = myPricePrev + vndrPrice1.getPriceTen();
                }
                if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().equals("AUCTION")) {
                    if (MyPrice <= myPricePrev) {
                        message = "My bid value must not be less than My Last bid value";
                        error = true;
                    }
                } else if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().equals("REVERSE_AUCTION")) {
                    if (MyPrice >= myPricePrev) {
                        message = "My bid value must not be greator than My Last bid value";
                        error = true;
                    }
                }
                if( error == true) {
                    List<VndrRank> vprice = getHighestPrice(vndrPrice1.getVariant().getId(), message, vndrPrice1.getVendorCode());
                    return ResponseEntity.ok()
                        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, variantId.toString()))
                        .body(vprice);
                }
            }
        }
        vndrPrice.setCreatedOn(Instant.now());
        Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotation.getId());
        VndrPrice result = null;
        boolean allowBid = true;
        if (auction.getAllowTieBids() != null && auction.getAllowTieBids() == true) {
            result = vndrPriceRepository.save(vndrPrice);
            message = "Bid Save Successfully!!!";
        } else {
            List<VndrPrice> vndrPriceList = vndrPriceRepository.getAllbyVariant(variantId);
            if (vndrPriceList.size() > 0) {
                for (VndrPrice vndrPrice1 : vndrPriceList) {
                    Float TotalPrice = 0.0f;
                    if (vndrPrice1.getPriceOne() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceOne();
                    }
                    if (vndrPrice1.getPriceTwo() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceTwo();
                    }
                    if (vndrPrice1.getPriceThree() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceThree();
                    }
                    if (vndrPrice1.getPriceFour() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceFour();
                    }
                    if (vndrPrice1.getPriceFive() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceFive();
                    }
                    if (vndrPrice1.getPriceSix() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceSix();
                    }
                    if (vndrPrice1.getPriceSeven() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceSeven();
                    }
                    if (vndrPrice1.getPriceEight() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceEight();
                    }
                    if (vndrPrice1.getPriceNine() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceNine();
                    }
                    if (vndrPrice1.getPriceTen() != null) {
                        TotalPrice = TotalPrice + vndrPrice1.getPriceTen();
                    }
                    if (Float.compare(MyPrice, TotalPrice) == 0) {
                        allowBid = false;
                        message = "Tie Bid not allowed!!!";
                        break;
                    }
                }
            }
            if (allowBid == true) {
                result = vndrPriceRepository.save(vndrPrice);
                message = "Bid Save Successfully!!!";
            }
        }


        AuctionVarDetails auctionVarDetails = auctionVarDetailsRepository.getAuctionVarDetailsByVariant(vndrPrice.getVariant().getId());

        Instant auctionStartDateTime = auctionVarDetails.getLotStartTime();
        Instant auctionEndDateTime = auctionVarDetails.getLotEndTime();
        Instant currentTime = Instant.now();

        Duration betweenCurrent = Duration.between(auctionStartDateTime, currentTime);

        Duration betweenFinal = Duration.between(auctionStartDateTime, auctionEndDateTime);

        long minutesDifference = betweenFinal.toMinutes() - betweenCurrent.toMinutes();

        Integer bidRankOverTime = auction.getBidRankOverTime();
        Integer overtimePeriod = (Integer) auction.getOvertimePeriod();
        float overtimePeriodFloat = (float) overtimePeriod;
        Integer auctionBidTimeForOvertime = auction.getBidTimeForOvertimeStart();

        Integer position = this.getPosition(vndrPrice.getVariant().getId(), vendorCode);

        if (position <= bidRankOverTime && allowBid) {
            if (minutesDifference > 0 && minutesDifference <= auctionBidTimeForOvertime) {

                if (variant.getOverTime() != null && variant.getOverTime().intValue() > 0) {
                    overtimePeriodFloat = overtimePeriodFloat + variant.getOverTime();
                }
                variant.setOverTime(overtimePeriodFloat);
                Integer ctr = updateAuctionVarDetails(variant.getQuotation().getId(), vndrPrice.getVariant().getId(), overtimePeriod);
                if(ctr>0){
                    RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.save(variant);
                }
            }
        }

        if (result != null && result.isSurrogate() == true) {
            User user = userRepository.findByLogin(result.getVendorCode());
            User currentUser = userRepository.findByLogin(getCurrentUserLogin());
            try {
                String emailTemplateCode = "biddingSurrogate";
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
                    if (user != null) {
                        Locale locale = Locale.forLanguageTag(user.getLangKey());
                        Context context = new Context(locale);
                        context.setVariable("vendor", user);
                        context.setVariable("rnsQuotation", rnsQuotation);
                        context.setVariable("applicationProperties", applicationProperties);
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
            } catch (Exception e) {
                System.out.println("RnsQuotationResource updateAuction()" + e.getMessage());
            }
        }

        List<VndrRank> vprice = getHighestPrice(vndrPrice.getVariant().getId(), message, vndrPrice.getVendorCode());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vndrPrice.getVariant().getId().toString()))
            .body(vprice);
    }

    /**
     * POST  /vndr-prices : Create a new vndrPrice.
     *
     * @param vndrPrice the vndrPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vndrPrice, or with status 400 (Bad Request) if the vndrPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @MessageMapping("/vndr-prices-delete/{variantId}")
    @SendTo("/topic/vndr-prices/get-top-price/{variantId}")
    @Timed
    public ResponseEntity<List<VndrRank>> deleteVndrPrice(@DestinationVariable Long variantId, @RequestBody VndrPrice vndrPrice) throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        log.debug("REST request to save VndrPrice : {}");
        String message = null;
        VndrPrice vndrPrice1 = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(vndrPrice.getVariant().getId(), vndrPrice.getVendorCode());
        VndrPriceDelete vndrPriceDelete = new VndrPriceDelete();
        BeanUtils.copyProperties(vndrPriceDelete, vndrPrice1);
        vndrPriceDelete.setDeletedBy(getCurrentUserLogin());
        vndrPriceDelete.setDeletedDate(Instant.now());
        VndrPriceDelete result = vndrPriceDeleteRepository.save(vndrPriceDelete);
        if (result != null) {
            vndrPriceRepository.deleteByVariantandVendorCodeLastPrice(vndrPrice.getVariant().getId(), vndrPrice.getVendorCode());
            message = "Bid Delete Successfully!!!";

            User user = userRepository.findByLogin(result.getVendorCode());
            User currentUser = userRepository.findByLogin(getCurrentUserLogin());
            try {
                String emailTemplateCode = "biddingDeleted";
                EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateCode(emailTemplateCode);
                List<EmailTemplateBody> emailTemplateBodies = emailTemplateBodyRepository.findAllByTemplateCode(emailTemplateCode);
                RnsQuotation rnsQuotation = rnsQuotationVariantRepository.getRnsQuotationByRnsQuotationVariant(result.getVariant().getId());
                RnsQuotationVariant variant = rnsQuotationVariantRepository.findById(result.getVariant().getId()).orElse(null);
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
                    if (user != null) {
                        Locale locale = Locale.forLanguageTag(user.getLangKey());
                        Context context = new Context(locale);
                        context.setVariable("vendor", user);
                        context.setVariable("rnsQuotation", rnsQuotation);
                        context.setVariable("applicationProperties", applicationProperties);
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
            } catch (Exception e) {
                System.out.println("RnsQuotationResource updateAuction()" + e.getMessage());
            }
        }

        List<VndrRank> vprice = getHighestPrice(vndrPrice.getVariant().getId(), message, vndrPrice.getVendorCode());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vndrPrice.getVariant().getId().toString()))
            .body(vprice);
    }

    public Integer getAuctionRank(@PathVariable Long variantId) {
        String vendorCode = getCurrentUserLogin();
        Integer position = this.getPosition(variantId, vendorCode);
        return position;
    }

    private Integer updateAuctionVarDetails(long quotationId, long variantId, Integer overtimePeriod){
        Integer ctr = 0;
        if(overtimePeriod!=null && overtimePeriod>0) {
            Long overtimePeriodLong = overtimePeriod.longValue();
            List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotationAndVariant(quotationId, variantId);
            for (AuctionVarDetails auctionVarDetails : auctionVarDetailsList) {
                ++ctr;
                if (variantId == auctionVarDetails.getVariantId().longValue()) {
                    Instant lotEndTime = auctionVarDetails.getLotEndTime();
                    lotEndTime = lotEndTime.plus(overtimePeriod, ChronoUnit.MINUTES);
                    auctionVarDetails.setLotEndTime(lotEndTime);
                    if (auctionVarDetails.getOvertimeMinutes() != null && auctionVarDetails.getOvertimeMinutes() > 0) {
                        overtimePeriodLong = overtimePeriodLong + auctionVarDetails.getOvertimeMinutes();
                    }
                    auctionVarDetails.setOvertimeMinutes(overtimePeriodLong);
                    auctionVarDetailsRepository.save(auctionVarDetails);
                } else{
                    Instant lotStartTime = auctionVarDetails.getLotStartTime();
                    lotStartTime = lotStartTime.plus(overtimePeriod, ChronoUnit.MINUTES);
                    auctionVarDetails.setLotStartTime(lotStartTime);

                    Instant lotEndTime = auctionVarDetails.getLotEndTime();
                    lotEndTime = lotEndTime.plus(overtimePeriod, ChronoUnit.MINUTES);
                    auctionVarDetails.setLotEndTime(lotEndTime);
                    auctionVarDetailsRepository.save(auctionVarDetails);
                }
            }
        }
        return ctr;
    }

    /**
     * GET  /vndr-prices/:id : get the "id" vndrPrice.
     *
     * @param @id the id of the vndrPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vndrPrice, or with status 404 (Not Found)
     */
    public List<VndrRank> getHighestPrice(Long variantId, String message, String cvendorCode) {
        List<VndrRank> vndrRank = new ArrayList<VndrRank>();
        log.debug("REST request to get VndrPrice : {}", variantId);
        VndrPrice vPrice = new VndrPrice();
        Integer position = 0;
        Float myPrice = 1.0f;
        boolean myPriceExist = false;
        Float highestPrice = 1.0f;

        RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(variantId).orElse(null);
        //Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotationVariant.getQuotation().getId());
        RnsQuotation rnsQuotation = rnsQuotationVariant.getQuotation();

        List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(variantId);
        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(variantId);

        Map<String, Float> vendorMap = new HashMap<String, Float>();
        Map<String, Instant> vendorCreatedOnMap = new HashMap<String, Instant>();
        Map<String, Integer> vendorRevisionMap = new HashMap<String, Integer>();
        Map<String, Long> vendoridMap = new HashMap<String, Long>();
        Map<String, Integer> vendorTempMap = new HashMap<String, Integer>();
        for (VndrPrice price : vndrPrice) {
            if (vendorMap.containsKey(price.getVendorCode())) {
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }

                Integer ctrVendor = vendorRevisionMap.get(price.getVendorCode());
                ++ctrVendor;
                vendorRevisionMap.put(price.getVendorCode(), ctrVendor);

                if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")) {
                    if (totalPrice > vendorMap.get(price.getVendorCode())) {
                        vendorMap.put(price.getVendorCode(), totalPrice);
                        vendorCreatedOnMap.put(price.getVendorCode(), price.getCreatedOn());
                        vendoridMap.put(price.getVendorCode(), price.getId());
                    }
                } else if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                    if (totalPrice < vendorMap.get(price.getVendorCode())) {
                        vendorMap.put(price.getVendorCode(), totalPrice);
                        vendorCreatedOnMap.put(price.getVendorCode(), price.getCreatedOn());
                        vendoridMap.put(price.getVendorCode(), price.getId());
                    }
                }
            } else {
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                vendorMap.put(price.getVendorCode(), totalPrice);
                vendoridMap.put(price.getVendorCode(), price.getId());
                vendorCreatedOnMap.put(price.getVendorCode(), price.getCreatedOn());
                vendorRevisionMap.put(price.getVendorCode(), 1);
                vendorTempMap.put(price.getVendorCode(), 1);
            }
        }
        TreeMap<String, Float> sorted_map = null;
        if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")) {
            ValueComparator bvc = new ValueComparator(vendorMap, true);
            sorted_map = new TreeMap<String, Float>(bvc);
            sorted_map.putAll(vendorMap);
        } else if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
            ValueComparator bvc = new ValueComparator(vendorMap, false);
            sorted_map = new TreeMap<String, Float>(bvc);
            sorted_map.putAll(vendorMap);
        }

        boolean positionExist = false;
        long ctr = 0;

        float tothighestPrice = 0.0f;
        for (Map.Entry<String, Float> entry : sorted_map.entrySet()) {
            float rate = entry.getValue();
            String key = entry.getKey();
            Instant dt = vendorCreatedOnMap.get(key);
            int rev = vendorRevisionMap.get(key);
            long id = vendoridMap.get(key);
            if (ctr == 0) {
                tothighestPrice = rate;
                ++ctr;
            } else {
                ++ctr;
            }
            vndrRank.add(new VndrRank(id, key, ctr, rate, dt, rev, tothighestPrice, message, cvendorCode));
        }
        return vndrRank;
    }

    public Integer getPosition(Long variantId, String vendorCode) {
        log.debug("REST request to get VndrPrice : {}", variantId);
        VndrPrice vPrice = new VndrPrice();
        Integer position = 0;
        Float myPrice = 1.0f;
        boolean myPriceExist = false;
        Float highestPrice = 1.0f;

        RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(variantId).orElse(null);
        //Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotationVariant.getQuotation().getId());
        RnsQuotation rnsQuotation = rnsQuotationVariant.getQuotation();

        List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(variantId);
        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(variantId);

        Map<String, Float> vendorMap = new HashMap<String, Float>();
        for (VndrPrice price : vndrPrice) {
            if (vendorMap.containsKey(price.getVendorCode())) {
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")) {
                    if (totalPrice > vendorMap.get(price.getVendorCode()))
                        vendorMap.put(price.getVendorCode(), totalPrice);
                } else if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                    if (totalPrice < vendorMap.get(price.getVendorCode()))
                        vendorMap.put(price.getVendorCode(), totalPrice);
                }
            } else {
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                vendorMap.put(price.getVendorCode(), totalPrice);
            }
        }
        TreeMap<String, Float> sorted_map = null;
        if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")) {
            ValueComparator bvc = new ValueComparator(vendorMap, true);
            sorted_map = new TreeMap<String, Float>(bvc);
            sorted_map.putAll(vendorMap);
        } else if (rnsQuotation.getEventType() != null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
            ValueComparator bvc = new ValueComparator(vendorMap, false);
            sorted_map = new TreeMap<String, Float>(bvc);
            sorted_map.putAll(vendorMap);
        }
        boolean positionExist = false;
        for (String key : sorted_map.keySet()) {
            ++position;
            if (key.equalsIgnoreCase(vendorCode)) {
                positionExist = true;
                break;
            }
        }

        if (positionExist == false)
            position = 0;
        return position;
    }
}
