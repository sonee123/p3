package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsForwardTypeMaster;

import rightchamps.repository.RnsForwardTypeMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import rightchamps.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsForwardTypeMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsForwardTypeMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsForwardTypeMasterResource.class);

    private static final String ENTITY_NAME = "Forward Type Master";

    private final RnsForwardTypeMasterRepository rnsForwardTypeMasterRepository;

    public RnsForwardTypeMasterResource(RnsForwardTypeMasterRepository rnsForwardTypeMasterRepository) {
        this.rnsForwardTypeMasterRepository = rnsForwardTypeMasterRepository;
    }

    /**
     * POST  /rns-forward-type-masters : Create a new rnsForwardTypeMaster.
     *
     * @param rnsForwardTypeMaster the rnsForwardTypeMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsForwardTypeMaster, or with status 400 (Bad Request) if the rnsForwardTypeMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-forward-type-masters")
    @Timed
    public ResponseEntity<RnsForwardTypeMaster> createRnsForwardTypeMaster(@RequestBody RnsForwardTypeMaster rnsForwardTypeMaster) throws URISyntaxException {
        log.debug("REST request to save RnsForwardTypeMaster : {}", rnsForwardTypeMaster);
        if (rnsForwardTypeMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsForwardTypeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsForwardTypeMaster result = rnsForwardTypeMasterRepository.save(rnsForwardTypeMaster);
        return ResponseEntity.created(new URI("/api/rns-forward-type-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-forward-type-masters : Updates an existing rnsForwardTypeMaster.
     *
     * @param rnsForwardTypeMaster the rnsForwardTypeMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsForwardTypeMaster,
     * or with status 400 (Bad Request) if the rnsForwardTypeMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsForwardTypeMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-forward-type-masters")
    @Timed
    public ResponseEntity<RnsForwardTypeMaster> updateRnsForwardTypeMaster(@RequestBody RnsForwardTypeMaster rnsForwardTypeMaster) throws URISyntaxException {
        log.debug("REST request to update RnsForwardTypeMaster : {}", rnsForwardTypeMaster);
        if (rnsForwardTypeMaster.getId() == null) {
            return createRnsForwardTypeMaster(rnsForwardTypeMaster);
        }
        RnsForwardTypeMaster result = rnsForwardTypeMasterRepository.save(rnsForwardTypeMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsForwardTypeMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-forward-type-masters : get all the rnsForwardTypeMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rnsForwardTypeMasters in body
     */
    @GetMapping("/rns-forward-type-masters")
    @Timed
    public ResponseEntity<List<RnsForwardTypeMaster>> getAllRnsForwardTypeMasters(Pageable pageable) {
        log.debug("REST request to get a page of RnsForwardTypeMasters");
        Page<RnsForwardTypeMaster> page = rnsForwardTypeMasterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rns-forward-type-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rns-forward-type-masters/:id : get the "id" rnsForwardTypeMaster.
     *
     * @param id the id of the rnsForwardTypeMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsForwardTypeMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-forward-type-masters/{id}")
    @Timed
    public ResponseEntity<RnsForwardTypeMaster> getRnsForwardTypeMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsForwardTypeMaster : {}", id);
        Optional<RnsForwardTypeMaster> rnsForwardTypeMaster = rnsForwardTypeMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsForwardTypeMaster);
    }

    /**
     * DELETE  /rns-forward-type-masters/:id : delete the "id" rnsForwardTypeMaster.
     *
     * @param id the id of the rnsForwardTypeMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-forward-type-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsForwardTypeMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsForwardTypeMaster : {}", id);
        rnsForwardTypeMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
