package rightchamps.web.rest;

// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;

import com.codahale.metrics.annotation.Timed;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.*;
import rightchamps.modal.*;
import rightchamps.repository.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * REST controller for managing RnsQuotation.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationReportResource {
    private final Logger log = LoggerFactory.getLogger(RnsQuotationResource.class);

    private static final String ENTITY_NAME = "Quotation";

    private final RnsQuotationRepository rnsQuotationRepository;

    @Inject
    private TemplateRepository templateRepository;

    @Inject
    private RnsPayTermsMasterRepository rnsPayTermsMasterRepository;

    @Inject
    private RnsDelPlaceMasterRepository rnsDelPlaceMasterRepository;

    @Inject
    private RnsTaxTermsMasterRepository rnsTaxTermsMasterRepository;

    @Inject
    private RnsDelTermsMasterRepository rnsDelTermsMasterRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private VndrPriceRepository vndrPriceRepository;

    @Inject
    private AuctionVrntRepository auctionVrntRepository;

    @Inject
    private RnsUpchargeDtlRepository rnsUpchargeDtlRepository;

    @Inject
    private AuctionRepository auctionRepository;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ApplicationProperties applicationProperties;

    public RnsQuotationReportResource(RnsQuotationRepository rnsQuotationRepository) {
        this.rnsQuotationRepository = rnsQuotationRepository;

    }

    /**
     * GET  /rns-quotations/:id : get the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotation, or with status 404 (Not Found)
     */

    @GetMapping(value ="/rns-quotations-details/pdf/{id}")
    public @ResponseBody void getQuotationFullDetailsForPdf(@PathVariable Long id, HttpServletResponse response){
        String path = applicationProperties.getTemplatePath()+"jasper/";
        //InputStream inputStream = this.getClass().getResourceAsStream("/jasper/rnsQuotationReport.jrxml");
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(path+"/RnsQuotationReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String,Object> parameters = new HashMap<String, Object>();

            List<RnsQuotationReportDetails> rnsQuotationFullDetails = new ArrayList<RnsQuotationReportDetails>();
            //String vendorCode = getCurrentUserLogin();
            RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);;
            Template template = null;
            if(rnsQuotation.getTemplate()!=null && rnsQuotation.getTemplate().length()>0) {
                template = templateRepository.findTemplateById(new Long(rnsQuotation.getTemplate()));
            }
            if(template==null){
                template = new Template();
            }

            RnsQuotationReportDetails quotationFullDetails = new RnsQuotationReportDetails();
            BeanUtils.copyProperties(quotationFullDetails,rnsQuotation);
            quotationFullDetails.setCreatedByName(quotationFullDetails.getUser().getFirstName()+" "+ quotationFullDetails.getUser().getLastName());
            quotationFullDetails.setCreatedOnTimeStamp(Timestamp.from(quotationFullDetails.getCreatedOn()));

            Auction auction = auctionRepository.getAuctionByQuotationId(quotationFullDetails.getId());
            List<RnsQuotationVariant> dataVariants= rnsQuotationRepository.getRnsQuotationVariantsList(id);
            List<RnsQuotationVariantReportDetails> fullVariant = new ArrayList<RnsQuotationVariantReportDetails>();
            Map<String, User> usersMap = new HashMap<String, User>();
            Map<Long, AuctionVrnt> vrntMap = new HashMap<Long, AuctionVrnt>();
            Integer inc = 0;
            Integer inctemp = 0;
            Integer overTime = 0;
            for(RnsQuotationVariant rqvariant: dataVariants){
                ++inc;
                RnsQuotationVariantReportDetails rnsQuotationVariantReportDetails = new RnsQuotationVariantReportDetails();
                BeanUtils.copyProperties(rnsQuotationVariantReportDetails,rqvariant);

                rnsQuotationVariantReportDetails.setVarDescSpec1(template.getSpecificationOne());
                rnsQuotationVariantReportDetails.setVarDescSpec2(template.getSpecificationTwo());
                rnsQuotationVariantReportDetails.setVarDescSpec3(template.getSpecificationThree());
                rnsQuotationVariantReportDetails.setVarDescSpec4(template.getSpecificationFour());
                rnsQuotationVariantReportDetails.setVarDescSpec5(template.getSpecificationFive());
                rnsQuotationVariantReportDetails.setVarDescSpec6(template.getSpecificationSix());
                rnsQuotationVariantReportDetails.setVarDescSpec7(template.getSpecificationSeven());
                rnsQuotationVariantReportDetails.setVarDescSpec8(template.getSpecificationEight());
                rnsQuotationVariantReportDetails.setVarDescSpec9(template.getSpecificationNine());
                rnsQuotationVariantReportDetails.setVarDescSpec10(template.getSpecificationTen());

                if(rnsQuotationVariantReportDetails.getDealtermDeliveryTerms()!=null && rnsQuotationVariantReportDetails.getDealtermDeliveryTerms().length()>0){
                    RnsDelTermsMaster rnsDelTermsMaster = rnsDelTermsMasterRepository.findByDelTermsCode(rnsQuotationVariantReportDetails.getDealtermDeliveryTerms());
                    if(rnsDelTermsMaster!=null)
                        rnsQuotationVariantReportDetails.setDealtermDeliveryTermsDesc(rnsDelTermsMaster.getDelTermsCodeDesc());
                }
                if(rnsQuotationVariantReportDetails.getTaxTerms()!=null && rnsQuotationVariantReportDetails.getTaxTerms().length()>0) {
                    RnsTaxTermsMaster rnsTaxTermsMaster =rnsTaxTermsMasterRepository.findByTaxTermsCode(rnsQuotationVariantReportDetails.getTaxTerms());
                    if(rnsTaxTermsMaster!=null)
                        rnsQuotationVariantReportDetails.setDealtermsTaxTermsDesc(rnsTaxTermsMaster.getTaxTermsDesc());
                }
                if(rnsQuotationVariantReportDetails.getDealtermDeliverAt()!=null && rnsQuotationVariantReportDetails.getDealtermDeliverAt().length()>0) {
                    RnsDelPlaceMaster rnsDelPlaceMaster = rnsDelPlaceMasterRepository.findByDelPlaceCode(rnsQuotationVariantReportDetails.getDealtermDeliverAt());
                    if(rnsDelPlaceMaster!=null)
                        rnsQuotationVariantReportDetails.setDealtermsDelPlaceDesc(rnsDelPlaceMaster.getCodeDesc());
                }
                if(rnsQuotationVariantReportDetails.getDealtermPaymentTerms()!=null && rnsQuotationVariantReportDetails.getDealtermPaymentTerms().length()>0){
                    RnsPayTermsMaster rnsPayTermsMaster = rnsPayTermsMasterRepository.findByPayTermsCode(rnsQuotationVariantReportDetails.getDealtermPaymentTerms());
                    if(rnsPayTermsMaster!=null)
                        rnsQuotationVariantReportDetails.setDealtermPaymentTermsDesc(rnsPayTermsMaster.getPayTermsCodeDesc());
                }

                List<RnsQuotationVendors> vendors = rnsQuotationRepository.getRnsQuotationVendorsByVariantList(rnsQuotationVariantReportDetails.getId());
                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantReportDetails.getId());
                TreeMap<String, VndrRank> positions = getPosition(rnsQuotationVariantReportDetails, auctionVrnt, quotationFullDetails.getEventType());

                //rnsQuotationVariantReportDetails.setRfqCreatedDate();
                //rnsQuotationVariantReportDetails.setRfqPublishedDate();
                rnsQuotationVariantReportDetails.setRfqExpiredDate(quotationFullDetails.getValidity());
                rnsQuotationVariantReportDetails.setNoOfInvites(vendors.size());

                //rnsQuotationVariantReportDetails.setAuctionCreatedDate();
                //rnsQuotationVariantReportDetails.setAuctionPublishedDate();
                rnsQuotationVariantReportDetails.setAuctionNoOfInvites(vendors.size());
                rnsQuotationVariantReportDetails.setAuctionNoOfPortal(positions.size());
                rnsQuotationVariantReportDetails.setAuctionNoOfSurrogate(0);
                rnsQuotationVariantReportDetails.setAuctionNoOfTotal(positions.size()+0);
                int noOfPortal = 0;
                int noOfSurrogate = 0;

                if (rqvariant.getOverTime() != null) {
                    overTime += rqvariant.getOverTime().intValue();
                }

                if(auction!=null && auction.getOvertimePeriod()>0 && rqvariant.getOverTime()!=null) {
                    rnsQuotationVariantReportDetails.setNoOfOverTime(rqvariant.getOverTime().intValue() / auction.getOvertimePeriod());
                }

                if(auction!=null) {
                    Instant startTime = auction.getBiddingStartTime();
                    Integer lotGap = auction.getTimeBetweenLots();
                    Integer lotRunningTime = auction.getLotRunningTime();
                    if (inc == 1) {
                        rnsQuotationVariantReportDetails.setAuctionStartDate(Timestamp.from(startTime));
                        Instant endTime = startTime.plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES);
                        rnsQuotationVariantReportDetails.setAuctionEndDate(Timestamp.from(endTime));
                    } else {
                        rnsQuotationVariantReportDetails.setAuctionStartDate(Timestamp.from(startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES)));
                        Instant endTime = startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES);
                        rnsQuotationVariantReportDetails.setAuctionEndDate(Timestamp.from(endTime));
                    }
                }


                List<RnsQuotationVendorsReportDetails> rnsQuotationVendorsReportDetails = new ArrayList<RnsQuotationVendorsReportDetails>();
                for (RnsQuotationVendors quotationVendors : vendors) {
                    RnsQuotationVendorsReportDetails vendorsReportDetails = new RnsQuotationVendorsReportDetails();
                    BeanUtils.copyProperties(vendorsReportDetails,quotationVendors);
                    if(usersMap.containsKey(vendorsReportDetails.getVendorCode())){
                        User user = usersMap.get(vendorsReportDetails.getVendorCode());
                        vendorsReportDetails.setVendorCode(user.getVendorCode());
                        vendorsReportDetails.setVendorName(user.getVendorName());
                        vendorsReportDetails.setFirstName(user.getFirstName());
                        vendorsReportDetails.setLastName(user.getLastName());
                    } else{
                        User user=userRepository.findByLogin(vendorsReportDetails.getVendorCode());
                        vendorsReportDetails.setVendorCode(user.getVendorCode());
                        vendorsReportDetails.setVendorName(user.getVendorName());
                        vendorsReportDetails.setFirstName(user.getFirstName());
                        vendorsReportDetails.setLastName(user.getLastName());
                        usersMap.put(vendorsReportDetails.getVendorCode(),user);
                    }
                    if(positions.containsKey(quotationVendors.getVendorCode())){
                        VndrRank vndrRank = positions.get(quotationVendors.getVendorCode());
                        vendorsReportDetails.setBidRate(vndrRank.getPrice());
                        vendorsReportDetails.setRank(vndrRank.getRank());
                    }

                    if(vendorsReportDetails.getBidRate()==null){
                        vendorsReportDetails.setBidRate(0.0f);
                    }
                    if(vendorsReportDetails.getRegularRate()==null){
                        vendorsReportDetails.setRegularRate(0.0f);
                    }
                    vendorsReportDetails.setUpCharge(rnsUpchargeDtlRepository.getValuesByVendorId(vendorsReportDetails.getId()));


                    if(vendorsReportDetails.getRfqUserType()!=null && vendorsReportDetails.getRfqUserType().equals("V")){
                        ++noOfPortal;
                    } else if(vendorsReportDetails.getRfqUserType()!=null && vendorsReportDetails.getRfqUserType().equals("S")){
                        ++noOfSurrogate;
                    }

                    rnsQuotationVendorsReportDetails.add(vendorsReportDetails);
                }

                rnsQuotationVariantReportDetails.setNoOfPortal(noOfPortal);
                rnsQuotationVariantReportDetails.setNoOfSurrogate(noOfSurrogate);
                rnsQuotationVariantReportDetails.setNoOfTotal(noOfPortal+noOfSurrogate);


                rnsQuotationVariantReportDetails.setQuotationVendors(rnsQuotationVendorsReportDetails);
                fullVariant.add(rnsQuotationVariantReportDetails);
            }
            quotationFullDetails.setQuotationVariants(fullVariant);

            rnsQuotationFullDetails.add(quotationFullDetails);

            JRDataSource jrDataSource = new JRBeanCollectionDataSource(rnsQuotationFullDetails);

            parameters.put("datasource", jrDataSource);
            parameters.put("SUBREPORT_DIR",path);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, jrDataSource);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition","attachment; filename=Project"+id+".pdf");

            final OutputStream outputStream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeMap<String, VndrRank> getPosition(RnsQuotationVariantReportDetails rnsQuotationVariant, AuctionVrnt auctionVrnt, String eventType) {
        VndrPrice vPrice = new VndrPrice();
        Integer position = 0;
        Float myPrice = 1.0f;
        boolean myPriceExist = false;
        Float highestPrice = 1.0f;

        List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(rnsQuotationVariant.getId());

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

                if (eventType!=null && eventType.equalsIgnoreCase("AUCTION")) {
                    if (totalPrice > vendorMap.get(price.getVendorCode())) {
                        vendorMap.put(price.getVendorCode(), totalPrice);
                        vendorCreatedOnMap.put(price.getVendorCode(),price.getCreatedOn());
                    }
                } else if (eventType!=null && eventType.equalsIgnoreCase("REVERSE_AUCTION")) {
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
        if (eventType!=null && eventType.equalsIgnoreCase("AUCTION")) {
            ValueComparator bvc = new ValueComparator(vendorMap,true);
            sorted_map = new TreeMap<String, Float>(bvc);
            sorted_map.putAll(vendorMap);
        }  else if (eventType!=null && eventType.equalsIgnoreCase("REVERSE_AUCTION")) {
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

    /*@GetMapping(value ="/rns-quotations-details/pdf/{id}")
    public ModelAndView getQuotationFullDetailsForPdf(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException, JRException {
        log.debug("REST request to get RnsQuotation : {}", id);

        JasperReportsPdfView view = new JasperReportsPdfView();
        view.setUrl("classpath:/jasper/rnsQuotationReport.jrxml");
        view.setApplicationContext(applicationContext);

        Map<String, Object> params = new HashMap<String, Object>();

        List<RnsQuotationFullDetails> rnsQuotationFullDetails = new ArrayList<RnsQuotationFullDetails>();
        //String vendorCode = getCurrentUserLogin();
        RnsQuotation rnsQuotation = rnsQuotationRepository.getOne(id);

        RnsQuotationFullDetails quotationFullDetails = new RnsQuotationFullDetails();
        BeanUtils.copyProperties(quotationFullDetails,rnsQuotation);

        rnsQuotationFullDetails.add(quotationFullDetails);

        params.put("datasource",rnsQuotationFullDetails);

        return new ModelAndView(view,params);
    }*/

    /**
     * GET  /rns-quotations/:id : get the "id" rnsQuotation.
     *
     * @param id the id of the rnsQuotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotation, or with status 404 (Not Found)
     */
    @GetMapping(value ="/rns-quotation-details/xlsx/{id}", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Timed
    public HttpEntity<byte[]> getQuotationFullDetailsForXlsx(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException, JRException, IOException {
        log.debug("REST request to get RnsQuotation : {}", id);


        List<RnsQuotationFullDetails> rnsQuotationFullDetails = new ArrayList<RnsQuotationFullDetails>();
        //String vendorCode = getCurrentUserLogin();
        RnsQuotation rnsQuotation = rnsQuotationRepository.findById(id).orElse(null);

        RnsQuotationFullDetails quotationFullDetails = new RnsQuotationFullDetails();
        BeanUtils.copyProperties(quotationFullDetails, rnsQuotation);

        quotationFullDetails.setCreatedByName(quotationFullDetails.getUser().getFirstName()+ " " + quotationFullDetails.getUser().getLastName());

        rnsQuotationFullDetails.add(quotationFullDetails);

        InputStream rnsQuotationReportStream
            = getClass().getResourceAsStream("/rnsQuotationReport.jrxml");
        JasperReport jasperReport
            = JasperCompileManager.compileReport(rnsQuotationReportStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
            jasperReport, null, new JRBeanCollectionDataSource(rnsQuotationFullDetails));

        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        final byte[] data;

        try(ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()){
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            xlsxExporter.exportReport();

            data = xlsReport.toByteArray();
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rnsQuotation_" + id + ".xlsx");
        header.setContentLength(data.length);

        return new HttpEntity<byte[]>(data, header);
    }
}
