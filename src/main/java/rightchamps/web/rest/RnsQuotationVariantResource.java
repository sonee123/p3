package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.AuctionVrnt;
import rightchamps.domain.RnsQuotationVariant;

import rightchamps.repository.AuctionVrntRepository;
import rightchamps.repository.RnsQuotationVariantRepository;
import rightchamps.repository.RnsQuotationVendorsRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * REST controller for managing RnsQuotationVariant.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationVariantResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationVariantResource.class);

    private static final String ENTITY_NAME = "Quotation Variant";

    private final RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private RnsQuotationVendorsRepository rnsQuotationVendorsRepository;


    @Inject
    private AuctionVrntRepository auctionVrntRepository;


    public RnsQuotationVariantResource(RnsQuotationVariantRepository rnsQuotationVariantRepository) {
        this.rnsQuotationVariantRepository = rnsQuotationVariantRepository;
    }

    /**
     * POST  /rns-quotation-variants : Create a new rnsQuotationVariant.
     *
     * @param rnsQuotationVariant the rnsQuotationVariant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotationVariant, or with status 400 (Bad Request) if the rnsQuotationVariant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotation-variants")
    @Timed
    public ResponseEntity<RnsQuotationVariant> createRnsQuotationVariant(@RequestBody RnsQuotationVariant rnsQuotationVariant) throws URISyntaxException {
        log.debug("REST request to save RnsQuotationVariant before saving ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: {}", rnsQuotationVariant);
        if (rnsQuotationVariant.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotationVariant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsQuotationVariant result = null;
        result = rnsQuotationVariantRepository.getRnsQuotationVariantByTitleAndqAndQuotation(rnsQuotationVariant.getTitle(),rnsQuotationVariant.getQuotation().getId());
        if(result!=null && result.getId()!=null){} else {
            result = rnsQuotationVariantRepository.save(rnsQuotationVariant);
        }
        log.debug("REST request to save RnsQuotationVariants after saving::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: {}", result);
        return ResponseEntity.created(new URI("/api/rns-quotation-variants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotation-variants : Updates an existing rnsQuotationVariant.
     *
     * @param @rnsQuotationVariant the rnsQuotationVariant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationVariant,
     * or with status 400 (Bad Request) if the rnsQuotationVariant is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationVariant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-variants/updatemultiple")
    @Timed
    public List<RnsQuotationVariant> updateRnsQuotationVariantupdateMultiple(@RequestBody RnsQuotationVariant[] rnsQuotationVariants) throws URISyntaxException {
        log.debug("REST request to update rnsQuotationVariants ::::::::::::::: {}", rnsQuotationVariants);
        List<RnsQuotationVariant> resultList = new ArrayList<RnsQuotationVariant>();
        for(RnsQuotationVariant o: rnsQuotationVariants) {
            if (o.getId() == null) {
                RnsQuotationVariant result = rnsQuotationVariantRepository.save(o);
                resultList.add(result);
                //Auction Variant save
                AuctionVrnt auctionVrnt = new AuctionVrnt();
                auctionVrnt.setVariant(result);
                auctionVrnt.setUomOne(result.getOrderUom());
                auctionVrnt.setConvFactOne(1.0f);
                auctionVrntRepository.save(auctionVrnt);
            }else{
                RnsQuotationVariant result = rnsQuotationVariantRepository.save(o);
                resultList.add(result);
            }
        }
        return resultList;
    }

    /**
     * PUT  /rns-quotation-variants : Updates an existing rnsQuotationVariant.
     *
     * @param rnsQuotationVariant the rnsQuotationVariant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationVariant,
     * or with status 400 (Bad Request) if the rnsQuotationVariant is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationVariant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-variants")
    @Timed
    public ResponseEntity<RnsQuotationVariant> updateRnsQuotationVariant(@RequestBody RnsQuotationVariant rnsQuotationVariant) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationVariant : {}", rnsQuotationVariant);
        if (rnsQuotationVariant.getId() == null) {
            return createRnsQuotationVariant(rnsQuotationVariant);
        }
        RnsQuotationVariant result = rnsQuotationVariantRepository.save(rnsQuotationVariant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationVariant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-quotation-variants : get all the rnsQuotationVariants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotationVariants in body
     */
    @GetMapping("/rns-quotation-variants")
    @Timed
    public List<RnsQuotationVariant> getAllRnsQuotationVariants() {
        log.debug("REST request to get all RnsQuotationVariants");
        return rnsQuotationVariantRepository.findAll();
        }

    /**
     * GET  /rns-quotation-variants/:id : get the "id" rnsQuotationVariant.
     *
     * @param id the id of the rnsQuotationVariant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationVariant, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-variants/{id}")
    @Timed
    public ResponseEntity<RnsQuotationVariant> getRnsQuotationVariant(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationVariant : {}", id);
        Optional<RnsQuotationVariant> rnsQuotationVariant = rnsQuotationVariantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsQuotationVariant);
    }

    /**
     * DELETE  /rns-quotation-variants/:id : delete the "id" rnsQuotationVariant.
     *
     * @param id the id of the rnsQuotationVariant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotation-variants/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsQuotationVariant(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotationVariant : {}", id);
        rnsQuotationVendorsRepository.deleteRnsQuotationVendorsByVariant(id);
        auctionVrntRepository.deleteAuctionVrntbyVariant(id);
        rnsQuotationVariantRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
