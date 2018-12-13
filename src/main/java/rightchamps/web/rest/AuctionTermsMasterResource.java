package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.AuctionTermsBodyMaster;
import rightchamps.domain.AuctionTermsMaster;

import rightchamps.domain.User;
import rightchamps.modal.AuctionTermsMasterBean;
import rightchamps.repository.AuctionTermsBodyMasterRepository;
import rightchamps.repository.AuctionTermsMasterRepository;
import rightchamps.repository.UserRepository;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AuctionTermsMaster.
 */
@RestController
@RequestMapping("/api")
public class AuctionTermsMasterResource {

    private final Logger log = LoggerFactory.getLogger(AuctionTermsMasterResource.class);

    private static final String ENTITY_NAME = "Auction Terms Master";

    private final AuctionTermsMasterRepository auctionTermsMasterRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuctionTermsBodyMasterRepository auctionTermsBodyMasterRepository;

    public AuctionTermsMasterResource(AuctionTermsMasterRepository auctionTermsMasterRepository) {
        this.auctionTermsMasterRepository = auctionTermsMasterRepository;
    }

    /**
     * POST  /auction-terms-masters : Create a new auctionTermsMaster.
     *
     * @param auctionTermsMaster the auctionTermsMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auctionTermsMaster, or with status 400 (Bad Request) if the auctionTermsMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auction-terms-masters")
    @Timed
    public ResponseEntity<AuctionTermsMaster> createAuctionTermsMaster(@Valid @RequestBody AuctionTermsMaster auctionTermsMaster) throws URISyntaxException {
        log.debug("REST request to save AuctionTermsMaster : {}", auctionTermsMaster);
        if (auctionTermsMaster.getId() != null) {
            throw new BadRequestAlertException("A new auctionTermsMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

        AuctionTermsMaster auctionTermsMaster1 = auctionTermsMasterRepository.findByRnsCatgMasterAndQuoteTypeAndSourceTeamId(auctionTermsMaster.getRnsCatgMaster().getId(), auctionTermsMaster.getQuoteType().getTypeCode(), auctionTermsMaster.getSourceTeamId().getId());

        if(auctionTermsMaster1 !=null){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, auctionTermsMaster1.getId().toString(),"Entry already exist!!!"))
                .body(auctionTermsMaster1);
        } else {
            User user = userRepository.findByLogin(getCurrentUserLogin());
            auctionTermsMaster.setUser(user);
            auctionTermsMaster.setCreatedDate(Instant.now());
            String mailBody = auctionTermsMaster.getTermsBody();
            auctionTermsMaster.setTermsBody("");
            AuctionTermsMaster result = auctionTermsMasterRepository.save(auctionTermsMaster);

            List<String> strings = new ArrayList<String>();
            int index = 0;
            while (index < mailBody.length()) {
                strings.add(mailBody.substring(index, Math.min(index + 2000, mailBody.length())));
                index += 2000;
            }
            for (String body : strings) {
                AuctionTermsBodyMaster auctionTermsBodyMaster = new AuctionTermsBodyMaster();
                auctionTermsBodyMaster.setTermId(result.getId());
                auctionTermsBodyMaster.setTermsBody(body);
                auctionTermsBodyMaster.setCreatedBy(getCurrentUserLogin());
                auctionTermsBodyMaster.setCreatedDate(Instant.now());
                auctionTermsBodyMasterRepository.save(auctionTermsBodyMaster);
            }
            return ResponseEntity.created(new URI("/api/auction-terms-masters/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
    }

    /**
     * PUT  /auction-terms-masters : Updates an existing auctionTermsMaster.
     *
     * @param auctionTermsMaster the auctionTermsMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auctionTermsMaster,
     * or with status 400 (Bad Request) if the auctionTermsMaster is not valid,
     * or with status 500 (Internal Server Error) if the auctionTermsMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auction-terms-masters")
    @Timed
    public ResponseEntity<AuctionTermsMaster> updateAuctionTermsMaster(@Valid @RequestBody AuctionTermsMaster auctionTermsMaster) throws URISyntaxException {
        log.debug("REST request to update AuctionTermsMaster : {}", auctionTermsMaster);
        if (auctionTermsMaster.getId() == null) {
            return createAuctionTermsMaster(auctionTermsMaster);
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        auctionTermsMaster.setUpdatedUser(user);
        auctionTermsMaster.setLastUpdatedDate(Instant.now());
        String mailBody = auctionTermsMaster.getTermsBody();
        auctionTermsMaster.setTermsBody("");
        AuctionTermsMaster result = auctionTermsMasterRepository.save(auctionTermsMaster);
        List<String> strings = new ArrayList<String>();
        int index = 0;
        while (index < mailBody.length()) {
            strings.add(mailBody.substring(index, Math.min(index + 2000, mailBody.length())));
            index += 2000;
        }
        auctionTermsBodyMasterRepository.deleteByTermId(result.getId());
        for(String body : strings){
            AuctionTermsBodyMaster auctionTermsBodyMaster = new AuctionTermsBodyMaster();
            auctionTermsBodyMaster.setTermId(result.getId());
            auctionTermsBodyMaster.setTermsBody(body);
            auctionTermsBodyMaster.setCreatedBy(getCurrentUserLogin());
            auctionTermsBodyMaster.setCreatedDate(Instant.now());
            auctionTermsBodyMasterRepository.save(auctionTermsBodyMaster);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auctionTermsMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auction-terms-masters : get all the auctionTermsMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auctionTermsMasters in body
     */
    @GetMapping("/auction-terms-masters")
    @Timed
    public List<AuctionTermsMaster> getAllAuctionTermsMasters() {
        log.debug("REST request to get all AuctionTermsMasters");
        List<AuctionTermsMaster> auctionTermsMastersTemp = auctionTermsMasterRepository.findAll();
        List<AuctionTermsMaster> auctionTermsMasters = new ArrayList<AuctionTermsMaster>();
        for (AuctionTermsMaster auctionTermsMaster : auctionTermsMastersTemp) {
            List<AuctionTermsBodyMaster> auctionTermsBodyMasters = auctionTermsBodyMasterRepository.findAllByTermId(auctionTermsMaster.getId());
            String emailBody = "";
            for (AuctionTermsBodyMaster body : auctionTermsBodyMasters) {
                emailBody += body.getTermsBody();
            }
            auctionTermsMaster.setTermsBody(emailBody);
            auctionTermsMasters.add(auctionTermsMaster);
        }
        return auctionTermsMasters;
    }

    /**
     * GET  /auction-terms-masters/:id : get the "id" auctionTermsMaster.
     *
     * @param id the id of the auctionTermsMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionTermsMaster, or with status 404 (Not Found)
     */
    @GetMapping("/auction-terms-masters/{id}")
    @Timed
    public ResponseEntity<AuctionTermsMaster> getAuctionTermsMaster(@PathVariable Long id) {
        log.debug("REST request to get AuctionTermsMaster : {}", id);
        AuctionTermsMaster auctionTermsMaster = auctionTermsMasterRepository.findById(id).orElse(null);
        List<AuctionTermsBodyMaster> auctionTermsBodyMasters = auctionTermsBodyMasterRepository.findAllByTermId(auctionTermsMaster.getId());
        String emailBody="";
        for(AuctionTermsBodyMaster body: auctionTermsBodyMasters){
            emailBody+=body.getTermsBody();
        }
        auctionTermsMaster.setTermsBody(emailBody);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auctionTermsMaster));
    }

    /**
     * GET  /auction-terms-masters/:id : get the "id" auctionTermsMaster.
     *
     * @param auctionTermsMasterBean the id of the auctionTermsMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionTermsMaster, or with status 404 (Not Found)
     */
    @PostMapping("/auction-terms-masters-agree")
    @Timed
    public ResponseEntity<AuctionTermsMaster> getAuctionTermsMasterPost(@RequestBody AuctionTermsMasterBean auctionTermsMasterBean) {
        log.debug("REST request to get AuctionTermsMaster : {}", auctionTermsMasterBean);
        AuctionTermsMaster auctionTermsMaster = auctionTermsMasterRepository.findByRnsCatgMasterAndQuoteTypeAndSourceTeamId(auctionTermsMasterBean.getCategoryId(), auctionTermsMasterBean.getQuoteTypeCode(), auctionTermsMasterBean.getSourceTeam());
        if(auctionTermsMaster != null) {
            List<AuctionTermsBodyMaster> auctionTermsBodyMasters = auctionTermsBodyMasterRepository.findAllByTermId(auctionTermsMaster.getId());
            String emailBody = "";
            for (AuctionTermsBodyMaster body : auctionTermsBodyMasters) {
                emailBody += body.getTermsBody();
            }
            auctionTermsMaster.setTermsBody(emailBody);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auctionTermsMaster));
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new  AuctionTermsMaster()));
        }
    }

    /**
     * DELETE  /auction-terms-masters/:id : delete the "id" auctionTermsMaster.
     *
     * @param id the id of the auctionTermsMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auction-terms-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuctionTermsMaster(@PathVariable Long id) {
        log.debug("REST request to delete AuctionTermsMaster : {}", id);
        auctionTermsMasterRepository.deleteById(id);
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
