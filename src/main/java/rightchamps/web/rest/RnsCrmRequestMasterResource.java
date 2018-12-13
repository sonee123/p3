package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsCrmRequestMaster;

import rightchamps.repository.RnsCrmRequestMasterRepository;
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
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import java.util.ArrayList;
import rightchamps.domain.RnsCatgMaster;
import rightchamps.repository.RnsCatgMasterRepository;
import rightchamps.domain.RnsPchMaster;
import rightchamps.domain.RnsRelation;
import rightchamps.domain.User;
import rightchamps.repository.RnsPchMasterRepository;
import rightchamps.repository.RnsRelationRepository;
import rightchamps.repository.UserRepository;

/**
 * REST controller for managing RnsCrmRequestMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsCrmRequestMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsCrmRequestMasterResource.class);

    private static final String ENTITY_NAME = "CRM Request Master";

    private final RnsCrmRequestMasterRepository rnsCrmRequestMasterRepository;
    @Inject
    private RnsCatgMasterRepository rnsCatgMasterRepository;

    @Inject
    private RnsPchMasterRepository rnsPchMasterRepository;

    @Inject
    private UserRepository userRepository;

    public RnsCrmRequestMasterResource(RnsCrmRequestMasterRepository rnsCrmRequestMasterRepository) {
        this.rnsCrmRequestMasterRepository = rnsCrmRequestMasterRepository;
    }

    /**
     * POST  /rns-crm-request-masters : Create a new rnsCrmRequestMaster.
     *
     * @param rnsCrmRequestMaster the rnsCrmRequestMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsCrmRequestMaster, or with status 400 (Bad Request) if the rnsCrmRequestMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-crm-request-masters")
    @Timed
    public ResponseEntity<RnsCrmRequestMaster> createRnsCrmRequestMaster(@RequestBody RnsCrmRequestMaster rnsCrmRequestMaster) throws URISyntaxException {
        log.debug("REST request to save RnsCrmRequestMaster : {}", rnsCrmRequestMaster);
        if (rnsCrmRequestMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsCrmRequestMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String userName = getCurrentUserLogin();
        User user = userRepository.findByLogin(userName);
        rnsCrmRequestMaster.setUser(user);
        rnsCrmRequestMaster.setCreatedDate(Instant.now());
        RnsCrmRequestMaster result;
        RnsCrmRequestMaster crmRequestMaster = rnsCrmRequestMasterRepository.findByCrmCode(rnsCrmRequestMaster.getCrmCode().toUpperCase());
        if (crmRequestMaster != null) {
            result = crmRequestMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(), "CRM Code already exist"))
                .body(result);
        } else {
            rnsCrmRequestMaster.setCrmCode(rnsCrmRequestMaster.getCrmCode().toUpperCase());
            result = rnsCrmRequestMasterRepository.save(rnsCrmRequestMaster);
        }

        return ResponseEntity.created(new URI("/api/rns-crm-request-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-crm-request-masters : Updates an existing rnsCrmRequestMaster.
     *
     * @param rnsCrmRequestMaster the rnsCrmRequestMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsCrmRequestMaster,
     * or with status 400 (Bad Request) if the rnsCrmRequestMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsCrmRequestMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-crm-request-masters")
    @Timed
    public ResponseEntity<RnsCrmRequestMaster> updateRnsCrmRequestMaster(@RequestBody RnsCrmRequestMaster rnsCrmRequestMaster) throws URISyntaxException {
        log.debug("REST request to update RnsCrmRequestMaster : {}", rnsCrmRequestMaster);
        if (rnsCrmRequestMaster.getId() == null) {
            return createRnsCrmRequestMaster(rnsCrmRequestMaster);
        }
        String userName = getCurrentUserLogin();
        User user = userRepository.findByLogin(userName);

        rnsCrmRequestMaster.setUpdateUser(user);
        rnsCrmRequestMaster.setLastUpdatedDate(Instant.now());

        RnsCrmRequestMaster result = rnsCrmRequestMasterRepository.save(rnsCrmRequestMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsCrmRequestMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-crm-request-masters : get all the rnsCrmRequestMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsCrmRequestMasters in body
     */
    @GetMapping("/rns-crm-request-masters")
    @Timed
    public List<RnsCrmRequestMaster> getAllRnsCrmRequestMasters() {
        log.debug("REST request to get all RnsCrmRequestMasters");

        List<RnsCrmRequestMaster> rnscrmList = new ArrayList<RnsCrmRequestMaster>();
        List<RnsCrmRequestMaster> crmList = rnsCrmRequestMasterRepository.findAll();
        //List<RnsPchMaster> rnsPchMasterList = getAllPchCode();

        /*for (RnsCrmRequestMaster crmData : crmList) {
            log.debug("------===---");
            Long crmid = crmData.getId();
            log.debug("crmid....", crmid);
            for (RnsPchMaster rnsPchMaster : rnsPchMasterList) {
                if (crmData.getRnsPchMaster() != null && rnsPchMaster.getPchCode() != null && rnsPchMaster.getPchCode().equals(crmData.getRnsPchMaster().getPchCode())) {
                    rnscrmList.add(crmData);
                }
            }
        }*/
        return crmList;
    }

    public List<RnsCatgMaster> getAllRnsCatgMasters() {
        log.debug("REST request to get all RnsCatgMasters");
        List<RnsCatgMaster> rnsCatgMasterList = rnsCatgMasterRepository.findAllWithEagerRelationshipsList(getCurrentUserLogin());
        return rnsCatgMasterList;
    }
    /*public List<RnsPchMaster> getAllPchCode() {
        log.debug("REST request to get all PchCode");
        List<RnsPchMaster> rnsPchMasterFetchData =  rnsPchMasterRepository.findAll();

         return rnsPchMasterFetchData;
    }*/
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
     * GET  /rns-crm-request-masters/:id : get the "id" rnsCrmRequestMaster.
     *
     * @param id the id of the rnsCrmRequestMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsCrmRequestMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-crm-request-masters/{id}")
    @Timed
    public ResponseEntity<RnsCrmRequestMaster> getRnsCrmRequestMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsCrmRequestMaster : {}", id);
        Optional<RnsCrmRequestMaster> rnsCrmRequestMaster = rnsCrmRequestMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsCrmRequestMaster);
    }

    /**
     * DELETE  /rns-crm-request-masters/:id : delete the "id" rnsCrmRequestMaster.
     *
     * @param id the id of the rnsCrmRequestMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-crm-request-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsCrmRequestMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsCrmRequestMaster : {}", id);
        rnsCrmRequestMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
