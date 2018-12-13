package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.Auction;

import rightchamps.domain.AuctionVarDetails;
import rightchamps.domain.RnsQuotationVariant;
import rightchamps.repository.AuctionRepository;
import rightchamps.repository.AuctionVarDetailsRepository;
import rightchamps.repository.RnsQuotationVariantRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Auction.
 */
@RestController
@RequestMapping("/api")
public class AuctionResource {

    private final Logger log = LoggerFactory.getLogger(AuctionResource.class);

    private static final String ENTITY_NAME = "Auction";

    private final AuctionRepository auctionRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private AuctionVarDetailsRepository auctionVarDetailsRepository;

    public AuctionResource(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    /**
     * POST  /auctions : Create a new auction.
     *
     * @param auction the auction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auction, or with status 400 (Bad Request) if the auction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auctions")
    @Timed
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) throws URISyntaxException {
        log.debug("REST request to save Auction : {}", auction);
        if (auction.getId() != null) {
            throw new BadRequestAlertException("A new auction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Auction result = auctionRepository.save(auction);
        List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(result.getQuotationId());
        Integer inc = 0;
        Integer inctemp = 0;
        Instant startTime = result.getBiddingStartTime();
        Integer lotGap = result.getTimeBetweenLots();
        Integer lotRunningTime = result.getLotRunningTime();
        Integer overTime = 0;

        for (RnsQuotationVariant rqvariant : rnsQuotationVariants) {
            ++inc;
            Instant lotStartTime = null;
            Instant lotEndTime = null;
            if (inc == 1) {
                lotStartTime = startTime;
                lotEndTime = startTime.plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES);
            } else {
                lotStartTime = startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES);
                lotEndTime = startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES);
            }
            AuctionVarDetails auctionVarDetails = new AuctionVarDetails();
            auctionVarDetails.setLotStartTime(lotStartTime);
            auctionVarDetails.setLotEndTime(lotEndTime);
            auctionVarDetails.setVariantId(rqvariant.getId());
            auctionVarDetails.setQuotationId(result.getQuotationId());
            auctionVarDetails.setCreatedBy(getCurrentUserLogin());
            auctionVarDetails.setCreatedDate(Instant.now());
            auctionVarDetailsRepository.save(auctionVarDetails);
        }
        return ResponseEntity.created(new URI("/api/auctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auctions : Updates an existing auction.
     *
     * @param auction the auction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auction,
     * or with status 400 (Bad Request) if the auction is not valid,
     * or with status 500 (Internal Server Error) if the auction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auctions")
    @Timed
    public ResponseEntity<Auction> updateAuction(@RequestBody Auction auction) throws URISyntaxException {
        log.debug("REST request to update Auction : {}", auction);
        if (auction.getId() == null) {
            return createAuction(auction);
        }
        Auction result = auctionRepository.save(auction);

        Integer inc = 0;
        Integer inctemp = 0;
        Instant startTime = result.getBiddingStartTime();
        Integer lotGap = result.getTimeBetweenLots();
        Integer lotRunningTime = result.getLotRunningTime();
        Integer overTime = 0;
        List<AuctionVarDetails> auctionVarDetailsList = auctionVarDetailsRepository.getAuctionVarDetailsByQuotation(result.getQuotationId());
        if(auctionVarDetailsList!=null && auctionVarDetailsList.size()>0){
            for (AuctionVarDetails auctionVarDetails : auctionVarDetailsList) {
                ++inc;
                Instant lotStartTime = null;
                Instant lotEndTime = null;
                if (inc == 1) {
                    lotStartTime = startTime;
                    lotEndTime = startTime.plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES);
                } else {
                    lotStartTime = startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES);
                    lotEndTime = startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES);
                }
                auctionVarDetails.setLotStartTime(lotStartTime);
                auctionVarDetails.setLotEndTime(lotEndTime);
                auctionVarDetails.setUpdatedBy(getCurrentUserLogin());
                auctionVarDetails.setUpdatedDate(Instant.now());
                auctionVarDetailsRepository.save(auctionVarDetails);
            }
        } else{
            List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(result.getQuotation().getId());
            for (RnsQuotationVariant rqvariant : rnsQuotationVariants) {
                ++inc;
                Instant lotStartTime = null;
                Instant lotEndTime = null;
                if (inc == 1) {
                    lotStartTime = startTime;
                    lotEndTime = startTime.plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES);
                } else {
                    lotStartTime = startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES);
                    lotEndTime = startTime.plus(lotRunningTime * (inc - 1), ChronoUnit.MINUTES).plus(lotRunningTime, ChronoUnit.MINUTES).plus(overTime, ChronoUnit.MINUTES).plus(lotGap * (inc - 1), ChronoUnit.MINUTES);
                }
                AuctionVarDetails auctionVarDetails = new AuctionVarDetails();
                auctionVarDetails.setLotStartTime(lotStartTime);
                auctionVarDetails.setLotEndTime(lotEndTime);
                auctionVarDetails.setVariantId(rqvariant.getId());
                auctionVarDetails.setQuotationId(result.getQuotationId());
                auctionVarDetails.setCreatedBy(getCurrentUserLogin());
                auctionVarDetails.setCreatedDate(Instant.now());
                auctionVarDetailsRepository.save(auctionVarDetails);
            }
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auctions : get all the auctions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auctions in body
     */
    @GetMapping("/auctions")
    @Timed
    public List<Auction> getAllAuctions() {
        log.debug("REST request to get all Auctions");
        return auctionRepository.findAll();
        }

    /**
     * GET  /auctions/:id : get the "id" auction.
     *
     * @param id the id of the auction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auction, or with status 404 (Not Found)
     */
    @GetMapping("/auctions/{id}")
    @Timed
    public ResponseEntity<Auction> getAuction(@PathVariable Long id) {
        log.debug("REST request to get Auction : {}", id);
        Auction auction = auctionRepository.getAuctionByQuotationId(id);
        if(auction!=null && auction.getId()>0) {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auction));
        } else{
            auction = new Auction();
            auction.setQuotationId(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auction));
        }
    }

    /**
     * DELETE  /auctions/:id : delete the "id" auction.
     *
     * @param id the id of the auction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auctions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        log.debug("REST request to delete Auction : {}", id);
        auctionRepository.deleteById(id);
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
