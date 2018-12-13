package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsRefrDetails;

import rightchamps.repository.RnsRefrDetailsRepository;
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
 * REST controller for managing RnsRefrDetails.
 */
@RestController
@RequestMapping("/api")
public class RnsRefrDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RnsRefrDetailsResource.class);

    private static final String ENTITY_NAME = "Reference Details";

    private final RnsRefrDetailsRepository rnsRefrDetailsRepository;

    public RnsRefrDetailsResource(RnsRefrDetailsRepository rnsRefrDetailsRepository) {
        this.rnsRefrDetailsRepository = rnsRefrDetailsRepository;
    }

    /**
     * POST  /rns-refr-details : Create a new rnsRefrDetails.
     *
     * @param rnsRefrDetails the rnsRefrDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsRefrDetails, or with status 400 (Bad Request) if the rnsRefrDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-refr-details")
    @Timed
    public ResponseEntity<RnsRefrDetails> createRnsRefrDetails(@RequestBody RnsRefrDetails rnsRefrDetails) throws URISyntaxException {
        log.debug("REST request to save RnsRefrDetails : {}", rnsRefrDetails);
        if (rnsRefrDetails.getId() != null) {
            throw new BadRequestAlertException("A new rnsRefrDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsRefrDetails result = rnsRefrDetailsRepository.save(rnsRefrDetails);
        return ResponseEntity.created(new URI("/api/rns-refr-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-refr-details : Updates an existing rnsRefrDetails.
     *
     * @param rnsRefrDetails the rnsRefrDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsRefrDetails,
     * or with status 400 (Bad Request) if the rnsRefrDetails is not valid,
     * or with status 500 (Internal Server Error) if the rnsRefrDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-refr-details")
    @Timed
    public ResponseEntity<RnsRefrDetails> updateRnsRefrDetails(@RequestBody RnsRefrDetails rnsRefrDetails) throws URISyntaxException {
        log.debug("REST request to update RnsRefrDetails : {}", rnsRefrDetails);
        if (rnsRefrDetails.getId() == null) {
            return createRnsRefrDetails(rnsRefrDetails);
        }
        RnsRefrDetails result = rnsRefrDetailsRepository.save(rnsRefrDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsRefrDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-refr-details : get all the rnsRefrDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsRefrDetails in body
     */
    @GetMapping("/rns-refr-details")
    @Timed
    public List<RnsRefrDetails> getAllRnsRefrDetails() {
        log.debug("REST request to get all RnsRefrDetails");
        return rnsRefrDetailsRepository.findAll();
        }

    /**
     * GET  /rns-refr-details/:id : get the "id" rnsRefrDetails.
     *
     * @param id the id of the rnsRefrDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsRefrDetails, or with status 404 (Not Found)
     */
    @GetMapping("/rns-refr-details/{id}")
    @Timed
    public ResponseEntity<RnsRefrDetails> getRnsRefrDetails(@PathVariable Long id) {
        log.debug("REST request to get RnsRefrDetails : {}", id);
        Optional<RnsRefrDetails> rnsRefrDetails = rnsRefrDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsRefrDetails);
    }

    /**
     * DELETE  /rns-refr-details/:id : delete the "id" rnsRefrDetails.
     *
     * @param id the id of the rnsRefrDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-refr-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsRefrDetails(@PathVariable Long id) {
        log.debug("REST request to delete RnsRefrDetails : {}", id);
        rnsRefrDetailsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
