package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsUomMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsUomMasterRepository;
import rightchamps.repository.UserRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

/**
 * REST controller for managing RnsUomMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsUomMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsUomMasterResource.class);

    private static final String ENTITY_NAME = "UOM Master";

    private final RnsUomMasterRepository rnsUomMasterRepository;

    @Inject
    private UserRepository userRepository;

    public RnsUomMasterResource(RnsUomMasterRepository rnsUomMasterRepository) {
        this.rnsUomMasterRepository = rnsUomMasterRepository;
    }

    /**
     * POST  /rns-uom-masters : Create a new rnsUomMaster.
     *
     * @param rnsUomMaster the rnsUomMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsUomMaster, or with status 400 (Bad Request) if the rnsUomMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-uom-masters")
    @Timed
    public ResponseEntity<RnsUomMaster> createRnsUomMaster(@RequestBody RnsUomMaster rnsUomMaster) throws URISyntaxException {
        log.debug("REST request to save RnsUomMaster : {}", rnsUomMaster);
        if (rnsUomMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsUomMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

        RnsUomMaster uomMaster = rnsUomMasterRepository.findByUomCode(rnsUomMaster.getUomCode().toUpperCase());
        RnsUomMaster result = null;
        if (uomMaster != null) {
            result = uomMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(), "UOM code already exist"))
                .body(result);
        } else {
            rnsUomMaster.setUomCode(rnsUomMaster.getUomCode().toUpperCase());
            User user = userRepository.findByLogin(getCurrentUserLogin());
            rnsUomMaster.setUser(user);
            rnsUomMaster.setCreatedDate(Instant.now());

            result = rnsUomMasterRepository.save(rnsUomMaster);
        }


        return ResponseEntity.created(new URI("/api/rns-uom-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    /**
     * PUT  /rns-uom-masters : Updates an existing rnsUomMaster.
     *
     * @param rnsUomMaster the rnsUomMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsUomMaster,
     * or with status 400 (Bad Request) if the rnsUomMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsUomMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-uom-masters")
    @Timed
    public ResponseEntity<RnsUomMaster> updateRnsUomMaster(@RequestBody RnsUomMaster rnsUomMaster) throws URISyntaxException {
        log.debug("REST request to update RnsUomMaster : {}", rnsUomMaster);
        if (rnsUomMaster.getId() == null) {
            return createRnsUomMaster(rnsUomMaster);
        }

        String userName = getCurrentUserLogin();
        User user = userRepository.findByLogin(userName);

        rnsUomMaster.setUpdateUser(user);
        rnsUomMaster.setLastUpdatedDate(Instant.now());

        RnsUomMaster result = rnsUomMasterRepository.save(rnsUomMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsUomMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-uom-masters : get all the rnsUomMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsUomMasters in body
     */
    @GetMapping("/rns-uom-masters")
    @Timed
    public List<RnsUomMaster> getAllRnsUomMasters() {
        log.debug("REST request to get all RnsUomMasters");
        return rnsUomMasterRepository.findAll();
        }

    private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }
    /**
     * GET  /rns-uom-masters/:id : get the "id" rnsUomMaster.
     *
     * @param id the id of the rnsUomMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsUomMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-uom-masters/{id}")
    @Timed
    public ResponseEntity<RnsUomMaster> getRnsUomMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsUomMaster : {}", id);
        Optional<RnsUomMaster> rnsUomMaster = rnsUomMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsUomMaster);
    }

    /**
     * DELETE  /rns-uom-masters/:id : delete the "id" rnsUomMaster.
     *
     * @param id the id of the rnsUomMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-uom-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsUomMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsUomMaster : {}", id);
        rnsUomMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
