package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;

import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.RnsPchMaster;
import rightchamps.domain.RnsQuotation;
import rightchamps.domain.RnsRelation;
import rightchamps.domain.RnsTypeMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsCatgMasterRepository;
import rightchamps.repository.RnsPchMasterRepository;
import rightchamps.repository.RnsTypeMasterRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

/**
 * REST controller for managing RnsTypeMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsTypeMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsTypeMasterResource.class);

    private static final String ENTITY_NAME = "Project Region Master";

    private final RnsTypeMasterRepository rnsTypeMasterRepository;
    @Inject
    private RnsCatgMasterRepository rnsCatgMasterRepository;

    @Inject
    private RnsPchMasterRepository rnsPchMasterRepository;
     @Inject
    private final UserRepository userRepository;

    public RnsTypeMasterResource(RnsTypeMasterRepository rnsTypeMasterRepository,UserRepository userRepository) {
        this.rnsTypeMasterRepository = rnsTypeMasterRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-type-masters : Create a new rnsTypeMaster.
     *
     * @param rnsTypeMaster the rnsTypeMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsTypeMaster, or with status 400 (Bad Request) if the rnsTypeMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-type-masters")
    @Timed
    public ResponseEntity<RnsTypeMaster> createRnsTypeMaster(@RequestBody RnsTypeMaster rnsTypeMaster) throws URISyntaxException {
        log.debug("REST request to save RnsTypeMaster : {}", rnsTypeMaster);
        if (rnsTypeMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsTypeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsTypeMaster.setUser(user);
        rnsTypeMaster.setCreatedDate(Instant.now());
        List<RnsTypeMaster> typeMaster = rnsTypeMasterRepository.findByTypeCode(rnsTypeMaster.getTypeCode().toUpperCase());
        RnsTypeMaster result = null;
        if(typeMaster!=null && typeMaster.size()>0){
            result = typeMaster.get(0);
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Project Region Code already exist"))
                .body(result);
        } else{
            rnsTypeMaster.setTypeCode(rnsTypeMaster.getTypeCode().toUpperCase());
            result = rnsTypeMasterRepository.save(rnsTypeMaster);
            return ResponseEntity.created(new URI("/api/rns-type-masters/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
    }

    /**
     * PUT  /rns-type-masters : Updates an existing rnsTypeMaster.
     *
     * @param rnsTypeMaster the rnsTypeMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsTypeMaster,
     * or with status 400 (Bad Request) if the rnsTypeMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsTypeMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-type-masters")
    @Timed
    public ResponseEntity<RnsTypeMaster> updateRnsTypeMaster(@RequestBody RnsTypeMaster rnsTypeMaster) throws URISyntaxException {
        log.debug("REST request to update RnsTypeMaster : {}", rnsTypeMaster);
        if (rnsTypeMaster.getId() == null) {
            return createRnsTypeMaster(rnsTypeMaster);
        }
         User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsTypeMaster.setUpdatedUser(user);
        rnsTypeMaster.setLastUpdatedDate(Instant.now());
        RnsTypeMaster result = rnsTypeMasterRepository.save(rnsTypeMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsTypeMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-type-masters : get all the rnsTypeMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsTypeMasters in body
     */
    @GetMapping("/rns-type-masters")
    @Timed
    public List<RnsTypeMaster> getAllRnsTypeMasters() {
        log.debug("REST request to get all RnsTypeMasters");
        // return rnsTypeMasterRepository.findAll();
        List<RnsTypeMaster> rnsQuotationList = new ArrayList<RnsTypeMaster>();
        List<RnsTypeMaster> quotationList = rnsTypeMasterRepository.findAllBySort();
        List<Long> catgList= getAllRnsCatgMasters();
        for (RnsTypeMaster rnsTypeMasterData : quotationList) {
            log.debug("------===---");
            if (catgList!=null && catgList.contains(rnsTypeMasterData.getRnsCatgCode().getId())) {
                rnsQuotationList.add(rnsTypeMasterData);
            }

        }
        return rnsQuotationList;
    }

    /**
     * GET  /rns-type-masters : get all the rnsTypeMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsTypeMasters in body
     */
    @GetMapping("/rns-type-masters-by-catg/{id}")
    @Timed
    public List<RnsTypeMaster> getAllRnsTypeMasters(@PathVariable Long id) {
        log.debug("REST request to get all RnsTypeMasters");
        return rnsTypeMasterRepository.findAllByRnsCatgCode(id);
    }

    public List<Long> getAllRnsCatgMasters() {
        log.debug("REST request to get all RnsCatgMasters");
        return rnsCatgMasterRepository.findAllWithEagerRelationships(getCurrentUserLogin());
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
     * GET  /rns-type-masters/:id : get the "id" rnsTypeMaster.
     *
     * @param id the id of the rnsTypeMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsTypeMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-type-masters/{id}")
    @Timed
    public ResponseEntity<RnsTypeMaster> getRnsTypeMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsTypeMaster : {}", id);
        Optional<RnsTypeMaster> rnsTypeMaster = rnsTypeMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsTypeMaster);
    }

    /**
     * DELETE  /rns-type-masters/:id : delete the "id" rnsTypeMaster.
     *
     * @param id the id of the rnsTypeMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-type-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsTypeMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsTypeMaster : {}", id);
        rnsTypeMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
