package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsRelation;

import rightchamps.repository.RnsRelationRepository;
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
 * REST controller for managing RnsRelation.
 */
@RestController
@RequestMapping("/api")
public class RnsRelationResource {

    private final Logger log = LoggerFactory.getLogger(RnsRelationResource.class);

    private static final String ENTITY_NAME = "Rns Relation";

    private final RnsRelationRepository rnsRelationRepository;

    public RnsRelationResource(RnsRelationRepository rnsRelationRepository) {
        this.rnsRelationRepository = rnsRelationRepository;
    }

    /**
     * POST  /rns-relations : Create a new rnsRelation.
     *
     * @param rnsRelation the rnsRelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsRelation, or with status 400 (Bad Request) if the rnsRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-relations")
    @Timed
    public ResponseEntity<RnsRelation> createRnsRelation(@RequestBody RnsRelation rnsRelation) throws URISyntaxException {
        log.debug("REST request to save RnsRelation : {}", rnsRelation);
        if (rnsRelation.getId() != null) {
            throw new BadRequestAlertException("A new rnsRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsRelation result = rnsRelationRepository.save(rnsRelation);
        return ResponseEntity.created(new URI("/api/rns-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-relations : Updates an existing rnsRelation.
     *
     * @param rnsRelation the rnsRelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsRelation,
     * or with status 400 (Bad Request) if the rnsRelation is not valid,
     * or with status 500 (Internal Server Error) if the rnsRelation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-relations")
    @Timed
    public ResponseEntity<RnsRelation> updateRnsRelation(@RequestBody RnsRelation rnsRelation) throws URISyntaxException {
        log.debug("REST request to update RnsRelation : {}", rnsRelation);
        if (rnsRelation.getId() == null) {
            return createRnsRelation(rnsRelation);
        }
        RnsRelation result = rnsRelationRepository.save(rnsRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsRelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-relations : get all the rnsRelations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsRelations in body
     */
    @GetMapping("/rns-relations")
    @Timed
    public List<RnsRelation> getAllRnsRelations() {
        log.debug("REST request to get all RnsRelations");
        return rnsRelationRepository.findAll();
        }

    /**
     * GET  /rns-relations/:id : get the "id" rnsRelation.
     *
     * @param id the id of the rnsRelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsRelation, or with status 404 (Not Found)
     */
    @GetMapping("/rns-relations/{id}")
    @Timed
    public ResponseEntity<RnsRelation> getRnsRelation(@PathVariable Long id) {
        log.debug("REST request to get RnsRelation : {}", id);
        Optional<RnsRelation> rnsRelation = rnsRelationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsRelation);
    }

    /**
     * DELETE  /rns-relations/:id : delete the "id" rnsRelation.
     *
     * @param id the id of the rnsRelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-relations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsRelation(@PathVariable Long id) {
        log.debug("REST request to delete RnsRelation : {}", id);
        rnsRelationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
