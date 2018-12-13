package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.RnsSourceTeamMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsSourceTeamMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rightchamps.repository.UserRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsSourceTeamMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsSourceTeamMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsSourceTeamMasterResource.class);

    private static final String ENTITY_NAME = "Sourcing Team Master";

    private final RnsSourceTeamMasterRepository rnsSourceTeamMasterRepository;

    private final UserRepository userRepository;

    public RnsSourceTeamMasterResource(RnsSourceTeamMasterRepository rnsSourceTeamMasterRepository,UserRepository userRepository) {
        this.rnsSourceTeamMasterRepository = rnsSourceTeamMasterRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-source-team-masters : Create a new rnsSourceTeamMaster.
     *
     * @param rnsSourceTeamMaster the rnsSourceTeamMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsSourceTeamMaster, or with status 400 (Bad Request) if the rnsSourceTeamMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-source-team-masters")
    @Timed
    public ResponseEntity<RnsSourceTeamMaster> createRnsSourceTeamMaster(@Valid @RequestBody RnsSourceTeamMaster rnsSourceTeamMaster) throws URISyntaxException {
        log.debug("REST request to save RnsSourceTeamMaster : {}", rnsSourceTeamMaster);
        if (rnsSourceTeamMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsSourceTeamMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsSourceTeamMaster.setUser(user);
        rnsSourceTeamMaster.setCreatedDate(Instant.now());
        RnsSourceTeamMaster result = rnsSourceTeamMasterRepository.save(rnsSourceTeamMaster);
        return ResponseEntity.created(new URI("/api/rns-source-team-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-source-team-masters : Updates an existing rnsSourceTeamMaster.
     *
     * @param rnsSourceTeamMaster the rnsSourceTeamMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsSourceTeamMaster,
     * or with status 400 (Bad Request) if the rnsSourceTeamMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsSourceTeamMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-source-team-masters")
    @Timed
    public ResponseEntity<RnsSourceTeamMaster> updateRnsSourceTeamMaster(@Valid @RequestBody RnsSourceTeamMaster rnsSourceTeamMaster) throws URISyntaxException {
        log.debug("REST request to update RnsSourceTeamMaster : {}", rnsSourceTeamMaster);
        if (rnsSourceTeamMaster.getId() == null) {
            return createRnsSourceTeamMaster(rnsSourceTeamMaster);
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsSourceTeamMaster.setUpdatedUser(user);
        rnsSourceTeamMaster.setLastUpdatedDate(Instant.now());
        RnsSourceTeamMaster result = rnsSourceTeamMasterRepository.save(rnsSourceTeamMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsSourceTeamMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-source-team-masters : get all the rnsSourceTeamMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsSourceTeamMasters in body
     */
    @GetMapping("/rns-source-team-masters")
    @Timed
    public List<RnsSourceTeamMaster> getAllRnsSourceTeamMasters() {
        log.debug("REST request to get all RnsSourceTeamMasters");
        return rnsSourceTeamMasterRepository.findAll();
        }


    /**
     * GET  /rns-source-team-masters : get all the rnsSourceTeamMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsSourceTeamMasters in body
     */
    @GetMapping("/rns-source-team-masters-login")
    @Timed
    public List<RnsSourceTeamMaster> getAllRnsSourceTeamMastersByLogin() {
        log.debug("REST request to get all RnsSourceTeamMasters");
        String userId = getCurrentUserLogin();
        return rnsSourceTeamMasterRepository.findAllByActive(userId);
    }

    /**
     * GET  /rns-source-team-masters/:id : get the "id" rnsSourceTeamMaster.
     *
     * @param id the id of the rnsSourceTeamMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsSourceTeamMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-source-team-masters/{id}")
    @Timed
    public ResponseEntity<RnsSourceTeamMaster> getRnsSourceTeamMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsSourceTeamMaster : {}", id);
        Optional<RnsSourceTeamMaster> rnsSourceTeamMaster = rnsSourceTeamMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsSourceTeamMaster);
    }

    /**
     * DELETE  /rns-source-team-masters/:id : delete the "id" rnsSourceTeamMaster.
     *
     * @param id the id of the rnsSourceTeamMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-source-team-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsSourceTeamMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsSourceTeamMaster : {}", id);
        rnsSourceTeamMasterRepository.deleteById(id);
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
