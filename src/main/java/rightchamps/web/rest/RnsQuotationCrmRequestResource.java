package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsQuotationCrmRequest;

import rightchamps.repository.RnsQuotationCrmRequestRepository;
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
 * REST controller for managing RnsQuotationCrmRequest.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationCrmRequestResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationCrmRequestResource.class);

    private static final String ENTITY_NAME = "Quotation Crm Request";

    private final RnsQuotationCrmRequestRepository rnsQuotationCrmRequestRepository;

    public RnsQuotationCrmRequestResource(RnsQuotationCrmRequestRepository rnsQuotationCrmRequestRepository) {
        this.rnsQuotationCrmRequestRepository = rnsQuotationCrmRequestRepository;
    }

    /**
     * POST  /rns-quotation-crm-requests : Create a new rnsQuotationCrmRequest.
     *
     * @param rnsQuotationCrmRequest the rnsQuotationCrmRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotationCrmRequest, or with status 400 (Bad Request) if the rnsQuotationCrmRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotation-crm-requests")
    @Timed
    public ResponseEntity<RnsQuotationCrmRequest> createRnsQuotationCrmRequest(@RequestBody RnsQuotationCrmRequest rnsQuotationCrmRequest) throws URISyntaxException {
        log.debug("REST request to save RnsQuotationCrmRequest : {}", rnsQuotationCrmRequest);
        if (rnsQuotationCrmRequest.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotationCrmRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsQuotationCrmRequest result = rnsQuotationCrmRequestRepository.save(rnsQuotationCrmRequest);
        return ResponseEntity.created(new URI("/api/rns-quotation-crm-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotation-crm-requests : Updates an existing rnsQuotationCrmRequest.
     *
     * @param rnsQuotationCrmRequest the rnsQuotationCrmRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationCrmRequest,
     * or with status 400 (Bad Request) if the rnsQuotationCrmRequest is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationCrmRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-crm-requests")
    @Timed
    public ResponseEntity<RnsQuotationCrmRequest> updateRnsQuotationCrmRequest(@RequestBody RnsQuotationCrmRequest rnsQuotationCrmRequest) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationCrmRequest : {}", rnsQuotationCrmRequest);
        if (rnsQuotationCrmRequest.getId() == null) {
            return createRnsQuotationCrmRequest(rnsQuotationCrmRequest);
        }
        RnsQuotationCrmRequest result = rnsQuotationCrmRequestRepository.save(rnsQuotationCrmRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationCrmRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-quotation-crm-requests : get all the rnsQuotationCrmRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotationCrmRequests in body
     */
    @GetMapping("/rns-quotation-crm-requests")
    @Timed
    public List<RnsQuotationCrmRequest> getAllRnsQuotationCrmRequests() {
        log.debug("REST request to get all RnsQuotationCrmRequests");
        return rnsQuotationCrmRequestRepository.findAll();
        }

    /**
     * GET  /rns-quotation-crm-requests/:id : get the "id" rnsQuotationCrmRequest.
     *
     * @param id the id of the rnsQuotationCrmRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationCrmRequest, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-crm-requests/{id}")
    @Timed
    public ResponseEntity<RnsQuotationCrmRequest> getRnsQuotationCrmRequest(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationCrmRequest : {}", id);
        Optional<RnsQuotationCrmRequest> rnsQuotationCrmRequest = rnsQuotationCrmRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsQuotationCrmRequest);
    }

    /**
     * DELETE  /rns-quotation-crm-requests/:id : delete the "id" rnsQuotationCrmRequest.
     *
     * @param id the id of the rnsQuotationCrmRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotation-crm-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsQuotationCrmRequest(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotationCrmRequest : {}", id);
        rnsQuotationCrmRequestRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
