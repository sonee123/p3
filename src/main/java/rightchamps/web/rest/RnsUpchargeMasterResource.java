package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.RnsUpchargeMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsUpchargeMasterRepository;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsUpchargeMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsUpchargeMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsUpchargeMasterResource.class);

    private static final String ENTITY_NAME = "Upcharge Master";

    private final RnsUpchargeMasterRepository rnsUpchargeMasterRepository;
    
    @Inject
    private UserRepository userRepository;
    

    public RnsUpchargeMasterResource(RnsUpchargeMasterRepository rnsUpchargeMasterRepository) {
        this.rnsUpchargeMasterRepository = rnsUpchargeMasterRepository;
    }

    /**
     * POST  /rns-upcharge-masters : Create a new rnsUpchargeMaster.
     *
     * @param rnsUpchargeMaster the rnsUpchargeMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsUpchargeMaster, or with status 400 (Bad Request) if the rnsUpchargeMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-upcharge-masters")
    @Timed
    public ResponseEntity<RnsUpchargeMaster> createRnsUpchargeMaster(@Valid @RequestBody RnsUpchargeMaster rnsUpchargeMaster) throws URISyntaxException {
        log.debug("REST request to save RnsUpchargeMaster : {}", rnsUpchargeMaster);
        if (rnsUpchargeMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsUpchargeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String userName = getCurrentUserLogin();
        User user = userRepository.findByLogin(userName);
        rnsUpchargeMaster.setUser(user);
        rnsUpchargeMaster.setCreatedDate(Instant.now());
        
        RnsUpchargeMaster result = rnsUpchargeMasterRepository.save(rnsUpchargeMaster);
        return ResponseEntity.created(new URI("/api/rns-upcharge-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    
    /**
     * PUT  /rns-upcharge-masters : Updates an existing rnsUpchargeMaster.
     *
     * @param rnsUpchargeMaster the rnsUpchargeMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsUpchargeMaster,
     * or with status 400 (Bad Request) if the rnsUpchargeMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsUpchargeMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-upcharge-masters")
    @Timed
    public ResponseEntity<RnsUpchargeMaster> updateRnsUpchargeMaster(@Valid @RequestBody RnsUpchargeMaster rnsUpchargeMaster) throws URISyntaxException {
        log.debug("REST request to update RnsUpchargeMaster : {}", rnsUpchargeMaster);
        if (rnsUpchargeMaster.getId() == null) {
            return createRnsUpchargeMaster(rnsUpchargeMaster);
        }
        String userName = getCurrentUserLogin();
        User user = userRepository.findByLogin(userName);
        rnsUpchargeMaster.setUpdateUser(user);
        rnsUpchargeMaster.setLastUpdatedDate(Instant.now());
        
        RnsUpchargeMaster result = rnsUpchargeMasterRepository.save(rnsUpchargeMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsUpchargeMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-upcharge-masters : get all the rnsUpchargeMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsUpchargeMasters in body
     */
    @GetMapping("/rns-upcharge-masters")
    @Timed
    public List<RnsUpchargeMaster> getAllRnsUpchargeMasters() {
        log.debug("REST request to get all RnsUpchargeMasters");
        return rnsUpchargeMasterRepository.findAll();
        }

    /**
     * GET  /rns-upcharge-masters/:id : get the "id" rnsUpchargeMaster.
     *
     * @param id the id of the rnsUpchargeMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsUpchargeMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-upcharge-masters/{id}")
    @Timed
    public ResponseEntity<RnsUpchargeMaster> getRnsUpchargeMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsUpchargeMaster : {}", id);
        Optional<RnsUpchargeMaster> rnsUpchargeMaster = rnsUpchargeMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsUpchargeMaster);
    }

    /**
     * DELETE  /rns-upcharge-masters/:id : delete the "id" rnsUpchargeMaster.
     *
     * @param id the id of the rnsUpchargeMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-upcharge-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsUpchargeMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsUpchargeMaster : {}", id);
        rnsUpchargeMasterRepository.deleteById(id);
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
