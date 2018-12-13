package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsBuyerMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsBuyerMasterRepository;
import rightchamps.repository.UserRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsBuyerMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsBuyerMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsBuyerMasterResource.class);

    private static final String ENTITY_NAME = "End User Master";

    private final RnsBuyerMasterRepository rnsBuyerMasterRepository;
    private final UserRepository userRepository;

    public RnsBuyerMasterResource(RnsBuyerMasterRepository rnsBuyerMasterRepository,UserRepository userRepository) {
        this.rnsBuyerMasterRepository = rnsBuyerMasterRepository;
        this.userRepository = userRepository;
    }


    /**
     * POST  /rns-buyer-masters : Create a new rnsBuyerMaster.
     *
     * @param rnsBuyerMaster the rnsBuyerMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsBuyerMaster, or with status 400 (Bad Request) if the rnsBuyerMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-buyer-masters")
    @Timed
    public ResponseEntity<RnsBuyerMaster> createRnsBuyerMaster(@RequestBody RnsBuyerMaster rnsBuyerMaster) throws URISyntaxException {
        log.debug("REST request to save RnsBuyerMaster : {}", rnsBuyerMaster);
        if (rnsBuyerMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsBuyerMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

        RnsBuyerMaster buyerMaster = rnsBuyerMasterRepository.findByBuyerCode(rnsBuyerMaster.getBuyerCode().toUpperCase());
        RnsBuyerMaster result = null;
        if (buyerMaster != null) {
            result = buyerMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(), "End User code already exist"))
                .body(result);
        } else {
            rnsBuyerMaster.setBuyerCode(rnsBuyerMaster.getBuyerCode().toUpperCase());
            User user = userRepository.findByLogin(getCurrentUserLogin());
            rnsBuyerMaster.setUser(user);
            rnsBuyerMaster.setCreatedDate(Instant.now());

            result = rnsBuyerMasterRepository.save(rnsBuyerMaster);
        }
        return ResponseEntity.created(new URI("/api/rns-buyer-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-buyer-masters : Updates an existing rnsBuyerMaster.
     *
     * @param rnsBuyerMaster the rnsBuyerMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsBuyerMaster,
     * or with status 400 (Bad Request) if the rnsBuyerMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsBuyerMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-buyer-masters")
    @Timed
    public ResponseEntity<RnsBuyerMaster> updateRnsBuyerMaster(@RequestBody RnsBuyerMaster rnsBuyerMaster) throws URISyntaxException {
        log.debug("REST request to update RnsBuyerMaster : {}", rnsBuyerMaster);
        if (rnsBuyerMaster.getId() == null) {
            return createRnsBuyerMaster(rnsBuyerMaster);
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsBuyerMaster.setUpdatedUser(user);
        rnsBuyerMaster.setLastUpdatedDate(Instant.now());
        RnsBuyerMaster result = rnsBuyerMasterRepository.save(rnsBuyerMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsBuyerMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-buyer-masters : get all the rnsBuyerMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsBuyerMasters in body
     */
    @GetMapping("/rns-buyer-masters")
    @Timed
    public List<RnsBuyerMaster> getAllRnsBuyerMasters() {
        log.debug("REST request to get all RnsBuyerMasters");
        return rnsBuyerMasterRepository.findAll();
        }

    /**
     * GET  /rns-buyer-masters/:id : get the "id" rnsBuyerMaster.
     *
     * @param id the id of the rnsBuyerMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsBuyerMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-buyer-masters/{id}")
    @Timed
    public ResponseEntity<RnsBuyerMaster> getRnsBuyerMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsBuyerMaster : {}", id);
        Optional<RnsBuyerMaster> rnsBuyerMaster = rnsBuyerMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsBuyerMaster);
    }

    /**
     * DELETE  /rns-buyer-masters/:id : delete the "id" rnsBuyerMaster.
     *
     * @param id the id of the rnsBuyerMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-buyer-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsBuyerMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsBuyerMaster : {}", id);
        rnsBuyerMasterRepository.deleteById(id);
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
