package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.apache.commons.beanutils.BeanUtils;
import rightchamps.domain.*;

import rightchamps.modal.*;
import rightchamps.repository.*;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing RnsQuotationVendors.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationVendorsResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationVendorsResource.class);

    private static final String ENTITY_NAME = "Quotation Vendors";

    private final RnsQuotationVendorsRepository rnsQuotationVendorsRepository;

    @Inject
    private VndrPriceRepository vndrPriceRepository;

    @Inject
    RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    AuctionVrntRepository auctionVrntRepository;

    @Inject
    AuctionRepository auctionRepository;


    public RnsQuotationVendorsResource(RnsQuotationVendorsRepository rnsQuotationVendorsRepository) {
        this.rnsQuotationVendorsRepository = rnsQuotationVendorsRepository;
    }

    /**
     * POST  /rns-quotation-vendors : Create a new rnsQuotationVendors.
     *
     * @param rnsQuotationVendors the rnsQuotationVendors to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotationVendors, or with status 400 (Bad Request) if the rnsQuotationVendors has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotation-vendors")
    @Timed
    public ResponseEntity<RnsQuotationVendors> createRnsQuotationVendors(@RequestBody RnsQuotationVendors rnsQuotationVendors) throws URISyntaxException {
        log.debug("REST request to save RnsQuotationVendors : {}", rnsQuotationVendors);
        if (rnsQuotationVendors.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotationVendors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsQuotationVendors result = null;
        result = rnsQuotationVendorsRepository.getRnsQuotationVendorsByVariantAndVendorQuotationAndVendorCode(rnsQuotationVendors.getVariant().getId(),rnsQuotationVendors.getVendorQuotation().getId(),rnsQuotationVendors.getVendorCode());
        if(result!=null && result.getId()!=null){} else {
            result = rnsQuotationVendorsRepository.save(rnsQuotationVendors);
        }
        return ResponseEntity.created(new URI("/api/rns-quotation-vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotation-vendors : Updates an existing rnsQuotationVendors.
     *
     * @param rnsQuotationVendors the rnsQuotationVendors to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationVendors,
     * or with status 400 (Bad Request) if the rnsQuotationVendors is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationVendors couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-vendors")
    @Timed
    public ResponseEntity<RnsQuotationVendors> updateRnsQuotationVendors(@RequestBody RnsQuotationVendors rnsQuotationVendors) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationVendors : {}", rnsQuotationVendors);
        if (rnsQuotationVendors.getId() == null) {
            return createRnsQuotationVendors(rnsQuotationVendors);
        }
        RnsQuotationVendors result = rnsQuotationVendorsRepository.save(rnsQuotationVendors);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationVendors.getId().toString()))
            .body(result);
    }
    /**
     * PUT  /rns-quotation-vendors-portal : Updates an existing rnsQuotationVendors.
     *
     * @param rnsQuotationVendors the rnsQuotationVendors to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationVendors,
     * or with status 400 (Bad Request) if the rnsQuotationVendors is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationVendors couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PutMapping("/rns-quotation-vendors-portal")
    @Timed
    public ResponseEntity<RnsQuotationVendors> updateVendorQuotation(@RequestBody RnsQuotationVendors rnsQuotationVendorsData) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationVendors : {}", rnsQuotationVendorsData);
        if (rnsQuotationVendorsData.getId() == null) {
            return createRnsQuotationVendors(rnsQuotationVendorsData);
        }
        Long id=rnsQuotationVendorsData.getId();
        RnsQuotationVendors rnsQuotationVendors=rnsQuotationVendorsRepository.findById(id).orElse(null);

        	rnsQuotationVendors.setCurrency(rnsQuotationVendorsData.getCurrency());
        	rnsQuotationVendors.setDeliveryTerms(rnsQuotationVendorsData.getDeliveryTerms());
        	rnsQuotationVendors.setRegularRate(rnsQuotationVendorsData.getRegularRate());
        	rnsQuotationVendors.setQuoteQty(rnsQuotationVendorsData.getQuoteQty());
        	rnsQuotationVendors.setPaymentTerms(rnsQuotationVendorsData.getPaymentTerms());
        	rnsQuotationVendors.setConfDelDate(rnsQuotationVendorsData.getConfDelDate());

        log.debug("GGGGGGGGGGGGGGGGGGGGG...",rnsQuotationVendors);
        RnsQuotationVendors result = rnsQuotationVendorsRepository.save(rnsQuotationVendors);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationVendors.getId().toString()))
            .body(result);
    }
    /**
     * GET  /rns-quotation-vendors : get all the rnsQuotationVendors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotationVendors in body
     */
    @GetMapping("/rns-quotation-vendors")
    @Timed
    public List<RnsQuotationVendors> getAllRnsQuotationVendors() {
        log.debug("REST request to get all RnsQuotationVendors");
        return rnsQuotationVendorsRepository.findAll();
        }

    /**
     * GET  /rns-quotation-vendors/:id : get the "id" rnsQuotationVendors.
     *
     * @param id the id of the rnsQuotationVendors to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationVendors, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-vendors/{id}")
    @Timed
    public ResponseEntity<RnsQuotationVendorsFullDetails> getRnsQuotationVendors(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationVendors : {}", id);
        RnsQuotationVendors rnsQuotationVendors = rnsQuotationVendorsRepository.findById(id).orElse(null);
        RnsQuotationVendorsFullDetails rnsQuotationVendorsFullDetails = new RnsQuotationVendorsFullDetails();
        try {
            BeanUtils.copyProperties(rnsQuotationVendorsFullDetails,rnsQuotationVendors);
            VndrPrice price = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVendorsFullDetails.getVariant().getId(),rnsQuotationVendorsFullDetails.getVendorCode());
            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVendorsFullDetails.getVariant().getId());

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
                rnsQuotationVendorsFullDetails.setBidRate(totalPrice);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsQuotationVendorsFullDetails));
    }


    /**
     * GET  /rns-quotation-vendors/:id : get the "id" rnsQuotationVendors.
     *
     * @param id the id of the rnsQuotationVendors to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationVendors, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-vendors-surrogate/{id}")
    @Timed
    public ResponseEntity<RnsQuotationVendorsFullDetails> getRnsQuotationVendorsSurrogate(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationVendors : {}", id);
        RnsQuotationVendors rnsQuotationVendors = rnsQuotationVendorsRepository.findById(id).orElse(null);
        RnsQuotationVendorsFullDetails rnsQuotationVendorsFullDetails = new RnsQuotationVendorsFullDetails();
        try {
            BeanUtils.copyProperties(rnsQuotationVendorsFullDetails,rnsQuotationVendors);
            VndrPrice price = vndrPriceRepository.getAllbyVariantandVendorCodeMaxPrice(rnsQuotationVendorsFullDetails.getVariant().getId(),rnsQuotationVendorsFullDetails.getVendorCode());
            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVendorsFullDetails.getVariant().getId());

            RnsQuotationVariantFullDetails rnsQuotationVariantFullDetails = new RnsQuotationVariantFullDetails();
            BeanUtils.copyProperties(rnsQuotationVariantFullDetails, rnsQuotationVendors.getVariant());
            rnsQuotationVariantFullDetails.setAuctionVrnt(auctionVrnt);

            RnsQuotationFullDetails rnsQuotationFullDetails = new RnsQuotationFullDetails();

            BeanUtils.copyProperties(rnsQuotationFullDetails, rnsQuotationVendors.getVariant().getQuotation());

            Auction auction = auctionRepository.getAuctionByQuotationId(rnsQuotationFullDetails.getId());
            rnsQuotationFullDetails.setAuctionDetails(auction);

            rnsQuotationVariantFullDetails.setQuotationFullDetails(rnsQuotationFullDetails);

            rnsQuotationVendorsFullDetails.setFullDetails(rnsQuotationVariantFullDetails);



            if(price!=null) {
                rnsQuotationVendorsFullDetails.setVndrPrice(price);
            } else{
                price = new VndrPrice();
                rnsQuotationVendorsFullDetails.setVndrPrice(price);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsQuotationVendorsFullDetails));
    }



    @GetMapping("/rns-quotation-vendors-custom/{id}")
    @Timed
   // public List<AuctionCustomVm> getAuctionCustomVm(@PathVariable Long id) {
    	public List<RnsQuotationVendors> getAuctionCustomVm(@PathVariable Long id) {
        log.debug("REST request to get AuctionCustomVm : {}", id);
        List<AuctionCustomVm> returnData  = new ArrayList<AuctionCustomVm>();
        List<RnsQuotationVendors> rnsQuotationVendorsList = rnsQuotationVendorsRepository.findByAucionAndUserIdIsCurrentUser(id);

        for(RnsQuotationVendors item:rnsQuotationVendorsList)
        {
        	/*System.out.println("variant :::::::::::::");*/
        	System.out.printf("variant ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::",item.getVariant());
        }
        return rnsQuotationVendorsList;
    }

    /**
     * DELETE  /rns-quotation-vendors/:id : delete the "id" rnsQuotationVendors.
     *
     * @param id the id of the rnsQuotationVendors to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotation-vendors/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsQuotationVendors(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotationVendors : {}", id);
        rnsQuotationVendorsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
