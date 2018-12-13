package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsQuotationArticle;

import rightchamps.repository.RnsQuotationArticleRepository;
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
 * REST controller for managing RnsQuotationArticle.
 */
@RestController
@RequestMapping("/api")
public class RnsQuotationArticleResource {

    private final Logger log = LoggerFactory.getLogger(RnsQuotationArticleResource.class);

    private static final String ENTITY_NAME = "Quotation Article";

    private final RnsQuotationArticleRepository rnsQuotationArticleRepository;

    public RnsQuotationArticleResource(RnsQuotationArticleRepository rnsQuotationArticleRepository) {
        this.rnsQuotationArticleRepository = rnsQuotationArticleRepository;
    }

    /**
     * POST  /rns-quotation-articles : Create a new rnsQuotationArticle.
     *
     * @param rnsQuotationArticle the rnsQuotationArticle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsQuotationArticle, or with status 400 (Bad Request) if the rnsQuotationArticle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-quotation-articles")
    @Timed
    public ResponseEntity<RnsQuotationArticle> createRnsQuotationArticle(@RequestBody RnsQuotationArticle rnsQuotationArticle) throws URISyntaxException {
        log.debug("REST request to save RnsQuotationArticle : {}", rnsQuotationArticle);
        if (rnsQuotationArticle.getId() != null) {
            throw new BadRequestAlertException("A new rnsQuotationArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsQuotationArticle result = rnsQuotationArticleRepository.save(rnsQuotationArticle);
        return ResponseEntity.created(new URI("/api/rns-quotation-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-quotation-articles : Updates an existing rnsQuotationArticle.
     *
     * @param rnsQuotationArticle the rnsQuotationArticle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsQuotationArticle,
     * or with status 400 (Bad Request) if the rnsQuotationArticle is not valid,
     * or with status 500 (Internal Server Error) if the rnsQuotationArticle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-quotation-articles")
    @Timed
    public ResponseEntity<RnsQuotationArticle> updateRnsQuotationArticle(@RequestBody RnsQuotationArticle rnsQuotationArticle) throws URISyntaxException {
        log.debug("REST request to update RnsQuotationArticle : {}", rnsQuotationArticle);
        if (rnsQuotationArticle.getId() == null) {
            return createRnsQuotationArticle(rnsQuotationArticle);
        }
        RnsQuotationArticle result = rnsQuotationArticleRepository.save(rnsQuotationArticle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsQuotationArticle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-quotation-articles : get all the rnsQuotationArticles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsQuotationArticles in body
     */
    @GetMapping("/rns-quotation-articles")
    @Timed
    public List<RnsQuotationArticle> getAllRnsQuotationArticles() {
        log.debug("REST request to get all RnsQuotationArticles");
        return rnsQuotationArticleRepository.findAll();
        }

    /**
     * GET  /rns-quotation-articles/:id : get the "id" rnsQuotationArticle.
     *
     * @param id the id of the rnsQuotationArticle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsQuotationArticle, or with status 404 (Not Found)
     */
    @GetMapping("/rns-quotation-articles/{id}")
    @Timed
    public ResponseEntity<RnsQuotationArticle> getRnsQuotationArticle(@PathVariable Long id) {
        log.debug("REST request to get RnsQuotationArticle : {}", id);
        Optional<RnsQuotationArticle> rnsQuotationArticle = rnsQuotationArticleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsQuotationArticle);
    }

    /**
     * DELETE  /rns-quotation-articles/:id : delete the "id" rnsQuotationArticle.
     *
     * @param id the id of the rnsQuotationArticle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-quotation-articles/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsQuotationArticle(@PathVariable Long id) {
        log.debug("REST request to delete RnsQuotationArticle : {}", id);
        rnsQuotationArticleRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
