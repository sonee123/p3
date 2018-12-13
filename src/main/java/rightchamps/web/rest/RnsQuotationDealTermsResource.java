package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsQuotationDealTerms;

import rightchamps.repository.RnsQuotationDealTermsRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsQuotationDealTerms.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationDealTermsResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationDealTermsResource.class);

    private static final String ENTITY_NAME = "Quotation Delivery Terms";

    private final RnsQuotationDealTermsRepository rnsQuotationDealTermsRepository;

    public RnsQuotationDealTermsResource(RnsQuotationDealTermsRepository rnsQuotationDealTermsRepository) {
        this.rnsQuotationDealTermsRepository = rnsQuotationDealTermsRepository;
    }

    /**
     * POST  /rns-quotation-deal-terms : Create a new rnsQuotationDealTerms.
     *
     * @param rnsQuotationDealTerms the rnsQuotationDealTerms to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotationDealTerms, or with status 400 (Bad Request) if the rnsQuotationDealTerms has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotation-deal-terms")
    @Timed
    public ResponseEntity<RnsQuotationDealTerms> createRnsQuotationDealTerms(@RequestBody RnsQuotationDealTerms rnsQuotationDealTerms) throws URISyntaxException {
        log.debug("REST request to save RnsQuotationDealTerms : {}", rnsQuotationDealTerms);
        if (rnsQuotationDealTerms.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotationDealTerms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsQuotationDealTerms result = rnsQuotationDealTermsRepository.save(rnsQuotationDealTerms);
        return ResponseEntity.created(new URI("/api/rns-quotation-deal-terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotation-deal-terms : Updates an existing rnsQuotationDealTerms.
     *
     * @param rnsQuotationDealTerms the rnsQuotationDealTerms to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationDealTerms,
     * or with status 400 (Bad Request) if the rnsQuotationDealTerms is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationDealTerms couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-deal-terms")
    @Timed
    public ResponseEntity<RnsQuotationDealTerms> updateRnsQuotationDealTerms(@RequestBody RnsQuotationDealTerms rnsQuotationDealTerms) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationDealTerms : {}", rnsQuotationDealTerms);
        if (rnsQuotationDealTerms.getId() == null) {
            return createRnsQuotationDealTerms(rnsQuotationDealTerms);
        }
        RnsQuotationDealTerms result = rnsQuotationDealTermsRepository.save(rnsQuotationDealTerms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationDealTerms.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-quotation-deal-terms : get all the rnsQuotationDealTerms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotationDealTerms in body
     */
    @GetMapping("/rns-quotation-deal-terms")
    @Timed
    public List<RnsQuotationDealTerms> getAllRnsQuotationDealTerms() {
        log.debug("REST request to get all RnsQuotationDealTerms");
        return rnsQuotationDealTermsRepository.findAll();
        }

    /**
     * GET  /rns-quotation-deal-terms/:id : get the "id" rnsQuotationDealTerms.
     *
     * @param id the id of the rnsQuotationDealTerms to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationDealTerms, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-deal-terms/{id}")
    @Timed
    public ResponseEntity<RnsQuotationDealTerms> getRnsQuotationDealTerms(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationDealTerms : {}", id);
        Optional<RnsQuotationDealTerms> rnsQuotationDealTerms = rnsQuotationDealTermsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsQuotationDealTerms);
    }

    /**
     * DELETE  /rns-quotation-deal-terms/:id : delete the "id" rnsQuotationDealTerms.
     *
     * @param id the id of the rnsQuotationDealTerms to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotation-deal-terms/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsQuotationDealTerms(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotationDealTerms : {}", id);
        rnsQuotationDealTermsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
