package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import jxl.read.biff.BiffException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import rightchamps.domain.*;
import rightchamps.domain.Currency;
import rightchamps.domain.enumeration.ShowAsTemplate;
import rightchamps.modal.*;
import rightchamps.modal.sort.*;
import rightchamps.repository.*;
import rightchamps.service.MailService;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import rightchamps.config.ApplicationProperties;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import javax.inject.Inject;


import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import java.time.Instant;

// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;
/**
 * REST controller for managing RnsQuotation.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationResource.class);

    private static final String ENTITY_NAME = "Quotation";

    private static final String USER = "user";
    private static final String VENDOR = "vendor";

    private final RnsQuotationRepository rnsQuotationRepository;


    @Inject
    private ApplicationProperties applicationProperties;

    @Inject
    private RnsCatgMasterRepository rnsCatgMasterRepository;

    @Inject
    private RnsPchMasterRepository rnsPchMasterRepository;

    @Inject
    private AuctionVrntRepository auctionVrntRepository;

    @Inject
    private VndrPriceRepository vndrPriceRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private RnsPayTermsMasterRepository rnsPayTermsMasterRepository;

    @Inject
    private RnsDelPlaceMasterRepository rnsDelPlaceMasterRepository;

    @Inject
    private RnsTaxTermsMasterRepository rnsTaxTermsMasterRepository;

    @Inject
    private RnsDelTermsMasterRepository rnsDelTermsMasterRepository;

    @Inject
    private RnsQuotationVendorsRepository rnsQuotationVendorsRepository;

    @Inject
    private RnsUpchargeDtlRepository rnsUpchargeDtlRepository;

    @Inject
    private RnsUomMasterRepository rnsUomMasterRepository;

    @Inject
    private CurrencyRepository currencyRepository;

    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    @Inject
    private EmailTemplateBodyRepository emailTemplateBodyRepository;

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private MessageBodyRepository messageBodyRepository;

    @Inject
    private RnsSourceTeamMasterRepository rnsSourceTeamMasterRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MailService mailService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TemplateRepository templateRepository;

    @Inject
    private RnsRfqPriceRepository rnsRfqPriceRepository;

    @Inject
    private RnsTypeMasterRepository rnsTypeMasterRepository;

    @Inject
    private RnsVendorRemarkRepository rnsVendorRemarkRepository;

    @Inject
    private AuctionVarDetailsRepository auctionVarDetailsRepository;

    @Inject
    private AuctionPauseDetailsRepository auctionPauseDetailsRepository;

    @Inject
    private RnsFileUploadRepository rnsFileUploadRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private ServletContext servletContext;

    public RnsQuotationResource(RnsQuotationRepository rnsQuotationRepository) {
        this.rnsQuotationRepository = rnsQuotationRepository;

    }

    /**
     * POST  /rns-quotations : Create a new rnsQuotation.
     *
     * @param rnsQuotation the rnsQuotation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotation, or with status 400 (Bad Request) if the rnsQuotation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotations")
    @Timed
    public ResponseEntity<RnsQuotation> createRnsQuotation(@RequestBody RnsQuotation rnsQuotation) throws URISyntaxException {
        log.debug("REST request to save RnsQuotation : {}", rnsQuotation);
        if (rnsQuotation.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Instant instant = Instant.now();
        System.out.println(instant);
        rnsQuotation.setCreatedOn(instant);
        String userName = getCurrentUserLogin();
        User user = userRepository.findByLogin(userName);
        rnsQuotation.setUser(user);
        RnsTypeMaster rnsTypeMaster = rnsTypeMasterRepository.findByTypeCodeAndRnsCatgCode(rnsQuotation.getQuoteType(), rnsQuotation.getRnsCatgCode().getId());
        rnsQuotation.setRnsTypeMaster(rnsTypeMaster);
        RnsQuotation result = rnsQuotationRepository.save(rnsQuotation);
        return ResponseEntity.created(new URI("/api/rns-quotations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotations : Updates an existing rnsQuotation.
     *
     * @param rnsQuotation the rnsQuotation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotation,
     * or with status 400 (Bad Request) if the rnsQuotation is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotations")
    @Timed
    public ResponseEntity<RnsQuotation> updateRnsQuotation(@RequestBody RnsQuotation rnsQuotation) throws URISyntaxException {
        log.debug("REST request to update RnsQuotation : {}", rnsQuotation);
        if (rnsQuotation.getId() == null) {
            return createRnsQuotation(rnsQuotation);
        }
        RnsTypeMaster rnsTypeMaster = rnsTypeMasterRepository.findByTypeCodeAndRnsCatgCode(rnsQuotation.getQuoteType(), rnsQuotation.getRnsCatgCode().getId());
        rnsQuotation.setRnsTypeMaster(rnsTypeMaster);
        rnsQuotation.setUpdatedDate(Instant.now());
        String userName = getCurrentUserLogin();
        User user = userRepository.findByLogin(userName);
        rnsQuotation.setUpdatedUser(user);
        RnsQuotation result = rnsQuotationRepository.save(rnsQuotation);
        log.debug("==============================================", rnsQuotation.isPublished());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotation.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotations/publish : Updates an existing rnsQuotation.
     *
     * @param //rnsQuotation the rnsQuotation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotation,
     * or with status 400 (Bad Request) if the rnsQuotation is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotations/publish")
    @Timed
    public RnsQuotation updatePublish(@RequestBody RnsQuotation rnsQuotationrequestData) throws URISyntaxException {
        log.debug("REST request to update RnsQuotation : {}", rnsQuotationrequestData.getId());
        Long id = rnsQuotationrequestData.getId();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);

        rnsQuotation.setPublished(true);
        RnsQuotation result = rnsQuotationRepository.save(rnsQuotation);

        //List Variants
        List<RnsQuotationVariant> variants = rnsQuotationRepository.getRnsQuotationVariantsList(rnsQuotation.getId());
        for (RnsQuotationVariant variant : variants) {
            // Send Mail to Vendor
            List<RnsQuotationVendors> vendors= rnsQuotationRepository.getRnsQuotationVendorsList(rnsQuotation.getId());
            //       for (RnsQuotationVendors vendor : vendors) {
            //           if(vendor.getVendor().getEmail() != null){
            //               log.debug("Sending Email to vendor: ",vendor.getVendor().getEmail());


            //mailService.sendEmail(vendor.getVendor().getEmail(), "Quotation Published", "quotationPublished", false, true);

        }
        return result;
    }



    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotations")
    @Timed
    public List<RnsQuotation> getAllRnsQuotations() {
        log.debug("REST request to get all RnsQuotations");
        List<RnsQuotation> rnsQuotationList = new ArrayList<RnsQuotation>();
        List<RnsQuotation> quotationList = rnsQuotationRepository.findAll();
        List<Long> userCatgList = getAllRnsCatgMasters();
        //List<String> rnsPchMasterList = getAllPchCode();
        //System.out.printf("rnsPchMasterList", rnsPchMasterList);
        for (RnsQuotation quotationData : quotationList) {
            log.debug("------===---");
            Long quotationId = quotationData.getId();
            if (userCatgList != null && userCatgList.contains(quotationData.getRnsCatgCode().getId())) {
                rnsQuotationList.add(quotationData);
                log.debug("------===--------------------------------------------------------rnsQuotationList",rnsQuotationList);
            }
        }
        return rnsQuotationList;
    }

    private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotations-custom")
    @Timed
    public List<RnsQuotationCustomVM> getAllRnsQuotationsCustomVM() {
        log.debug("REST request to get all RnsQuotations");
        List<RnsQuotationCustomVM> rnsQuotationList = new ArrayList<RnsQuotationCustomVM>();
        List<RnsQuotation> quotationList = rnsQuotationRepository.findAllBySort();
        List<Long> userCatgList = getAllRnsCatgMasters();
        //List<String> rnsPchMasterList = getAllPchCode();
        for (RnsQuotation quotationData : quotationList) {
            log.debug("------===---");
            Long quotationId = quotationData.getId();
            if (userCatgList != null && userCatgList.contains(quotationData.getRnsCatgCode().getId())) {
                RnsQuotationCustomVM rnsQuotation = new RnsQuotationCustomVM();
                rnsQuotation.setId(quotationData.getId());
                rnsQuotation.setQuoteNumber(quotationData.getQuoteNumber());
                rnsQuotation.setProjectTitle(quotationData.getProjectTitle());
                rnsQuotation.setRnsCatgCode(quotationData.getRnsCatgCode());
                rnsQuotation.setRnsPchMaster(quotationData.getRnsPchMaster());
                rnsQuotation.setArticleCode(quotationData.getArticleCode());
                rnsQuotation.setArticleDesc(quotationData.getArticleDesc());
                rnsQuotation.setRfq(quotationData.getRfq());
                rnsQuotation.setAuction(quotationData.getAuction());
                rnsQuotation.setValidity(quotationData.getValidity());
                rnsQuotation.setRnsTypeMaster(quotationData.getRnsTypeMaster());
                rnsQuotation.setUser(quotationData.getUser());
                rnsQuotation.setCreatedOn(quotationData.getCreatedOn());
                rnsQuotation.setApprovedBy(quotationData.getApprovedBy());
                rnsQuotation.setApprovedDate(quotationData.getApprovedDate());
                Boolean passVendor = true;
                Boolean passRFQ = true;
                if (quotationData.getValidity() == null) {
                    passRFQ = false;
                }
                            /*List<RnsQuotationVendors> vendors= rnsQuotationRepository.getRnsQuotationVendorsList(quotationData.getId());
                            for(RnsQuotationVendors vendor: vendors){
                                if(vendor.getPaymentTermsCharge() == null){
                                    passVendor = false;
                                    passRFQ = false;
                                }
                            }
                            List<RnsQuotationVariant> variants= rnsQuotationRepository.getRnsQuotationVariantsList(quotationData.getId());
                            for(RnsQuotationVariant variant: variants){
                                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(variant.getId());
                                if(auctionVrnt == null){
                                    passVendor = false;
                                }
                            }*/

                rnsQuotation.setAuctionValidate(passVendor);
                rnsQuotation.setRfqValidate(passRFQ);
                rnsQuotationList.add(rnsQuotation);
            }
        }
        return rnsQuotationList;
    }

    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @PostMapping("/rns-quotations-custom-query")
    @Timed
    public List<RnsQuotationCustomVM> getAllRnsQuotationsCustomVM(@RequestBody RnsQuotationSearch rnsQuotationSearch) {
        log.debug("REST request to get all RnsQuotations");
        //Criteria Builder
        String id = "%";
        String projectTitle="%";

        //End Criteria Builder
        List<RnsQuotationCustomVM> rnsQuotationList = new ArrayList<RnsQuotationCustomVM>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RnsQuotation> cq = builder.createQuery(RnsQuotation.class);
        Root<RnsQuotation> root = cq.from(RnsQuotation.class);
        cq.select(root);
        Predicate predicate = builder.conjunction();
        if(rnsQuotationSearch.getTitle()!=null && rnsQuotationSearch.getTitle().length()>0)
        {
            predicate = builder.and(predicate, builder.like(builder.upper(root.get("projectTitle")),"%"+rnsQuotationSearch.getTitle().toUpperCase()+"%"));
        }
        if(rnsQuotationSearch.getDateFrom()!=null && rnsQuotationSearch.getDateTo()!=null){
            try {
                System.out.println("Date Range "+rnsQuotationSearch.getDateFrom()+" "+rnsQuotationSearch.getDateTo());
                predicate = builder.and(predicate, builder.between(builder.function("TRUNC",java.time.Instant.class,root.get("createdOn")), new SimpleDateFormat("yyyy-MM-dd").parse(rnsQuotationSearch.getDateFrom()).toInstant(), new SimpleDateFormat("yyyy-MM-dd").parse(rnsQuotationSearch.getDateTo()).toInstant()));
            } catch(ParseException pe){System.out.println("RnsQuotationResource "+pe.getMessage());}

        }
        if(rnsQuotationSearch.getCatgCode()!=null){
            predicate = builder.and(predicate, builder.equal(root.get("rnsCatgCode"),rnsQuotationSearch.getCatgCode()));
        }

        if(rnsQuotationSearch.getProjectType()!=null && rnsQuotationSearch.getProjectType().length()>0){
            predicate = builder.and(predicate, builder.like(root.get("quoteType"),rnsQuotationSearch.getProjectType()+"%"));
        }

        if(rnsQuotationSearch.getSourceTeam()!=null && rnsQuotationSearch.getSourceTeam().length()>0){
            predicate = builder.and(predicate, builder.equal(root.get("sourceTeam"),rnsQuotationSearch.getSourceTeam()));
        }
        if(rnsQuotationSearch.getId()>0)
        {
            predicate = builder.and(builder.equal(root.get("id"),rnsQuotationSearch.getId()));
        }

        if(rnsQuotationSearch.getRfqStatus() != null && rnsQuotationSearch.getRfqStatus().equalsIgnoreCase("N")) {
            Predicate predicate1 = builder.conjunction();
            predicate1 = builder.isNull(root.get("rfqApplicable"));
            predicate1 = builder.or(predicate1, builder.equal(root.get("rfqApplicable"),0));
            predicate = builder.and(predicate, predicate1);
        } else if(rnsQuotationSearch.getRfqStatus() != null && rnsQuotationSearch.getRfqStatus().equalsIgnoreCase("F")) {
            Predicate predicate1 = builder.conjunction();
            predicate1 = builder.isNull(root.get("approvedFlag"));
            predicate1 = builder.or(predicate1, builder.notEqual(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, predicate1);
            predicate = builder.and(predicate, builder.equal(root.get("rfqApplicable"),1));
        } else if(rnsQuotationSearch.getRfqStatus() != null && rnsQuotationSearch.getRfqStatus().equalsIgnoreCase("P")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.equal(root.get("rfqApplicable"),1));
            Predicate predicate1 = builder.conjunction();
            predicate1 = builder.isNull(root.get("rfq"));
            predicate1 = builder.or(predicate1, builder.equal(root.get("rfq"),0));
            predicate = builder.and(predicate, predicate1);
        } else if(rnsQuotationSearch.getRfqStatus() != null && rnsQuotationSearch.getRfqStatus().equalsIgnoreCase("I")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.equal(root.get("rfqApplicable"),1));
            predicate = builder.and(predicate, builder.greaterThan(root.<Timestamp>get("validity"), Timestamp.from(Instant.now())));
        } else if(rnsQuotationSearch.getRfqStatus() != null && rnsQuotationSearch.getRfqStatus().equalsIgnoreCase("C")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.equal(root.get("rfqApplicable"),1));
            predicate = builder.and(predicate, builder.lessThan(root.<Timestamp>get("validity"), Timestamp.from(Instant.now())));
        }

        if(rnsQuotationSearch.getRfbStatus() != null && rnsQuotationSearch.getRfbStatus().equalsIgnoreCase("N")) {
            Predicate predicate1 = builder.conjunction();
            predicate1 = builder.isNull(root.get("auctionApplicable"));
            predicate1 = builder.or(predicate1, builder.equal(root.get("auctionApplicable"),0));
            predicate = builder.and(predicate, predicate1);
        } else if(rnsQuotationSearch.getRfbStatus() != null && rnsQuotationSearch.getRfbStatus().equalsIgnoreCase("F")) {
            Predicate predicate1 = builder.conjunction();
            predicate1 = builder.isNull(root.get("approvedFlag"));
            predicate1 = builder.or(predicate1, builder.notEqual(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, predicate1);
            predicate = builder.and(predicate, builder.equal(root.get("auctionApplicable"),1));
        } else if(rnsQuotationSearch.getRfbStatus() != null && rnsQuotationSearch.getRfbStatus().equalsIgnoreCase("P")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.equal(root.get("auctionApplicable"),1));
            Predicate predicate1 = builder.conjunction();
            predicate1 = builder.isNull(root.get("auction"));
            predicate1 = builder.or(predicate1, builder.equal(root.get("auction"),0));
            predicate = builder.and(predicate, predicate1);
        } else if(rnsQuotationSearch.getRfbStatus() != null && rnsQuotationSearch.getRfbStatus().equalsIgnoreCase("I")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.equal(root.get("auctionApplicable"),1));
            predicate = builder.and(predicate, builder.equal(root.get("auction"),1));
            predicate = builder.and(predicate, builder.isNull(root.get("auctionClose")));
        } else if(rnsQuotationSearch.getRfbStatus() != null && rnsQuotationSearch.getRfbStatus().equalsIgnoreCase("C")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.equal(root.get("auctionApplicable"),1));
            predicate = builder.and(predicate, builder.equal(root.get("auction"),1));
            predicate = builder.and(predicate, builder.equal(root.get("auctionClose"), 1));
        }

        if(rnsQuotationSearch.getWorkflowStatus() != null && rnsQuotationSearch.getWorkflowStatus().equalsIgnoreCase("F")) {
            Predicate predicate1 = builder.conjunction();
            predicate1 = builder.isNull(root.get("approvedFlag"));
            predicate1 = builder.or(predicate1, builder.notEqual(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, predicate1);
            predicate = builder.and(predicate, builder.isNull(root.get("workflowStatus")));
        } else if(rnsQuotationSearch.getWorkflowStatus() != null && rnsQuotationSearch.getWorkflowStatus().equalsIgnoreCase("P")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.isNull(root.get("workflowStatus")));
        } else if(rnsQuotationSearch.getWorkflowStatus() != null && rnsQuotationSearch.getWorkflowStatus().equalsIgnoreCase("I")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.isNotNull(root.get("workflowStatus")));
            predicate = builder.and(predicate, builder.notEqual(root.get("workflowStatus"), "C"));
        } else if(rnsQuotationSearch.getWorkflowStatus() != null && rnsQuotationSearch.getWorkflowStatus().equalsIgnoreCase("C")) {
            predicate = builder.and(predicate, builder.equal(root.get("approvedFlag"),"C"));
            predicate = builder.and(predicate, builder.isNotNull(root.get("workflowStatus")));
            predicate = builder.and(predicate, builder.equal(root.get("workflowStatus"), "C"));
        }

        cq.where(predicate);
        cq.orderBy(builder.desc(root.get("id")));

        List<RnsQuotation> quotationList = null;
        try {
            quotationList = entityManager.createQuery(cq.select(root)).getResultList();
        } catch(Exception e){}

        if(quotationList!=null && quotationList.size()>0) {
            List<Long> userCatgList = getAllRnsCatgMasters();
            for (RnsQuotation quotationData : quotationList) {
                log.debug("------===---");
                Long quotationId = quotationData.getId();
                if (userCatgList != null && userCatgList.contains(quotationData.getRnsCatgCode().getId())) {
                    RnsQuotationCustomVM rnsQuotation = new RnsQuotationCustomVM();
                    rnsQuotation.setId(quotationData.getId());
                    rnsQuotation.setQuoteNumber(quotationData.getQuoteNumber());
                    rnsQuotation.setProjectTitle(quotationData.getProjectTitle());
                    rnsQuotation.setRnsCatgCode(quotationData.getRnsCatgCode());
                    rnsQuotation.setRnsPchMaster(quotationData.getRnsPchMaster());
                    rnsQuotation.setArticleCode(quotationData.getArticleCode());
                    rnsQuotation.setArticleDesc(quotationData.getArticleDesc());
                    rnsQuotation.setRfq(quotationData.getRfq());
                    rnsQuotation.setAuction(quotationData.getAuction());
                    rnsQuotation.setValidity(quotationData.getValidity());
                    rnsQuotation.setRnsTypeMaster(quotationData.getRnsTypeMaster());
                    rnsQuotation.setUser(quotationData.getUser());
                    rnsQuotation.setCreatedOn(quotationData.getCreatedOn());
                    rnsQuotation.setAuctionClose(quotationData.getAuctionClose());
                    rnsQuotation.setApprovedFlag(quotationData.getApprovedFlag());
                    rnsQuotation.setApprovedDate(quotationData.getApprovedDate());
                    rnsQuotation.setApprovedBy(quotationData.getApprovedBy());
                    rnsQuotation.setRfqApplicable(quotationData.getRfqApplicable());
                    rnsQuotation.setAuctionApplicable(quotationData.getAuctionApplicable());
                    String workflowStatus = quotationData.getWorkflowStatus();
                    Boolean passVendor = true;
                    Boolean passRFQ = true;
                    if (quotationData.getValidity() == null) {
                        passRFQ = false;
                    }
                    rnsQuotation.setAuctionValidate(passVendor);
                    rnsQuotation.setRfqValidate(passRFQ);
                    rnsQuotationList.add(rnsQuotation);

                    if (rnsQuotation.getApprovedFlag() != null && rnsQuotation.getApprovedFlag().equalsIgnoreCase("C")) {
                        // Rfq Status
                        if (rnsQuotation.getRfqApplicable() != null && rnsQuotation.getRfqApplicable() == true) {
                            if (rnsQuotation.getRfq() != null && rnsQuotation.getRfq() == true) {
                                rnsQuotation.setRfqActive(false);
                                if(rnsQuotation.getValidity() != null && Date.from(rnsQuotation.getValidity().toInstant()).before(new Date())) {
                                    rnsQuotation.setRfqStatus("btn-success");
                                    rnsQuotation.setRfqIcon("check");
                                } else {
                                    rnsQuotation.setRfqStatus("btn-danger");
                                    rnsQuotation.setRfqIcon("check");
                                }
                            } else {
                                rnsQuotation.setRfqActive(true);
                                rnsQuotation.setRfqStatus("btn-success");
                                rnsQuotation.setRfqIcon("times");
                            }
                        } else {
                            rnsQuotation.setRfqActive(false);
                            rnsQuotation.setRfqIcon("times");
                            rnsQuotation.setRfqMessage("RFQ is not applicable for Project# " + quotationData.getId() + "!!!");
                        }

                        // Rfb Status
                        if (rnsQuotation.getAuctionApplicable() != null && rnsQuotation.getAuctionApplicable() == true) {
                            if (rnsQuotation.getAuction() != null && rnsQuotation.getAuction() == true) {
                                rnsQuotation.setRfbActive(false);
                                if(rnsQuotation.getAuctionClose()!= null && rnsQuotation.getAuctionClose() == true) {
                                    rnsQuotation.setRfbStatus("btn-success");
                                    rnsQuotation.setRfbIcon("check");
                                } else {
                                    rnsQuotation.setRfbStatus("btn-danger");
                                    rnsQuotation.setRfbIcon("check");
                                }
                            } else {
                                if (rnsQuotation.getRfqApplicable() != null && rnsQuotation.getRfqApplicable() == true) {
                                    if (rnsQuotation.getRfq() != null && rnsQuotation.getRfq() == true) {
                                        if(rnsQuotation.getValidity() != null && Date.from(rnsQuotation.getValidity().toInstant()).before(new Date())) {
                                            rnsQuotation.setRfbActive(true);
                                            rnsQuotation.setRfbStatus("btn-success");
                                            rnsQuotation.setRfbIcon("times");
                                        } else {
                                            rnsQuotation.setRfbActive(false);
                                            rnsQuotation.setRfbStatus("btn-success");
                                            rnsQuotation.setRfbIcon("times");
                                            rnsQuotation.setRfbMessage("RFQ is not closed!!!");
                                        }
                                    } else {
                                        rnsQuotation.setRfbActive(false);
                                        rnsQuotation.setRfbStatus("btn-success");
                                        rnsQuotation.setRfbIcon("times");
                                        rnsQuotation.setRfbMessage("RFQ initialize first!!!");
                                    }
                                } else {
                                    rnsQuotation.setRfbActive(true);
                                    rnsQuotation.setRfbStatus("btn-success");
                                    rnsQuotation.setRfbIcon("times");
                                }
                            }
                        } else {
                            rnsQuotation.setRfbActive(false);
                            rnsQuotation.setRfbIcon("times");
                            rnsQuotation.setRfbMessage("Bidding is not applicable for Project# " + quotationData.getId() + "!");
                        }

                        //Workflow
                        if (rnsQuotation.getRfqApplicable() != null && rnsQuotation.getRfqApplicable() == true && rnsQuotation.getAuctionApplicable() != null && rnsQuotation.getAuctionApplicable() == true){
                            boolean workflowAllow = false;
                            if (rnsQuotation.getRfq() != null && rnsQuotation.getRfq() == true) {
                                if (rnsQuotation.getValidity() != null && Date.from(rnsQuotation.getValidity().toInstant()).before(new Date())) {
                                    workflowAllow = true;
                                } else {
                                    workflowAllow = false;
                                    rnsQuotation.setWorkflowMessage("RFQ is not expired for Project# " + quotationData.getId() + "!!!");
                                }
                            } else {
                                workflowAllow = false;
                                rnsQuotation.setWorkflowMessage("RFQ is pending for Project# " + quotationData.getId() + "!!!");
                            }
                            if (workflowAllow == true) {
                                if (rnsQuotation.getAuctionClose() != null && rnsQuotation.getAuctionClose() == true) {
                                    workflowAllow = true;
                                } else {
                                    workflowAllow = false;
                                    rnsQuotation.setWorkflowMessage("Bidding is not closed for Project# " + quotationData.getId() + "!!!");
                                }
                            }
                            rnsQuotation.setWorkflowActive(workflowAllow);
                            rnsQuotation.setWorkflowIcon("times");
                            rnsQuotation.setWorkflowStatus("btn-success");
                        } else if (rnsQuotation.getRfqApplicable() != null && rnsQuotation.getRfqApplicable() == true) {
                            boolean workflowAllow = false;
                            if (rnsQuotation.getRfq() != null && rnsQuotation.getRfq() == true) {
                                if (rnsQuotation.getValidity() != null && Date.from(rnsQuotation.getValidity().toInstant()).before(new Date())) {
                                    workflowAllow = true;
                                } else {
                                    workflowAllow = false;
                                    rnsQuotation.setWorkflowMessage("RFQ is not expired for Project# " + quotationData.getId() + "!!!");
                                }
                            } else {
                                workflowAllow = false;
                                rnsQuotation.setWorkflowMessage("RFQ is pending for Project# " + quotationData.getId() + "!!!");
                            }
                            rnsQuotation.setWorkflowActive(workflowAllow);
                            rnsQuotation.setWorkflowIcon("times");
                            rnsQuotation.setWorkflowStatus("btn-success");
                        } else if (rnsQuotation.getAuctionApplicable() != null && rnsQuotation.getAuctionApplicable() == true) {
                            boolean workflowAllow = false;
                            if (rnsQuotation.getAuctionClose() != null && rnsQuotation.getAuctionClose() == true) {
                                workflowAllow = true;
                            } else {
                                workflowAllow = false;
                                rnsQuotation.setWorkflowMessage("Bidding is not closed for Project# " + quotationData.getId() + "!!!");
                            }
                            rnsQuotation.setWorkflowActive(workflowAllow);
                            rnsQuotation.setWorkflowIcon("times");
                            rnsQuotation.setWorkflowStatus("btn-success");
                        }
                        if(workflowStatus != null && workflowStatus.equalsIgnoreCase("F")) {
                            rnsQuotation.setWorkflowIcon("check");
                            rnsQuotation.setWorkflowStatus("btn-danger");
                        } else if(workflowStatus != null && workflowStatus.equalsIgnoreCase("C")) {
                            rnsQuotation.setWorkflowIcon("check");
                            rnsQuotation.setWorkflowStatus("btn-success");
                        }
                    } else {
                        rnsQuotation.setRfqActive(false);
                        rnsQuotation.setRfbActive(false);
                        rnsQuotation.setWorkflowActive(false);

                        rnsQuotation.setRfqIcon("times");
                        rnsQuotation.setRfbIcon("times");
                        rnsQuotation.setWorkflowIcon("times");

                        if (rnsQuotation.getRfqApplicable() != null && rnsQuotation.getRfqApplicable() == true) {
                            rnsQuotation.setRfqStatus("btn-danger");
                        }

                        if (rnsQuotation.getAuctionApplicable() != null && rnsQuotation.getAuctionApplicable() == true) {
                            rnsQuotation.setRfbStatus("btn-danger");
                        }
                        rnsQuotation.setWorkflowStatus("btn-danger");

                        rnsQuotation.setRfqMessage("Project# " + quotationData.getId() + " is not approved!!!");
                        rnsQuotation.setRfbMessage("Project# " + quotationData.getId() + " is not approved!!!");
                        rnsQuotation.setWorkflowMessage("Project# " + quotationData.getId() + " is not approved!!!");
                    }
                }
            }
        }
        return rnsQuotationList;
    }

    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @PostMapping("/rns-quotations-detail-reports")
    @Timed
    public @ResponseBody void getAllRnsQuotationsDetailReports(@RequestBody RnsQuotationSearch rnsQuotationSearch, HttpServletResponse response) {
        log.debug("REST request to get all RnsQuotations");
        //Criteria Builder
        String id = "%";
        String projectTitle="%";

        //End Criteria Builder
        List<RnsQuotationDetailReportBean> rnsQuotationList = new ArrayList<RnsQuotationDetailReportBean>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RnsQuotation> cq = builder.createQuery(RnsQuotation.class);
        Root<RnsQuotation> root = cq.from(RnsQuotation.class);
        cq.select(root);
        Predicate predicate = builder.conjunction();
        if(rnsQuotationSearch.getTitle()!=null && rnsQuotationSearch.getTitle().length()>0)
        {
            predicate = builder.and(predicate, builder.like(builder.upper(root.get("projectTitle")),"%"+rnsQuotationSearch.getTitle().toUpperCase()+"%"));
        }
        if(rnsQuotationSearch.getDateFrom()!=null && rnsQuotationSearch.getDateTo()!=null){
            try {
                System.out.println("Date Range "+rnsQuotationSearch.getDateFrom()+" "+rnsQuotationSearch.getDateTo());
                predicate = builder.and(predicate, builder.between(builder.function("TRUNC",java.time.Instant.class,root.get("createdOn")), new SimpleDateFormat("yyyy-MM-dd").parse(rnsQuotationSearch.getDateFrom()).toInstant(), new SimpleDateFormat("yyyy-MM-dd").parse(rnsQuotationSearch.getDateTo()).toInstant()));
            } catch(ParseException pe){System.out.println("RnsQuotationResource "+pe.getMessage());}

        }
        if(rnsQuotationSearch.getCatgCode()!=null){
            predicate = builder.and(predicate, builder.equal(root.get("rnsCatgCode"),rnsQuotationSearch.getCatgCode()));
        }

        if(rnsQuotationSearch.getProjectType()!=null && rnsQuotationSearch.getProjectType().length()>0){
            predicate = builder.and(predicate, builder.like(root.get("quoteType"),rnsQuotationSearch.getProjectType()+"%"));
        }

        if(rnsQuotationSearch.getSourceTeam()!=null && rnsQuotationSearch.getSourceTeam().length()>0){
            predicate = builder.and(predicate, builder.equal(root.get("sourceTeam"),rnsQuotationSearch.getSourceTeam()));
        }
        if(rnsQuotationSearch.getId()>0)
        {
            predicate = builder.and(builder.equal(root.get("id"),rnsQuotationSearch.getId()));
        }
        cq.where(predicate);
        cq.orderBy(builder.desc(root.get("id")));

        List<RnsQuotation> quotationList = null;
        try {
            quotationList = entityManager.createQuery(cq.select(root)).getResultList();
        } catch(Exception e){}
        if(quotationList!=null && quotationList.size()>0) {
            List<Long> userCatgList = getAllRnsCatgMasters();
            Map<String, RnsSourceTeamMaster> mapSourceTeam = new HashMap<String, RnsSourceTeamMaster>();
            Map<String, Template> mapTemplate = new HashMap<String, Template>();
            Map<String, RnsDelTermsMaster> rnsDelTermsMasterMap = new HashMap<String, RnsDelTermsMaster>();
            Map<String, RnsTaxTermsMaster> rnsTaxTermsMasterMap = new HashMap<String, RnsTaxTermsMaster>();
            Map<String, RnsDelPlaceMaster> rnsDelPlaceMasterMap = new HashMap<String, RnsDelPlaceMaster>();
            Map<String, RnsPayTermsMaster> rnsPayTermsMasterMap = new HashMap<String, RnsPayTermsMaster>();
            for (RnsQuotation quotationData : quotationList) {
                Long quotationId = quotationData.getId();
                if (userCatgList != null && userCatgList.contains(quotationData.getRnsCatgCode().getId())) {
                    List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(quotationData.getId());
                    for(RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants) {
                        RnsQuotationDetailReportBean rnsQuotationDetailReportBean = new RnsQuotationDetailReportBean();
                        rnsQuotationDetailReportBean.setId(quotationData.getId());
                        rnsQuotationDetailReportBean.setCreatedOn(Timestamp.from(quotationData.getCreatedOn()));
                        rnsQuotationDetailReportBean.setCreatedBy(quotationData.getUser().getFirstName() + " " + quotationData.getUser().getLastName());
                        if(quotationData.getUpdatedUser()!=null) {
                            rnsQuotationDetailReportBean.setUpdatedBy(quotationData.getUpdatedUser().getFirstName() + " " + quotationData.getUpdatedUser().getLastName());
                            rnsQuotationDetailReportBean.setUpdatedDate(Timestamp.from(quotationData.getUpdatedDate()));
                        }
                        rnsQuotationDetailReportBean.setEventType(quotationData.getEventType());
                        if(mapSourceTeam.containsKey(quotationData.getSourceTeam())){
                            RnsSourceTeamMaster rnsSourceTeamMaster = mapSourceTeam.get(quotationData.getSourceTeam());
                            rnsQuotationDetailReportBean.setSourceTeam(rnsSourceTeamMaster.getDescription());
                        } else {
                            RnsSourceTeamMaster rnsSourceTeamMaster = rnsSourceTeamMasterRepository.findById(Long.parseLong(quotationData.getSourceTeam())).orElse(new RnsSourceTeamMaster());
                            rnsQuotationDetailReportBean.setSourceTeam(rnsSourceTeamMaster.getDescription());
                            mapSourceTeam.put(quotationData.getSourceTeam(), rnsSourceTeamMaster);
                        }
                        rnsQuotationDetailReportBean.setQuoteType(quotationData.getQuoteType());
                        rnsQuotationDetailReportBean.setPchCode(quotationData.getPchCode());
                        rnsQuotationDetailReportBean.setCrmRequestNumber(quotationData.getCrmRequestNumber());
                        if(quotationData.getTargetPcd()!=null) {
                            rnsQuotationDetailReportBean.setTargetPcd(Date.from(quotationData.getTargetPcd().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        }
                        rnsQuotationDetailReportBean.setRequestedBy(quotationData.getRequestedBy());
                        rnsQuotationDetailReportBean.setBuyerCode(quotationData.getBuyerCode());
                        rnsQuotationDetailReportBean.setBuyerName(quotationData.getBuyerName());
                        rnsQuotationDetailReportBean.setInternalRemarks(quotationData.getInternalRemarks());
                        rnsQuotationDetailReportBean.setMerchantRemarks(quotationData.getMerchantRemarks());
                        rnsQuotationDetailReportBean.setArticleCode(quotationData.getArticleCode());
                        rnsQuotationDetailReportBean.setArticleDesc(quotationData.getArticleDesc());
                        rnsQuotationDetailReportBean.setCatgCode(quotationData.getRnsCatgCode().getCatgCodeDesc());
                        rnsQuotationDetailReportBean.setLotNumber(rnsQuotationVariant.getTitle());
                        Template template = null;
                        if(mapTemplate.containsKey(quotationData.getTemplate())){
                            template = mapTemplate.get(quotationData.getTemplate());
                        } else {
                            template = templateRepository.findById(new Long(quotationData.getTemplate())).orElse(new Template());
                            mapTemplate.put(quotationData.getTemplate(), template);
                        }
                        rnsQuotationDetailReportBean.setVarDescSpec1(template.getSpecificationOne());
                        rnsQuotationDetailReportBean.setVarDescSpec2(template.getSpecificationTwo());
                        rnsQuotationDetailReportBean.setVarDescSpec3(template.getSpecificationThree());
                        rnsQuotationDetailReportBean.setVarDescSpec4(template.getSpecificationFour());
                        rnsQuotationDetailReportBean.setVarDescSpec5(template.getSpecificationFive());
                        rnsQuotationDetailReportBean.setVarDescSpec6(template.getSpecificationSix());
                        rnsQuotationDetailReportBean.setVarDescSpec7(template.getSpecificationSeven());
                        rnsQuotationDetailReportBean.setVarDescSpec8(template.getSpecificationEight());
                        rnsQuotationDetailReportBean.setVarDescSpec9(template.getSpecificationNine());
                        rnsQuotationDetailReportBean.setVarDescSpec10(template.getSpecificationTen());

                        rnsQuotationDetailReportBean.setVarDescSpec1Value(rnsQuotationVariant.getVarDescSpec1Value());
                        rnsQuotationDetailReportBean.setVarDescSpec2Value(rnsQuotationVariant.getVarDescSpec2Value());
                        rnsQuotationDetailReportBean.setVarDescSpec3Value(rnsQuotationVariant.getVarDescSpec3Value());
                        rnsQuotationDetailReportBean.setVarDescSpec4Value(rnsQuotationVariant.getVarDescSpec4Value());
                        rnsQuotationDetailReportBean.setVarDescSpec5Value(rnsQuotationVariant.getVarDescSpec5Value());
                        rnsQuotationDetailReportBean.setVarDescSpec6Value(rnsQuotationVariant.getVarDescSpec6Value());
                        rnsQuotationDetailReportBean.setVarDescSpec7Value(rnsQuotationVariant.getVarDescSpec7Value());
                        rnsQuotationDetailReportBean.setVarDescSpec8Value(rnsQuotationVariant.getVarDescSpec8Value());
                        rnsQuotationDetailReportBean.setVarDescSpec9Value(rnsQuotationVariant.getVarDescSpec9Value());
                        rnsQuotationDetailReportBean.setVarDescSpec10Value(rnsQuotationVariant.getVarDescSpec10Value());

                        try {
                            if (rnsQuotationVariant.getTaxTerms() != null && rnsQuotationVariant.getTaxTerms().length() > 0) {
                                if(rnsTaxTermsMasterMap.containsKey(rnsQuotationVariant.getTaxTerms())) {
                                    RnsTaxTermsMaster rnsTaxTermsMaster = rnsTaxTermsMasterMap.get(rnsQuotationVariant.getTaxTerms());
                                    if (rnsTaxTermsMaster != null) {
                                        rnsQuotationDetailReportBean.setTaxTerms(rnsQuotationVariant.getTaxTerms());
                                        rnsQuotationDetailReportBean.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                                    }
                                } else{
                                    RnsTaxTermsMaster rnsTaxTermsMaster = rnsTaxTermsMasterRepository.findByTaxTermsCode(rnsQuotationVariant.getTaxTerms());
                                    if (rnsTaxTermsMaster != null) {
                                        rnsQuotationDetailReportBean.setTaxTerms(rnsQuotationVariant.getTaxTerms());
                                        rnsQuotationDetailReportBean.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                                        rnsTaxTermsMasterMap.put(rnsQuotationVariant.getTaxTerms(), rnsTaxTermsMaster);
                                    }
                                }
                            }
                            if (rnsQuotationVariant.getDealtermPaymentTerms() != null && rnsQuotationVariant.getDealtermPaymentTerms().length() > 0) {
                                if(rnsPayTermsMasterMap.containsKey(rnsQuotationVariant.getDealtermPaymentTerms())) {
                                    RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterMap.get(rnsQuotationVariant.getDealtermPaymentTerms());
                                    if (rnsPayTermsMaster != null) {
                                        rnsQuotationDetailReportBean.setDealtermPaymentTerms(rnsQuotationVariant.getDealtermPaymentTerms());
                                        rnsQuotationDetailReportBean.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                                    }
                                } else{
                                    RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterRepository.findByPayTermsCode(rnsQuotationVariant.getDealtermPaymentTerms());
                                    if (rnsPayTermsMaster != null) {
                                        rnsQuotationDetailReportBean.setDealtermPaymentTerms(rnsQuotationVariant.getDealtermPaymentTerms());
                                        rnsQuotationDetailReportBean.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                                        rnsPayTermsMasterMap.put(rnsQuotationVariant.getDealtermPaymentTerms(), rnsPayTermsMaster);
                                    }
                                }
                            }
                            if (rnsQuotationVariant.getDealtermDeliveryTerms() != null && rnsQuotationVariant.getDealtermDeliveryTerms().length() > 0) {
                                if(rnsDelTermsMasterMap.containsKey(rnsQuotationVariant.getDealtermDeliveryTerms())) {
                                    RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterMap.get(rnsQuotationVariant.getDealtermDeliveryTerms());
                                    if (rnsDelTermsMaster != null) {
                                        rnsQuotationDetailReportBean.setDealtermDeliveryTerms(rnsQuotationVariant.getDealtermDeliveryTerms());
                                        rnsQuotationDetailReportBean.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                                    }
                                } else{
                                    RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterRepository.findByDelTermsCode(rnsQuotationVariant.getDealtermDeliveryTerms());
                                    if (rnsDelTermsMaster != null) {
                                        rnsQuotationDetailReportBean.setDealtermDeliveryTerms(rnsQuotationVariant.getDealtermDeliveryTerms());
                                        rnsQuotationDetailReportBean.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                                        rnsDelTermsMasterMap.put(rnsQuotationVariant.getDealtermDeliveryTerms(), rnsDelTermsMaster);
                                    }
                                }
                            }
                            if (rnsQuotationVariant.getDealtermDeliverAt() != null && rnsQuotationVariant.getDealtermDeliverAt().length() > 0) {
                                if(rnsDelPlaceMasterMap.containsKey(rnsQuotationVariant.getDealtermDeliverAt())) {
                                    RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterMap.get(rnsQuotationVariant.getDealtermDeliverAt());
                                    if (rnsDelPlaceMaster != null) {
                                        rnsQuotationDetailReportBean.setDealtermDeliverAt(rnsQuotationVariant.getDealtermDeliverAt());
                                        rnsQuotationDetailReportBean.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                                    }
                                } else{
                                    RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterRepository.findByDelPlaceCode(rnsQuotationVariant.getDealtermDeliverAt());
                                    if (rnsDelPlaceMaster != null) {
                                        rnsQuotationDetailReportBean.setDealtermDeliverAt(rnsQuotationVariant.getDealtermDeliverAt());
                                        rnsQuotationDetailReportBean.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                                        rnsDelPlaceMasterMap.put(rnsQuotationVariant.getDealtermDeliverAt(), rnsDelPlaceMaster);
                                    }
                                }
                            }
                        } catch(Exception e){System.out.println("RnsQuotationResource getRnsAuctionQuotationFullDetails()"+ e.getMessage());}

                        rnsQuotationDetailReportBean.setOrderQuantity(rnsQuotationVariant.getOrderQuantity());
                        rnsQuotationDetailReportBean.setOrderUom(rnsQuotationVariant.getOrderUom());
                        rnsQuotationDetailReportBean.setHistoricalPrice(rnsQuotationVariant.getHistoricalPrice());
                        rnsQuotationDetailReportBean.setCurrency(rnsQuotationVariant.getCurrency());
                        rnsQuotationDetailReportBean.setOpenCosting(rnsQuotationVariant.getOpenCosting());
                        rnsQuotationDetailReportBean.setRemarks(rnsQuotationVariant.getRemarks());
                        List<RnsFileUpload> rnsFileUploads = rnsFileUploadRepository.findAll(rnsQuotationVariant.getId(), "L");
                        if(rnsFileUploads !=null && rnsFileUploads.size()>0) {
                            rnsQuotationDetailReportBean.setAttachments("Yes");
                        } else {
                            rnsQuotationDetailReportBean.setAttachments("No");
                        }

                        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariant.getId());
                        if (auctionVrnt != null) {
                            rnsQuotationDetailReportBean.setTextOne(auctionVrnt.getTextOne());
                            rnsQuotationDetailReportBean.setUomOne(auctionVrnt.getUomOne());
                            rnsQuotationDetailReportBean.setConvFactOne(auctionVrnt.getConvFactOne());
                            rnsQuotationDetailReportBean.setTextTwo(auctionVrnt.getTextTwo());
                            rnsQuotationDetailReportBean.setUomTwo(auctionVrnt.getUomTwo());
                            rnsQuotationDetailReportBean.setConvFactTwo(auctionVrnt.getConvFactTwo());
                            rnsQuotationDetailReportBean.setTextThree(auctionVrnt.getTextThree());
                            rnsQuotationDetailReportBean.setUomThree(auctionVrnt.getUomThree());
                            rnsQuotationDetailReportBean.setConvFactThree(auctionVrnt.getConvFactThree());
                            rnsQuotationDetailReportBean.setTextFour(auctionVrnt.getTextFour());
                            rnsQuotationDetailReportBean.setUomFour(auctionVrnt.getUomFour());
                            rnsQuotationDetailReportBean.setConvFactFour(auctionVrnt.getConvFactFour());
                            rnsQuotationDetailReportBean.setTextFive(auctionVrnt.getTextFive());
                            rnsQuotationDetailReportBean.setUomFive(auctionVrnt.getUomFive());
                            rnsQuotationDetailReportBean.setConvFactFive(auctionVrnt.getConvFactFive());
                            rnsQuotationDetailReportBean.setTextSix(auctionVrnt.getTextSix());
                            rnsQuotationDetailReportBean.setUomSix(auctionVrnt.getUomSix());
                            rnsQuotationDetailReportBean.setConvFactSix(auctionVrnt.getConvFactSix());
                            rnsQuotationDetailReportBean.setTextSeven(auctionVrnt.getTextSeven());
                            rnsQuotationDetailReportBean.setUomSeven(auctionVrnt.getUomSeven());
                            rnsQuotationDetailReportBean.setConvFactSeven(auctionVrnt.getConvFactSeven());
                            rnsQuotationDetailReportBean.setTextEight(auctionVrnt.getTextEight());
                            rnsQuotationDetailReportBean.setUomEight(auctionVrnt.getUomEight());
                            rnsQuotationDetailReportBean.setConvFactEight(auctionVrnt.getConvFactEight());
                            rnsQuotationDetailReportBean.setTextNine(auctionVrnt.getTextNine());
                            rnsQuotationDetailReportBean.setUomNine(auctionVrnt.getUomNine());
                            rnsQuotationDetailReportBean.setConvFactNine(auctionVrnt.getConvFactNine());
                            rnsQuotationDetailReportBean.setTextTen(auctionVrnt.getTextTen());
                            rnsQuotationDetailReportBean.setUomTen(auctionVrnt.getUomTen());
                            rnsQuotationDetailReportBean.setConvFactTen(auctionVrnt.getConvFactTen());
                        }
                        if(quotationData.getRfqPublishDate()!=null){
                            rnsQuotationDetailReportBean.setRfqSent("Yes");
                        } else{
                            rnsQuotationDetailReportBean.setRfqSent("No");
                        }

                        if(quotationData.getAuctionPublishDate()!=null){
                            rnsQuotationDetailReportBean.setRfbSent("Yes");
                        } else{
                            rnsQuotationDetailReportBean.setRfbSent("No");
                        }
                        rnsQuotationList.add(rnsQuotationDetailReportBean);
                    }
                }
            }
        }
        String path = applicationProperties.getTemplatePath()+"jasper/";
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(path+"/quotationDetails.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String,Object> parameters = new HashMap<String, Object>();
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(rnsQuotationList);
            parameters.put("datasource", jrDataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, jrDataSource);
            if(rnsQuotationSearch.getType() !=null && rnsQuotationSearch.getType().equalsIgnoreCase("CSV")) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=rnsQuotationDetails.csv");
                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                OutputStream ouputStream = response.getOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                exporter.exportReport();
            } else if(rnsQuotationSearch.getType() !=null && rnsQuotationSearch.getType().equalsIgnoreCase("XLSX")) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=rnsQuotationDetails.xlsx");
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                OutputStream ouputStream = response.getOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                exporter.exportReport();
            } else if(rnsQuotationSearch.getType() !=null && rnsQuotationSearch.getType().equalsIgnoreCase("PDF")){
                response.setContentType("application/x-pdf");
                response.setHeader("Content-Disposition","attachment; filename=templateMasters.pdf");
                final OutputStream outputStream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
            }
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Long> getAllRnsCatgMasters() {
        log.debug("REST request to get all RnsCatgMasters");

        return rnsCatgMasterRepository.findAllWithEagerRelationships(getCurrentUserLogin());
    }


    /*public List<String> getAllPchCode() {
        log.debug("REST request to get all PchCode");
        return rnsPchMasterRepository.findAllWithEagerRelationships(getCurrentUserLogin());
    }
    public List<RnsPchMaster> getAllPchCode1() {
        log.debug("REST request to get all PchCode");
        return rnsPchMasterRepository.findAll();
    }*/

    private Sort sortByIdAscVendor() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotations-rns-vendor-quotations")
    @Timed
    public List<RnsQuotationFullDetails> getRnsVendorQuotations() throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get all RnsQuotations");
        String vendorCode = getCurrentUserLogin();
        List<RnsQuotationFullDetails> responseList = new ArrayList<RnsQuotationFullDetails>();
        List<RnsQuotation> quotationList = rnsQuotationRepository.findByVendorId(vendorCode);
        Map<Long, Long> lotCount = new HashMap<Long, Long>();

        Date date = new Date();

        for (RnsQuotation quotation : quotationList) {
            if (lotCount.containsKey(quotation.getId())) {
                Long counter = lotCount.get(quotation.getId()) + 1;
                lotCount.put(quotation.getId(), counter);
            } else {
                lotCount.put(quotation.getId(), new Long(1));
            }
        }

        List<String> quotations = new ArrayList<String>();
        for (RnsQuotation quotation : quotationList) {
            if(quotations.contains(quotation.getId().toString())){}
            else{
                RnsQuotationFullDetails rnsQuotationFullDetails = new RnsQuotationFullDetails();
                BeanUtils.copyProperties(rnsQuotationFullDetails, quotation);
                rnsQuotationFullDetails.setNoOfLot(lotCount.get(rnsQuotationFullDetails.getId()));
                if(rnsQuotationFullDetails.getValidity().before(date)){
                    rnsQuotationFullDetails.setRfqStatus("Close");
                } else{
                    rnsQuotationFullDetails.setRfqStatus("Open");
                }
                responseList.add(rnsQuotationFullDetails);
                quotations.add(quotation.getId().toString());
            }
        }
        return responseList;
    }

    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotations-auction-vendor-quotations")
    @Timed
    public List<RnsQuotationFullDetails> getAuctionVendorQuotations() throws InvocationTargetException, IllegalAccessException{
        log.debug("REST request to get all RnsQuotations");
        String vendorCode = getCurrentUserLogin();

        List<RnsQuotationFullDetails> responseList = new ArrayList<RnsQuotationFullDetails>();
        List<RnsQuotation> quotationList = rnsQuotationRepository.findAuctionByVendorId(vendorCode);

        Map<Long, Long> lotCount = new HashMap<Long, Long>();
        for(RnsQuotation quotation: quotationList){
            if(lotCount.containsKey(quotation.getId())){
                Long counter = lotCount.get(quotation.getId())+1;
                lotCount.put(quotation.getId(),counter);
            } else{
                lotCount.put(quotation.getId(),new Long(1));
            }
        }
        for(RnsQuotation quotation: quotationList){
            RnsQuotationFullDetails rnsQuotationFullDetails = new RnsQuotationFullDetails();
            if(quotation.getAuction() == true){
                boolean isAdded = false;
                for(RnsQuotationFullDetails quotationItem:responseList) {
                    if(quotationItem.getId()==quotation.getId()) {
                        isAdded = true;
                    }
                }
                if(!isAdded) {
                    BeanUtils.copyProperties(rnsQuotationFullDetails, quotation);
                    rnsQuotationFullDetails.setNoOfLot(lotCount.get(rnsQuotationFullDetails.getId()));
                    rnsQuotationFullDetails.setAuctionDetails(auctionRepository.getAuctionByQuotationId(rnsQuotationFullDetails.getId()));
                    responseList.add(rnsQuotationFullDetails);
                }
            }
        }
        return responseList;
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
     * GET  /rns-quotations/:id : get the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotation, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotations/{id}")
    @Timed
    public ResponseEntity<RnsQuotation> getRnsQuotation(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotation : {}", id);
        Optional<RnsQuotation> rnsQuotation = rnsQuotationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsQuotation);
    }

    /**
     * GET  /rns-quotations/:id : get the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotation, or with status 404 (Not Found)
     */
    @GetMapping("/rns-auction-quotation-details/{id}")
    @Timed
    public ResponseEntity<RnsQuotationFullDetails> getRnsQuotationFullDetails(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get RnsQuotation : {}", id);


        String vendorCode = getCurrentUserLogin();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);

        List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotation(id);
        Map<Long, AuctionVarDetails> auctionVarDetailsMap = new HashMap<Long, AuctionVarDetails>();
        for(AuctionVarDetails auctionVarDetails : auctionVarDetailsList){
            auctionVarDetailsMap.put(auctionVarDetails.getVariantId(), auctionVarDetails);
        }

        Template template=null;
        if(rnsQuotation.getTemplate()!=null && rnsQuotation.getTemplate().length()>0) {
            template = templateRepository.findTemplateById(new Long(rnsQuotation.getTemplate()));
        }
        if(template==null){
            template = new Template();
        }

        RnsQuotationFullDetails rnsQuotationFullDetails = new RnsQuotationFullDetails();
        BeanUtils.copyProperties(rnsQuotationFullDetails,rnsQuotation);

        AuctionPauseDetails auctionPauseDetails = auctionPauseDetailsRepository.findAuctionPauseDetailsByQuotationId(id);
        if(auctionPauseDetails!=null){
            rnsQuotationFullDetails.setPaused(true);
        } else{
            rnsQuotationFullDetails.setPaused(false);
        }
        Auction auction = auctionRepository.getAuctionByQuotationId(id);
        rnsQuotationFullDetails.setAuctionDetails(auction);

        List<RnsQuotationVariant> Datavariants= rnsQuotationRepository.getRnsQuotationVariantsList(id);
        Set<RnsQuotationVariantFullDetails> fullVariant = new HashSet<RnsQuotationVariantFullDetails>();

        Map<String, RnsDelTermsMaster> rnsDelTermsMasterMap = new HashMap<String, RnsDelTermsMaster>();
        Map<String, RnsTaxTermsMaster> rnsTaxTermsMasterMap = new HashMap<String, RnsTaxTermsMaster>();
        Map<String, RnsDelPlaceMaster> rnsDelPlaceMasterMap = new HashMap<String, RnsDelPlaceMaster>();
        Map<String, RnsPayTermsMaster> rnsPayTermsMasterMap = new HashMap<String, RnsPayTermsMaster>();
        Integer inc = 0;
        for(RnsQuotationVariant rqvariant: Datavariants){

            List<RnsQuotationVendors> vendors = rnsQuotationRepository.getRnsQuotationVendorsByVariantList(rqvariant.getId());
            Set<RnsQuotationVendorsBean> setVendors = new HashSet<RnsQuotationVendorsBean>();
            Boolean vendorFound = false;
            for (RnsQuotationVendors item : vendors) {
                //System.out.println(item.getVendorCode());
                if(item.getVendorCode().equals(vendorCode)) {
                    RnsQuotationVendorsBean rqvbean = new RnsQuotationVendorsBean();
                    BeanUtils.copyProperties(rqvbean, item);
                    setVendors.add(rqvbean);
                    vendorFound = true;
                }
            }
            if(vendorFound) {
                ++inc;
                RnsQuotationVariantFullDetails rnsQuotationVariantFullDetails = new RnsQuotationVariantFullDetails();
                BeanUtils.copyProperties(rnsQuotationVariantFullDetails, rqvariant);
                rnsQuotationVariantFullDetails.setVendors(setVendors);
                rnsQuotationVariantFullDetails.setVariantRank(inc);

                //Auction Variant
                AuctionVrnt auctionVrnt= auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantFullDetails.getId());

                //Auction Price
                VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVariantFullDetails.getId(), getCurrentUserLogin());
                //End Auction Price
                if(auctionVrnt!=null && vndrPrice!=null) {
                    auctionVrnt.setPriceOne(vndrPrice.getPriceOne());
                    auctionVrnt.setPriceTwo(vndrPrice.getPriceTwo());
                    auctionVrnt.setPriceThree(vndrPrice.getPriceThree());
                    auctionVrnt.setPriceFour(vndrPrice.getPriceFour());
                    auctionVrnt.setPriceFive(vndrPrice.getPriceFive());
                    auctionVrnt.setPriceSix(vndrPrice.getPriceSix());
                    auctionVrnt.setPriceSeven(vndrPrice.getPriceSeven());
                    auctionVrnt.setPriceEight(vndrPrice.getPriceEight());
                    auctionVrnt.setPriceNine(vndrPrice.getPriceNine());
                    auctionVrnt.setPriceTen(vndrPrice.getPriceTen());
                } else if(auctionVrnt!=null){
                    auctionVrnt.setPriceOne(0.0F);
                    auctionVrnt.setPriceTwo(0.0F);
                    auctionVrnt.setPriceThree(0.0F);
                    auctionVrnt.setPriceFour(0.0F);
                    auctionVrnt.setPriceFive(0.0F);
                    auctionVrnt.setPriceSix(0.0F);
                    auctionVrnt.setPriceSeven(0.0F);
                    auctionVrnt.setPriceEight(0.0F);
                    auctionVrnt.setPriceNine(0.0F);
                    auctionVrnt.setPriceTen(0.0F);
                }
                rnsQuotationVariantFullDetails.setAuctionVrnt(auctionVrnt);

                if(auctionVarDetailsMap.containsKey(rnsQuotationVariantFullDetails.getId())) {
                    AuctionVarDetails auctionVarDetails = auctionVarDetailsMap.get(rnsQuotationVariantFullDetails.getId());
                    rnsQuotationVariantFullDetails.setLotStartTime(auctionVarDetails.getLotStartTime());
                    rnsQuotationVariantFullDetails.setLotEndTime(auctionVarDetails.getLotEndTime());//startTime.plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES)
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(rnsQuotationVariantFullDetails.getLotStartTime()!=null){
                        rnsQuotationVariantFullDetails.setLotStartTimeDateFormat(format.format(Date.from(rnsQuotationVariantFullDetails.getLotStartTime())));
                    }
                    if(rnsQuotationVariantFullDetails.getLotEndTime()!=null){
                        rnsQuotationVariantFullDetails.setLotEndTimeDateFormat(format.format(Date.from(rnsQuotationVariantFullDetails.getLotEndTime())));
                    }
                }

                rnsQuotationVariantFullDetails.setVarDescSpec1(template.getSpecificationOne());
                rnsQuotationVariantFullDetails.setVarDescSpec2(template.getSpecificationTwo());
                rnsQuotationVariantFullDetails.setVarDescSpec3(template.getSpecificationThree());
                rnsQuotationVariantFullDetails.setVarDescSpec4(template.getSpecificationFour());
                rnsQuotationVariantFullDetails.setVarDescSpec5(template.getSpecificationFive());
                rnsQuotationVariantFullDetails.setVarDescSpec6(template.getSpecificationSix());
                rnsQuotationVariantFullDetails.setVarDescSpec7(template.getSpecificationSeven());
                rnsQuotationVariantFullDetails.setVarDescSpec8(template.getSpecificationEight());
                rnsQuotationVariantFullDetails.setVarDescSpec9(template.getSpecificationNine());
                rnsQuotationVariantFullDetails.setVarDescSpec10(template.getSpecificationTen());

                rnsQuotationVariantFullDetails.setShowInAuctionOneRequired(template.getShowInAuctionOneRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionTwoRequired(template.getShowInAuctionTwoRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionThreeRequired(template.getShowInAuctionThreeRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionFourRequired(template.getShowInAuctionFourRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionFiveRequired(template.getShowInAuctionFiveRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionSixRequired(template.getShowInAuctionSixRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionSevenRequired(template.getShowInAuctionSevenRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionEightRequired(template.getShowInAuctionEightRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionNineRequired(template.getShowInAuctionNineRequired());
                rnsQuotationVariantFullDetails.setShowInAuctionTenRequired(template.getShowInAuctionTenRequired());

                if(inc==1){
                    rnsQuotationFullDetails.setDisplayVariant(rnsQuotationVariantFullDetails.getTitle());
                }
                try {
                    if (rnsQuotationVariantFullDetails.getDealtermDeliveryTerms() != null && rnsQuotationVariantFullDetails.getDealtermDeliveryTerms().length() > 0) {
                        if(rnsDelTermsMasterMap.containsKey(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms())) {
                            RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterMap.get(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms());
                            if (rnsDelTermsMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                        } else{
                            RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterRepository.findByDelTermsCode(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms());
                            if (rnsDelTermsMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                                rnsDelTermsMasterMap.put(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms(), rnsDelTermsMaster);
                            }
                        }
                    }
                    if (rnsQuotationVariantFullDetails.getTaxTerms() != null && rnsQuotationVariantFullDetails.getTaxTerms().length() > 0) {
                        if(rnsTaxTermsMasterMap.containsKey(rnsQuotationVariantFullDetails.getTaxTerms())) {
                            RnsTaxTermsMaster rnsTaxTermsMaster = rnsTaxTermsMasterMap.get(rnsQuotationVariantFullDetails.getTaxTerms());
                            if (rnsTaxTermsMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                        } else{
                            RnsTaxTermsMaster rnsTaxTermsMaster = rnsTaxTermsMasterRepository.findByTaxTermsCode(rnsQuotationVariantFullDetails.getTaxTerms());
                            if (rnsTaxTermsMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                                rnsTaxTermsMasterMap.put(rnsQuotationVariantFullDetails.getTaxTerms(), rnsTaxTermsMaster);
                            }
                        }
                    }
                    if (rnsQuotationVariantFullDetails.getDealtermDeliverAt() != null && rnsQuotationVariantFullDetails.getDealtermDeliverAt().length() > 0) {
                        if(rnsDelPlaceMasterMap.containsKey(rnsQuotationVariantFullDetails.getDealtermDeliverAt())) {
                            RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterMap.get(rnsQuotationVariantFullDetails.getDealtermDeliverAt());
                            if (rnsDelPlaceMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                        } else{
                            RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterRepository.findByDelPlaceCode(rnsQuotationVariantFullDetails.getDealtermDeliverAt());
                            if (rnsDelPlaceMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                                rnsDelPlaceMasterMap.put(rnsQuotationVariantFullDetails.getDealtermDeliverAt(), rnsDelPlaceMaster);
                            }
                        }
                    }
                    if (rnsQuotationVariantFullDetails.getDealtermPaymentTerms() != null && rnsQuotationVariantFullDetails.getDealtermPaymentTerms().length() > 0) {
                        if(rnsPayTermsMasterMap.containsKey(rnsQuotationVariantFullDetails.getDealtermPaymentTerms())) {
                            RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterMap.get(rnsQuotationVariantFullDetails.getDealtermPaymentTerms());
                            if (rnsPayTermsMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                        } else{
                            RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterRepository.findByPayTermsCode(rnsQuotationVariantFullDetails.getDealtermPaymentTerms());
                            if (rnsPayTermsMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                                rnsPayTermsMasterMap.put(rnsQuotationVariantFullDetails.getDealtermPaymentTerms(), rnsPayTermsMaster);
                            }
                        }
                    }
                } catch(Exception e){System.out.println("RnsQuotationResource getRnsAuctionQuotationFullDetails()"+ e.getMessage());}
                fullVariant.add(rnsQuotationVariantFullDetails);
            }

        }
        rnsQuotationFullDetails.setVariants(fullVariant);

        //List<RnsQuotationVariantFullDetails> variantsFD = variants;
        //RnsQuotationFullDetails.setVariants(variantsFD);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsQuotationFullDetails));
    }


    /**
     * GET  /rns-quotations/:id : get the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotation, or with status 404 (Not Found)
     */
    @GetMapping("/rns-auction-quotation-variant-details/{id}")
    @Timed
    public ResponseEntity<List<RnsQuotationVariantFullDetails>> getRnsQuotationVariantFullDetails(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get RnsQuotation : {}", id);

        List<RnsQuotationVariant> Datavariants = rnsQuotationRepository.getRnsQuotationVariantsList(id);
        List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotation(id);
        Map<Long, AuctionVarDetails> auctionVarDetailsMap = new HashMap<Long, AuctionVarDetails>();
        for(AuctionVarDetails auctionVarDetails : auctionVarDetailsList){
            auctionVarDetailsMap.put(auctionVarDetails.getVariantId(), auctionVarDetails);
        }

        Integer inc = 0;
        List<RnsQuotationVariantFullDetails> fullVariant = new ArrayList<RnsQuotationVariantFullDetails>();
        for (RnsQuotationVariant rqvariant : Datavariants) {
            RnsQuotationVariantFullDetails rnsQuotationVariantFullDetails = new RnsQuotationVariantFullDetails();
            BeanUtils.copyProperties(rnsQuotationVariantFullDetails, rqvariant);
            rnsQuotationVariantFullDetails.setVariantRank(inc);
            if(auctionVarDetailsMap.containsKey(rnsQuotationVariantFullDetails.getId())) {
                AuctionVarDetails auctionVarDetails = auctionVarDetailsMap.get(rnsQuotationVariantFullDetails.getId());
                rnsQuotationVariantFullDetails.setLotStartTime(auctionVarDetails.getLotStartTime());
                rnsQuotationVariantFullDetails.setLotEndTime(auctionVarDetails.getLotEndTime());//startTime.plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES)
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(rnsQuotationVariantFullDetails.getLotStartTime()!=null){
                    rnsQuotationVariantFullDetails.setLotStartTimeDateFormat(format.format(Date.from(rnsQuotationVariantFullDetails.getLotStartTime())));
                }
                if(rnsQuotationVariantFullDetails.getLotEndTime()!=null){
                    rnsQuotationVariantFullDetails.setLotEndTimeDateFormat(format.format(Date.from(rnsQuotationVariantFullDetails.getLotEndTime())));
                }
            }
            fullVariant.add(rnsQuotationVariantFullDetails);
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fullVariant));
    }

    /**
     * GET  /rns-quotations/:id : get the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotation, or with status 404 (Not Found)
     */
    @GetMapping("/rns-auction-quotation-details/full/{id}")
    @Timed
    public ResponseEntity<RnsQuotationFullDetails> getRnsAuctionQuotationFullDetails(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get RnsQuotation : {}", id);


        //String vendorCode = getCurrentUserLogin();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);

        Template template=null;
        if(rnsQuotation.getTemplate()!=null && rnsQuotation.getTemplate().length()>0) {
            template = templateRepository.findTemplateById(new Long(rnsQuotation.getTemplate()));
        }
        if(template==null){
            template = new Template();
        }

        RnsQuotationFullDetails rnsQuotationFullDetails = new RnsQuotationFullDetails();
        BeanUtils.copyProperties(rnsQuotationFullDetails,rnsQuotation);

        Auction auction = auctionRepository.getAuctionByQuotationId(id);
        rnsQuotationFullDetails.setAuctionDetails(auction);

        List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotation(id);
        Map<Long, AuctionVarDetails> auctionVarDetailsMap = new HashMap<Long, AuctionVarDetails>();
        for(AuctionVarDetails auctionVarDetails : auctionVarDetailsList){
            auctionVarDetailsMap.put(auctionVarDetails.getVariantId(), auctionVarDetails);
        }
        List<RnsQuotationVariant> Datavariants= rnsQuotationRepository.getRnsQuotationVariantsList(id);
        List<RnsQuotationVendors> vendors = rnsQuotationRepository.getRnsQuotationVendorsList(id);

        AuctionPauseDetails auctionPauseDetails = auctionPauseDetailsRepository.findAuctionPauseDetailsByQuotationId(id);
        if(auctionPauseDetails!=null){
            rnsQuotationFullDetails.setPaused(true);
            rnsQuotationFullDetails.setAuctionPauseDetails(auctionPauseDetails);
        } else{
            rnsQuotationFullDetails.setPaused(false);
        }

        Set<RnsQuotationVariantFullDetails> fullVariant = new HashSet<RnsQuotationVariantFullDetails>();
        Integer inc = 0;
        Map<String, RnsDelTermsMaster> rnsDelTermsMasterMap = new HashMap<String, RnsDelTermsMaster>();
        Map<String, RnsTaxTermsMaster> rnsTaxTermsMasterMap = new HashMap<String, RnsTaxTermsMaster>();
        Map<String, RnsDelPlaceMaster> rnsDelPlaceMasterMap = new HashMap<String, RnsDelPlaceMaster>();
        Map<String, RnsPayTermsMaster> rnsPayTermsMasterMap = new HashMap<String, RnsPayTermsMaster>();
        for(RnsQuotationVariant rqvariant: Datavariants){
            ++inc;
            Set<RnsQuotationVendorsBean> setVendors = new HashSet<RnsQuotationVendorsBean>();
            Boolean vendorFound = false;
            for (RnsQuotationVendors item : vendors) {
                if(item.getVariant().getId().longValue()==rqvariant.getId().longValue()) {
                    RnsQuotationVendorsBean rqvbean = new RnsQuotationVendorsBean();
                    BeanUtils.copyProperties(rqvbean, item);
                    setVendors.add(rqvbean);
                    vendorFound = true;
                }
            }
            if(vendorFound) {
                RnsQuotationVariantFullDetails rnsQuotationVariantFullDetails = new RnsQuotationVariantFullDetails();
                BeanUtils.copyProperties(rnsQuotationVariantFullDetails, rqvariant);
                rnsQuotationVariantFullDetails.setVendors(setVendors);
                rnsQuotationVariantFullDetails.setVariantRank(inc);

                //Auction Variant
                AuctionVrnt auctionVrnt= auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantFullDetails.getId());
                rnsQuotationVariantFullDetails.setAuctionVrnt(auctionVrnt);

                if(auctionVarDetailsMap.containsKey(rnsQuotationVariantFullDetails.getId())) {
                    AuctionVarDetails auctionVarDetails = auctionVarDetailsMap.get(rnsQuotationVariantFullDetails.getId());
                    rnsQuotationVariantFullDetails.setLotStartTime(auctionVarDetails.getLotStartTime());
                    rnsQuotationVariantFullDetails.setLotEndTime(auctionVarDetails.getLotEndTime());//startTime.plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES)
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(rnsQuotationVariantFullDetails.getLotStartTime()!=null){
                        rnsQuotationVariantFullDetails.setLotStartTimeDateFormat(format.format(Date.from(rnsQuotationVariantFullDetails.getLotStartTime())));
                    }
                    if(rnsQuotationVariantFullDetails.getLotEndTime()!=null){
                        rnsQuotationVariantFullDetails.setLotEndTimeDateFormat(format.format(Date.from(rnsQuotationVariantFullDetails.getLotEndTime())));
                    }
                }

                rnsQuotationVariantFullDetails.setVarDescSpec1(template.getSpecificationOne());
                rnsQuotationVariantFullDetails.setVarDescSpec2(template.getSpecificationTwo());
                rnsQuotationVariantFullDetails.setVarDescSpec3(template.getSpecificationThree());
                rnsQuotationVariantFullDetails.setVarDescSpec4(template.getSpecificationFour());
                rnsQuotationVariantFullDetails.setVarDescSpec5(template.getSpecificationFive());
                rnsQuotationVariantFullDetails.setVarDescSpec6(template.getSpecificationSix());
                rnsQuotationVariantFullDetails.setVarDescSpec7(template.getSpecificationSeven());
                rnsQuotationVariantFullDetails.setVarDescSpec8(template.getSpecificationEight());
                rnsQuotationVariantFullDetails.setVarDescSpec9(template.getSpecificationNine());
                rnsQuotationVariantFullDetails.setVarDescSpec10(template.getSpecificationTen());

                if(inc==1){
                    rnsQuotationFullDetails.setDisplayVariant(rnsQuotationVariantFullDetails.getTitle());
                }
                try {
                    if (rnsQuotationVariantFullDetails.getDealtermDeliveryTerms() != null && rnsQuotationVariantFullDetails.getDealtermDeliveryTerms().length() > 0) {
                        if(rnsDelTermsMasterMap.containsKey(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms())) {
                            RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterMap.get(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms());
                            if (rnsDelTermsMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                        } else{
                            RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterRepository.findByDelTermsCode(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms());
                            if (rnsDelTermsMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                                rnsDelTermsMasterMap.put(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms(), rnsDelTermsMaster);
                            }
                        }
                    }
                    if (rnsQuotationVariantFullDetails.getTaxTerms() != null && rnsQuotationVariantFullDetails.getTaxTerms().length() > 0) {
                        if(rnsTaxTermsMasterMap.containsKey(rnsQuotationVariantFullDetails.getTaxTerms())) {
                            RnsTaxTermsMaster rnsTaxTermsMaster = rnsTaxTermsMasterMap.get(rnsQuotationVariantFullDetails.getTaxTerms());
                            if (rnsTaxTermsMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                        } else{
                            RnsTaxTermsMaster rnsTaxTermsMaster = rnsTaxTermsMasterRepository.findByTaxTermsCode(rnsQuotationVariantFullDetails.getTaxTerms());
                            if (rnsTaxTermsMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                                rnsTaxTermsMasterMap.put(rnsQuotationVariantFullDetails.getTaxTerms(), rnsTaxTermsMaster);
                            }
                        }
                    }
                    if (rnsQuotationVariantFullDetails.getDealtermDeliverAt() != null && rnsQuotationVariantFullDetails.getDealtermDeliverAt().length() > 0) {
                        if(rnsDelPlaceMasterMap.containsKey(rnsQuotationVariantFullDetails.getDealtermDeliverAt())) {
                            RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterMap.get(rnsQuotationVariantFullDetails.getDealtermDeliverAt());
                            if (rnsDelPlaceMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                        } else{
                            RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterRepository.findByDelPlaceCode(rnsQuotationVariantFullDetails.getDealtermDeliverAt());
                            if (rnsDelPlaceMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                                rnsDelPlaceMasterMap.put(rnsQuotationVariantFullDetails.getDealtermDeliverAt(), rnsDelPlaceMaster);
                            }
                        }
                    }
                    if (rnsQuotationVariantFullDetails.getDealtermPaymentTerms() != null && rnsQuotationVariantFullDetails.getDealtermPaymentTerms().length() > 0) {
                        if(rnsPayTermsMasterMap.containsKey(rnsQuotationVariantFullDetails.getDealtermPaymentTerms())) {
                            RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterMap.get(rnsQuotationVariantFullDetails.getDealtermPaymentTerms());
                            if (rnsPayTermsMaster != null)
                                rnsQuotationVariantFullDetails.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                        } else{
                            RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterRepository.findByPayTermsCode(rnsQuotationVariantFullDetails.getDealtermPaymentTerms());
                            if (rnsPayTermsMaster != null) {
                                rnsQuotationVariantFullDetails.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                                rnsPayTermsMasterMap.put(rnsQuotationVariantFullDetails.getDealtermPaymentTerms(), rnsPayTermsMaster);
                            }
                        }
                    }
                } catch(Exception e){System.out.println("RnsQuotationResource getRnsAuctionQuotationFullDetails()"+ e.getMessage());}
                fullVariant.add(rnsQuotationVariantFullDetails);
            }

        }
        rnsQuotationFullDetails.setVariants(fullVariant);

        //List<RnsQuotationVariantFullDetails> variantsFD = variants;
        //RnsQuotationFullDetails.setVariants(variantsFD);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsQuotationFullDetails));
    }

    /**
     * DELETE  /rns-quotations/:id : delete the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsQuotation(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotation : {}", id);
        rnsQuotationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @GetMapping("/rns-quotation-variants/getByQuotationId/{id}")
    @Timed
    public ResponseEntity<List<RnsQuotationVariantFullDetails>> getRnsQuotationVariants(@PathVariable Long id) throws IllegalAccessException, InvocationTargetException {
        log.debug("REST request to get all RnsQuotations Variants");
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);

        List<RnsQuotationVariant> variantDataList= rnsQuotationRepository.getRnsQuotationVariantsList(id);



        List<RnsQuotationVariantFullDetails> fullVariantDetails = new ArrayList<RnsQuotationVariantFullDetails>();
        for(RnsQuotationVariant rnsQuoVariants: variantDataList) {
            RnsQuotationVariantFullDetails rnsQuotationVariantFullDetails = new RnsQuotationVariantFullDetails();

            BeanUtils.copyProperties(rnsQuotationVariantFullDetails,rnsQuoVariants);

            if(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms()!=null && rnsQuotationVariantFullDetails.getDealtermDeliveryTerms().length()>0){
                RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterRepository.findByDelTermsCode(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms());
                if(rnsDelTermsMaster!=null)
                    rnsQuotationVariantFullDetails.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
            }
            if(rnsQuotationVariantFullDetails.getTaxTerms()!=null && rnsQuotationVariantFullDetails.getTaxTerms().length()>0) {
                RnsTaxTermsMaster rnsTaxTermsMaster =rnsTaxTermsMasterRepository.findByTaxTermsCode(rnsQuotationVariantFullDetails.getTaxTerms());
                if(rnsTaxTermsMaster!=null)
                    rnsQuotationVariantFullDetails.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
            }
            if(rnsQuotationVariantFullDetails.getDealtermDeliverAt()!=null && rnsQuotationVariantFullDetails.getDealtermDeliverAt().length()>0) {
                RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterRepository.findByDelPlaceCode(rnsQuotationVariantFullDetails.getDealtermDeliverAt());
                if(rnsDelPlaceMaster!=null)
                    rnsQuotationVariantFullDetails.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
            }
            if(rnsQuotationVariantFullDetails.getDealtermPaymentTerms()!=null && rnsQuotationVariantFullDetails.getDealtermPaymentTerms().length()>0){
                RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterRepository.findByPayTermsCode(rnsQuotationVariantFullDetails.getDealtermPaymentTerms());
                if(rnsPayTermsMaster!=null)
                    rnsQuotationVariantFullDetails.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
            }
            fullVariantDetails.add(rnsQuotationVariantFullDetails);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fullVariantDetails));
    }


    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotation-variants-getByvendors")
    @Timed
    public List<RnsQuotationVariant> getRnsVendorQuotationVariants() {
        log.debug("REST request to get all RnsvendorQuotations Variants");
        List<RnsQuotationVariant> variants= rnsQuotationRepository.getRnsQuotationVariantsByVendorId();
        return variants;
    }


    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotation-vendors/getByQuotationId/{id}")
    @Timed
    public List<RnsQuotationVendorsFullDetails> getRnsQuotationVendors(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get all RnsQuotations Variants");
        List<RnsQuotationVendorsFullDetails> rnsQuotationVendorsFullDetails = new ArrayList<RnsQuotationVendorsFullDetails>();
        List<RnsQuotationVendors> vendors= rnsQuotationRepository.getRnsQuotationVendorsList(id);

        Map<Long, AuctionVrnt> vrntMap = new HashMap<Long, AuctionVrnt>();
        Map<String, User> vendorMap = new HashMap<String, User>();
        for(RnsQuotationVendors vendor: vendors){
            RnsQuotationVendorsFullDetails vendorsFullDetails = new RnsQuotationVendorsFullDetails();
            BeanUtils.copyProperties(vendorsFullDetails,vendor);
            VndrPrice price = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(vendorsFullDetails.getVariant().getId(),vendorsFullDetails.getVendorCode());
            List<RnsVendorRemark> rnsVendorRemarks = rnsVendorRemarkRepository.findAllBySort(id, vendor.getVendorCode());
            if(rnsVendorRemarks!=null && rnsVendorRemarks.size()>0){
                vendorsFullDetails.setRemarkExist(true);
            } else{
                vendorsFullDetails.setRemarkExist(false);
            }
            AuctionVrnt auctionVrnt = null;
            if(vrntMap.containsKey(vendorsFullDetails.getVariant().getId())){
                auctionVrnt = vrntMap.get(vendorsFullDetails.getVariant().getId());
            } else{
                auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(vendorsFullDetails.getVariant().getId());
                vrntMap.put(vendorsFullDetails.getVariant().getId(),auctionVrnt);
            }
            if(vendorMap.containsKey(vendorsFullDetails.getVendorCode())) {
                vendorsFullDetails.setVendor(vendorMap.get(vendorsFullDetails.getVendorCode()));
            } else {
                User user = userRepository.findByLogin(vendorsFullDetails.getVendorCode());
                vendorsFullDetails.setVendor(user);
                vendorMap.put(vendorsFullDetails.getVendorCode(), user);
            }
            if(price!=null){
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne()!=null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo()!=null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree()!=null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour()!=null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive()!=null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix()!=null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven()!=null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight()!=null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine()!=null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen()!=null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                vendorsFullDetails.setBidRate(totalPrice);
            }
            vendorsFullDetails.setUpCharge(rnsUpchargeDtlRepository.getValuesByVendorId(vendorsFullDetails.getId()));
            rnsQuotationVendorsFullDetails.add(vendorsFullDetails);
        }
        Collections.sort(rnsQuotationVendorsFullDetails, new RnsQuotationVendorsSort(
            new RnsQuotationVendorsByVendorName(),
            new RnsQuotationVendorsByFirstName())
        );
        return rnsQuotationVendorsFullDetails;
    }


    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotations/getRfqDetailsById/{id}")
    @Timed
    public ResponseEntity<RnsQuotationFullDetails> getRnsQuotationnRfqDetailsById(@PathVariable Long id) throws IllegalAccessException, InvocationTargetException{
        RnsQuotationFullDetails rnsQuotationFullDetails = new RnsQuotationFullDetails();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(rnsQuotationFullDetails, rnsQuotation);
        Template template=null;
        if(rnsQuotation.getTemplate()!=null && rnsQuotation.getTemplate().length()>0) {
            template = templateRepository.findTemplateById(new Long(rnsQuotation.getTemplate()));
        }
        if(template==null){
            template = new Template();
        }
        List<RnsQuotationVendors> rnsQuotationVendors = rnsQuotationVendorsRepository.findByAucionAndUserIdIsCurrentUser(id);
        Set<RnsQuotationVariantFullDetails> rnsQuotationVariants = new HashSet<RnsQuotationVariantFullDetails>();
        List<String> varString = new ArrayList<String>();
        for(RnsQuotationVendors quotationVendors : rnsQuotationVendors){
            if(!varString.contains(quotationVendors.getVariant().getTitle())){
                RnsQuotationVariantFullDetails rnsQuotationVariantFullDetails = new RnsQuotationVariantFullDetails();
                BeanUtils.copyProperties(rnsQuotationVariantFullDetails, quotationVendors.getVariant());
                if(rnsQuotationFullDetails.getDisplayVariant()==null){
                    rnsQuotationFullDetails.setDisplayVariant(rnsQuotationVariantFullDetails.getTitle());
                }
                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantFullDetails.getId());
                if(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms()!=null && rnsQuotationVariantFullDetails.getDealtermDeliveryTerms().length()>0){
                    RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterRepository.findByDelTermsCode(rnsQuotationVariantFullDetails.getDealtermDeliveryTerms());
                    if(rnsDelTermsMaster!=null)
                        rnsQuotationVariantFullDetails.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                }
                if(rnsQuotationVariantFullDetails.getTaxTerms()!=null && rnsQuotationVariantFullDetails.getTaxTerms().length()>0) {
                    RnsTaxTermsMaster rnsTaxTermsMaster =rnsTaxTermsMasterRepository.findByTaxTermsCode(rnsQuotationVariantFullDetails.getTaxTerms());
                    if(rnsTaxTermsMaster!=null)
                        rnsQuotationVariantFullDetails.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                }
                if(rnsQuotationVariantFullDetails.getDealtermDeliverAt()!=null && rnsQuotationVariantFullDetails.getDealtermDeliverAt().length()>0) {
                    RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterRepository.findByDelPlaceCode(rnsQuotationVariantFullDetails.getDealtermDeliverAt());
                    if(rnsDelPlaceMaster!=null)
                        rnsQuotationVariantFullDetails.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                }
                if(rnsQuotationVariantFullDetails.getDealtermPaymentTerms()!=null && rnsQuotationVariantFullDetails.getDealtermPaymentTerms().length()>0){
                    RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterRepository.findByPayTermsCode(rnsQuotationVariantFullDetails.getDealtermPaymentTerms());
                    if(rnsPayTermsMaster!=null)
                        rnsQuotationVariantFullDetails.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                }
                rnsQuotationVariantFullDetails.setVarDescSpec1(template.getSpecificationOne());
                rnsQuotationVariantFullDetails.setVarDescSpec2(template.getSpecificationTwo());
                rnsQuotationVariantFullDetails.setVarDescSpec3(template.getSpecificationThree());
                rnsQuotationVariantFullDetails.setVarDescSpec4(template.getSpecificationFour());
                rnsQuotationVariantFullDetails.setVarDescSpec5(template.getSpecificationFive());
                rnsQuotationVariantFullDetails.setVarDescSpec6(template.getSpecificationSix());
                rnsQuotationVariantFullDetails.setVarDescSpec7(template.getSpecificationSeven());
                rnsQuotationVariantFullDetails.setVarDescSpec8(template.getSpecificationEight());
                rnsQuotationVariantFullDetails.setVarDescSpec9(template.getSpecificationNine());
                rnsQuotationVariantFullDetails.setVarDescSpec10(template.getSpecificationTen());

                rnsQuotationVariantFullDetails.setShowInRfqOneRequired(template.getShowInRfqOneRequired());
                rnsQuotationVariantFullDetails.setShowInRfqTwoRequired(template.getShowInRfqTwoRequired());
                rnsQuotationVariantFullDetails.setShowInRfqThreeRequired(template.getShowInRfqThreeRequired());
                rnsQuotationVariantFullDetails.setShowInRfqFourRequired(template.getShowInRfqFourRequired());
                rnsQuotationVariantFullDetails.setShowInRfqFiveRequired(template.getShowInRfqFiveRequired());
                rnsQuotationVariantFullDetails.setShowInRfqSixRequired(template.getShowInRfqSixRequired());
                rnsQuotationVariantFullDetails.setShowInRfqSevenRequired(template.getShowInRfqSevenRequired());
                rnsQuotationVariantFullDetails.setShowInRfqEightRequired(template.getShowInRfqEightRequired());
                rnsQuotationVariantFullDetails.setShowInRfqNineRequired(template.getShowInRfqNineRequired());
                rnsQuotationVariantFullDetails.setShowInRfqTenRequired(template.getShowInRfqTenRequired());

                rnsQuotationVariants.add(rnsQuotationVariantFullDetails);
                RnsQuotationVendorsBean rnsQuotationVendorsBean = new RnsQuotationVendorsBean();
                BeanUtils.copyProperties(rnsQuotationVendorsBean, quotationVendors);
                RnsRfqPrice rnsRfqPrice = rnsRfqPriceRepository.getByVendorId(quotationVendors.getId());
                if(rnsRfqPrice!=null){
                    auctionVrnt.setPriceOne(rnsRfqPrice.getPriceOne());
                    auctionVrnt.setAmountOne(rnsRfqPrice.getPriceOne()*auctionVrnt.getConvFactOne()*Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));

                    if(rnsRfqPrice.getPriceTwo()!=null && auctionVrnt.getConvFactTwo()!=null) {
                        auctionVrnt.setPriceTwo(rnsRfqPrice.getPriceTwo());
                        auctionVrnt.setAmountTwo(rnsRfqPrice.getPriceTwo() * auctionVrnt.getConvFactTwo() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceTwo(null);
                        auctionVrnt.setAmountTwo(null);
                    }

                    if(rnsRfqPrice.getPriceThree()!=null && auctionVrnt.getConvFactThree()!=null) {
                        auctionVrnt.setPriceThree(rnsRfqPrice.getPriceThree());
                        auctionVrnt.setAmountThree(rnsRfqPrice.getPriceThree() * auctionVrnt.getConvFactThree() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceThree(null);
                        auctionVrnt.setAmountThree(null);
                    }

                    if(rnsRfqPrice.getPriceFour()!=null && auctionVrnt.getConvFactFour()!=null) {
                        auctionVrnt.setPriceFour(rnsRfqPrice.getPriceFour());
                        auctionVrnt.setAmountFour(rnsRfqPrice.getPriceFour() * auctionVrnt.getConvFactFour() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceFour(null);
                        auctionVrnt.setAmountFour(null);
                    }

                    if(rnsRfqPrice.getPriceFive()!=null && auctionVrnt.getConvFactFive()!=null) {
                        auctionVrnt.setPriceFive(rnsRfqPrice.getPriceFive());
                        auctionVrnt.setAmountFive(rnsRfqPrice.getPriceFive() * auctionVrnt.getConvFactFive() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceFive(null);
                        auctionVrnt.setAmountFive(null);
                    }

                    if(rnsRfqPrice.getPriceSix()!=null && auctionVrnt.getConvFactSix()!=null) {
                        auctionVrnt.setPriceSix(rnsRfqPrice.getPriceSix());
                        auctionVrnt.setAmountSix(rnsRfqPrice.getPriceSix() * auctionVrnt.getConvFactSix() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceSix(null);
                        auctionVrnt.setAmountSix(null);
                    }

                    if(rnsRfqPrice.getPriceSeven()!=null && auctionVrnt.getConvFactSeven()!=null) {
                        auctionVrnt.setPriceSeven(rnsRfqPrice.getPriceSeven());
                        auctionVrnt.setAmountSeven(rnsRfqPrice.getPriceSeven() * auctionVrnt.getConvFactSeven() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceSeven(null);
                        auctionVrnt.setAmountSeven(null);
                    }

                    if(rnsRfqPrice.getPriceEight()!=null && auctionVrnt.getConvFactEight()!=null) {
                        auctionVrnt.setPriceEight(rnsRfqPrice.getPriceEight());
                        auctionVrnt.setAmountEight(rnsRfqPrice.getPriceEight() * auctionVrnt.getConvFactEight() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceEight(null);
                        auctionVrnt.setAmountEight(null);
                    }

                    if(rnsRfqPrice.getPriceNine()!=null && auctionVrnt.getConvFactNine()!=null) {
                        auctionVrnt.setPriceNine(rnsRfqPrice.getPriceNine());
                        auctionVrnt.setAmountNine(rnsRfqPrice.getPriceNine() * auctionVrnt.getConvFactNine() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceNine(null);
                        auctionVrnt.setAmountNine(null);
                    }

                    if(rnsRfqPrice.getPriceTen()!=null && auctionVrnt.getConvFactTen()!=null) {
                        auctionVrnt.setPriceTen(rnsRfqPrice.getPriceTen());
                        auctionVrnt.setAmountTen(rnsRfqPrice.getPriceTen() * auctionVrnt.getConvFactTen() * Float.parseFloat(rnsQuotationVariantFullDetails.getOrderQuantity()));
                    } else{
                        auctionVrnt.setPriceTen(null);
                        auctionVrnt.setAmountTen(null);
                    }
                }
                rnsQuotationVariantFullDetails.setAuctionVrnt(auctionVrnt);

                Set<RnsQuotationVendorsBean> rnsQuotationVendorsBeans = new HashSet<RnsQuotationVendorsBean>();
                rnsQuotationVendorsBeans.add(rnsQuotationVendorsBean);
                rnsQuotationVariantFullDetails.setVendors(rnsQuotationVendorsBeans);
                varString.add(quotationVendors.getVariant().getTitle());
            }
        }
        rnsQuotationFullDetails.setVariants(rnsQuotationVariants);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationFullDetails.getId().toString()))
            .body(rnsQuotationFullDetails);
    }


    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotation-vendors/getRevisionByQuotationId/{id}")
    @Timed
    public List<VndrPriceCustom> getRnsQuotationVendorsandPriceRevisions(@PathVariable Long id) {
        List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
        List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(id);
        for(RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants) {
            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariant.getId());
            List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(rnsQuotationVariant.getId());
            for (VndrPrice price : vndrPrice) {
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
                prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariant.getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate()));
            }
        }
        return prices;
    }


    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotation-vendors/getRevisionByVendorId/{id}")
    @Timed
    public List<VndrPriceCustom> getRnsQuotationVendorsandPriceRevisionsByUser(@PathVariable Long id) {
        List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
        List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(id);
        String vendorCode = getCurrentUserLogin();
        User user = userRepository.findByLogin(vendorCode);
        for(RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants) {
            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariant.getId());
            List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCode(rnsQuotationVariant.getId(), vendorCode);
            for (VndrPrice price : vndrPrice) {
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
                prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariant.getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate(), user.getFirstName(), user.getLastName(), user.getVendorName()));
            }
        }
        return prices;
    }

    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotation-vendors/getPriceByQuotationId/{id}")
    @Timed
    public List<RnsQuotationVendorsBean> getRnsQuotationVendorsandPrice(@PathVariable Long id) {
        log.debug("REST request to get all RnsQuotations Variants");
        List<RnsQuotationVendors> vendors= rnsQuotationRepository.getRnsQuotationVendorsList(id);
        Set<Long> varientList = new HashSet<Long>();
        for(RnsQuotationVendors rnsQuotationVendors : vendors){
            varientList.add(rnsQuotationVendors.getVariant().getId());
        }
        Map<String, User> vendorMap = new HashMap<String, User>();
        List<RnsQuotationVendorsBean> vendorsT=new ArrayList<RnsQuotationVendorsBean>();
        for(Long varientId : varientList){
            TreeMap<String, VndrRank> sorted_rank_map = getPosition(varientId);
            for(RnsQuotationVendors rnsQuotationVendors : vendors){
                RnsQuotationVendorsBean bean = new RnsQuotationVendorsBean();
                if(rnsQuotationVendors.getVariant().getId()==varientId) {
                    if(sorted_rank_map.containsKey(rnsQuotationVendors.getVendorCode())){
                        bean.setRank(sorted_rank_map.get(rnsQuotationVendors.getVendorCode()).getRank());
                        bean.setRegularRate(sorted_rank_map.get(rnsQuotationVendors.getVendorCode()).getPrice());
                        bean.setNoRevision(sorted_rank_map.get(rnsQuotationVendors.getVendorCode()).getRevision());
                        bean.setCreatedOn(sorted_rank_map.get(rnsQuotationVendors.getVendorCode()).getCreatedOn());
                    } else{
                        bean.setRegularRate(0.0f);
                    }

                    bean.setId(rnsQuotationVendors.getId());
                    bean.setPaymentTerms(rnsQuotationVendors.getPaymentTerms());
                    bean.setPaymentTermsCharge(rnsQuotationVendors.getPaymentTermsCharge());
                    bean.setPaymentTermsChargeType(rnsQuotationVendors.getPaymentTermsChargeType());
                    bean.setDeliveryTerms(rnsQuotationVendors.getDeliveryTerms());
                    bean.setDeliveryTermsCharge(rnsQuotationVendors.getDeliveryTermsCharge());
                    bean.setDeliveryTermsChargeType(rnsQuotationVendors.getDeliveryTermsChargeType());
                    bean.setExpDelDate(rnsQuotationVendors.getExpDelDate());
                    bean.setConfDelDate(rnsQuotationVendors.getConfDelDate());
                    bean.setCurrency(rnsQuotationVendors.getCurrency());
                    bean.setDisRate(rnsQuotationVendors.getDisRate());
                    bean.setExpiryQty(rnsQuotationVendors.getExpiryQty());
                    bean.setQuoteQty(rnsQuotationVendors.getQuoteQty());
                    bean.setAwardQty(rnsQuotationVendors.getAwardQty());
                    bean.setVendorCode(rnsQuotationVendors.getVendorCode());
                    bean.setUserId(rnsQuotationVendors.getUserId());
                    bean.setVariant(rnsQuotationVendors.getVariant());
                    bean.setVendorQuotation(rnsQuotationVendors.getVendorQuotation());

                    if(vendorMap.containsKey(bean.getVendorCode())) {
                        bean.setVendor(vendorMap.get(bean.getVendorCode()));
                    } else {
                        User user = userRepository.findByLogin(bean.getVendorCode());
                        bean.setVendor(user);
                        vendorMap.put(bean.getVendorCode(), user);
                    }
                    vendorsT.add(bean);
                }
            }
        }

        Collections.sort(vendorsT, new RnsQuotationVendorsBeanSort(
            new RnsQuotationVendorsBeanByVendorName(),
            new RnsQuotationVendorsBeanByFirstName())
        );
        return vendorsT;
    }

    public TreeMap<String, VndrRank> getPosition(Long variantId) {
        log.debug("REST request to get VndrPrice : {}", variantId);
        VndrPrice vPrice = new VndrPrice();
        Integer position = 0;
        Float myPrice = 1.0f;
        boolean myPriceExist = false;
        Float highestPrice = 1.0f;

        RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(variantId).orElse(null);
        //Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotationVariant.getQuotation().getId());
        RnsQuotation rnsQuotation = rnsQuotationVariant.getQuotation();
        System.out.println(rnsQuotation);

        List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(variantId);
        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(variantId);

        Map<String,Float> vendorMap = new HashMap<String,Float>();
        Map<String,Instant> vendorCreatedOnMap = new HashMap<String,Instant>();
        Map<String,Integer> vendorRevisionMap = new HashMap<String,Integer>();
        Map<String,Integer> vendorTempMap = new HashMap<String,Integer>();
        for (VndrPrice price : vndrPrice) {
            if(vendorMap.containsKey(price.getVendorCode())){
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne()!=null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo()!=null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree()!=null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour()!=null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive()!=null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix()!=null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven()!=null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight()!=null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine()!=null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen()!=null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }

                Integer ctrVendor = vendorRevisionMap.get(price.getVendorCode());
                ++ctrVendor;
                vendorRevisionMap.put(price.getVendorCode(),ctrVendor);

                if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")) {
                    if (totalPrice > vendorMap.get(price.getVendorCode())) {
                        vendorMap.put(price.getVendorCode(), totalPrice);
                        vendorCreatedOnMap.put(price.getVendorCode(),price.getCreatedOn());
                    }
                } else if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                    if (totalPrice < vendorMap.get(price.getVendorCode())) {
                        vendorMap.put(price.getVendorCode(), totalPrice);
                        vendorCreatedOnMap.put(price.getVendorCode(),price.getCreatedOn());
                    }
                }
            } else{
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne()!=null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo()!=null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree()!=null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour()!=null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive()!=null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix()!=null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven()!=null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight()!=null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine()!=null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen()!=null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                vendorMap.put(price.getVendorCode(),totalPrice);
                vendorCreatedOnMap.put(price.getVendorCode(),price.getCreatedOn());
                vendorRevisionMap.put(price.getVendorCode(),1);
                vendorTempMap.put(price.getVendorCode(),1);
            }
        }
        TreeMap<String, Float> sorted_map = null;
        if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")) {
            ValueComparator bvc = new ValueComparator(vendorMap,true);
            sorted_map = new TreeMap<String, Float>(bvc);
            sorted_map.putAll(vendorMap);
        }  else if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
            ValueComparator bvc = new ValueComparator(vendorMap,false);
            sorted_map = new TreeMap<String, Float>(bvc);
            sorted_map.putAll(vendorMap);
        }

        TreeMap<String, VndrRank> sorted_rank_map = new TreeMap<String, VndrRank>();
        boolean positionExist=false;
        long ctr=0;

        System.out.println(sorted_map);
        for (Map.Entry<String, Float> entry : sorted_map.entrySet()) {
            float rate = entry.getValue();
            String key = entry.getKey();
            Instant dt = vendorCreatedOnMap.get(key);
            int rev = vendorRevisionMap.get(key);
            VndrRank rnk = new VndrRank(++ctr, rate, dt, rev);
            sorted_rank_map.put(key,rnk);
        }
        return sorted_rank_map;
    }


    /**
     * GET  /rns-quotations : get all the rnsQuotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/rns-quotation-vendors/getLoginVendorByQuotationId/{id}")
    @Timed
    public List<RnsQuotationVendors> getLoginVendorByQuotationId(@PathVariable Long id) {
        log.debug("REST request to get all RnsQuotations Variants");
        List<RnsQuotationVendors> vendors= rnsQuotationRepository.getRnsQuotationVendorsList(id);
        List<RnsQuotationVendors> newvendors= vendors;

        String vendorCode = getCurrentUserLogin();
        List<RnsQuotationVendors> responseVendors = new ArrayList<RnsQuotationVendors>();
        for(RnsQuotationVendors vendor: vendors){
            if(vendor.getVendorCode().equals(vendorCode)){
                responseVendors.add(vendor);
            }
        }
        return responseVendors;
    }

    /**
     * PUT  /rns-quotations/publish : Updates an existing rnsQuotation.
     *
     * @param @rnsQuotation the rnsQuotation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotation,
     * or with status 400 (Bad Request) if the rnsQuotation is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotations-rfq")
    @Timed
    public RnsQuotationFullDetails updateRfq(@RequestBody RnsQuotation rnsQuotationdata) throws URISyntaxException, IOException, IllegalAccessException, InvocationTargetException {
        log.debug("REST request to update RnsQuotation : {}", rnsQuotationdata);
        Long id = rnsQuotationdata.getId();
        User currentUser = userRepository.findByLogin(getCurrentUserLogin());
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);
        List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(id);
        for(RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants){
            if(rnsQuotationVariant.getOpenCosting()!=null && rnsQuotationVariant.getOpenCosting().equalsIgnoreCase("Required")){
                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariant.getId());
                if(auctionVrnt!=null && auctionVrnt.isApplicable()!=null && auctionVrnt.isApplicable()==true){}
                else{
                    RnsQuotationFullDetails rnsQuotationFullDetails  = new RnsQuotationFullDetails();
                    BeanUtils.copyProperties(rnsQuotationFullDetails, rnsQuotation);
                    rnsQuotationFullDetails.setErrorMessage("Open costing required. Please fill in the open costing element information for "+rnsQuotationVariant.getTitle().replace("Variant", "Lot"));
                    return rnsQuotationFullDetails;
                }
            }
        }
        rnsQuotation.setRfq(true);
        rnsQuotation.setRfqPublishDate(Instant.now());
        RnsQuotation result = rnsQuotationRepository.save(rnsQuotation);
        if(result!=null){
            try {
                List<RnsQuotationVendors> rnsQuotationVendorss = rnsQuotationVendorsRepository.findByVendorQuotationId(rnsQuotation.getId());
                List<String> vendors = new ArrayList<String>();
                for (RnsQuotationVendors rnsQuotationVendors : rnsQuotationVendorss) {
                    if (vendors.contains(rnsQuotationVendors.getVendorCode())) {
                    } else {
                        vendors.add(rnsQuotationVendors.getVendorCode());
                    }
                }
                String emailTemplateCode = "rfqPublished";
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
                            context.setVariable(VENDOR, user);
                            context.setVariable("rnsQuotation", result);
                            context.setVariable("applicationProperties", applicationProperties);
                            context.setVariable(USER, currentUser);
                            String content = templateEngine.process("mail/" + emailTemplateCode, context);
                            subject = templateEngine.process("mail/" + emailTemplateCode + "Subject", context);
                            //mailService.sendEmail("sudeep@fulcrumconsultancy.com", subject, content, false, true);
                            //mailService.sendEmail("sheetal@fulcrumconsultancy.com", subject, content, false, true);
                            if (emailTemplate.isEmail() != null && emailTemplate.isEmail() == true) {
                                try {
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
                System.out.println("RnsQuotationResource updateRfq()" + e.getMessage());
            }
        }
        RnsQuotationFullDetails rnsQuotationFullDetails  = new RnsQuotationFullDetails();
        BeanUtils.copyProperties(rnsQuotationFullDetails, result);
        rnsQuotationFullDetails.setErrorMessage("RFQ published successfully for Project# "+ result.getId());
        return rnsQuotationFullDetails;
    }

    @PutMapping("/rns-quotations-auction")
    @Timed
    public ResponseEntity<RnsQuotationFullDetails> updateAuction(@RequestBody RnsQuotation rnsQuotationdata) throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        log.debug("REST request to update RnsQuotation : {}", rnsQuotationdata);
        User currentUser = userRepository.findByLogin(getCurrentUserLogin());
        Long id = rnsQuotationdata.getId();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);

        List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(id);
        for(RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants){
            if(rnsQuotationVariant.getOpenCosting()!=null && rnsQuotationVariant.getOpenCosting().equalsIgnoreCase("Required")){
                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariant.getId());
                if(auctionVrnt!=null && auctionVrnt.isApplicable()!=null && auctionVrnt.isApplicable()==true){}
                else{
                    RnsQuotationFullDetails rnsQuotationFullDetails  = new RnsQuotationFullDetails();
                    BeanUtils.copyProperties(rnsQuotationFullDetails, rnsQuotation);
                    rnsQuotationFullDetails.setErrorMessage("Open costing required. Please fill in the open costing element information");
                    return ResponseEntity.ok()
                        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotation.getId().toString()))
                        .body(rnsQuotationFullDetails);

                }
            }
        }

        Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotationdata.getId());

        if(auction==null){
            RnsQuotationFullDetails rnsQuotationFullDetails  = new RnsQuotationFullDetails();
            BeanUtils.copyProperties(rnsQuotationFullDetails, rnsQuotation);
            rnsQuotationFullDetails.setErrorMessage("Please create Auction Rules before publishing for "+rnsQuotation.getId().toString());
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotation.getId().toString()))
                .body(rnsQuotationFullDetails);
        }

        Instant currentTime = Instant.now();

        Instant publishTime = auction.getPublishTime();

        Instant biddingStartTime = auction.getBiddingStartTime();

        if(currentTime.isAfter(publishTime)){
            RnsQuotationFullDetails rnsQuotationFullDetails  = new RnsQuotationFullDetails();
            BeanUtils.copyProperties(rnsQuotationFullDetails, rnsQuotation);
            rnsQuotationFullDetails.setErrorMessage("Please revise the Publish Time to a future value for "+rnsQuotation.getId().toString());
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotation.getId().toString()))
                .body(rnsQuotationFullDetails);
        } else if(currentTime.isAfter(biddingStartTime)){
            RnsQuotationFullDetails rnsQuotationFullDetails  = new RnsQuotationFullDetails();
            BeanUtils.copyProperties(rnsQuotationFullDetails, rnsQuotation);
            rnsQuotationFullDetails.setErrorMessage("Please revise the Bidding Start Time to a future value for "+rnsQuotation.getId().toString());
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotation.getId().toString()))
                .body(rnsQuotationFullDetails);
        }

        rnsQuotation.setAuction(true);
        rnsQuotation.setAuctionPublishDate(Instant.now());
        RnsQuotation result = rnsQuotationRepository.save(rnsQuotation);

        // Bidding Published Mail
        if(result!=null){
            try {
                List<RnsQuotationVendors> rnsQuotationVendorss = rnsQuotationVendorsRepository.findByVendorQuotationId(rnsQuotation.getId());
                List<String> vendors = new ArrayList<String>();
                for (RnsQuotationVendors rnsQuotationVendors : rnsQuotationVendorss) {
                    if (vendors.contains(rnsQuotationVendors.getVendorCode())) {
                    } else {
                        vendors.add(rnsQuotationVendors.getVendorCode());
                    }
                }
                String emailTemplateCode = "biddingPublished";
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
                            context.setVariable(VENDOR, user);
                            context.setVariable("rnsQuotation", result);
                            context.setVariable("applicationProperties", applicationProperties);
                            context.setVariable(USER, currentUser);
                            Date bidStart = Date.from(biddingStartTime);
                            context.setVariable("bidStartTime", new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(bidStart));
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

        RnsQuotationFullDetails rnsQuotationFullDetails  = new RnsQuotationFullDetails();
        BeanUtils.copyProperties(rnsQuotationFullDetails, result);
        rnsQuotationFullDetails.setErrorMessage("Auction published successfully for Project#"+rnsQuotation.getId().toString());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(rnsQuotationFullDetails);
    }

    @GetMapping("/rns-quotations-bidding-compare/{id}")
    @Timed
    public ResponseEntity<BiddingCompareBean> getRnsQuotationBiddingCompare(@PathVariable Long id) throws IllegalAccessException, InvocationTargetException{
        log.debug("REST request to get RnsQuotation : {}", id);
        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(id);
        BiddingCompareBean biddingCompareBean = new BiddingCompareBean();
        BeanUtils.copyProperties(biddingCompareBean, auctionVrnt);
        List<VndrPrice> vndrPrices = getVendorsPrice(id);
        List<BiddingCompareVendorBean> biddingCompareVendorBeans = new ArrayList<BiddingCompareVendorBean>();
        for(VndrPrice vndrPrice : vndrPrices){
            BiddingCompareVendorBean biddingCompareVendorBean = new BiddingCompareVendorBean();
            biddingCompareVendorBean.setVendorCode(vndrPrice.getVendorCode());
            User user = userRepository.findByLogin(vndrPrice.getVendorCode());
            biddingCompareVendorBean.setVendorName(user.getVendorName());
            biddingCompareVendorBean.setFirstName(user.getFirstName());
            biddingCompareVendorBean.setLastName(user.getLastName());
            Float priceTotal=0.0F;
            Float amountTotal=0.0F;
            if(vndrPrice.getPriceOne()!=null && auctionVrnt.getConvFactOne()!=null) {
                biddingCompareVendorBean.setPriceOne(vndrPrice.getPriceOne());
                biddingCompareVendorBean.setAmountOne(vndrPrice.getPriceOne()*auctionVrnt.getConvFactOne());
                priceTotal += vndrPrice.getPriceOne();
                amountTotal += (vndrPrice.getPriceOne()*auctionVrnt.getConvFactOne());
            }
            if(vndrPrice.getPriceTwo()!=null && auctionVrnt.getConvFactTwo()!=null) {
                biddingCompareVendorBean.setPriceTwo(vndrPrice.getPriceTwo());
                biddingCompareVendorBean.setAmountTwo(vndrPrice.getPriceTwo()*auctionVrnt.getConvFactTwo());
                priceTotal += vndrPrice.getPriceTwo();
                amountTotal += (vndrPrice.getPriceTwo()*auctionVrnt.getConvFactTwo());
            }
            if(vndrPrice.getPriceThree()!=null && auctionVrnt.getConvFactThree()!=null) {
                biddingCompareVendorBean.setPriceThree(vndrPrice.getPriceThree());
                biddingCompareVendorBean.setAmountThree(vndrPrice.getPriceThree()*auctionVrnt.getConvFactThree());
                priceTotal += vndrPrice.getPriceThree();
                amountTotal += (vndrPrice.getPriceThree()*auctionVrnt.getConvFactThree());
            }
            if(vndrPrice.getPriceFour()!=null && auctionVrnt.getConvFactFour()!=null) {
                biddingCompareVendorBean.setPriceFour(vndrPrice.getPriceFour());
                biddingCompareVendorBean.setAmountFour(vndrPrice.getPriceFour()*auctionVrnt.getConvFactFour());
                priceTotal += vndrPrice.getPriceFour();
                amountTotal += (vndrPrice.getPriceFour()*auctionVrnt.getConvFactFour());
            }
            if(vndrPrice.getPriceFive()!=null && auctionVrnt.getConvFactFive()!=null) {
                biddingCompareVendorBean.setPriceFive(vndrPrice.getPriceFive());
                biddingCompareVendorBean.setAmountFive(vndrPrice.getPriceFive()*auctionVrnt.getConvFactFive());
                priceTotal += vndrPrice.getPriceFive();
                amountTotal += (vndrPrice.getPriceFive()*auctionVrnt.getConvFactFive());
            }
            if(vndrPrice.getPriceSix()!=null && auctionVrnt.getConvFactSix()!=null) {
                biddingCompareVendorBean.setPriceSix(vndrPrice.getPriceSix());
                biddingCompareVendorBean.setAmountSix(vndrPrice.getPriceSix()*auctionVrnt.getConvFactSix());
                priceTotal += vndrPrice.getPriceSix();
                amountTotal += (vndrPrice.getPriceSix()*auctionVrnt.getConvFactSix());
            }
            if(vndrPrice.getPriceSeven()!=null && auctionVrnt.getConvFactSeven()!=null) {
                biddingCompareVendorBean.setPriceSeven(vndrPrice.getPriceSeven());
                biddingCompareVendorBean.setAmountSeven(vndrPrice.getPriceSeven()*auctionVrnt.getConvFactSeven());
                priceTotal += vndrPrice.getPriceSeven();
                amountTotal += (vndrPrice.getPriceSeven()*auctionVrnt.getConvFactSeven());
            }
            if(vndrPrice.getPriceEight()!=null && auctionVrnt.getConvFactEight()!=null) {
                biddingCompareVendorBean.setPriceEight(vndrPrice.getPriceEight());
                biddingCompareVendorBean.setAmountEight(vndrPrice.getPriceEight()*auctionVrnt.getConvFactEight());
                priceTotal += vndrPrice.getPriceEight();
                amountTotal += (vndrPrice.getPriceEight()*auctionVrnt.getConvFactEight());
            }
            if(vndrPrice.getPriceNine()!=null && auctionVrnt.getConvFactNine()!=null) {
                biddingCompareVendorBean.setPriceNine(vndrPrice.getPriceNine());
                biddingCompareVendorBean.setAmountNine(vndrPrice.getPriceNine()*auctionVrnt.getConvFactNine());
                priceTotal += vndrPrice.getPriceNine();
                amountTotal += (vndrPrice.getPriceNine()*auctionVrnt.getConvFactNine());
            }
            if(vndrPrice.getPriceTen()!=null && auctionVrnt.getConvFactTen()!=null) {
                biddingCompareVendorBean.setPriceTen(vndrPrice.getPriceTen());
                biddingCompareVendorBean.setAmountTen(vndrPrice.getPriceTen()*auctionVrnt.getConvFactTen());
                priceTotal += vndrPrice.getPriceTen();
                amountTotal += (vndrPrice.getPriceTen()*auctionVrnt.getConvFactTen());
            }
            biddingCompareVendorBean.setPriceTotal(priceTotal);
            biddingCompareVendorBean.setAmountTotal(amountTotal);

            biddingCompareVendorBeans.add(biddingCompareVendorBean);
        }
        biddingCompareBean.setVendorBeans(biddingCompareVendorBeans);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(biddingCompareBean));
    }


    /**
     * GET  /vndr-prices/:id : get the "id" vndrPrice.
     *
     * @param //id the id of the vndrPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vndrPrice, or with status 404 (Not Found)
     */
    public List<VndrPrice> getVendorsPrice(Long variantId) {
        log.debug("REST request to get VndrPrice : {}", variantId);
        List<VndrPrice> vndrPrices = new ArrayList<VndrPrice>();
        List<RnsQuotationVendors> quotationVendorsList = rnsQuotationVendorsRepository.getRnsQuotationVendorsByVariant(variantId);
        for(RnsQuotationVendors quotationVendors : quotationVendorsList){
            VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(variantId, quotationVendors.getVendorCode());
            if(vndrPrice!=null){
                vndrPrices.add(vndrPrice);
            }
        }
        return vndrPrices;
    }

    /**
     * Agree  /rns-quotations/:id : agree the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to Agree
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/rns-quotations-agree/{id}")
    @Timed
    public ResponseEntity<Void> agreeRnsQuotation(@PathVariable Long id) {
        log.debug("REST request to Agree RnsQuotation : {}", id);
        List<RnsQuotationVendors> quotationVendors = rnsQuotationVendorsRepository.findByAucionAndUserIdIsCurrentUser(id);
        for(RnsQuotationVendors rnsQuotationVendors : quotationVendors){
            rnsQuotationVendors.setAuctionApplicable(true);
            rnsQuotationVendorsRepository.save(rnsQuotationVendors);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * Agree  /rns-quotations/:id : agree the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to Agree
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/rns-quotations-disagree/{id}")
    @Timed
    public ResponseEntity<Void> disagreeRnsQuotation(@PathVariable Long id) {
        log.debug("REST request to Disagree RnsQuotation : {}", id);
        List<RnsQuotationVendors> quotationVendors = rnsQuotationVendorsRepository.findByAucionAndUserIdIsCurrentUser(id);
        for(RnsQuotationVendors rnsQuotationVendors : quotationVendors){
            rnsQuotationVendors.setAuctionApplicable(false);
            rnsQuotationVendorsRepository.save(rnsQuotationVendors);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /export-rns-quotations-variants : export all variants of a selected quotation.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/export-rns-quotations-variants/{id}")
    @Timed
    public @ResponseBody void downloadFile(@PathVariable Long id, HttpServletResponse response) {
        Workbook workbook = null;
        try {
            List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationRepository.getRnsQuotationVariantsList(id);
            if(rnsQuotationVariants != null && rnsQuotationVariants.size()>0) {
                RnsQuotation rnsQuotation = rnsQuotationVariants.get(0).getQuotation();
                Template template = templateRepository.findTemplateById(new Long(rnsQuotation.getTemplate()));

                workbook = new XSSFWorkbook();
                Sheet spreadsheet = workbook.createSheet("Project# " + id);

                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
                font.setBold(true);
                style.setFont(font);

                Map<String, String> rnsTaxTermsMasterMap = new HashMap<String, String>();
                Map<String, String> rnsDelTermsMasterMap = new HashMap<String, String>();
                Map<String, String> rnsPayTermsMasterMap = new HashMap<String, String>();
                Map<String, String> rnsDelPlaceMasterMap = new HashMap<String, String>();
                Map<String, String> rnsUomMasterMap = new HashMap<String, String>();
                Map<String, String> currencyMap = new HashMap<String, String>();

                List<RnsTaxTermsMaster> rnsTaxTermsMasters = rnsTaxTermsMasterRepository.findAll();
                if(rnsTaxTermsMasters != null) {
                    Sheet hidden = workbook.createSheet("taxTerms");
                    int i=-1;
                    for (RnsTaxTermsMaster rnsTaxTermsMaster : rnsTaxTermsMasters) {
                        String desc = rnsTaxTermsMaster.getTaxTermsDesc() + "##" + rnsTaxTermsMaster.getTaxTermsCode();
                        if(desc.length()<=255) {
                            Row row = hidden.createRow(++i);
                            Cell cell = row.createCell(0);
                            cell.setCellValue(desc);
                            rnsTaxTermsMasterMap.put(rnsTaxTermsMaster.getTaxTermsCode(), desc);
                        }
                    }
                    CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 11, 11);

                    Name namedCell = workbook.createName();
                    namedCell.setNameName("taxTerms");
                    namedCell.setRefersToFormula("taxTerms!$A$1:$A$" + rnsTaxTermsMasterMap.size());
                    DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                    DataValidationConstraint explicitListConstraint = validationHelper.createFormulaListConstraint("taxTerms");
                    DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    spreadsheet.addValidationData(dataValidation);
                }

                List<RnsPayTermsMaster> rnsPayTermsMasters = rnsPayTermsMasterRepository.findAll();
                if(rnsPayTermsMasters != null) {
                    Sheet hidden = workbook.createSheet("payTerms");
                    int i=-1;
                    for (RnsPayTermsMaster rnsPayTermsMaster : rnsPayTermsMasters) {
                        String desc = rnsPayTermsMaster.getPayTermsCodeDesc() + "##" + rnsPayTermsMaster.getPayTermsCode();
                        if(desc.length()<=255) {
                            Row row = hidden.createRow(++i);
                            Cell cell = row.createCell(0);
                            cell.setCellValue(desc);
                            rnsPayTermsMasterMap.put(rnsPayTermsMaster.getPayTermsCode(), desc);
                        }
                    }

                    CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 12, 12);
                    Name namedCell = workbook.createName();
                    namedCell.setNameName("payTerms");
                    namedCell.setRefersToFormula("payTerms!$A$1:$A$" + rnsPayTermsMasterMap.size());
                    DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                    DataValidationConstraint explicitListConstraint = validationHelper.createFormulaListConstraint("payTerms");
                    DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    spreadsheet.addValidationData(dataValidation);
                }

                List<RnsDelTermsMaster> rnsDelTermsMasters = rnsDelTermsMasterRepository.findAll();
                if(rnsDelTermsMasters != null) {
                    Sheet hidden = workbook.createSheet("delTerms");
                    int i=-1;
                    for (RnsDelTermsMaster rnsDelTermsMaster : rnsDelTermsMasters) {
                        String desc = rnsDelTermsMaster.getDelTermsCodeDesc() + "##" + rnsDelTermsMaster.getDelTermsCode();
                        if(desc.length()<=255) {
                            Row row = hidden.createRow(++i);
                            Cell cell = row.createCell(0);
                            cell.setCellValue(desc);
                            rnsDelTermsMasterMap.put(rnsDelTermsMaster.getDelTermsCode(), desc);
                        }
                    }
                    CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 13, 13);

                    Name namedCell = workbook.createName();
                    namedCell.setNameName("delTerms");
                    namedCell.setRefersToFormula("delTerms!$A$1:$A$" + rnsDelTermsMasterMap.size());
                    DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                    DataValidationConstraint explicitListConstraint = validationHelper.createFormulaListConstraint("delTerms");
                    DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    spreadsheet.addValidationData(dataValidation);
                }

                List<RnsDelPlaceMaster> rnsDelPlaceMasters = rnsDelPlaceMasterRepository.findAll();
                if(rnsDelPlaceMasters != null) {
                    Sheet hidden = workbook.createSheet("delPlaces");
                    int i=-1;
                    for (RnsDelPlaceMaster rnsDelPlaceMaster : rnsDelPlaceMasters) {
                        String desc = rnsDelPlaceMaster.getCodeDesc() + "##" + rnsDelPlaceMaster.getCode();
                        if(desc.length()<=255) {
                            Row row = hidden.createRow(++i);
                            Cell cell = row.createCell(0);
                            cell.setCellValue(desc);
                            rnsDelPlaceMasterMap.put(rnsDelPlaceMaster.getCode(), desc);
                        }
                    }
                    CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 14, 14);

                    Name namedCell = workbook.createName();
                    namedCell.setNameName("delPlaces");
                    namedCell.setRefersToFormula("delPlaces!$A$1:$A$" + rnsDelPlaceMasterMap.size());
                    DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                    DataValidationConstraint explicitListConstraint = validationHelper.createFormulaListConstraint("delPlaces");
                    DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    spreadsheet.addValidationData(dataValidation);
                }

                List<RnsUomMaster> rnsUomMasters = rnsUomMasterRepository.findAll();
                if(rnsUomMasters != null) {
                    Sheet hidden = workbook.createSheet("uomMaster");
                    int i=-1;
                    for (RnsUomMaster rnsUomMaster : rnsUomMasters) {
                        String desc = rnsUomMaster.getUomName() + "##" + rnsUomMaster.getUomCode();
                        if(desc.length()<=255) {
                            Row row = hidden.createRow(++i);
                            Cell cell = row.createCell(0);
                            cell.setCellValue(desc);
                            rnsUomMasterMap.put(rnsUomMaster.getUomCode(), desc);
                        }
                    }
                    CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 16, 16);

                    Name namedCell = workbook.createName();
                    namedCell.setNameName("uomMaster");
                    namedCell.setRefersToFormula("uomMaster!$A$1:$A$" + rnsUomMasterMap.size());
                    DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                    DataValidationConstraint explicitListConstraint = validationHelper.createFormulaListConstraint("uomMaster");
                    DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    spreadsheet.addValidationData(dataValidation);
                }

                List<Currency> currencies = currencyRepository.findAll();
                if(currencies != null) {
                    Sheet hidden = workbook.createSheet("currencyMaster");
                    int i=-1;
                    for (Currency currency : currencies) {
                        String desc = currency.getCurrency() + "##" + currency.getSymbol();
                        if(desc.length()<=255) {
                            Row row = hidden.createRow(++i);
                            Cell cell = row.createCell(0);
                            cell.setCellValue(desc);
                            currencyMap.put(currency.getSymbol(), desc);
                        }
                    }
                    CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 19, 19);

                    Name namedCell = workbook.createName();
                    namedCell.setNameName("currencyMaster");
                    namedCell.setRefersToFormula("currencyMaster!$A$1:$A$" + currencyMap.size());
                    DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                    DataValidationConstraint explicitListConstraint = validationHelper.createFormulaListConstraint("currencyMaster");
                    DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    spreadsheet.addValidationData(dataValidation);
                }

                int row = 0;
                // Header Start
                Row rowhead = spreadsheet.createRow(row);

                rowhead.createCell(0).setCellValue("Title");

                if (template.getSpecificationOne() != null) {
                    rowhead.createCell(1).setCellValue(template.getSpecificationOne());
                    // Drop Down Validation
                    if(template.getSpecificationOneShowAs() != null && template.getSpecificationOneShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationOneValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 1, 1);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(1).setCellValue("NA");
                }

                if (template.getSpecificationTwo() != null) {
                    rowhead.createCell(2).setCellValue(template.getSpecificationTwo());
                    // Drop Down Validation
                    if(template.getSpecificationTwoShowAs() != null && template.getSpecificationTwoShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationTwoValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 2, 2);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(2).setCellValue("NA");
                }

                if (template.getSpecificationThree() != null) {
                    rowhead.createCell(3).setCellValue(template.getSpecificationThree());
                    // Drop Down Validation
                    if(template.getSpecificationThreeShowAs() != null && template.getSpecificationThreeShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationThreeValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 3, 3);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(3).setCellValue("NA");
                }

                if (template.getSpecificationFour() != null) {
                    rowhead.createCell(4).setCellValue(template.getSpecificationFour());
                    // Drop Down Validation
                    if(template.getSpecificationFourShowAs() != null && template.getSpecificationFourShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationFourValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 4, 4);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(4).setCellValue("NA");
                }

                if (template.getSpecificationFive() != null) {
                    rowhead.createCell(5).setCellValue(template.getSpecificationFive());
                    // Drop Down Validation
                    if(template.getSpecificationFiveShowAs() != null && template.getSpecificationFiveShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationFiveValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 5, 5);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(5).setCellValue("NA");
                }

                if (template.getSpecificationSix() != null) {
                    rowhead.createCell(6).setCellValue(template.getSpecificationSix());
                    // Drop Down Validation
                    if(template.getSpecificationSixShowAs() != null && template.getSpecificationSixShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationSixValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 6, 6);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(6).setCellValue("NA");
                }

                if (template.getSpecificationSeven() != null) {
                    rowhead.createCell(7).setCellValue(template.getSpecificationSeven());
                    // Drop Down Validation
                    if(template.getSpecificationSevenShowAs() != null && template.getSpecificationSevenShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationSevenValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 7, 7);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(7).setCellValue("NA");
                }

                if (template.getSpecificationEight() != null) {
                    rowhead.createCell(8).setCellValue(template.getSpecificationEight());
                    // Drop Down Validation
                    if(template.getSpecificationEightShowAs() != null && template.getSpecificationEightShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationEightValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 8, 8);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(8).setCellValue("NA");
                }

                if (template.getSpecificationNine() != null) {
                    rowhead.createCell(9).setCellValue(template.getSpecificationNine());
                    // Drop Down Validation
                    if(template.getSpecificationNineShowAs() != null && template.getSpecificationNineShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationNineValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 9, 9);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(9).setCellValue("NA");
                }

                if (template.getSpecificationTen() != null) {
                    rowhead.createCell(10).setCellValue(template.getSpecificationTen());
                    // Drop Down Validation
                    if(template.getSpecificationTenShowAs() != null && template.getSpecificationTenShowAs().equals(ShowAsTemplate.DROPDOWN)) {
                        String result[] = template.getSpecificationTenValue().split("\\s*,\\s*");
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 10, 10);

                        DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(result);
                        DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                        dataValidation.setSuppressDropDownArrow(true);
                        spreadsheet.addValidationData(dataValidation);
                    }
                } else {
                    rowhead.createCell(10).setCellValue("NA");
                }

                rowhead.createCell(11).setCellValue("Tax Terms");
                rowhead.getCell(11).setCellStyle(style);
                rowhead.createCell(12).setCellValue("Payment Terms");
                rowhead.getCell(12).setCellStyle(style);
                rowhead.createCell(13).setCellValue("Delivery Terms");
                rowhead.getCell(13).setCellStyle(style);
                rowhead.createCell(14).setCellValue("Delivery Place");
                rowhead.getCell(14).setCellStyle(style);
                rowhead.createCell(15).setCellValue("Potential Qty");
                rowhead.getCell(15).setCellStyle(style);
                rowhead.createCell(16).setCellValue("UOM");
                rowhead.getCell(16).setCellStyle(style);
                rowhead.createCell(17).setCellValue("Remarks for Party (Max length 500 characters)");
                rowhead.createCell(18).setCellValue("Last Price");
                rowhead.getCell(18).setCellStyle(style);
                rowhead.createCell(19).setCellValue("Currency");
                rowhead.getCell(19).setCellStyle(style);
                rowhead.createCell(20).setCellValue("Open Costing");
                rowhead.getCell(20).setCellStyle(style);
                rowhead.createCell(21).setCellValue("Bid Capping Rate");
                // Header End
                for (RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants) {
                    Row rowData = spreadsheet.createRow(++row);

                    rowData.createCell(0).setCellValue(rnsQuotationVariant.getTitle().replace("Variant", "Lot"));

                    if (rnsQuotationVariant.getVarDescSpec1Value() != null) {
                        rowData.createCell(1).setCellValue(rnsQuotationVariant.getVarDescSpec1Value());
                    } else {
                        rowData.createCell(1).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec2Value() != null) {
                        rowData.createCell(2).setCellValue(rnsQuotationVariant.getVarDescSpec2Value());
                    } else {
                        rowData.createCell(2).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec3Value() != null) {
                        rowData.createCell(3).setCellValue(rnsQuotationVariant.getVarDescSpec3Value());
                    } else {
                        rowData.createCell(3).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec4Value() != null) {
                        rowData.createCell(4).setCellValue(rnsQuotationVariant.getVarDescSpec4Value());
                    } else {
                        rowData.createCell(4).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec5Value() != null) {
                        rowData.createCell(5).setCellValue(rnsQuotationVariant.getVarDescSpec5Value());
                    } else {
                        rowData.createCell(5).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec6Value() != null) {
                        rowData.createCell(6).setCellValue(rnsQuotationVariant.getVarDescSpec6Value());
                    } else {
                        rowData.createCell(6).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec7Value() != null) {
                        rowData.createCell(7).setCellValue(rnsQuotationVariant.getVarDescSpec7Value());
                    } else {
                        rowData.createCell(7).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec8Value() != null) {
                        rowData.createCell(8).setCellValue(rnsQuotationVariant.getVarDescSpec8Value());
                    } else {
                        rowData.createCell(8).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec9Value() != null) {
                        rowData.createCell(9).setCellValue(rnsQuotationVariant.getVarDescSpec9Value());
                    } else {
                        rowData.createCell(9).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getVarDescSpec10Value() != null) {
                        rowData.createCell(10).setCellValue(rnsQuotationVariant.getVarDescSpec10Value());
                    } else {
                        rowData.createCell(10).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getTaxTerms() != null) {
                        rowData.createCell(11).setCellValue(rnsTaxTermsMasterMap.get(rnsQuotationVariant.getTaxTerms()));
                    } else {
                        rowData.createCell(11).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getDealtermPaymentTerms() != null) {
                        rowData.createCell(12).setCellValue(rnsPayTermsMasterMap.get(rnsQuotationVariant.getDealtermPaymentTerms()));
                    } else {
                        rowData.createCell(12).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getDealtermDeliveryTerms() != null) {
                        rowData.createCell(13).setCellValue(rnsDelTermsMasterMap.get(rnsQuotationVariant.getDealtermDeliveryTerms()));
                    } else {
                        rowData.createCell(13).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getDealtermDeliverAt() != null) {
                        rowData.createCell(14).setCellValue(rnsDelPlaceMasterMap.get(rnsQuotationVariant.getDealtermDeliverAt()));
                    } else {
                        rowData.createCell(14).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getOrderQuantity() != null) {
                        rowData.createCell(15).setCellValue(rnsQuotationVariant.getOrderQuantity());
                    } else {
                        rowData.createCell(15).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getOrderUom() != null) {
                        rowData.createCell(16).setCellValue(rnsUomMasterMap.get(rnsQuotationVariant.getOrderUom()));
                    } else {
                        rowData.createCell(16).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getRemarks() != null) {
                        rowData.createCell(17).setCellValue(rnsQuotationVariant.getRemarks());
                    } else {
                        rowData.createCell(17).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getHistoricalPrice() != null) {
                        rowData.createCell(18).setCellValue(rnsQuotationVariant.getHistoricalPrice());
                    } else {
                        rowData.createCell(18).setCellValue("NA");
                    }

                    if (rnsQuotationVariant.getCurrency() != null) {
                        rowData.createCell(19).setCellValue(currencyMap.get(rnsQuotationVariant.getCurrency()));
                    } else {
                        rowData.createCell(19).setCellValue("NA");
                    }
                    rowData.createCell(20).setCellValue(rnsQuotationVariant.getOpenCosting());
                    if(rnsQuotationVariant.getBidStartPrice() != null) {
                        rowData.createCell(21).setCellValue(rnsQuotationVariant.getBidStartPrice());
                    }
                }

                for (int i = rnsQuotationVariants.size()+1 ; i<51 ; i++) {
                    Row rowData = spreadsheet.createRow(++row);
                    rowData.createCell(0).setCellValue("Lot "+ i);
                }
                CellRangeAddressList addressList = new CellRangeAddressList(1, 50, 20, 20);

                DataValidationHelper validationHelper = spreadsheet.getDataValidationHelper();
                DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(new String[] { "Not Required", "Required" });
                DataValidation dataValidation = validationHelper.createValidation(explicitListConstraint, addressList);
                dataValidation.setSuppressDropDownArrow(true);
                spreadsheet.addValidationData(dataValidation);
                // Set the content type and attachment header.
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=rnsQuotationDetails.xlsx");
                workbook.write(response.getOutputStream());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * POST  /UploadVariants : UploadVariants a new variant excel file.
     *
     * @param @feedback the feedback to create
     * @return the ResponseEntity with status 201 (Created) and with body the new , or with status 400 (Bad Request) if the  has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/import-rns-quotations-variants" , consumes = {"multipart/form-data"})
    @Timed
    public ResponseEntity<Void> UploadVariants(@RequestParam(required = false) MultipartFile file, Long id) throws URISyntaxException, IOException {

        List<RnsQuotationVariant> rnsQuotationVariants=new ArrayList<RnsQuotationVariant>();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);
        Workbook workbook = null;
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            FileInputStream excelFile = new FileInputStream(convFile);
            workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            List<RnsQuotationVendors> rnsQuotationVendors = null;

            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if(currentRow.getRowNum()==0) {} else {
                    Cell cell0 = currentRow.getCell(0);
                    cell0.setCellValue(cell0.getStringCellValue().replace("Lot", "Variant"));
                    cell0.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell1 = currentRow.getCell(1);
                    cell1.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell2 = currentRow.getCell(2);
                    cell2.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell3 = currentRow.getCell(3);
                    cell3.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell4 = currentRow.getCell(4);
                    cell4.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell5 = currentRow.getCell(5);
                    cell5.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell6 = currentRow.getCell(6);
                    cell6.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell7 = currentRow.getCell(7);
                    cell7.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell8 = currentRow.getCell(8);
                    cell8.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell9 = currentRow.getCell(9);
                    cell9.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell10 = currentRow.getCell(10);
                    cell10.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell11 = currentRow.getCell(11);
                    //cell11.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell12 = currentRow.getCell(12);
                    //cell12.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell13 = currentRow.getCell(13);
                    //cell13.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell14 = currentRow.getCell(14);
                    //cell14.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell15 = currentRow.getCell(15);
                    cell15.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell16 = currentRow.getCell(16);
                    //cell16.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell17 = currentRow.getCell(17);
                    cell17.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell18 = currentRow.getCell(18);
                    cell18.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell19 = currentRow.getCell(19);
                    //cell19.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell20 = currentRow.getCell(20);
                    //cell20.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell21 = currentRow.getCell(21);
                    if(cell21 != null) {
                        cell21.setCellType(Cell.CELL_TYPE_STRING);
                    }

                    if (cell0.getStringCellValue() != null && cell0.getStringCellValue().length() > 0) {
                        RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.getRnsQuotationVariantByTitleAndqAndQuotation(cell0.getStringCellValue(), id);
                        if (rnsQuotationVariant != null) {
                            if (cell0.getStringCellValue().equalsIgnoreCase("Variant 1")) {
                                rnsQuotationVendors = rnsQuotationVendorsRepository.getRnsQuotationVendorsByVariant(rnsQuotationVariant.getId());
                            }
                        } else if (cell0.getStringCellValue() != null && cell11.getStringCellValue() != null && cell12.getStringCellValue() != null && cell13.getStringCellValue() != null &&
                            cell14.getStringCellValue() != null && cell15.getStringCellValue() != null && cell16.getStringCellValue() != null &&
                            cell18.getStringCellValue() != null && cell19.getStringCellValue() != null && cell20.getStringCellValue() != null) {
                            rnsQuotationVariant = new RnsQuotationVariant();

                            rnsQuotationVariant.setTitle(cell0.getStringCellValue());
                            if(cell1.getStringCellValue() != null && cell1.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec1Value(cell1.getStringCellValue());
                            }
                            if(cell2.getStringCellValue() != null && cell2.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec2Value(cell2.getStringCellValue());
                            }
                            if(cell3.getStringCellValue() != null && cell3.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec3Value(cell3.getStringCellValue());
                            }
                            if(cell4.getStringCellValue() != null && cell4.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec4Value(cell4.getStringCellValue());
                            }
                            if(cell5.getStringCellValue() != null && cell5.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec5Value(cell5.getStringCellValue());
                            }
                            if(cell6.getStringCellValue() != null && cell6.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec6Value(cell6.getStringCellValue());
                            }
                            if(cell7.getStringCellValue() != null && cell7.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec7Value(cell7.getStringCellValue());
                            }
                            if(cell8.getStringCellValue() != null && cell8.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec8Value(cell8.getStringCellValue());
                            }
                            if(cell9.getStringCellValue() != null && cell9.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec9Value(cell9.getStringCellValue());
                            }
                            if(cell10.getStringCellValue() != null && cell10.getStringCellValue().equalsIgnoreCase("NA")) { } else {
                                rnsQuotationVariant.setVarDescSpec10Value(cell10.getStringCellValue());
                            }
                            rnsQuotationVariant.setTaxTerms(cell11.getStringCellValue().substring(cell11.getStringCellValue().lastIndexOf("##") + 2, cell11.getStringCellValue().length()));
                            rnsQuotationVariant.setDealtermPaymentTerms(cell12.getStringCellValue().substring(cell12.getStringCellValue().lastIndexOf("##") + 2, cell12.getStringCellValue().length()));
                            rnsQuotationVariant.setDealtermDeliveryTerms(cell13.getStringCellValue().substring(cell13.getStringCellValue().lastIndexOf("##") + 2, cell13.getStringCellValue().length()));
                            rnsQuotationVariant.setDealtermDeliverAt(cell14.getStringCellValue().substring(cell14.getStringCellValue().lastIndexOf("##") + 2, cell14.getStringCellValue().length()));
                            rnsQuotationVariant.setOrderQuantity(cell15.getStringCellValue());
                            rnsQuotationVariant.setOrderUom(cell16.getStringCellValue().substring(cell16.getStringCellValue().lastIndexOf("##") + 2, cell16.getStringCellValue().length()));
                            rnsQuotationVariant.setRemarks(cell17.getStringCellValue());
                            rnsQuotationVariant.setHistoricalPrice(new Float(cell18.getStringCellValue()));
                            rnsQuotationVariant.setCurrency(cell19.getStringCellValue().substring(cell19.getStringCellValue().lastIndexOf("##") + 2, cell19.getStringCellValue().length()));
                            rnsQuotationVariant.setOpenCosting(cell20.getStringCellValue());
                            if (cell21 != null && cell21.getStringCellValue() != null && StringUtils.isNumeric(cell21.getStringCellValue())) {
                                rnsQuotationVariant.setBidStartPrice(new Float(cell21.getStringCellValue()));
                            }
                            rnsQuotationVariant.setQuotation(rnsQuotation);
                            RnsQuotationVariant result = rnsQuotationVariantRepository.save(rnsQuotationVariant);
                            if (result != null) {
                                //Auction Variant save
                                AuctionVrnt auctionVrnt = new AuctionVrnt();
                                auctionVrnt.setVariant(result);
                                auctionVrnt.setUomOne(result.getOrderUom());
                                auctionVrnt.setConvFactOne(1.0f);
                                auctionVrntRepository.save(auctionVrnt);
                                if (rnsQuotationVendors != null && rnsQuotationVendors.size() > 0) {
                                    for (RnsQuotationVendors quotationVendors : rnsQuotationVendors) {
                                        quotationVendors.setId(null);
                                        quotationVendors.setExpiryQty(result.getOrderQuantity());
                                        quotationVendors.setVariant(result);
                                        quotationVendors.setVendorQuotation(rnsQuotation);
                                        rnsQuotationVendorsRepository.save(quotationVendors);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return ResponseEntity.ok().headers((HeaderUtil.importEntityCreationAlert(ENTITY_NAME, id.toString()))).build();
    }

    /**
     * GET  /export-rns-quotations-variants : export all variants of a selected quotation.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotations in body
     */
    @GetMapping("/export-rns-quotations-rfq-price/{id}")
    @Timed
    public @ResponseBody void downloadRFQFile(@PathVariable Long id, HttpServletResponse response) {
        Workbook workbook = null;
        try {
            RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);
            List<RnsQuotationVendors> rnsQuotationVendors = rnsQuotationVendorsRepository.findByVendorQuotationIdOrderByVendorCode(id);
            if(rnsQuotationVendors != null && rnsQuotationVendors.size()>0) {
                Template template = templateRepository.findTemplateById(new Long(rnsQuotation.getTemplate()));

                workbook = new XSSFWorkbook();
                Sheet spreadsheet = workbook.createSheet("Project# " + id);

                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
                font.setBold(true);
                style.setFont(font);

                int row = 0;

                Row rowTitle = spreadsheet.createRow(row);
                rowTitle.createCell(0).setCellValue("Project#");
                rowTitle.createCell(1).setCellValue(rnsQuotation.getId());
                spreadsheet.addMergedRegion(new CellRangeAddress(row, row, 2, 6));
                rowTitle.createCell(2).setCellValue(rnsQuotation.getProjectTitle());

                // Header Start
                Row rowhead = spreadsheet.createRow(++row);

                rowhead.createCell(0).setCellValue("Party ID");
                rowhead.createCell(1).setCellValue("Party Name");
                rowhead.createCell(2).setCellValue("Lot ID");
                rowhead.createCell(3).setCellValue("Lot Title");
                rowhead.createCell(4).setCellValue("Lot Description");
                rowhead.createCell(5).setCellValue("Element Name");
                rowhead.createCell(6).setCellValue("Value");
                // Header End
                Map<String, User> vendorMap = new HashMap<>();
                Map<String, AuctionVrnt> auctionVrntMap = new HashMap<>();
                for (RnsQuotationVendors quotationVendors : rnsQuotationVendors) {
                    AuctionVrnt auctionVrnt = null;
                    if(auctionVrntMap.containsKey(quotationVendors.getVariant().getId()+"")){
                        auctionVrnt = auctionVrntMap.get(quotationVendors.getVariant().getId()+"");
                    } else {
                        auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(quotationVendors.getVariant().getId());
                        auctionVrntMap.put(quotationVendors.getVariant().getId()+"", auctionVrnt);
                    }
                    if (auctionVrnt!=null && auctionVrnt.isApplicable() != null && auctionVrnt.isApplicable()) {
                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceRepository.getByVendorId(quotationVendors.getId());
                        if (auctionVrnt.getTextOne() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextOne());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceOne());
                            }
                        }
                        if (auctionVrnt.getTextTwo() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                vendorMap.put(quotationVendors.getVendorCode(), user);
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextTwo());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceTwo());
                            }
                        }
                        if (auctionVrnt.getTextThree() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextThree());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceThree());
                            }
                        }
                        if (auctionVrnt.getTextFour() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextFour());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceFour());
                            }
                        }
                        if (auctionVrnt.getTextFive() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextFive());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceFive());
                            }
                        }
                        if (auctionVrnt.getTextSix() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextSix());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceSix());
                            }
                        }
                        if (auctionVrnt.getTextSeven() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextSeven());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceSeven());
                            }
                        }
                        if (auctionVrnt.getTextEight() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextEight());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceEight());
                            }
                        }
                        if (auctionVrnt.getTextNine() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextNine());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceNine());
                            }
                        }
                        if (auctionVrnt.getTextTen() != null) {
                            Row rowData = spreadsheet.createRow(++row);

                            rowData.createCell(0).setCellValue(quotationVendors.getId());
                            if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                                User user = vendorMap.get(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            } else {
                                User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                                rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                            }
                            rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                            rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                            String desc = "";
                            if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                            } else {
                                desc += "NA" + "...";
                            }

                            if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                                desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                            } else {
                                desc += "NA";
                            }
                            rowData.createCell(4).setCellValue(desc);
                            rowData.createCell(5).setCellValue(auctionVrnt.getTextTen());

                            if (rnsRfqPrice != null) {
                                rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceTen());
                            }
                        }
                    } else {
                        Row rowData = spreadsheet.createRow(++row);

                        rowData.createCell(0).setCellValue(quotationVendors.getId());
                        if(vendorMap.containsKey(quotationVendors.getVendorCode())) {
                            User user = vendorMap.get(quotationVendors.getVendorCode());
                            rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                        } else {
                            User user = userRepository.findByLogin(quotationVendors.getVendorCode());
                            rowData.createCell(1).setCellValue(user.getVendorName()+"("+user.getFirstName()+" "+user.getLastName()+")");
                        }
                        rowData.createCell(2).setCellValue(quotationVendors.getVariant().getId());
                        rowData.createCell(3).setCellValue(quotationVendors.getVariant().getTitle().replace("Variant", "Lot"));
                        String desc = "";
                        if (quotationVendors.getVariant().getVarDescSpec1Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec1Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec2Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec2Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec3Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec3Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec4Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec4Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec5Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec5Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec6Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec6Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec7Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec7Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec8Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec8Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec9Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec9Value() + "...";
                        } else {
                            desc += "NA" + "...";
                        }

                        if (quotationVendors.getVariant().getVarDescSpec10Value() != null) {
                            desc += quotationVendors.getVariant().getVarDescSpec10Value() ;
                        } else {
                            desc += "NA";
                        }
                        rowData.createCell(4).setCellValue(desc);
                        rowData.createCell(5).setCellValue(rnsQuotation.getProjectTitle());
                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceRepository.getByVendorId(quotationVendors.getId());
                        if (rnsRfqPrice != null) {
                            rowData.createCell(6).setCellValue(rnsRfqPrice.getPriceOne());
                        }
                    }
                }
                // Set the content type and attachment header.
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=rnsQuotationDetails.xlsx");
                workbook.write(response.getOutputStream());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    /**
     * POST  /UploadVariants : UploadVariants a new variant excel file.
     *
     * @param @feedback the feedback to create
     * @return the ResponseEntity with status 201 (Created) and with body the new , or with status 400 (Bad Request) if the  has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/import-rns-quotations-rfq-price" , consumes = {"multipart/form-data"})
    @Timed
    public ResponseEntity<Void> UploadRfqPrice(@RequestParam(required = false) MultipartFile file, Long id) throws URISyntaxException, IOException {

        List<RnsQuotationVariant> rnsQuotationVariants = new ArrayList<RnsQuotationVariant>();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);
        Workbook workbook = null;
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            FileInputStream excelFile = new FileInputStream(convFile);
            workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            List<RnsQuotationVendors> rnsQuotationVendors = null;

            Iterator<Row> iterator = sheet.iterator();
            Map<String, AuctionVrnt> auctionVrntMap = new HashMap<>();
            Map<String, RnsRfqPrice> rnsRfqPriceMap = new HashMap<>();
            Map<String, RnsQuotationVendors> rnsQuotationVendorsMap = new HashMap<>();
            Map<String, VndrPrice> vndrPriceMap = new HashMap<>();
            if (rnsQuotation != null && rnsQuotation.getRfq() != null && rnsQuotation.getRfq() == true && rnsQuotation.getValidity() != null && Date.from(rnsQuotation.getValidity().toInstant()).after(new Date())) {
                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();
                    if (currentRow.getRowNum() == 0 || currentRow.getRowNum() == 1) {
                    } else {
                        // Party Id
                        Cell cell0 = currentRow.getCell(0);
                        cell0.setCellType(Cell.CELL_TYPE_STRING);

                        //Lot Id
                        Cell cell2 = currentRow.getCell(2);
                        cell2.setCellType(Cell.CELL_TYPE_STRING);

                        //Element
                        Cell cell5 = currentRow.getCell(5);
                        cell5.setCellType(Cell.CELL_TYPE_STRING);

                        //value
                        Cell cell6 = currentRow.getCell(6);
                        if(cell6 != null) {
                            cell6.setCellType(Cell.CELL_TYPE_STRING);
                        }

                        if (cell0.getStringCellValue() != null && cell0.getStringCellValue().length() > 0) {
                            if (cell0.getStringCellValue() != null && cell2.getStringCellValue() != null && cell6.getStringCellValue() != null) {
                                RnsRfqPrice result = null;
                                if (rnsRfqPriceMap.containsKey(cell0.getStringCellValue()+cell2.getStringCellValue())) {
                                    AuctionVrnt auctionVrnt = null;
                                    if (auctionVrntMap.containsKey(cell2.getStringCellValue())) {
                                        auctionVrnt = auctionVrntMap.get(cell2.getStringCellValue());
                                    } else {
                                        auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(new Long(cell2.getStringCellValue()));
                                        auctionVrntMap.put(cell2.getStringCellValue(), auctionVrnt);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextTwo() != null && auctionVrnt.getTextTwo().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceTwo(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceTwo(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactTwo() != null && auctionVrnt.getConvFactTwo() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactTwo() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    } else if(auctionVrnt != null && auctionVrnt.getTextThree() != null && auctionVrnt.getTextThree().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceThree(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceThree(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactThree() != null && auctionVrnt.getConvFactThree() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactThree() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextFour() != null && auctionVrnt.getTextFour().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceFour(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceFour(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactFour() != null && auctionVrnt.getConvFactFour() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactFour() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextFive() != null && auctionVrnt.getTextFive().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceFive(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceFive(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactFive() != null && auctionVrnt.getConvFactFive() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactFive() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextSix() != null && auctionVrnt.getTextSix().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceSix(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceSix(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactSix() != null && auctionVrnt.getConvFactSix() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactSix() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextSeven() != null && auctionVrnt.getTextSeven().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceSeven(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceSeven(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactSeven() != null && auctionVrnt.getConvFactSeven() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactSeven() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextEight() != null && auctionVrnt.getTextEight().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceEight(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceEight(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactEight() != null && auctionVrnt.getConvFactEight() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactEight() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextNine() != null && auctionVrnt.getTextNine().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceNine(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);

                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceNine(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactNine() != null && auctionVrnt.getConvFactNine() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactNine() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                    if(auctionVrnt != null && auctionVrnt.getTextTen() != null && auctionVrnt.getTextTen().equalsIgnoreCase(cell5.getStringCellValue())) {
                                        RnsRfqPrice rnsRfqPrice = rnsRfqPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        rnsRfqPrice.setPriceTen(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);
                                        VndrPrice vndrPrice = vndrPriceMap.get(cell0.getStringCellValue()+cell2.getStringCellValue());
                                        vndrPrice.setPriceTen(new Float(cell6.getStringCellValue()));
                                        VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                        vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);

                                        RnsQuotationVendors quotationVendors = rnsQuotationVendorsMap.get(cell0.getStringCellValue());
                                        Float totalValue = quotationVendors.getRegularRate()+new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactTen() != null && auctionVrnt.getConvFactTen() > 0) {
                                            totalValue = quotationVendors.getRegularRate() + (auctionVrnt.getConvFactTen() * new Float(cell6.getStringCellValue()));
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                    }
                                } else {
                                    RnsRfqPrice rnsRfqPriceupdate = rnsRfqPriceRepository.getByVendorId(new Long(cell0.getStringCellValue()));
                                    RnsQuotationVendors quotationVendors = rnsQuotationVendorsRepository.findById(new Long(cell0.getStringCellValue())).orElse(null);
                                    if (rnsRfqPriceupdate != null) {
                                        rnsRfqPriceupdate.createdBy(getCurrentUserLogin());
                                        rnsRfqPriceupdate.setCreatedDate(Instant.now());
                                        rnsRfqPriceupdate.setPriceOne(new Float(cell6.getStringCellValue()));
                                        result = rnsRfqPriceRepository.save(rnsRfqPriceupdate);
                                        AuctionVrnt auctionVrnt = null;
                                        if (auctionVrntMap.containsKey(cell2.getStringCellValue())) {
                                            auctionVrnt = auctionVrntMap.get(cell2.getStringCellValue());
                                        } else {
                                            auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(new Long(cell2.getStringCellValue()));
                                            auctionVrntMap.put(cell2.getStringCellValue(), auctionVrnt);
                                        }
                                        Float totalValue = new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactOne() != null && auctionVrnt.getConvFactOne() > 0) {
                                            totalValue = auctionVrnt.getConvFactOne() * new Float(cell6.getStringCellValue());
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                        if (result != null) {
                                            VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(quotationVendors.getVariant().getId(), quotationVendors.getVendorCode());
                                            if (vndrPrice != null) {
                                                vndrPrice.setPriceOne(result.getPriceOne());
                                                vndrPrice.setCreatedOn(Instant.now());
                                                vndrPrice.setSurrogate(true);
                                                VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                                vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);
                                            } else {
                                                vndrPrice = new VndrPrice();
                                                vndrPrice.setPriceOne(result.getPriceOne());
                                                vndrPrice.setVendorCode(quotationVendors.getVendorCode());
                                                vndrPrice.setCreatedOn(Instant.now());
                                                vndrPrice.setVariant(quotationVendors.getVariant());
                                                vndrPrice.setVndrQuotation(quotationVendors);
                                                vndrPrice.setSurrogate(true);
                                                VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                                vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);
                                            }
                                        }
                                    } else {
                                        RnsRfqPrice rnsRfqPrice = new RnsRfqPrice();
                                        rnsRfqPrice.setPriceOne(new Float(cell6.getStringCellValue()));
                                        rnsRfqPrice.setVendorId(quotationVendors.getId());
                                        rnsRfqPrice.createdBy(getCurrentUserLogin());
                                        rnsRfqPrice.setCreatedDate(Instant.now());
                                        result = rnsRfqPriceRepository.save(rnsRfqPrice);
                                        AuctionVrnt auctionVrnt = null;
                                        if (auctionVrntMap.containsKey(cell2.getStringCellValue())) {
                                            auctionVrnt = auctionVrntMap.get(cell2.getStringCellValue());
                                        } else {
                                            auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(new Long(cell2.getStringCellValue()));
                                            auctionVrntMap.put(cell2.getStringCellValue(), auctionVrnt);
                                        }
                                        Float totalValue = new Float(cell6.getStringCellValue());
                                        if (auctionVrnt != null && auctionVrnt.getConvFactOne() != null && auctionVrnt.getConvFactOne() > 0) {
                                            totalValue = auctionVrnt.getConvFactOne() * new Float(cell6.getStringCellValue());
                                        }
                                        quotationVendors.setRegularRate(totalValue);
                                        quotationVendors.setRfqUserType("S");
                                        RnsQuotationVendors vendors = rnsQuotationVendorsRepository.save(quotationVendors);
                                        rnsQuotationVendorsMap.put(cell0.getStringCellValue(), vendors);
                                        if (result != null) {
                                            VndrPrice vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(quotationVendors.getVariant().getId(), quotationVendors.getVendorCode());
                                            if (vndrPrice != null) {
                                                vndrPrice.setPriceOne(result.getPriceOne());
                                                vndrPrice.setCreatedOn(Instant.now());
                                                vndrPrice.setSurrogate(true);
                                                VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                                vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);
                                            } else {
                                                vndrPrice = new VndrPrice();
                                                vndrPrice.setPriceOne(result.getPriceOne());
                                                vndrPrice.setVendorCode(quotationVendors.getVendorCode());
                                                vndrPrice.setCreatedOn(Instant.now());
                                                vndrPrice.setVariant(quotationVendors.getVariant());
                                                vndrPrice.setVndrQuotation(quotationVendors);
                                                vndrPrice.setSurrogate(true);
                                                VndrPrice price = vndrPriceRepository.save(vndrPrice);
                                                vndrPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), price);
                                            }
                                        }
                                    }
                                    if (result != null) {
                                        rnsRfqPriceMap.put(cell0.getStringCellValue()+cell2.getStringCellValue(), result);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return ResponseEntity.ok().headers((HeaderUtil.importEntityCreationAlert(ENTITY_NAME, id.toString()))).build();
    }

    /**
     * GET  /rns-quotations/:id : get the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotation, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotations-closed/{id}")
    @Timed
    public ResponseEntity<RnsQuotation> getRnsQuotationClosed(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotation : {}", id);
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);
        rnsQuotation.setClosedBy(getCurrentUserLogin());
        rnsQuotation.setClosedDate(Instant.now());
        RnsQuotation result = rnsQuotationRepository.save(rnsQuotation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
