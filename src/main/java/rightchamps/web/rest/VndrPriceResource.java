package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.*;

import rightchamps.modal.ValueComparator;
import rightchamps.modal.VndrPriceBean;
import rightchamps.modal.VndrRank;
import rightchamps.repository.*;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.Instant;
import java.time.Clock;
import java.util.*;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;


/**
 * REST controller for managing VndrPrice.
 */
@RestController
@RequestMapping("/api")
public class VndrPriceResource {

    private final Logger log = LoggerFactory.getLogger(VndrPriceResource.class);

    private static final String ENTITY_NAME = "Vendor Price";

    private final VndrPriceRepository vndrPriceRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private AuctionVrntRepository auctionVrntRepository;


    public VndrPriceResource(VndrPriceRepository vndrPriceRepository) {
        this.vndrPriceRepository = vndrPriceRepository;
    }

    /**
     * POST  /vndr-prices : Create a new vndrPrice.
     *
     * @param vndrPrice the vndrPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vndrPrice, or with status 400 (Bad Request) if the vndrPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vndr-prices")
    @Timed
    public ResponseEntity<VndrPrice> createVndrPrice(@RequestBody VndrPrice vndrPrice) throws URISyntaxException {
        log.debug("REST request to save VndrPrice : {}");
        if (vndrPrice.getId() != null) {
            throw new BadRequestAlertException("A new vndrPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vndrPrice.setCreatedOn(Instant.now());
        VndrPrice result = vndrPriceRepository.save(vndrPrice);
        System.out.printf("result hjdasgdsdghs:::::::::::::::::::::::::::::::::::");
        String vendorCode = getCurrentUserLogin();
        Auction auction =  auctionRepository.getAuctionByQuotationId(result.getVariant().getQuotation().getId());
        Instant auctionStartDateTime =auction.getBiddingStartTime();
        Integer auctionBidTimeForOvertime = auction.getBidTimeForOvertimeStart();
        Instant currentTime = Instant.now();

      Duration between = Duration.between(auctionStartDateTime, currentTime);

        long minutesDifference = between.abs().toMinutes();

System.out.printf("difference between seconds:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::",minutesDifference);

        Integer bidRankOverTime = auction.getBidRankOverTime();
        Integer overtimePeriod = (Integer) auction.getOvertimePeriod();
        float overtimePeriodFloat = (float) overtimePeriod;
        Integer position = this.getPosition(result.getVariant().getId(), vendorCode);

        // System.out.println("------------------------------");
        // System.out.println("------------------------------");
        // System.out.println("------------------------------");
        // System.out.println("------------------------------");
        // System.out.println("------------------------------");
        // System.out.println("------------------------------");
        // System.out.println(position);
        if(position<=bidRankOverTime){
        	if(minutesDifference >= auctionBidTimeForOvertime){
                RnsQuotationVariant variant = rnsQuotationVariantRepository.findById(result.getVariant().getId()).orElse(null);
                variant.setOverTime(overtimePeriodFloat);
	            rnsQuotationVariantRepository.save(variant);
        	}
        }
        return ResponseEntity.created(new URI("/api/vndr-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vndr-prices : Updates an existing vndrPrice.
     *
     * @param vndrPrice the vndrPrice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vndrPrice,
     * or with status 400 (Bad Request) if the vndrPrice is not valid,
     * or with status 500 (Internal Server Error) if the vndrPrice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vndr-prices")
    @Timed
    public ResponseEntity<VndrPrice> updateVndrPrice(@RequestBody VndrPrice vndrPrice) throws URISyntaxException {
        log.debug("REST request to update VndrPrice : {}", vndrPrice);
        if (vndrPrice.getId() == null) {
            return createVndrPrice(vndrPrice);
        }
        VndrPrice result = vndrPriceRepository.save(vndrPrice);
        String vendorCode = getCurrentUserLogin();
        Auction auction =  auctionRepository.getAuctionByQuotationId(result.getVariant().getQuotation().getId());
        Instant auctionStartDateTime =auction.getBiddingStartTime();
        Integer auctionBidTimeForOvertime = auction.getBidTimeForOvertimeStart();
        Instant currentTime = Instant.now();

      Duration between = Duration.between(auctionStartDateTime, currentTime);

        long minutesDifference = between.abs().toMinutes();

System.out.printf("difference between seconds:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::",minutesDifference);

        Integer bidRankOverTime = auction.getBidRankOverTime();
        Integer overtimePeriod = (Integer) auction.getOvertimePeriod();
        float overtimePeriodFloat = (float) overtimePeriod;
        Integer position = this.getPosition(result.getVariant().getId(), vendorCode);


         if(position<=bidRankOverTime){
         	if(minutesDifference >= auctionBidTimeForOvertime){
                RnsQuotationVariant variant = rnsQuotationVariantRepository.findById(result.getVariant().getId()).orElse(null);
                variant.setOverTime(overtimePeriodFloat);
 	            rnsQuotationVariantRepository.save(variant);
         	}
         }


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vndrPrice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vndr-prices : get all the vndrPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vndrPrices in body
     */
    @GetMapping("/vndr-prices")
    @Timed
    public List<VndrPrice> getAllVndrPrices() {
        log.debug("REST request to get all VndrPrices");
        return vndrPriceRepository.findAll();
        }

    /**
     * GET  /auction-vrnts/:id : get the "id" auctionVrnt.
     *
     * @param @id the id of the auctionVrnt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionVrnt, or with status 404 (Not Found)
     */
    @PostMapping("/vndr-prices/get-by-variant-post")
    @Timed
    public ResponseEntity<VndrPrice> getVrntbyVariant(@RequestBody VndrPriceBean priceBean) {
        // log.debug("-----------------------------------------------");
        // log.debug("-----------------------------------------------");
        // log.debug("-----------------------------------------------");
        // log.debug("-----------------------------------------------");
        // log.debug("-----------------------------------------------");
        // log.debug("-----------------------------------------------");
        //log.debug("REST request to get AuctionVrnt : {}", variantId);
        // log.debug("Code : ");
        // System.out.println(vrntcode);
        VndrPrice vPrice = new VndrPrice();
        List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(priceBean.getVariantId());

        RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(priceBean.getVariantId()).orElse(null);
        //Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotationVariant.getQuotation().getId());
        RnsQuotation rnsQuotation = rnsQuotationVariant.getQuotation();
        // System.out.println(vndrPrice);
        for(VndrPrice price: vndrPrice){
            //vivek
            // System.out.println(price.getVendorCode().equals(vrntcode.trim()));
            // System.out.println(price.getVendorCode());
            // System.out.println(vrntcode);
            if(price.getVendorCode().trim().equals(priceBean.getVrntcode().trim())){
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null) {
                    totalPrice = totalPrice + price.getPriceOne();
                }
                if (price.getPriceTwo() != null) {
                    totalPrice = totalPrice + price.getPriceTwo();
                }
                if (price.getPriceThree() != null) {
                    totalPrice = totalPrice + price.getPriceThree();
                }
                if (price.getPriceFour() != null) {
                    totalPrice = totalPrice + price.getPriceFour();
                }
                if (price.getPriceFive() != null) {
                    totalPrice = totalPrice + price.getPriceFive();
                }
                if (price.getPriceSix() != null) {
                    totalPrice = totalPrice + price.getPriceSix();
                }
                if (price.getPriceSeven() != null) {
                    totalPrice = totalPrice + price.getPriceSeven();
                }
                if (price.getPriceEight() != null) {
                    totalPrice = totalPrice + price.getPriceEight();
                }
                if (price.getPriceNine() != null) {
                    totalPrice = totalPrice + price.getPriceNine();
                }
                if (price.getPriceTen() != null) {
                    totalPrice = totalPrice + price.getPriceTen();
                }

                if(vPrice.getId()==null){
                    vPrice = price;
                } else{
                    Float myPrice = 0.0f;
                    if (price.getPriceOne() != null) {
                        myPrice = myPrice + vPrice.getPriceOne();
                    }
                    if (price.getPriceTwo() != null) {
                        myPrice = myPrice + vPrice.getPriceTwo();
                    }
                    if (price.getPriceThree() != null) {
                        myPrice = myPrice + vPrice.getPriceThree();
                    }
                    if (price.getPriceFour() != null) {
                        myPrice = myPrice + vPrice.getPriceFour();
                    }
                    if (price.getPriceFive() != null) {
                        myPrice = myPrice + vPrice.getPriceFive();
                    }
                    if (price.getPriceSix() != null) {
                        myPrice = myPrice + vPrice.getPriceSix();
                    }
                    if (price.getPriceSeven() != null) {
                        myPrice = myPrice + vPrice.getPriceSeven();
                    }
                    if (price.getPriceEight() != null) {
                        myPrice = myPrice + vPrice.getPriceEight();
                    }
                    if (price.getPriceNine() != null) {
                        myPrice = myPrice + vPrice.getPriceNine();
                    }
                    if (price.getPriceTen() != null) {
                        myPrice = myPrice + vPrice.getPriceTen();
                    }
                    if(rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")){
                        if (totalPrice > myPrice)
                            vPrice = price;
                    } else if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                        if (totalPrice < myPrice)
                            vPrice = price;
                    }
                }
            }
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vPrice));
    }

    /**
     * GET  /vndr-prices/:id : get the "id" vndrPrice.
     *
     * @param @id the id of the vndrPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vndrPrice, or with status 404 (Not Found)
     */
    @GetMapping("/vndr-prices/{variantId}")
    @Timed
    public ResponseEntity<VndrPrice> getVndrPrice(@PathVariable Long variantId) {
        log.debug("REST request to get VndrPrice : {}", variantId);
        Optional<VndrPrice> vndrPrice = vndrPriceRepository.findById(variantId);
        return ResponseUtil.wrapOrNotFound(vndrPrice);
    }


    @GetMapping("vndr-prices-rank/{variantId}")
    @Timed
    public Integer getAuctionRank(@PathVariable Long variantId) {
        String vendorCode = getCurrentUserLogin();
        Integer position = this.getPosition(variantId, vendorCode);
        return position;
    }

    /**
     * GET  /vndr-prices/:id : get the "id" vndrPrice.
     *
     * @param @id the id of the vndrPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vndrPrice, or with status 404 (Not Found)
     */
    @PostMapping("/vndr-prices-highest")
    @Timed
    public ResponseEntity<VndrRank> getHighestPricePost(@RequestBody VndrPriceBean vndrPriceBean) {
        VndrRank vndrRank = null;
        String vendorCode = vndrPriceBean.getVrntcode();
        log.debug("REST request to get VndrPrice : {}", vndrPriceBean.getVariantId());
        VndrPrice vPrice = new VndrPrice();
        Integer position = 0;
        Float myPrice = 1.0f;
        boolean myPriceExist = false;
        Float highestPrice = 1.0f;

        RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(vndrPriceBean.getVariantId()).orElse(null);
        //Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotationVariant.getQuotation().getId());
        RnsQuotation rnsQuotation = rnsQuotationVariant.getQuotation();

        List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(vndrPriceBean.getVariantId());
        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(vndrPriceBean.getVariantId());

        Map<String,Float> vendorMap = new HashMap<String,Float>();
        Map<String,Long> vendorIdMap = new HashMap<String,Long>();
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
                        vendorIdMap.put(price.getVendorCode(),price.getId());
                    }
                } else if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                    if (totalPrice < vendorMap.get(price.getVendorCode())) {
                        vendorMap.put(price.getVendorCode(), totalPrice);
                        vendorCreatedOnMap.put(price.getVendorCode(),price.getCreatedOn());
                        vendorIdMap.put(price.getVendorCode(),price.getId());
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
                vendorIdMap.put(price.getVendorCode(),price.getId());
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

        boolean positionExist=false;
        long ctr=0;

        float tothighestPrice=0.0f;
        for (Map.Entry<String, Float> entry : sorted_map.entrySet()) {
            float rate = entry.getValue();
            String key = entry.getKey();
            Instant dt = vendorCreatedOnMap.get(key);
            int rev = vendorRevisionMap.get(key);
            long id = vendorIdMap.get(key);
            if(ctr==0) {
                tothighestPrice = rate;
                ++ctr;
            } else{
                ++ctr;
            }
            if(key.equals(vendorCode)) {
                vndrRank = new VndrRank(id, vendorCode, ctr, rate, dt, rev, tothighestPrice,"","");
                positionExist=true;
            }
        }
        if(tothighestPrice!=0.0f && positionExist==false){
            vndrRank = new VndrRank(new Long(0),vendorCode, null, null, null, null, tothighestPrice,"","");
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vndrRank));
    }

    /**
     * GET  /vndr-prices/:id : get the "id" vndrPrice.
     *
     * @param @id the id of the vndrPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vndrPrice, or with status 404 (Not Found)
     */
    @GetMapping("/vndr-prices-highest/{variantId}")
    @Timed
    public ResponseEntity<VndrRank> getHighestPrice(@PathVariable Long variantId) {
        VndrRank vndrRank = null;
        String vendorCode = getCurrentUserLogin();
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

        Map<String,Float> vendorMap = new HashMap<String,Float>();
        Map<String,Long> vendorIdMap = new HashMap<String,Long>();
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
                        vendorIdMap.put(price.getVendorCode(),price.getId());
                    }
                } else if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                    if (totalPrice < vendorMap.get(price.getVendorCode())) {
                        vendorMap.put(price.getVendorCode(), totalPrice);
                        vendorCreatedOnMap.put(price.getVendorCode(),price.getCreatedOn());
                        vendorIdMap.put(price.getVendorCode(),price.getId());
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
                vendorIdMap.put(price.getVendorCode(),price.getId());
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

        boolean positionExist=false;
        long ctr=0;

        float tothighestPrice=0.0f;
        for (Map.Entry<String, Float> entry : sorted_map.entrySet()) {
            float rate = entry.getValue();
            String key = entry.getKey();
            Instant dt = vendorCreatedOnMap.get(key);
            int rev = vendorRevisionMap.get(key);
            long id = vendorIdMap.get(key);
            if(ctr==0) {
                tothighestPrice = rate;
                ++ctr;
            } else{
                ++ctr;
            }
            if(key.equals(vendorCode)) {
                vndrRank = new VndrRank(id, vendorCode, ctr, rate, dt, rev, tothighestPrice,"","");
                positionExist=true;
            }
        }
        if(tothighestPrice!=0.0f && positionExist==false){
            vndrRank = new VndrRank(new Long(0),vendorCode, null, null, null, null, tothighestPrice,"","");
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vndrRank));
    }

    /**
     * GET  /vndr-prices/:id : get the "id" vndrPrice.
     *
     * @param @id the id of the vndrPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vndrPrice, or with status 404 (Not Found)
     */
    @GetMapping("/vndr-prices-lowest/{variantId}")
    @Timed
    public ResponseEntity<VndrPrice> getLowestPrice(@PathVariable Long variantId) {
        log.debug("REST request to get VndrPrice : {}", variantId);
        VndrPrice vPrice = new VndrPrice();
        Float highestPrice = 1.0f;
        List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(variantId);
        for(VndrPrice price: vndrPrice){
            Float totalPrice = 0.0f;
            if(price.getPriceOne() != null){
                totalPrice = totalPrice +price.getPriceOne();
            }
            if(price.getPriceTwo() != null){
                totalPrice = totalPrice +price.getPriceTwo();
            }
            if(price.getPriceThree() != null){
                totalPrice = totalPrice +price.getPriceThree();
            }
            if(price.getPriceFour() != null){
                totalPrice = totalPrice +price.getPriceFour();
            }
            if(price.getPriceFive() != null){
                totalPrice = totalPrice +price.getPriceFive();
            }
            if(price.getPriceSix() != null){
                totalPrice = totalPrice +price.getPriceSix();
            }
            if(price.getPriceSeven() != null){
                totalPrice = totalPrice +price.getPriceSeven();
            }
            if(price.getPriceEight() != null){
                totalPrice = totalPrice +price.getPriceEight();
            }
            if(price.getPriceNine() != null){
                totalPrice = totalPrice +price.getPriceNine();
            }
            if(price.getPriceTen() != null){
                totalPrice = totalPrice +price.getPriceTen();
            }

            if((totalPrice < highestPrice) || (highestPrice == 1)){
                highestPrice = totalPrice;
                vPrice = price;
            }
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vPrice));
    }

    public LinkedHashMap<String, Float> sortHashMapByValues(
        HashMap<String, Float> passedMap) {
        List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
        List<Float> mapValues = new ArrayList<Float>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Float> sortedMap =
            new LinkedHashMap<>();

        Iterator<Float> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Float val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Float comp1 = passedMap.get(key);
                Float comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
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

        Map<String,Float> vendorMap = new HashMap<String,Float>();
        for (VndrPrice price : vndrPrice) {
            if(vendorMap.containsKey(price.getVendorCode())){
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null) {
                    totalPrice = totalPrice + price.getPriceOne();
                }
                if (price.getPriceTwo() != null) {
                    totalPrice = totalPrice + price.getPriceTwo();
                }
                if (price.getPriceThree() != null) {
                    totalPrice = totalPrice + price.getPriceThree();
                }
                if (price.getPriceFour() != null) {
                    totalPrice = totalPrice + price.getPriceFour();
                }
                if (price.getPriceFive() != null) {
                    totalPrice = totalPrice + price.getPriceFive();
                }
                if (price.getPriceSix() != null) {
                    totalPrice = totalPrice + price.getPriceSix();
                }
                if (price.getPriceSeven() != null) {
                    totalPrice = totalPrice + price.getPriceSeven();
                }
                if (price.getPriceEight() != null) {
                    totalPrice = totalPrice + price.getPriceEight();
                }
                if (price.getPriceNine() != null) {
                    totalPrice = totalPrice + price.getPriceNine();
                }
                if (price.getPriceTen() != null) {
                    totalPrice = totalPrice + price.getPriceTen();
                }
                if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("AUCTION")) {
                    if (totalPrice > vendorMap.get(price.getVendorCode()))
                        vendorMap.put(price.getVendorCode(),totalPrice);
                } else if (rnsQuotation.getEventType()!=null && rnsQuotation.getEventType().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                    if (totalPrice < vendorMap.get(price.getVendorCode()))
                        vendorMap.put(price.getVendorCode(),totalPrice);
                }
            } else{
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null) {
                    totalPrice = totalPrice + price.getPriceOne();
                }
                if (price.getPriceTwo() != null) {
                    totalPrice = totalPrice + price.getPriceTwo();
                }
                if (price.getPriceThree() != null) {
                    totalPrice = totalPrice + price.getPriceThree();
                }
                if (price.getPriceFour() != null) {
                    totalPrice = totalPrice + price.getPriceFour();
                }
                if (price.getPriceFive() != null) {
                    totalPrice = totalPrice + price.getPriceFive();
                }
                if (price.getPriceSix() != null) {
                    totalPrice = totalPrice + price.getPriceSix();
                }
                if (price.getPriceSeven() != null) {
                    totalPrice = totalPrice + price.getPriceSeven();
                }
                if (price.getPriceEight() != null) {
                    totalPrice = totalPrice + price.getPriceEight();
                }
                if (price.getPriceNine() != null) {
                    totalPrice = totalPrice + price.getPriceNine();
                }
                if (price.getPriceTen() != null) {
                    totalPrice = totalPrice + price.getPriceTen();
                }
                vendorMap.put(price.getVendorCode(),totalPrice);
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
        boolean positionExist=false;
        for (String key : sorted_map.keySet()) {
            ++position;
            if (key.equalsIgnoreCase(vendorCode)) {
                positionExist=true;
                break;
            }
        }

        if(positionExist==false)
            position=0;

        /*for (VndrPrice price : vndrPrice) {
            if (vendorCode != null && price.getVendorCode().equalsIgnoreCase(vendorCode)) {
                Float totalPrice = 0.0f;
                    if (price.getPriceOne() != null) {
                    totalPrice = totalPrice + price.getPriceOne();
                }
                if (price.getPriceTwo() != null) {
                    totalPrice = totalPrice + price.getPriceTwo();
                }
                if (price.getPriceThree() != null) {
                    totalPrice = totalPrice + price.getPriceThree();
                }
                if (price.getPriceFour() != null) {
                    totalPrice = totalPrice + price.getPriceFour();
                }
                if (price.getPriceFive() != null) {
                    totalPrice = totalPrice + price.getPriceFive();
                }
                if (price.getPriceSix() != null) {
                    totalPrice = totalPrice + price.getPriceSix();
                }
                if (price.getPriceSeven() != null) {
                    totalPrice = totalPrice + price.getPriceSeven();
                }
                if (price.getPriceEight() != null) {
                    totalPrice = totalPrice + price.getPriceEight();
                }
                if (price.getPriceNine() != null) {
                    totalPrice = totalPrice + price.getPriceNine();
                }
                if (price.getPriceTen() != null) {
                    totalPrice = totalPrice + price.getPriceTen();
                }
                if (myPriceExist) {
                    if (auction.getEvent().toString().equalsIgnoreCase("AUCTION")) {
                        if (totalPrice > myPrice)
                            myPrice = totalPrice;
                    } else if (auction.getEvent().toString().equalsIgnoreCase("REVERSE_AUCTION")) {
                        if (totalPrice < myPrice)
                            myPrice = totalPrice;
                    }
                } else {
                    myPriceExist = true;
                    myPrice = totalPrice;
                }
            }
        }

        for (VndrPrice price1 : vndrPrice) {
            Float totalPrice = 0.0f;
            if (price1.getPriceOne() != null) {
                totalPrice = totalPrice + price1.getPriceOne();
            }
            if (price1.getPriceTwo() != null) {
                totalPrice = totalPrice + price1.getPriceTwo();
            }
            if (price1.getPriceThree() != null) {
                totalPrice = totalPrice + price1.getPriceThree();
            }
            if (price1.getPriceFour() != null) {
                totalPrice = totalPrice + price1.getPriceFour();
            }
            if (price1.getPriceFive() != null) {
                totalPrice = totalPrice + price1.getPriceFive();
            }
            if (price1.getPriceSix() != null) {
                totalPrice = totalPrice + price1.getPriceSix();
            }
            if (price1.getPriceSeven() != null) {
                totalPrice = totalPrice + price1.getPriceSeven();
            }
            if (price1.getPriceEight() != null) {
                totalPrice = totalPrice + price1.getPriceEight();
            }
            if (price1.getPriceNine() != null) {
                totalPrice = totalPrice + price1.getPriceNine();
            }
            if (price1.getPriceTen() != null) {
                totalPrice = totalPrice + price1.getPriceTen();
            }


            if (auction.getEvent().toString().equalsIgnoreCase("AUCTION")) {
                if (totalPrice > myPrice) {
                    position++;
                }
            } else {
                if (totalPrice < myPrice) {
                    position++;
                }
            }
        }*/
        return position;
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
     * DELETE  /vndr-prices/:id : delete the "id" vndrPrice.
     *
     * @param id the id of the vndrPrice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vndr-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteVndrPrice(@PathVariable Long id) {
        log.debug("REST request to delete VndrPrice : {}", id);
        vndrPriceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
