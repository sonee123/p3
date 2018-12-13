package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsEmpMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsEmpMasterRepository;
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
import rightchamps.repository.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsEmpMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsEmpMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsEmpMasterResource.class);

    private static final String ENTITY_NAME = "Employee Master";

    private final RnsEmpMasterRepository rnsEmpMasterRepository;
    private final UserRepository userRepository;

    public RnsEmpMasterResource(RnsEmpMasterRepository rnsEmpMasterRepository,UserRepository userRepository) {
        this.rnsEmpMasterRepository = rnsEmpMasterRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-emp-masters : Create a new rnsEmpMaster.
     *
     * @param rnsEmpMaster the rnsEmpMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsEmpMaster, or with status 400 (Bad Request) if the rnsEmpMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-emp-masters")
    @Timed
    public ResponseEntity<RnsEmpMaster> createRnsEmpMaster(@RequestBody RnsEmpMaster rnsEmpMaster) throws URISyntaxException {
        log.debug("REST request to save RnsEmpMaster : {}", rnsEmpMaster);
        if (rnsEmpMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsEmpMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

        RnsEmpMaster empMaster = rnsEmpMasterRepository.findByEmpCode(rnsEmpMaster.getEmpCode().toUpperCase());
        RnsEmpMaster result = null;
        if (empMaster != null) {
            result = empMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(), "Employee code already exist"))
                .body(result);
        } else {
            rnsEmpMaster.setEmpCode(rnsEmpMaster.getEmpCode().toUpperCase());
            User user = userRepository.findByLogin(getCurrentUserLogin());
            rnsEmpMaster.setUser(user);
            rnsEmpMaster.setCreatedDate(Instant.now());
            result = rnsEmpMasterRepository.save(rnsEmpMaster);
        }


        return ResponseEntity.created(new URI("/api/rns-emp-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-emp-masters : Updates an existing rnsEmpMaster.
     *
     * @param rnsEmpMaster the rnsEmpMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsEmpMaster,
     * or with status 400 (Bad Request) if the rnsEmpMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsEmpMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-emp-masters")
    @Timed
    public ResponseEntity<RnsEmpMaster> updateRnsEmpMaster(@RequestBody RnsEmpMaster rnsEmpMaster) throws URISyntaxException {
        log.debug("REST request to update RnsEmpMaster : {}", rnsEmpMaster);
        if (rnsEmpMaster.getId() == null) {
            return createRnsEmpMaster(rnsEmpMaster);
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsEmpMaster.setUpdatedUser(user);
        rnsEmpMaster.setLastUpdatedDate(Instant.now());
        RnsEmpMaster result = rnsEmpMasterRepository.save(rnsEmpMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsEmpMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-emp-masters : get all the rnsEmpMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsEmpMasters in body
     */
    @GetMapping("/rns-emp-masters")
    @Timed
    public List<RnsEmpMaster> getAllRnsEmpMasters() {
        log.debug("REST request to get all RnsEmpMasters");
        return rnsEmpMasterRepository.findAll();
        }

    /**
     * GET  /rns-emp-masters/:id : get the "id" rnsEmpMaster.
     *
     * @param id the id of the rnsEmpMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsEmpMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-emp-masters/{id}")
    @Timed
    public ResponseEntity<RnsEmpMaster> getRnsEmpMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsEmpMaster : {}", id);
        Optional<RnsEmpMaster> rnsEmpMaster = rnsEmpMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsEmpMaster);
    }

    /**
     * DELETE  /rns-emp-masters/:id : delete the "id" rnsEmpMaster.
     *
     * @param id the id of the rnsEmpMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-emp-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsEmpMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsEmpMaster : {}", id);
        rnsEmpMasterRepository.deleteById(id);
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
