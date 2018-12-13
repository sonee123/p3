package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsRefrMaster;

import rightchamps.repository.RnsRefrMasterRepository;
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
 * REST controller for managing RnsRefrMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsRefrMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsRefrMasterResource.class);

    private static final String ENTITY_NAME = "Reference Master";

    private final RnsRefrMasterRepository rnsRefrMasterRepository;

    public RnsRefrMasterResource(RnsRefrMasterRepository rnsRefrMasterRepository) {
        this.rnsRefrMasterRepository = rnsRefrMasterRepository;
    }

    /**
     * POST  /rns-refr-masters : Create a new rnsRefrMaster.
     *
     * @param rnsRefrMaster the rnsRefrMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsRefrMaster, or with status 400 (Bad Request) if the rnsRefrMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-refr-masters")
    @Timed
    public ResponseEntity<RnsRefrMaster> createRnsRefrMaster(@RequestBody RnsRefrMaster rnsRefrMaster) throws URISyntaxException {
        log.debug("REST request to save RnsRefrMaster : {}", rnsRefrMaster);
        if (rnsRefrMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsRefrMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsRefrMaster result = rnsRefrMasterRepository.save(rnsRefrMaster);
        return ResponseEntity.created(new URI("/api/rns-refr-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-refr-masters : Updates an existing rnsRefrMaster.
     *
     * @param rnsRefrMaster the rnsRefrMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsRefrMaster,
     * or with status 400 (Bad Request) if the rnsRefrMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsRefrMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-refr-masters")
    @Timed
    public ResponseEntity<RnsRefrMaster> updateRnsRefrMaster(@RequestBody RnsRefrMaster rnsRefrMaster) throws URISyntaxException {
        log.debug("REST request to update RnsRefrMaster : {}", rnsRefrMaster);
        if (rnsRefrMaster.getId() == null) {
            return createRnsRefrMaster(rnsRefrMaster);
        }
        RnsRefrMaster result = rnsRefrMasterRepository.save(rnsRefrMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsRefrMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-refr-masters : get all the rnsRefrMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsRefrMasters in body
     */
    @GetMapping("/rns-refr-masters")
    @Timed
    public List<RnsRefrMaster> getAllRnsRefrMasters() {
        log.debug("REST request to get all RnsRefrMasters");
        return rnsRefrMasterRepository.findAll();
        }

    /**
     * GET  /rns-refr-masters/:id : get the "id" rnsRefrMaster.
     *
     * @param id the id of the rnsRefrMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsRefrMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-refr-masters/{id}")
    @Timed
    public ResponseEntity<RnsRefrMaster> getRnsRefrMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsRefrMaster : {}", id);
        Optional<RnsRefrMaster> rnsRefrMaster = rnsRefrMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsRefrMaster);
    }

    /**
     * DELETE  /rns-refr-masters/:id : delete the "id" rnsRefrMaster.
     *
     * @param id the id of the rnsRefrMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-refr-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsRefrMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsRefrMaster : {}", id);
        rnsRefrMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
