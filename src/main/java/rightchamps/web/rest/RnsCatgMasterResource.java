package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import rightchamps.modal.RnsCategoryUserBean;
import rightchamps.modal.RnsCatgMasterBean;
import rightchamps.repository.RnsCatgMasterRepository;
import rightchamps.repository.RnsCatgMasterUserRepository;
import rightchamps.repository.RnsRelationRepository;
import rightchamps.repository.UserRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing RnsCatgMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsCatgMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsCatgMasterResource.class);

    private static final String ENTITY_NAME = "Category Master";

    private final RnsCatgMasterRepository rnsCatgMasterRepository;

    private final UserRepository userRepository;

    @Inject
    private RnsCatgMasterUserRepository rnsCatgMasterUserRepository;

    @Inject
    private RnsRelationRepository rnsRelationRepository;

    public RnsCatgMasterResource(RnsCatgMasterRepository rnsCatgMasterRepository,UserRepository userRepository) {
        this.rnsCatgMasterRepository = rnsCatgMasterRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /rns-catg-masters : Create a new rnsCatgMaster.
     *
     * @param rnsCatgMaster the rnsCatgMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsCatgMaster, or with status 400 (Bad Request) if the rnsCatgMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-catg-masters")
    @Timed
    public ResponseEntity<RnsCatgMaster> createRnsCatgMaster(@RequestBody RnsCatgMaster rnsCatgMaster) throws URISyntaxException {
        log.debug("REST request to save RnsCatgMaster : {}", rnsCatgMaster);
        if (rnsCatgMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsCatgMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsCatgMaster.setUser(user);
        rnsCatgMaster.setCreatedDate(Instant.now());

        RnsCatgMaster catgMaster = rnsCatgMasterRepository.findBycatgCode(rnsCatgMaster.getCatgCode().toUpperCase());
        RnsCatgMaster result = null;
        if(catgMaster!=null){
            result = catgMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Category Code already exist"))
                .body(result);
        } else{
            rnsCatgMaster.setCatgCode(rnsCatgMaster.getCatgCode().toUpperCase());
            result = rnsCatgMasterRepository.save(rnsCatgMaster);
        }
        return ResponseEntity.created(new URI("/api/rns-catg-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-catg-masters : Updates an existing rnsCatgMaster.
     *
     * @param rnsCatgMaster the rnsCatgMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsCatgMaster,
     * or with status 400 (Bad Request) if the rnsCatgMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsCatgMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-catg-masters")
    @Timed
    public ResponseEntity<RnsCatgMaster> updateRnsCatgMaster(@RequestBody RnsCatgMaster rnsCatgMaster) throws URISyntaxException {
        log.debug("REST request to update RnsCatgMaster : {}", rnsCatgMaster);
        if (rnsCatgMaster.getId() == null) {
            return createRnsCatgMaster(rnsCatgMaster);
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsCatgMaster.setUpdatedUser(user);
        rnsCatgMaster.setLastUpdatedDate(Instant.now());
        RnsCatgMaster result = rnsCatgMasterRepository.save(rnsCatgMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsCatgMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-catg-masters : get all the rnsCatgMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsCatgMasters in body
     */
    @GetMapping("/rns-catg-masters-login")
    @Timed
    public List<RnsCatgMaster> getAllRnsCatgMastersLogin() {
        log.debug("REST request to get all RnsCatgMasters");
        List<RnsCatgMaster> rnsCatgMasterList = rnsCatgMasterRepository.findAllWithEagerRelationshipsList(getCurrentUserLogin());
        return rnsCatgMasterList;
    }

    /**
     * GET  /rns-catg-masters : get all the rnsCatgMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsCatgMasters in body
     */
    @GetMapping("/rns-catg-masters")
    @Timed
    public List<RnsCatgMaster> getAllRnsCatgMasters() {
        log.debug("REST request to get all RnsCatgMasters");
        return rnsCatgMasterRepository.findAll();
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

    /**
     * GET  /rns-catg-masters/:id : get the "id" rnsCatgMaster.
     *
     * @param id the id of the rnsCatgMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsCatgMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-catg-masters/{id}")
    @Timed
    public ResponseEntity<RnsCatgMaster> getRnsCatgMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsCatgMaster : {}", id);
        RnsCatgMaster rnsCatgMaster = rnsCatgMasterRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsCatgMaster));
    }

    /**
     * DELETE  /rns-catg-masters/:id : delete the "id" rnsCatgMaster.
     *
     * @param id the id of the rnsCatgMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-catg-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsCatgMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsCatgMaster : {}", id);
        rnsCatgMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * Post  /rns-catg-masters/:id : delete the "id" rnsCatgMaster.
     *
     * @param @id the id of the rnsCatgMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/rns-catg-masters-users")
    @Timed
    public ResponseEntity<RnsCategoryUserBean> getRnsCatgMasterUser(@RequestBody RnsCategoryUserBean rnsCategoryUserBean) {
        log.debug("REST request to rnsCatgMasterUser : {}", rnsCategoryUserBean);
        User user = userRepository.findByLogin(rnsCategoryUserBean.getLogin());
        RnsRelation rnsRelation = rnsRelationRepository.findByUserId(user.getId());
        rnsCategoryUserBean.setUserId(rnsRelation.getId());
        List<RnsCatgMaster> rnsCatgMasters = rnsCatgMasterRepository.findAll();
        List<RnsCatgMaster> rnsCatgMastersSelected = rnsCatgMasterRepository.findAllWithEagerRelationshipsList(rnsCategoryUserBean.getLogin());

        List<String> selectedIds = new ArrayList<String>();
        List<RnsCatgMasterBean> rnsCatgMastersTempSelected = new ArrayList<RnsCatgMasterBean>();
        for (RnsCatgMaster rnsCatgMaster : rnsCatgMastersSelected) {
            selectedIds.add(rnsCatgMaster.getId().toString());
            rnsCatgMastersTempSelected.add(new RnsCatgMasterBean(rnsCatgMaster.getId(), rnsCatgMaster.getCatgCode() + " - " + rnsCatgMaster.getCatgCodeDesc(), rnsCatgMaster.getId().toString()));
        }
        rnsCategoryUserBean.setRnsCatgMasterSelectedList(rnsCatgMastersTempSelected);

        List<RnsCatgMasterBean> rnsCatgMastersTemp = new ArrayList<RnsCatgMasterBean>();
        for(RnsCatgMaster rnsCatgMaster : rnsCatgMasters){
            if(selectedIds.contains(rnsCatgMaster.getId().toString())){} else {
                rnsCatgMastersTemp.add(new RnsCatgMasterBean(rnsCatgMaster.getId(), rnsCatgMaster.getCatgCode() + " - " + rnsCatgMaster.getCatgCodeDesc(), rnsCatgMaster.getId().toString()));
            }
        }
        rnsCategoryUserBean.setRnsCatgMasterList(rnsCatgMastersTemp);
        return ResponseEntity.ok()
            .body(rnsCategoryUserBean);
    }

    @PostMapping("/rns-catg-masters-users-save")
    @Timed
    public ResponseEntity<RnsCategoryUserBean> saveRnsCatgMasterUser(@RequestBody RnsCategoryUserBean rnsCategoryUserBean) {
        if (rnsCategoryUserBean != null && rnsCategoryUserBean.getUserId() != null) {
            rnsCatgMasterUserRepository.deleteByRnsCatgMasterUserIdentityUsersId(rnsCategoryUserBean.getUserId());
            for (RnsCatgMasterBean rnsCatgMasterBean : rnsCategoryUserBean.getRnsCatgMasterSelectedList()) {
                RnsCatgMasterUser rnsCatgMasterUser = new RnsCatgMasterUser();
                RnsCatgMasterUserIdentity rnsCatgMasterUserIdentity = new RnsCatgMasterUserIdentity();
                rnsCatgMasterUserIdentity.setUsersId(rnsCategoryUserBean.getUserId());
                rnsCatgMasterUserIdentity.setRnsCatgMastersId(rnsCatgMasterBean.getId());
                rnsCatgMasterUser.setRnsCatgMasterUserIdentity(rnsCatgMasterUserIdentity);
                rnsCatgMasterUserRepository.save(rnsCatgMasterUser);
            }
        }
        return getRnsCatgMasterUser(rnsCategoryUserBean);
    }
}
