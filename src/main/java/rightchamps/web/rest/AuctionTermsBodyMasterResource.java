package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.AuctionTermsBodyMaster;

import rightchamps.repository.AuctionTermsBodyMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AuctionTermsBodyMaster.
 */
@RestController
@RequestMapping("/api")
public class AuctionTermsBodyMasterResource {

    private final Logger log = LoggerFactory.getLogger(AuctionTermsBodyMasterResource.class);

    private static final String ENTITY_NAME = "Auction Terms Body Master";

    private final AuctionTermsBodyMasterRepository auctionTermsBodyMasterRepository;

    public AuctionTermsBodyMasterResource(AuctionTermsBodyMasterRepository auctionTermsBodyMasterRepository) {
        this.auctionTermsBodyMasterRepository = auctionTermsBodyMasterRepository;
    }

    /**
     * POST  /auction-terms-body-masters : Create a new auctionTermsBodyMaster.
     *
     * @param auctionTermsBodyMaster the auctionTermsBodyMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auctionTermsBodyMaster, or with status 400 (Bad Request) if the auctionTermsBodyMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auction-terms-body-masters")
    @Timed
    public ResponseEntity<AuctionTermsBodyMaster> createAuctionTermsBodyMaster(@Valid @RequestBody AuctionTermsBodyMaster auctionTermsBodyMaster) throws URISyntaxException {
        log.debug("REST request to save AuctionTermsBodyMaster : {}", auctionTermsBodyMaster);
        if (auctionTermsBodyMaster.getId() != null) {
            throw new BadRequestAlertException("A new auctionTermsBodyMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuctionTermsBodyMaster result = auctionTermsBodyMasterRepository.save(auctionTermsBodyMaster);
        return ResponseEntity.created(new URI("/api/auction-terms-body-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auction-terms-body-masters : Updates an existing auctionTermsBodyMaster.
     *
     * @param auctionTermsBodyMaster the auctionTermsBodyMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auctionTermsBodyMaster,
     * or with status 400 (Bad Request) if the auctionTermsBodyMaster is not valid,
     * or with status 500 (Internal Server Error) if the auctionTermsBodyMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auction-terms-body-masters")
    @Timed
    public ResponseEntity<AuctionTermsBodyMaster> updateAuctionTermsBodyMaster(@Valid @RequestBody AuctionTermsBodyMaster auctionTermsBodyMaster) throws URISyntaxException {
        log.debug("REST request to update AuctionTermsBodyMaster : {}", auctionTermsBodyMaster);
        if (auctionTermsBodyMaster.getId() == null) {
            return createAuctionTermsBodyMaster(auctionTermsBodyMaster);
        }
        AuctionTermsBodyMaster result = auctionTermsBodyMasterRepository.save(auctionTermsBodyMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auctionTermsBodyMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auction-terms-body-masters : get all the auctionTermsBodyMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auctionTermsBodyMasters in body
     */
    @GetMapping("/auction-terms-body-masters")
    @Timed
    public List<AuctionTermsBodyMaster> getAllAuctionTermsBodyMasters() {
        log.debug("REST request to get all AuctionTermsBodyMasters");
        return auctionTermsBodyMasterRepository.findAll();
        }

    /**
     * GET  /auction-terms-body-masters/:id : get the "id" auctionTermsBodyMaster.
     *
     * @param id the id of the auctionTermsBodyMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionTermsBodyMaster, or with status 404 (Not Found)
     */
    @GetMapping("/auction-terms-body-masters/{id}")
    @Timed
    public ResponseEntity<AuctionTermsBodyMaster> getAuctionTermsBodyMaster(@PathVariable Long id) {
        log.debug("REST request to get AuctionTermsBodyMaster : {}", id);
        Optional<AuctionTermsBodyMaster> auctionTermsBodyMaster = auctionTermsBodyMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(auctionTermsBodyMaster);
    }

    /**
     * DELETE  /auction-terms-body-masters/:id : delete the "id" auctionTermsBodyMaster.
     *
     * @param id the id of the auctionTermsBodyMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auction-terms-body-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuctionTermsBodyMaster(@PathVariable Long id) {
        log.debug("REST request to delete AuctionTermsBodyMaster : {}", id);
        auctionTermsBodyMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
