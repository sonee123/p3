package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.AuctionVrnt;

import rightchamps.repository.AuctionVrntRepository;
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
 * REST controller for managing AuctionVrnt.
 */
@RestController
@RequestMapping("/api")
public class AuctionVrntResource {

    private final Logger log = LoggerFactory.getLogger(AuctionVrntResource.class);

    private static final String ENTITY_NAME = "Auction Vrnt";

    private final AuctionVrntRepository auctionVrntRepository;

    public AuctionVrntResource(AuctionVrntRepository auctionVrntRepository) {
        this.auctionVrntRepository = auctionVrntRepository;
    }

    /**
     * POST  /auction-vrnts : Create a new auctionVrnt.
     *
     * @param auctionVrnt the auctionVrnt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auctionVrnt, or with status 400 (Bad Request) if the auctionVrnt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auction-vrnts")
    @Timed
    public ResponseEntity<AuctionVrnt> createAuctionVrnt(@Valid @RequestBody AuctionVrnt auctionVrnt) throws URISyntaxException {
        log.debug("REST request to save AuctionVrnt : {}", auctionVrnt);
        if (auctionVrnt.getId() != null) {
            throw new BadRequestAlertException("A new auctionVrnt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuctionVrnt result = auctionVrntRepository.save(auctionVrnt);
        return ResponseEntity.created(new URI("/api/auction-vrnts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auction-vrnts : Updates an existing auctionVrnt.
     *
     * @param auctionVrnt the auctionVrnt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auctionVrnt,
     * or with status 400 (Bad Request) if the auctionVrnt is not valid,
     * or with status 500 (Internal Server Error) if the auctionVrnt couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auction-vrnts")
    @Timed
    public ResponseEntity<AuctionVrnt> updateAuctionVrnt(@Valid @RequestBody AuctionVrnt auctionVrnt) throws URISyntaxException {
        log.debug("REST request to update AuctionVrnt : {}", auctionVrnt);
        if (auctionVrnt.getId() == null) {
            return createAuctionVrnt(auctionVrnt);
        }
        AuctionVrnt result = auctionVrntRepository.save(auctionVrnt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auctionVrnt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auction-vrnts : get all the auctionVrnts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auctionVrnts in body
     */
    @GetMapping("/auction-vrnts")
    @Timed
    public List<AuctionVrnt> getAllAuctionVrnts() {
        log.debug("REST request to get all AuctionVrnts");
        return auctionVrntRepository.findAll();
        }

    /**
     * GET  /auction-vrnts/:id : get the "id" auctionVrnt.
     *
     * @param id the id of the auctionVrnt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionVrnt, or with status 404 (Not Found)
     */
    @GetMapping("/auction-vrnts/{id}")
    @Timed
    public ResponseEntity<AuctionVrnt> getAuctionVrnt(@PathVariable Long id) {
        log.debug("REST request to get AuctionVrnt : {}", id);
        Optional<AuctionVrnt> auctionVrnt = auctionVrntRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(auctionVrnt);
    }

    /**
     * GET  /auction-vrnts/:id : get the "id" auctionVrnt.
     *
     * @param id the id of the auctionVrnt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionVrnt, or with status 404 (Not Found)
     */
    @GetMapping("/auction-vrnts/get-auction-by-variant/{id}")
    @Timed
    public ResponseEntity<AuctionVrnt> getAuctionVrntbyVariant(@PathVariable Long id) {
        log.debug("REST request to get AuctionVrnt : {}", id);
        AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auctionVrnt));
    }

    

    /**
     * DELETE  /auction-vrnts/:id : delete the "id" auctionVrnt.
     *
     * @param id the id of the auctionVrnt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auction-vrnts/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuctionVrnt(@PathVariable Long id) {
        log.debug("REST request to delete AuctionVrnt : {}", id);
        auctionVrntRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
