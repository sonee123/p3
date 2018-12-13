package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.repository.UserRepository;
import rightchamps.domain.RnsDelPlaceMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsDelPlaceMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.RnsRelation;
import rightchamps.repository.RnsCatgMasterRepository;
import java.util.ArrayList;
/**
 * REST controller for managing RnsDelPlaceMaster.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
public class RnsDelPlaceMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsDelPlaceMasterResource.class);

    private static final String ENTITY_NAME = "Delivery Place Master";

    private final RnsDelPlaceMasterRepository rnsDelPlaceMasterRepository;
    private final UserRepository userRepository;


    public RnsDelPlaceMasterResource(RnsDelPlaceMasterRepository rnsDelPlaceMasterRepository,UserRepository userRepository) {
        this.rnsDelPlaceMasterRepository = rnsDelPlaceMasterRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /rns-del-place-masters : Create a new rnsDelPlaceMaster.
     *
     * @param rnsDelPlaceMaster the rnsDelPlaceMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsDelPlaceMaster, or with status 400 (Bad Request) if the rnsDelPlaceMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-del-place-masters")
    @Timed
    public ResponseEntity<RnsDelPlaceMaster> createRnsDelPlaceMaster(@RequestBody RnsDelPlaceMaster rnsDelPlaceMaster) throws URISyntaxException {
        log.debug("REST request to save RnsDelPlaceMaster : {}", rnsDelPlaceMaster);
        if (rnsDelPlaceMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsDelPlaceMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
          User user = userRepository.findByLogin(getCurrentUserLogin());
          rnsDelPlaceMaster.setUser(user);
          rnsDelPlaceMaster.setCreatedDate(Instant.now());
        //RnsDelPlaceMaster result = rnsDelPlaceMasterRepository.save(rnsDelPlaceMaster);

        RnsDelPlaceMaster delPlaceMaster = rnsDelPlaceMasterRepository.findByDelPlaceCode(rnsDelPlaceMaster.getCode().toUpperCase());
        RnsDelPlaceMaster result = null;
        if(delPlaceMaster!=null){
            result = delPlaceMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Delivery Place Code already exist"))
                .body(result);
        } else{
            rnsDelPlaceMaster.setCode(rnsDelPlaceMaster.getCode().toUpperCase());
            result = rnsDelPlaceMasterRepository.save(rnsDelPlaceMaster);
        }

        return ResponseEntity.created(new URI("/api/rns-del-place-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-del-place-masters : Updates an existing rnsDelPlaceMaster.
     *
     * @param rnsDelPlaceMaster the rnsDelPlaceMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsDelPlaceMaster,
     * or with status 400 (Bad Request) if the rnsDelPlaceMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsDelPlaceMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-del-place-masters")
    @Timed
    public ResponseEntity<RnsDelPlaceMaster> updateRnsDelPlaceMaster(@RequestBody RnsDelPlaceMaster rnsDelPlaceMaster) throws URISyntaxException {
        log.debug("REST request to update RnsDelPlaceMaster : {}", rnsDelPlaceMaster);
        if (rnsDelPlaceMaster.getId() == null) {
            return createRnsDelPlaceMaster(rnsDelPlaceMaster);
        }

        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsDelPlaceMaster.setUpdatedUser(user);
        rnsDelPlaceMaster.setLastUpdatedDate(Instant.now());
        RnsDelPlaceMaster result = rnsDelPlaceMasterRepository.save(rnsDelPlaceMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsDelPlaceMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-del-place-masters : get all the rnsDelPlaceMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsDelPlaceMasters in body
     */
    @GetMapping("/rns-del-place-masters")
    @Timed
    public List<RnsDelPlaceMaster> getAllRnsDelPlaceMasters() {
        log.debug("REST request to get all RnsDelPlaceMasters");
        return rnsDelPlaceMasterRepository.findAll();
        }

    /**
     * GET  /rns-del-place-masters/:id : get the "id" rnsDelPlaceMaster.
     *
     * @param id the id of the rnsDelPlaceMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsDelPlaceMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-del-place-masters/{id}")
    @Timed
    public ResponseEntity<RnsDelPlaceMaster> getRnsDelPlaceMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsDelPlaceMaster : {}", id);
        Optional<RnsDelPlaceMaster> rnsDelPlaceMaster = rnsDelPlaceMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsDelPlaceMaster);
    }

    /**
     * DELETE  /rns-del-place-masters/:id : delete the "id" rnsDelPlaceMaster.
     *
     * @param id the id of the rnsDelPlaceMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-del-place-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsDelPlaceMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsDelPlaceMaster : {}", id);
        rnsDelPlaceMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    private String getCurrentUserLogin() {
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
