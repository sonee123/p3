package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.AuctionPauseDetails;

import rightchamps.domain.AuctionVarDetails;
import rightchamps.repository.AuctionPauseDetailsRepository;
import rightchamps.repository.AuctionVarDetailsRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AuctionPauseDetails.
 */
@RestController
@RequestMapping("/api")
public class AuctionPauseDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AuctionPauseDetailsResource.class);

    private static final String ENTITY_NAME = "Auction Pause Details";

    private final AuctionPauseDetailsRepository auctionPauseDetailsRepository;

    @Inject
    private AuctionVarDetailsRepository auctionVarDetailsRepository;

    public AuctionPauseDetailsResource(AuctionPauseDetailsRepository auctionPauseDetailsRepository) {
        this.auctionPauseDetailsRepository = auctionPauseDetailsRepository;
    }

    /**
     * POST  /auction-pause-details : Create a new auctionPauseDetails.
     *
     * @param auctionPauseDetails the auctionPauseDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auctionPauseDetails, or with status 400 (Bad Request) if the auctionPauseDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auction-pause-details")
    @Timed
    public ResponseEntity<AuctionPauseDetails> createAuctionPauseDetails(@Valid @RequestBody AuctionPauseDetails auctionPauseDetails) throws URISyntaxException {
        log.debug("REST request to save AuctionPauseDetails : {}", auctionPauseDetails);
        if (auctionPauseDetails.getId() != null) {
            throw new BadRequestAlertException("A new auctionPauseDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        auctionPauseDetails.setPauseStartDate(Instant.now());
        auctionPauseDetails.setCreatedDate(Instant.now());
        auctionPauseDetails.setCreatedBy(getCurrentUserLogin());
        AuctionPauseDetails result = auctionPauseDetailsRepository.save(auctionPauseDetails);
        return ResponseEntity.created(new URI("/api/auction-pause-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auction-pause-details : Updates an existing auctionPauseDetails.
     *
     * @param auctionPauseDetails the auctionPauseDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auctionPauseDetails,
     * or with status 400 (Bad Request) if the auctionPauseDetails is not valid,
     * or with status 500 (Internal Server Error) if the auctionPauseDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auction-pause-details")
    @Timed
    public ResponseEntity<AuctionPauseDetails> updateAuctionPauseDetails(@Valid @RequestBody AuctionPauseDetails auctionPauseDetails) throws URISyntaxException {
        log.debug("REST request to update AuctionPauseDetails : {}", auctionPauseDetails);
        if (auctionPauseDetails.getId() == null) {
            return createAuctionPauseDetails(auctionPauseDetails);
        }
        auctionPauseDetails= auctionPauseDetailsRepository.findById(auctionPauseDetails.getId()).orElse(null);
        Instant instant = Instant.now();
        auctionPauseDetails.setPauseEndDate(instant);
        auctionPauseDetails.setUpdatedDate(instant);
        auctionPauseDetails.setUpdatedBy(getCurrentUserLogin());
        AuctionPauseDetails result = auctionPauseDetailsRepository.save(auctionPauseDetails);
        if(result!=null){
            Duration betweenCurrent = Duration.between(result.getPauseStartDate(), instant);
            Long minutesDifference = betweenCurrent.toMinutes();
            List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotationAndVariant(result.getQuotationId(), result.getVariantId());
            for (AuctionVarDetails auctionVarDetails : auctionVarDetailsList) {
                if (result.getVariantId().longValue() == auctionVarDetails.getVariantId().longValue()) {
                    Instant lotEndTime = auctionVarDetails.getLotEndTime();
                    lotEndTime = lotEndTime.plus(minutesDifference, ChronoUnit.MINUTES);
                    auctionVarDetails.setLotEndTime(lotEndTime);
                    auctionVarDetailsRepository.save(auctionVarDetails);
                } else {
                    Instant lotStartTime = auctionVarDetails.getLotStartTime();
                    lotStartTime = lotStartTime.plus(minutesDifference, ChronoUnit.MINUTES);
                    auctionVarDetails.setLotStartTime(lotStartTime);

                    Instant lotEndTime = auctionVarDetails.getLotEndTime();
                    lotEndTime = lotEndTime.plus(minutesDifference, ChronoUnit.MINUTES);
                    auctionVarDetails.setLotEndTime(lotEndTime);
                    auctionVarDetailsRepository.save(auctionVarDetails);
                }
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auctionPauseDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auction-pause-details : get all the auctionPauseDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auctionPauseDetails in body
     */
    @GetMapping("/auction-pause-details")
    @Timed
    public List<AuctionPauseDetails> getAllAuctionPauseDetails() {
        log.debug("REST request to get all AuctionPauseDetails");
        return auctionPauseDetailsRepository.findAll();
        }

    /**
     * GET  /auction-pause-details/:id : get the "id" auctionPauseDetails.
     *
     * @param id the id of the auctionPauseDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionPauseDetails, or with status 404 (Not Found)
     */
    @GetMapping("/auction-pause-details/{id}")
    @Timed
    public ResponseEntity<AuctionPauseDetails> getAuctionPauseDetails(@PathVariable Long id) {
        log.debug("REST request to get AuctionPauseDetails : {}", id);
        Optional<AuctionPauseDetails> auctionPauseDetails = auctionPauseDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(auctionPauseDetails);
    }

    /**
     * GET  /auction-pause-details/:id : get the "id" auctionPauseDetails.
     *
     * @param id the id of the auctionPauseDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionPauseDetails, or with status 404 (Not Found)
     */
    @GetMapping("/auction-pause-details-quotation/{id}")
    @Timed
    public ResponseEntity<AuctionPauseDetails> getAuctionPauseDetailsByQuotation(@PathVariable Long id) {
        log.debug("REST request to get AuctionPauseDetails : {}", id);
        AuctionPauseDetails auctionPauseDetails = auctionPauseDetailsRepository.findAuctionPauseDetailsByQuotationId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auctionPauseDetails));
    }

    /**
     * DELETE  /auction-pause-details/:id : delete the "id" auctionPauseDetails.
     *
     * @param id the id of the auctionPauseDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auction-pause-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuctionPauseDetails(@PathVariable Long id) {
        log.debug("REST request to delete AuctionPauseDetails : {}", id);
        auctionPauseDetailsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }
}
