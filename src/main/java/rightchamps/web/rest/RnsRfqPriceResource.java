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

import rightchamps.modal.RnsRfqPriceBean;
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
import javax.servlet.ServletContext;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * REST controller for managing RnsRfqPrice.
 */
@RestController
@RequestMapping("/api")
public class RnsRfqPriceResource {

    private final Logger log = LoggerFactory.getLogger(RnsRfqPriceResource.class);

    private static final String ENTITY_NAME = "Rfq Price";

    private final RnsRfqPriceRepository rnsRfqPriceRepository;

    @Inject
    private RnsQuotationVendorsRepository rnsQuotationVendorsRepository;

    @Inject
    private VndrPriceRepository vndrPriceRepository;

    @Inject
    private AuctionVrntRepository auctionVrntRepository;

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

    @Autowired
    private ServletContext servletContext;

    public RnsRfqPriceResource(RnsRfqPriceRepository rnsRfqPriceRepository) {
        this.rnsRfqPriceRepository = rnsRfqPriceRepository;
    }

    /**
     * POST  /rns-rfq-prices : Create a new rnsRfqPrice.
     *
     * //@param rnsRfqPrice the rnsRfqPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsRfqPrice, or with status 400 (Bad Request) if the rnsRfqPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-rfq-prices")
    @Timed
    public ResponseEntity<RnsRfqPrice> createRnsRfqPrice(@RequestBody RnsRfqPriceBean rnsRfqPriceBean) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        log.debug("REST request to save RnsRfqPrice : {}", rnsRfqPriceBean);
        /*if(rnsRfqPriceBean.getId()==null || (rnsRfqPriceBean.getId()!=null && rnsRfqPriceBean.getId()==0)){rnsRfqPriceBean.setId(null);}
        if (rnsRfqPriceBean.getId() != null) {
            throw new BadRequestAlertException("A new rnsRfqPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        RnsRfqPrice rnsRfqPrice = new RnsRfqPrice();
        BeanUtils.copyProperties(rnsRfqPrice, rnsRfqPriceBean);
        RnsRfqPrice rnsRfqPriceupdate = rnsRfqPriceRepository.getByVendorId(rnsRfqPrice.getVendorId());
        RnsRfqPrice result = null;
        RnsQuotationVendors rnsQuotationVendors = null;
        if(rnsRfqPriceupdate!=null){
            rnsRfqPrice.createdBy(getCurrentUserLogin());
            rnsRfqPrice.setCreatedDate(Instant.now());
            rnsRfqPrice.setId(rnsRfqPriceupdate.getId());
            result = rnsRfqPriceRepository.save(rnsRfqPrice);

            rnsQuotationVendors = rnsQuotationVendorsRepository.findById(result.getVendorId()).orElse(null);;
            rnsQuotationVendors.setPaymentTerms(rnsRfqPriceBean.getPaymentTerms());
            rnsQuotationVendors.setDeliveryTerms(rnsRfqPriceBean.getDeliveryTerms());
            rnsQuotationVendors.setConfDelDate(rnsRfqPriceBean.getConfDelDate());
            rnsQuotationVendors.setCurrency(rnsRfqPriceBean.getCurrency());
            rnsQuotationVendors.setRegularRate(rnsRfqPriceBean.getRegularRate());
            if(rnsRfqPriceBean.getRfqUserType() != null) {
                rnsQuotationVendors.setRfqUserType(rnsRfqPriceBean.getRfqUserType());
            } else {
                rnsQuotationVendors.setRfqUserType("V");
            }
            rnsQuotationVendorsRepository.save(rnsQuotationVendors);

            VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVendors.getVariant().getId(), rnsQuotationVendors.getVendorCode());
            if(vndrPrice!=null) {
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setCreatedOn(Instant.now());
                if(rnsRfqPriceBean.getRfqUserType() != null && rnsRfqPriceBean.getRfqUserType().equalsIgnoreCase("S")) {
                    vndrPrice.setSurrogate(true);
                }
                vndrPriceRepository.save(vndrPrice);
            } else{
                vndrPrice = new VndrPrice();
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setVendorCode(rnsQuotationVendors.getVendorCode());
                vndrPrice.setCreatedOn(Instant.now());
                vndrPrice.setVariant(rnsQuotationVendors.getVariant());
                vndrPrice.setVndrQuotation(rnsQuotationVendors);
                if(rnsRfqPriceBean.getRfqUserType() != null && rnsRfqPriceBean.getRfqUserType().equalsIgnoreCase("S")) {
                    vndrPrice.setSurrogate(true);
                }
                vndrPriceRepository.save(vndrPrice);
            }
        } else {
            rnsRfqPrice.createdBy(getCurrentUserLogin());
            rnsRfqPrice.setCreatedDate(Instant.now());
            result = rnsRfqPriceRepository.save(rnsRfqPrice);

            rnsQuotationVendors = rnsQuotationVendorsRepository.findById(result.getVendorId()).orElse(null);
            rnsQuotationVendors.setPaymentTerms(rnsRfqPriceBean.getPaymentTerms());
            rnsQuotationVendors.setDeliveryTerms(rnsRfqPriceBean.getDeliveryTerms());
            rnsQuotationVendors.setConfDelDate(rnsRfqPriceBean.getConfDelDate());
            rnsQuotationVendors.setCurrency(rnsRfqPriceBean.getCurrency());
            rnsQuotationVendors.setRegularRate(rnsRfqPriceBean.getRegularRate());
            if(rnsRfqPriceBean.getRfqUserType() != null) {
                rnsQuotationVendors.setRfqUserType(rnsRfqPriceBean.getRfqUserType());
            } else {
                rnsQuotationVendors.setRfqUserType("V");
            }
            rnsQuotationVendorsRepository.save(rnsQuotationVendors);

            VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVendors.getVariant().getId(), rnsQuotationVendors.getVendorCode());
            if(vndrPrice!=null) {
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setCreatedOn(Instant.now());
                if(rnsRfqPriceBean.getRfqUserType() != null && rnsRfqPriceBean.getRfqUserType().equalsIgnoreCase("S")) {
                    vndrPrice.setSurrogate(true);
                }
                vndrPriceRepository.save(vndrPrice);
            } else {
                vndrPrice = new VndrPrice();
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setVendorCode(rnsQuotationVendors.getVendorCode());
                vndrPrice.setCreatedOn(Instant.now());
                vndrPrice.setVariant(rnsQuotationVendors.getVariant());
                vndrPrice.setVndrQuotation(rnsQuotationVendors);
                if(rnsRfqPriceBean.getRfqUserType() != null && rnsRfqPriceBean.getRfqUserType().equalsIgnoreCase("S")) {
                    vndrPrice.setSurrogate(true);
                }
                vndrPriceRepository.save(vndrPrice);
            }
        }
        try {
            // Send mail
            String emailTemplateCode = "rfqSubmitted";
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RnsQuotation rnsQuotation = rnsQuotationVendors.getVendorQuotation();
                User user = rnsQuotation.getUser();
                User vendor = userRepository.findByLogin(getCurrentUserLogin());
                if (user != null) {
                    Locale locale = Locale.forLanguageTag(user.getLangKey());
                    Context context = new Context(locale);
                    context.setVariable("vendor", vendor);
                    context.setVariable("rnsQuotation", rnsQuotation);
                    context.setVariable("applicationProperties", applicationProperties);
                    context.setVariable("user", user);
                    String content = templateEngine.process("mail/" + emailTemplateCode, context);
                    subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);
                    mailService.sendEmail("sandeep@fulcrumconsultancy.com", subject, content, false, true);
                    try {
                        mailService.sendEmail(user.getEmail(), subject, content, false, true);
                    } catch (Exception e) {
                    }
                    //Notification
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
        } catch(Exception e){System.out.println("RnsRfqResource "+ e.getMessage());}
        // end Send mail
        return ResponseEntity.created(new URI("/api/rns-rfq-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * POST  /rns-rfq-prices : Create a new rnsRfqPrice.
     *
     * @param rnsRfqPrice the rnsRfqPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsRfqPrice, or with status 400 (Bad Request) if the rnsRfqPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-rfq-prices-vendors")
    @Timed
    public ResponseEntity<RnsRfqPrice> createRnsRfqPriceVendors(@RequestBody RnsRfqPrice rnsRfqPrice) throws URISyntaxException {
        log.debug("REST request to save RnsRfqPrice : {}", rnsRfqPrice);
        if(rnsRfqPrice.getId()==null || (rnsRfqPrice.getId()!=null && rnsRfqPrice.getId()==0)){rnsRfqPrice.setId(null);}

        RnsRfqPrice rnsRfqPriceupdate = rnsRfqPriceRepository.getByVendorId(rnsRfqPrice.getVendorId());
        RnsRfqPrice result = null;
        RnsQuotationVendors rnsQuotationVendors = null;
        if(rnsRfqPriceupdate!=null){
            rnsRfqPrice.createdBy(getCurrentUserLogin());
            rnsRfqPrice.setCreatedDate(Instant.now());
            rnsRfqPrice.setId(rnsRfqPriceupdate.getId());
            result = rnsRfqPriceRepository.save(rnsRfqPrice);

            rnsQuotationVendors = rnsQuotationVendorsRepository.findById(result.getVendorId()).orElse(null);
            VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVendors.getVariant().getId(), rnsQuotationVendors.getVendorCode());
            if(vndrPrice!=null) {
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setCreatedOn(Instant.now());
                vndrPriceRepository.save(vndrPrice);
            } else{
                vndrPrice = new VndrPrice();
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setVendorCode(rnsQuotationVendors.getVendorCode());
                vndrPrice.setCreatedOn(Instant.now());
                vndrPrice.setVariant(rnsQuotationVendors.getVariant());
                vndrPrice.setVndrQuotation(rnsQuotationVendors);
                vndrPriceRepository.save(vndrPrice);
            }

            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVendors.getVariant().getId());
            if(auctionVrnt!=null && rnsQuotationVendors!=null) {
                Float totalPrice = 0.0f;
                if (result.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (result.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (result.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (result.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (result.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (result.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (result.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (result.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (result.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (result.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (result.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (result.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (result.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (result.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (result.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (result.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (result.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (result.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (result.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (result.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                rnsQuotationVendors.setRegularRate(totalPrice);
                rnsQuotationVendors.setRfqUserType("S");
                rnsQuotationVendorsRepository.save(rnsQuotationVendors);
            }
        } else {
            rnsRfqPrice.createdBy(getCurrentUserLogin());
            rnsRfqPrice.setCreatedDate(Instant.now());
            result = rnsRfqPriceRepository.save(rnsRfqPrice);

            rnsQuotationVendors = rnsQuotationVendorsRepository.findById(result.getVendorId()).orElse(null);
            VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVendors.getVariant().getId(), rnsQuotationVendors.getVendorCode());
            if (vndrPrice != null) {
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setCreatedOn(Instant.now());
                vndrPriceRepository.save(vndrPrice);
            } else {
                vndrPrice = new VndrPrice();
                vndrPrice.setPriceOne(result.getPriceOne());
                vndrPrice.setPriceTwo(result.getPriceTwo());
                vndrPrice.setPriceThree(result.getPriceThree());
                vndrPrice.setPriceFour(result.getPriceFour());
                vndrPrice.setPriceFive(result.getPriceFive());
                vndrPrice.setPriceSix(result.getPriceSix());
                vndrPrice.setPriceSeven(result.getPriceSeven());
                vndrPrice.setPriceEight(result.getPriceEight());
                vndrPrice.setPriceNine(result.getPriceNine());
                vndrPrice.setPriceTen(result.getPriceTen());
                vndrPrice.setVendorCode(rnsQuotationVendors.getVendorCode());
                vndrPrice.setCreatedOn(Instant.now());
                vndrPrice.setVariant(rnsQuotationVendors.getVariant());
                vndrPrice.setVndrQuotation(rnsQuotationVendors);
                vndrPriceRepository.save(vndrPrice);
            }

            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVendors.getVariant().getId());
            if (auctionVrnt != null && rnsQuotationVendors != null) {
                Float totalPrice = 0.0f;
                if (result.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (result.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (result.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (result.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (result.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (result.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (result.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (result.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (result.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (result.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (result.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (result.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (result.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (result.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (result.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (result.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (result.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (result.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (result.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (result.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                rnsQuotationVendors.setRegularRate(totalPrice);
                rnsQuotationVendors.setRfqUserType("S");
                rnsQuotationVendorsRepository.save(rnsQuotationVendors);
            }
        }

        // Send mail
        try {
            String emailTemplateCode = "rfqSurrogate";
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RnsQuotation rnsQuotation = rnsQuotationVendors.getVendorQuotation();
                User user = userRepository.findByLogin(getCurrentUserLogin());
                User vendor = userRepository.findByLogin(rnsQuotationVendors.getVendorCode());
                if (user != null) {
                    Locale locale = Locale.forLanguageTag(user.getLangKey());
                    Context context = new Context(locale);
                    context.setVariable("vendor", vendor);
                    context.setVariable("rnsQuotation", rnsQuotation);
                    context.setVariable("applicationProperties", applicationProperties);
                    context.setVariable("user", user);
                    String content = templateEngine.process("mail/" + emailTemplateCode, context);
                    subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);
                    mailService.sendEmail("sandeep@fulcrumconsultancy.com", subject, content, false, true);
                    try {
                        mailService.sendEmail(vendor.getEmail(), subject, content, false, true);
                    } catch (Exception e) {
                    }
                    //Notification
                    Message message = new Message();
                    message.setFromMail(getCurrentUserLogin());
                    message.setToMail(vendor.getLogin());
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
        } catch (Exception e) {
            System.out.println("RnsRfqResource " + e.getMessage());
        }
        return ResponseEntity.created(new URI("/api/rns-rfq-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
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

    /**
     * PUT  /rns-rfq-prices : Updates an existing rnsRfqPrice.
     *
     * //@param rnsRfqPrice the rnsRfqPrice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsRfqPrice,
     * or with status 400 (Bad Request) if the rnsRfqPrice is not valid,
     * or with status 500 (Internal Server Error) if the rnsRfqPrice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-rfq-prices")
    @Timed
    public ResponseEntity<RnsRfqPrice> updateRnsRfqPrice(@RequestBody RnsRfqPriceBean rnsRfqPriceBean) throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        log.debug("REST request to update RnsRfqPrice : {}", rnsRfqPriceBean);
        if (rnsRfqPriceBean.getId() == null) {
            return createRnsRfqPrice(rnsRfqPriceBean);
        }
        RnsRfqPrice rnsRfqPrice = new RnsRfqPrice();
        BeanUtils.copyProperties(rnsRfqPrice, rnsRfqPriceBean);

        RnsRfqPrice result = rnsRfqPriceRepository.save(rnsRfqPrice);

        RnsQuotationVendors rnsQuotationVendors = rnsQuotationVendorsRepository.findById(result.getVendorId()).orElse(null);
        rnsQuotationVendors.setPaymentTerms(rnsRfqPriceBean.getPaymentTerms());
        rnsQuotationVendors.setDeliveryTerms(rnsRfqPriceBean.getDeliveryTerms());
        rnsQuotationVendors.setConfDelDate(rnsRfqPriceBean.getConfDelDate());
        rnsQuotationVendors.setCurrency(rnsRfqPriceBean.getCurrency());
        rnsQuotationVendors.setRegularRate(rnsRfqPriceBean.getRegularRate());
        rnsQuotationVendors.setRfqUserType("V");
        rnsQuotationVendorsRepository.save(rnsQuotationVendors);

        VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVendors.getVariant().getId(), rnsQuotationVendors.getVendorCode());
        if(vndrPrice!=null) {
            vndrPrice.setPriceOne(result.getPriceOne());
            vndrPrice.setPriceTwo(result.getPriceTwo());
            vndrPrice.setPriceThree(result.getPriceThree());
            vndrPrice.setPriceFour(result.getPriceFour());
            vndrPrice.setPriceFive(result.getPriceFive());
            vndrPrice.setPriceSix(result.getPriceSix());
            vndrPrice.setPriceSeven(result.getPriceSeven());
            vndrPrice.setPriceEight(result.getPriceEight());
            vndrPrice.setPriceNine(result.getPriceNine());
            vndrPrice.setPriceTen(result.getPriceTen());
            vndrPrice.setCreatedOn(Instant.now());
            vndrPriceRepository.save(vndrPrice);
        } else {
            vndrPrice = new VndrPrice();
            vndrPrice.setPriceOne(result.getPriceOne());
            vndrPrice.setPriceTwo(result.getPriceTwo());
            vndrPrice.setPriceThree(result.getPriceThree());
            vndrPrice.setPriceFour(result.getPriceFour());
            vndrPrice.setPriceFive(result.getPriceFive());
            vndrPrice.setPriceSix(result.getPriceSix());
            vndrPrice.setPriceSeven(result.getPriceSeven());
            vndrPrice.setPriceEight(result.getPriceEight());
            vndrPrice.setPriceNine(result.getPriceNine());
            vndrPrice.setPriceTen(result.getPriceTen());
            vndrPrice.setVendorCode(rnsQuotationVendors.getVendorCode());
            vndrPrice.setCreatedOn(Instant.now());
            vndrPrice.setVariant(rnsQuotationVendors.getVariant());
            vndrPrice.setVndrQuotation(rnsQuotationVendors);
            vndrPriceRepository.save(vndrPrice);
        }
        try{
            // Send mail
            String emailTemplateCode="rfqSubmitted";
            EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateCode(emailTemplateCode);
            List<EmailTemplateBody> emailTemplateBodies = emailTemplateBodyRepository.findAllByTemplateCode(emailTemplateCode);
            if(emailTemplateBodies!=null && emailTemplateBodies.size()>0){
                String emailBody="";
                for(EmailTemplateBody body : emailTemplateBodies){
                    emailBody+=body.getMailBody();
                }

                emailBody = StringEscapeUtils.unescapeHtml3(emailBody);

                emailBody=emailBody.replaceAll("<<","<span th:text=\"\\${");
                emailBody=emailBody.replaceAll(">>","}\" th:remove=\"tag\"/>");

                String subject = emailTemplate.getMailSubject();
                subject=subject.replaceAll("<<","<span th:text=\"\\${");
                subject=subject.replaceAll(">>","}\" th:remove=\"tag\"/>");

                PrintWriter writer = null;
                try {
                    String PATH = applicationProperties.getTemplatePath()+"templates/mail";
                    //String PATH="E:\\Project\\rns\\target\\classes\\mails";
                    File f = new File(PATH + "/" + emailTemplateCode + ".html");
                    File f1 = new File(PATH + "/" + emailTemplateCode + "Subject.html");
                    if(f.exists()){} else{
                        f.createNewFile();
                    }
                    if(f1.exists()){} else{
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
                RnsQuotation rnsQuotation = rnsQuotationVendors.getVendorQuotation();
                User user = rnsQuotation.getUser();
                User vendor = userRepository.findByLogin(getCurrentUserLogin());
                if(user!=null){
                    Locale locale = Locale.forLanguageTag(user.getLangKey());
                    Context context = new Context(locale);
                    context.setVariable("vendor", vendor);
                    context.setVariable("rnsQuotation", rnsQuotation);
                    context.setVariable("applicationProperties", applicationProperties);
                    context.setVariable("user", user);
                    String content = templateEngine.process("mail/" + emailTemplateCode, context);
                    subject = templateEngine.process("mail/" + emailTemplateCode+"Subject", context);
                    mailService.sendEmail("sandeep@fulcrumconsultancy.com", subject, content, false, true);
                    try {
                        mailService.sendEmail(user.getEmail(), subject, content, false, true);
                    } catch(Exception e){}
                    //Notification
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
                    for(String body : strings){
                        MessageBody messageBody = new MessageBody();
                        messageBody.setMessageId(message1.getId());
                        messageBody.setMessageBody(body);
                        messageBody.setCreatedBy(getCurrentUserLogin());
                        messageBody.setCreatedDate(LocalDate.now());
                        messageBodyRepository.save(messageBody);
                    }
                }

            }
        } catch(Exception e){System.out.println("RnsRfqResource "+ e.getMessage());}
        // end Send mail
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsRfqPrice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-rfq-prices : get all the rnsRfqPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsRfqPrices in body
     */
    @GetMapping("/rns-rfq-prices")
    @Timed
    public List<RnsRfqPrice> getAllRnsRfqPrices() {
        log.debug("REST request to get all RnsRfqPrices");
        return rnsRfqPriceRepository.findAll();
        }

    /**
     * GET  /rns-rfq-prices/:id : get the "id" rnsRfqPrice.
     *
     * @param id the id of the rnsRfqPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsRfqPrice, or with status 404 (Not Found)
     */
    @GetMapping("/rns-rfq-prices/{id}")
    @Timed
    public ResponseEntity<RnsRfqPrice> getRnsRfqPrice(@PathVariable Long id) {
        log.debug("REST request to get RnsRfqPrice : {}", id);
        Optional<RnsRfqPrice> rnsRfqPrice = rnsRfqPriceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsRfqPrice);
    }


    /**
     * GET  /rns-rfq-prices/:id : get the "id" rnsRfqPrice.
     *
     * @param id the id of the rnsRfqPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsRfqPrice, or with status 404 (Not Found)
     */
    @GetMapping("/rns-rfq-prices-vendor/{id}")
    @Timed
    public ResponseEntity<RnsRfqPrice> getRnsRfqPriceByVendorId(@PathVariable Long id) {
        log.debug("REST request to get RnsRfqPrice : {}", id);
        RnsRfqPrice rnsRfqPrice = rnsRfqPriceRepository.getByVendorId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsRfqPrice));
    }


    /**
     * GET  /rns-rfq-prices/:id : get the "id" rnsRfqPrice.
     *
     * @param id the id of the rnsRfqPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsRfqPrice, or with status 404 (Not Found)
     */
    @GetMapping("/rns-rfq-prices-popup/{id}")
    @Timed
    public ResponseEntity<RnsRfqPrice> getRnsRfqPriceByVendorIdPopup(@PathVariable Long id) {
        log.debug("REST request to get RnsRfqPrice : {}", id);
        RnsRfqPrice rnsRfqPrice = rnsRfqPriceRepository.getByVendorId(id);
        if(rnsRfqPrice==null){
            rnsRfqPrice = new RnsRfqPrice();
            rnsRfqPrice.setVendorId(id);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsRfqPrice));
    }



    /**
     * DELETE  /rns-rfq-prices/:id : delete the "id" rnsRfqPrice.
     *
     * @param id the id of the rnsRfqPrice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-rfq-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsRfqPrice(@PathVariable Long id) {
        log.debug("REST request to delete RnsRfqPrice : {}", id);
        rnsRfqPriceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
