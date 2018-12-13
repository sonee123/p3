package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsVendorRemarkComment;

import rightchamps.repository.RnsVendorRemarkCommentRepository;
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
 * REST controller for managing RnsVendorRemarkComment.
 */
@RestController
@RequestMapping("/api")
public class RnsVendorRemarkCommentResource {

    private final Logger log = LoggerFactory.getLogger(RnsVendorRemarkCommentResource.class);

    private static final String ENTITY_NAME = "Vendor Remark Comment";

    private final RnsVendorRemarkCommentRepository rnsVendorRemarkCommentRepository;

    public RnsVendorRemarkCommentResource(RnsVendorRemarkCommentRepository rnsVendorRemarkCommentRepository) {
        this.rnsVendorRemarkCommentRepository = rnsVendorRemarkCommentRepository;
    }

    /**
     * POST  /rns-vendor-remark-comments : Create a new rnsVendorRemarkComment.
     *
     * @param rnsVendorRemarkComment the rnsVendorRemarkComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsVendorRemarkComment, or with status 400 (Bad Request) if the rnsVendorRemarkComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-vendor-remark-comments")
    @Timed
    public ResponseEntity<RnsVendorRemarkComment> createRnsVendorRemarkComment(@RequestBody RnsVendorRemarkComment rnsVendorRemarkComment) throws URISyntaxException {
        log.debug("REST request to save RnsVendorRemarkComment : {}", rnsVendorRemarkComment);
        if (rnsVendorRemarkComment.getId() != null) {
            throw new BadRequestAlertException("A new rnsVendorRemarkComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsVendorRemarkComment result = rnsVendorRemarkCommentRepository.save(rnsVendorRemarkComment);
        return ResponseEntity.created(new URI("/api/rns-vendor-remark-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-vendor-remark-comments : Updates an existing rnsVendorRemarkComment.
     *
     * @param rnsVendorRemarkComment the rnsVendorRemarkComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsVendorRemarkComment,
     * or with status 400 (Bad Request) if the rnsVendorRemarkComment is not valid,
     * or with status 500 (Internal Server Error) if the rnsVendorRemarkComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-vendor-remark-comments")
    @Timed
    public ResponseEntity<RnsVendorRemarkComment> updateRnsVendorRemarkComment(@RequestBody RnsVendorRemarkComment rnsVendorRemarkComment) throws URISyntaxException {
        log.debug("REST request to update RnsVendorRemarkComment : {}", rnsVendorRemarkComment);
        if (rnsVendorRemarkComment.getId() == null) {
            return createRnsVendorRemarkComment(rnsVendorRemarkComment);
        }
        RnsVendorRemarkComment result = rnsVendorRemarkCommentRepository.save(rnsVendorRemarkComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsVendorRemarkComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-vendor-remark-comments : get all the rnsVendorRemarkComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsVendorRemarkComments in body
     */
    @GetMapping("/rns-vendor-remark-comments")
    @Timed
    public List<RnsVendorRemarkComment> getAllRnsVendorRemarkComments() {
        log.debug("REST request to get all RnsVendorRemarkComments");
        return rnsVendorRemarkCommentRepository.findAll();
        }

    /**
     * GET  /rns-vendor-remark-comments/:id : get the "id" rnsVendorRemarkComment.
     *
     * @param id the id of the rnsVendorRemarkComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsVendorRemarkComment, or with status 404 (Not Found)
     */
    @GetMapping("/rns-vendor-remark-comments/{id}")
    @Timed
    public ResponseEntity<RnsVendorRemarkComment> getRnsVendorRemarkComment(@PathVariable Long id) {
        log.debug("REST request to get RnsVendorRemarkComment : {}", id);
        Optional<RnsVendorRemarkComment> rnsVendorRemarkComment = rnsVendorRemarkCommentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsVendorRemarkComment);
    }

    /**
     * DELETE  /rns-vendor-remark-comments/:id : delete the "id" rnsVendorRemarkComment.
     *
     * @param id the id of the rnsVendorRemarkComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-vendor-remark-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsVendorRemarkComment(@PathVariable Long id) {
        log.debug("REST request to delete RnsVendorRemarkComment : {}", id);
        rnsVendorRemarkCommentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
