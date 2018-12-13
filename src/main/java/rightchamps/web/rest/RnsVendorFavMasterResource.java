package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.RnsVendorFavMaster;

import rightchamps.repository.RnsVendorFavMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsVendorFavMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsVendorFavMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsVendorFavMasterResource.class);

    private static final String ENTITY_NAME = "Vendor Fav Master";

    private final RnsVendorFavMasterRepository rnsVendorFavMasterRepository;

    public RnsVendorFavMasterResource(RnsVendorFavMasterRepository rnsVendorFavMasterRepository) {
        this.rnsVendorFavMasterRepository = rnsVendorFavMasterRepository;
    }

    /**
     * POST  /rns-vendor-fav-masters : Create a new rnsVendorFavMaster.
     *
     * @param rnsVendorFavMaster the rnsVendorFavMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsVendorFavMaster, or with status 400 (Bad Request) if the rnsVendorFavMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-vendor-fav-masters")
    @Timed
    public ResponseEntity<RnsVendorFavMaster> createRnsVendorFavMaster(@Valid @RequestBody RnsVendorFavMaster rnsVendorFavMaster) throws URISyntaxException {
        log.debug("REST request to save RnsVendorFavMaster : {}", rnsVendorFavMaster);
        if (rnsVendorFavMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsVendorFavMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(rnsVendorFavMaster.getCreatedBy()==null){
            rnsVendorFavMaster.setCreatedBy(getCurrentUserLogin());
        }
        rnsVendorFavMaster.createdDate(LocalDate.now());
        RnsVendorFavMaster result = rnsVendorFavMasterRepository.save(rnsVendorFavMaster);
        return ResponseEntity.created(new URI("/api/rns-vendor-fav-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-vendor-fav-masters : Updates an existing rnsVendorFavMaster.
     *
     * @param rnsVendorFavMaster the rnsVendorFavMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsVendorFavMaster,
     * or with status 400 (Bad Request) if the rnsVendorFavMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsVendorFavMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-vendor-fav-masters")
    @Timed
    public ResponseEntity<RnsVendorFavMaster> updateRnsVendorFavMaster(@Valid @RequestBody RnsVendorFavMaster rnsVendorFavMaster) throws URISyntaxException {
        log.debug("REST request to update RnsVendorFavMaster : {}", rnsVendorFavMaster);
        if (rnsVendorFavMaster.getId() == null) {
            return createRnsVendorFavMaster(rnsVendorFavMaster);
        }
        RnsVendorFavMaster result = rnsVendorFavMasterRepository.save(rnsVendorFavMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsVendorFavMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-vendor-fav-masters : get all the rnsVendorFavMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsVendorFavMasters in body
     */
    @GetMapping("/rns-vendor-fav-masters")
    @Timed
    public List<RnsVendorFavMaster> getAllRnsVendorFavMasters() {
        log.debug("REST request to get all RnsVendorFavMasters");
        return rnsVendorFavMasterRepository.findAll();
        }

    /**
     * GET  /rns-vendor-fav-masters/:id : get the "id" rnsVendorFavMaster.
     *
     * @param id the id of the rnsVendorFavMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsVendorFavMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-vendor-fav-masters/{id}")
    @Timed
    public ResponseEntity<RnsVendorFavMaster> getRnsVendorFavMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsVendorFavMaster : {}", id);
        Optional<RnsVendorFavMaster> rnsVendorFavMaster = rnsVendorFavMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsVendorFavMaster);
    }

    /**
     * DELETE  /rns-vendor-fav-masters/:id : delete the "id" rnsVendorFavMaster.
     *
     * @param id the id of the rnsVendorFavMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-vendor-fav-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsVendorFavMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsVendorFavMaster : {}", id);
        rnsVendorFavMasterRepository.deleteById(id);
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
