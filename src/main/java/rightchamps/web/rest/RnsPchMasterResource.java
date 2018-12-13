package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsPchMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsPchMasterRepository;
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
 * REST controller for managing RnsPchMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsPchMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsPchMasterResource.class);

    private static final String ENTITY_NAME = "PCH Master";

    private final RnsPchMasterRepository rnsPchMasterRepository;

    private final UserRepository userRepository;

    public RnsPchMasterResource(RnsPchMasterRepository rnsPchMasterRepository,UserRepository userRepository) {
    	this.rnsPchMasterRepository = rnsPchMasterRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-pch-masters : Create a new rnsPchMaster.
     *
     * @param rnsPchMaster the rnsPchMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsPchMaster, or with status 400 (Bad Request) if the rnsPchMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-pch-masters")
    @Timed
    public ResponseEntity<RnsPchMaster> createRnsPchMaster(@RequestBody RnsPchMaster rnsPchMaster) throws URISyntaxException {
        log.debug("REST request to save RnsPchMaster : {}", rnsPchMaster);
        if (rnsPchMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsPchMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsPchMaster.setUser(user);
        rnsPchMaster.setCreatedDate(Instant.now());

        RnsPchMaster pchMaster = rnsPchMasterRepository.findByPchCode(rnsPchMaster.getPchCode().toUpperCase());
        RnsPchMaster result = null;
        if (pchMaster != null) {
            result = pchMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(), "PCH Code already exist"))
                .body(result);
        } else {
            rnsPchMaster.setPchCode(rnsPchMaster.getPchCode().toUpperCase());
            result = rnsPchMasterRepository.save(rnsPchMaster);
        }
        return ResponseEntity.created(new URI("/api/rns-pch-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-pch-masters : Updates an existing rnsPchMaster.
     *
     * @param rnsPchMaster the rnsPchMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsPchMaster,
     * or with status 400 (Bad Request) if the rnsPchMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsPchMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-pch-masters")
    @Timed
    public ResponseEntity<RnsPchMaster> updateRnsPchMaster(@RequestBody RnsPchMaster rnsPchMaster) throws URISyntaxException {
        log.debug("REST request to update RnsPchMaster : {}", rnsPchMaster);
        if (rnsPchMaster.getId() == null) {
            return createRnsPchMaster(rnsPchMaster);
        }
         User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsPchMaster.setUpdatedUser(user);
        rnsPchMaster.setLastUpdatedDate(Instant.now());

        RnsPchMaster result = rnsPchMasterRepository.save(rnsPchMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsPchMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-pch-masters : get all the rnsPchMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsPchMasters in body
     */
    @GetMapping("/rns-pch-masters")
    @Timed
    public List<RnsPchMaster> getAllRnsPchMasters() {
        log.debug("REST request to get all RnsPchMasters");
        return rnsPchMasterRepository.findAll();
        }

    /**
     * GET  /rns-pch-masters/:id : get the "id" rnsPchMaster.
     *
     * @param id the id of the rnsPchMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsPchMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-pch-masters/{id}")
    @Timed
    public ResponseEntity<RnsPchMaster> getRnsPchMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsPchMaster : {}", id);
        Optional<RnsPchMaster> rnsPchMaster = rnsPchMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsPchMaster);
    }

    /**
     * DELETE  /rns-pch-masters/:id : delete the "id" rnsPchMaster.
     *
     * @param id the id of the rnsPchMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-pch-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsPchMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsPchMaster : {}", id);
        rnsPchMasterRepository.deleteById(id);
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
