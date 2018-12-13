package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsTaxTermsMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsTaxTermsMasterRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsTaxTermsMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsTaxTermsMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsTaxTermsMasterResource.class);

    private static final String ENTITY_NAME = "Tax Terms Master";

    private final RnsTaxTermsMasterRepository rnsTaxTermsMasterRepository;

    private final UserRepository userRepository;

    public RnsTaxTermsMasterResource(RnsTaxTermsMasterRepository rnsTaxTermsMasterRepository,UserRepository userRepository) {
        this.rnsTaxTermsMasterRepository = rnsTaxTermsMasterRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-tax-terms-masters : Create a new rnsTaxTermsMaster.
     *
     * @param rnsTaxTermsMaster the rnsTaxTermsMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsTaxTermsMaster, or with status 400 (Bad Request) if the rnsTaxTermsMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-tax-terms-masters")
    @Timed
    public ResponseEntity<RnsTaxTermsMaster> createRnsTaxTermsMaster(@Valid @RequestBody RnsTaxTermsMaster rnsTaxTermsMaster) throws URISyntaxException {
        log.debug("REST request to save RnsTaxTermsMaster : {}", rnsTaxTermsMaster);
        if (rnsTaxTermsMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsTaxTermsMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsTaxTermsMaster.setUser(user);
        rnsTaxTermsMaster.setCreatedDate(Instant.now());
        RnsTaxTermsMaster taxTermsMaster = rnsTaxTermsMasterRepository.findByTaxTermsCode(rnsTaxTermsMaster.getTaxTermsCode());
        RnsTaxTermsMaster result = null;
        if(taxTermsMaster!=null){
            result = taxTermsMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Tax Terms Code already exist"))
                .body(result);
        } else{
            result = rnsTaxTermsMasterRepository.save(rnsTaxTermsMaster);
        }

        return ResponseEntity.created(new URI("/api/rns-tax-terms-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-tax-terms-masters : Updates an existing rnsTaxTermsMaster.
     *
     * @param rnsTaxTermsMaster the rnsTaxTermsMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsTaxTermsMaster,
     * or with status 400 (Bad Request) if the rnsTaxTermsMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsTaxTermsMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-tax-terms-masters")
    @Timed
    public ResponseEntity<RnsTaxTermsMaster> updateRnsTaxTermsMaster(@Valid @RequestBody RnsTaxTermsMaster rnsTaxTermsMaster) throws URISyntaxException {
        log.debug("REST request to update RnsTaxTermsMaster : {}", rnsTaxTermsMaster);
        if (rnsTaxTermsMaster.getId() == null) {
            return createRnsTaxTermsMaster(rnsTaxTermsMaster);
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsTaxTermsMaster.setUpdatedUser(user);
        rnsTaxTermsMaster.setLastUpdatedDate(Instant.now());
        RnsTaxTermsMaster result = rnsTaxTermsMasterRepository.save(rnsTaxTermsMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsTaxTermsMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-tax-terms-masters : get all the rnsTaxTermsMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsTaxTermsMasters in body
     */
    @GetMapping("/rns-tax-terms-masters")
    @Timed
    public List<RnsTaxTermsMaster> getAllRnsTaxTermsMasters() {
        log.debug("REST request to get all RnsTaxTermsMasters");
        return rnsTaxTermsMasterRepository.findAll();
        }

    /**
     * GET  /rns-tax-terms-masters/:id : get the "id" rnsTaxTermsMaster.
     *
     * @param id the id of the rnsTaxTermsMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsTaxTermsMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-tax-terms-masters/{id}")
    @Timed
    public ResponseEntity<RnsTaxTermsMaster> getRnsTaxTermsMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsTaxTermsMaster : {}", id);
        Optional<RnsTaxTermsMaster> rnsTaxTermsMaster = rnsTaxTermsMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsTaxTermsMaster);
    }

    /**
     * DELETE  /rns-tax-terms-masters/:id : delete the "id" rnsTaxTermsMaster.
     *
     * @param id the id of the rnsTaxTermsMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-tax-terms-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsTaxTermsMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsTaxTermsMaster : {}", id);
        rnsTaxTermsMasterRepository.deleteById(id);
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
