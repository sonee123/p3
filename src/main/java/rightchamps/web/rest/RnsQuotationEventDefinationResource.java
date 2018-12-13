package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsQuotationEventDefination;

import rightchamps.repository.RnsQuotationEventDefinationRepository;
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
 * REST controller for managing RnsQuotationEventDefination.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationEventDefinationResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationEventDefinationResource.class);

    private static final String ENTITY_NAME = "Quotation Event Defination";

    private final RnsQuotationEventDefinationRepository rnsQuotationEventDefinationRepository;

    public RnsQuotationEventDefinationResource(RnsQuotationEventDefinationRepository rnsQuotationEventDefinationRepository) {
        this.rnsQuotationEventDefinationRepository = rnsQuotationEventDefinationRepository;
    }

    /**
     * POST  /rns-quotation-event-definations : Create a new rnsQuotationEventDefination.
     *
     * @param rnsQuotationEventDefination the rnsQuotationEventDefination to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotationEventDefination, or with status 400 (Bad Request) if the rnsQuotationEventDefination has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotation-event-definations")
    @Timed
    public ResponseEntity<RnsQuotationEventDefination> createRnsQuotationEventDefination(@RequestBody RnsQuotationEventDefination rnsQuotationEventDefination) throws URISyntaxException {
        log.debug("REST request to save RnsQuotationEventDefination : {}", rnsQuotationEventDefination);
        if (rnsQuotationEventDefination.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotationEventDefination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsQuotationEventDefination result = rnsQuotationEventDefinationRepository.save(rnsQuotationEventDefination);
        return ResponseEntity.created(new URI("/api/rns-quotation-event-definations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotation-event-definations : Updates an existing rnsQuotationEventDefination.
     *
     * @param rnsQuotationEventDefination the rnsQuotationEventDefination to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationEventDefination,
     * or with status 400 (Bad Request) if the rnsQuotationEventDefination is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationEventDefination couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-event-definations")
    @Timed
    public ResponseEntity<RnsQuotationEventDefination> updateRnsQuotationEventDefination(@RequestBody RnsQuotationEventDefination rnsQuotationEventDefination) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationEventDefination : {}", rnsQuotationEventDefination);
        if (rnsQuotationEventDefination.getId() == null) {
            return createRnsQuotationEventDefination(rnsQuotationEventDefination);
        }
        RnsQuotationEventDefination result = rnsQuotationEventDefinationRepository.save(rnsQuotationEventDefination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationEventDefination.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-quotation-event-definations : get all the rnsQuotationEventDefinations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotationEventDefinations in body
     */
    @GetMapping("/rns-quotation-event-definations")
    @Timed
    public List<RnsQuotationEventDefination> getAllRnsQuotationEventDefinations() {
        log.debug("REST request to get all RnsQuotationEventDefinations");
        return rnsQuotationEventDefinationRepository.findAll();
        }

    /**
     * GET  /rns-quotation-event-definations/:id : get the "id" rnsQuotationEventDefination.
     *
     * @param id the id of the rnsQuotationEventDefination to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationEventDefination, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-event-definations/{id}")
    @Timed
    public ResponseEntity<RnsQuotationEventDefination> getRnsQuotationEventDefination(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationEventDefination : {}", id);
        Optional<RnsQuotationEventDefination> rnsQuotationEventDefination = rnsQuotationEventDefinationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsQuotationEventDefination);
    }

    /**
     * DELETE  /rns-quotation-event-definations/:id : delete the "id" rnsQuotationEventDefination.
     *
     * @param id the id of the rnsQuotationEventDefination to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotation-event-definations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsQuotationEventDefination(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotationEventDefination : {}", id);
        rnsQuotationEventDefinationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
