package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsSectorMaster;

import rightchamps.repository.RnsSectorMasterRepository;
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
 * REST controller for managing RnsSectorMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsSectorMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsSectorMasterResource.class);

    private static final String ENTITY_NAME = "Sector Master";

    private final RnsSectorMasterRepository rnsSectorMasterRepository;

    public RnsSectorMasterResource(RnsSectorMasterRepository rnsSectorMasterRepository) {
        this.rnsSectorMasterRepository = rnsSectorMasterRepository;
    }

    /**
     * POST  /rns-sector-masters : Create a new rnsSectorMaster.
     *
     * @param rnsSectorMaster the rnsSectorMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsSectorMaster, or with status 400 (Bad Request) if the rnsSectorMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-sector-masters")
    @Timed
    public ResponseEntity<RnsSectorMaster> createRnsSectorMaster(@RequestBody RnsSectorMaster rnsSectorMaster) throws URISyntaxException {
        log.debug("REST request to save RnsSectorMaster : {}", rnsSectorMaster);
        if (rnsSectorMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsSectorMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsSectorMaster result = rnsSectorMasterRepository.save(rnsSectorMaster);
        return ResponseEntity.created(new URI("/api/rns-sector-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-sector-masters : Updates an existing rnsSectorMaster.
     *
     * @param rnsSectorMaster the rnsSectorMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsSectorMaster,
     * or with status 400 (Bad Request) if the rnsSectorMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsSectorMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-sector-masters")
    @Timed
    public ResponseEntity<RnsSectorMaster> updateRnsSectorMaster(@RequestBody RnsSectorMaster rnsSectorMaster) throws URISyntaxException {
        log.debug("REST request to update RnsSectorMaster : {}", rnsSectorMaster);
        if (rnsSectorMaster.getId() == null) {
            return createRnsSectorMaster(rnsSectorMaster);
        }
        RnsSectorMaster result = rnsSectorMasterRepository.save(rnsSectorMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsSectorMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-sector-masters : get all the rnsSectorMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsSectorMasters in body
     */
    @GetMapping("/rns-sector-masters")
    @Timed
    public List<RnsSectorMaster> getAllRnsSectorMasters() {
        log.debug("REST request to get all RnsSectorMasters");
        return rnsSectorMasterRepository.findAll();
        }

    /**
     * GET  /rns-sector-masters/:id : get the "id" rnsSectorMaster.
     *
     * @param id the id of the rnsSectorMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsSectorMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-sector-masters/{id}")
    @Timed
    public ResponseEntity<RnsSectorMaster> getRnsSectorMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsSectorMaster : {}", id);
        Optional<RnsSectorMaster> rnsSectorMaster = rnsSectorMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsSectorMaster);
    }

    /**
     * DELETE  /rns-sector-masters/:id : delete the "id" rnsSectorMaster.
     *
     * @param id the id of the rnsSectorMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-sector-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsSectorMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsSectorMaster : {}", id);
        rnsSectorMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
