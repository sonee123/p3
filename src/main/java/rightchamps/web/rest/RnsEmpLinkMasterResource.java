package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import rightchamps.domain.RnsEmpLinkMaster;

import rightchamps.domain.RnsForwardTypeMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsEmpLinkMasterRepository;
import rightchamps.repository.RnsForwardTypeMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import rightchamps.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import rightchamps.repository.UserRepository;
/**
 * REST controller for managing RnsEmpLinkMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsEmpLinkMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsEmpLinkMasterResource.class);

    private static final String ENTITY_NAME = "Employee Link Master";

    private final RnsEmpLinkMasterRepository rnsEmpLinkMasterRepository;
    @Inject
    private RnsForwardTypeMasterRepository rnsForwardTypeMasterRepository;
    private final UserRepository userRepository;


    public RnsEmpLinkMasterResource(RnsEmpLinkMasterRepository rnsEmpLinkMasterRepository,UserRepository userRepository) {
        this.rnsEmpLinkMasterRepository = rnsEmpLinkMasterRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-emp-link-masters : Create a new rnsEmpLinkMaster.
     *
     * @param rnsEmpLinkMaster the rnsEmpLinkMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsEmpLinkMaster, or with status 400 (Bad Request) if the rnsEmpLinkMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-emp-link-masters")
    @Timed
    public ResponseEntity<RnsEmpLinkMaster> createRnsEmpLinkMaster(@Valid @RequestBody RnsEmpLinkMaster rnsEmpLinkMaster) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        log.debug("REST request to save RnsEmpLinkMaster : {}", rnsEmpLinkMaster);
        if (rnsEmpLinkMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsEmpLinkMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsEmpLinkMaster result = null;
            RnsEmpLinkMaster empLinkMaster = rnsEmpLinkMasterRepository.getRnsEmpLinkMastersByEmpCodeWithFlag(rnsEmpLinkMaster.getEmpCode().getLogin(), rnsEmpLinkMaster.getForwardEmpType(), rnsEmpLinkMaster.getForwardEmpCode().getLogin());
        if (empLinkMaster != null) {
            result = empLinkMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Entry already exist"))
                .body(result);
        } else {
            User user = userRepository.findByLogin(getCurrentUserLogin());
            rnsEmpLinkMaster.setUser(user);
            rnsEmpLinkMaster.setCreatedDate(Instant.now());
            result = rnsEmpLinkMasterRepository.saveAndFlush(rnsEmpLinkMaster);
            if (result.getForwardEmpType() !=null && result.getForwardEmpType().equalsIgnoreCase("F")) {
                RnsEmpLinkMaster empLinkMasterReverse = new RnsEmpLinkMaster();
                BeanUtils.copyProperties(empLinkMasterReverse, rnsEmpLinkMaster);
                User user1 = empLinkMasterReverse.getEmpCode();
                empLinkMasterReverse.setEmpCode(empLinkMasterReverse.getForwardEmpCode());
                empLinkMasterReverse.setForwardEmpCode(user1);
                empLinkMasterReverse.setId(null);
                RnsEmpLinkMaster empLinkMaster1 = rnsEmpLinkMasterRepository.getRnsEmpLinkMastersByEmpCodeWithFlag(empLinkMasterReverse.getEmpCode().getLogin(), empLinkMasterReverse.getForwardEmpType(), empLinkMasterReverse.getForwardEmpCode().getLogin());
                if(empLinkMaster1 !=null ){} else {
                    rnsEmpLinkMasterRepository.saveAndFlush(empLinkMasterReverse);
                }
            }
            return ResponseEntity.created(new URI("/api/rns-emp-link-masters/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
    }

    /**
     * PUT  /rns-emp-link-masters : Updates an existing rnsEmpLinkMaster.
     *
     * @param rnsEmpLinkMaster the rnsEmpLinkMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsEmpLinkMaster,
     * or with status 400 (Bad Request) if the rnsEmpLinkMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsEmpLinkMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-emp-link-masters")
    @Timed
    public ResponseEntity<RnsEmpLinkMaster> updateRnsEmpLinkMaster(@Valid @RequestBody RnsEmpLinkMaster rnsEmpLinkMaster) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        log.debug("REST request to update RnsEmpLinkMaster : {}", rnsEmpLinkMaster);
        if (rnsEmpLinkMaster.getId() == null) {
            return createRnsEmpLinkMaster(rnsEmpLinkMaster);
        }

        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsEmpLinkMaster.setUpdatedUser(user);
        rnsEmpLinkMaster.setLastUpdatedDate(Instant.now());
        RnsEmpLinkMaster result = rnsEmpLinkMasterRepository.save(rnsEmpLinkMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsEmpLinkMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-emp-link-masters : get all the rnsEmpLinkMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rnsEmpLinkMasters in body
     */
    @GetMapping("/rns-emp-link-masters")
    @Timed
    public List<RnsEmpLinkMaster> getAllRnsEmpLinkMasters(Pageable pageable) {
        log.debug("REST request to get a page of RnsEmpLinkMasters");
        return rnsEmpLinkMasterRepository.findAll();
    }

    /**
     * GET  /rns-emp-link-masters/:id : get the "id" rnsEmpLinkMaster.
     *
     * @param id the id of the rnsEmpLinkMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsEmpLinkMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-emp-link-masters/{id}")
    @Timed
    public ResponseEntity<RnsEmpLinkMaster> getRnsEmpLinkMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsEmpLinkMaster : {}", id);
        RnsEmpLinkMaster rnsEmpLinkMaster = rnsEmpLinkMasterRepository.findById(id).orElse(null);
        rnsEmpLinkMaster.setRnsForwardTypeMaster(rnsForwardTypeMasterRepository.findByCode(rnsEmpLinkMaster.getForwardEmpType()));
        return ResponseUtil.wrapOrNotFound(Optional.of(rnsEmpLinkMaster));
    }

    /**
     * DELETE  /rns-emp-link-masters/:id : delete the "id" rnsEmpLinkMaster.
     *
     * @param id the id of the rnsEmpLinkMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-emp-link-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsEmpLinkMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsEmpLinkMaster : {}", id);
        rnsEmpLinkMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /rns-emp-link-masters-fetch : get all the rnsEmpLinkMasters.
     *
     * @param forwardEmpType the forwardEmpType of the RnsEmpLinkMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of rnsEmpLinkMasters in body
     */
    @GetMapping("/rns-emp-link-masters-fetch/{forwardEmpType}")
    @Timed
    public ResponseEntity<List<RnsEmpLinkMaster>> getAllRnsEmpLinkMastersByEmpCode(@PathVariable String forwardEmpType) {
        log.debug("REST request to get a page of RnsEmpLinkMasters");
        return ResponseEntity.ok().body(rnsEmpLinkMasterRepository.getRnsEmpLinkMastersByEmpCodeWithFlag(getCurrentUserLogin(),forwardEmpType));
    }

    /**
     * GET  /rns-emp-forward-fetch : get all the rnsEmpLinkMasters.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsEmpLinkMasters in body
     */

    @GetMapping("/rns-emp-forward-fetch")
    @Timed
    public ResponseEntity<List<RnsForwardTypeMaster>> getDistinctByForwardEmpTypeByEmpCode() {
        List<RnsForwardTypeMaster> rnsForwardList = new ArrayList<RnsForwardTypeMaster>();
        log.debug("REST request to get a page of RnsForwardTypeMaster");
        List<Object[]> rnsForwards = rnsEmpLinkMasterRepository.getDistinctByForwardEmpTypeByEmpCode(getCurrentUserLogin());
        if(rnsForwards!=null && rnsForwards.size()>0){
            for (Object [] forward: rnsForwards) {
                RnsForwardTypeMaster bean = new RnsForwardTypeMaster();
                bean.setCode(forward[0].toString());
                bean.setDescription(forward[1].toString());
                rnsForwardList.add(bean);
                System.out.println("TEST"+forward[0].toString());
            }
        }
        return ResponseEntity.ok().body(rnsForwardList);
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
