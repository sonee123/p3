package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.RnsSourceTeamDtl;

import rightchamps.domain.RnsSourceTeamMaster;
import rightchamps.domain.User;
import rightchamps.modal.RnsSourceTeamDetail;
import rightchamps.repository.RnsSourceTeamDtlRepository;
import rightchamps.repository.RnsSourceTeamMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rightchamps.repository.UserRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing RnsSourceTeamDtl.
 */
@RestController
@RequestMapping("/api")
public class RnsSourceTeamDtlResource {

    private final Logger log = LoggerFactory.getLogger(RnsSourceTeamDtlResource.class);

    private static final String ENTITY_NAME = "Sourcing Team Details";

    private final RnsSourceTeamDtlRepository rnsSourceTeamDtlRepository;

     private final UserRepository userRepository;

    @Inject
    private RnsSourceTeamMasterRepository rnsSourceTeamMasterRepository;

    public RnsSourceTeamDtlResource(RnsSourceTeamDtlRepository rnsSourceTeamDtlRepository,UserRepository userRepository) {
        this.rnsSourceTeamDtlRepository = rnsSourceTeamDtlRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-source-team-dtls : Create a new rnsSourceTeamDtl.
     *
     * @param rnsSourceTeamDtl the rnsSourceTeamDtl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsSourceTeamDtl, or with status 400 (Bad Request) if the rnsSourceTeamDtl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-source-team-dtls")
    @Timed
    public ResponseEntity<RnsSourceTeamDtl> createRnsSourceTeamDtl(@Valid @RequestBody RnsSourceTeamDtl rnsSourceTeamDtl) throws URISyntaxException {
        log.debug("REST request to save RnsSourceTeamDtl : {}", rnsSourceTeamDtl);
        if (rnsSourceTeamDtl.getId() != null) {
            throw new BadRequestAlertException("A new rnsSourceTeamDtl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsSourceTeamDtl result = null;
        RnsSourceTeamDtl rnsSourceTeamDtl1 = rnsSourceTeamDtlRepository.findByUserIdAndTeam(rnsSourceTeamDtl.getTeamUser().getLogin(), rnsSourceTeamDtl.getMasterId());
        if(rnsSourceTeamDtl1 != null) {
            result = rnsSourceTeamDtl1;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Same entry already exist"))
                .body(result);
        } else {
            User user = userRepository.findByLogin(getCurrentUserLogin());
            rnsSourceTeamDtl.setUser(user);
            rnsSourceTeamDtl.setCreatedDate(Instant.now());

            result = rnsSourceTeamDtlRepository.save(rnsSourceTeamDtl);
        }
        return ResponseEntity.created(new URI("/api/rns-source-team-dtls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-source-team-dtls : Updates an existing rnsSourceTeamDtl.
     *
     * @param rnsSourceTeamDtl the rnsSourceTeamDtl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsSourceTeamDtl,
     * or with status 400 (Bad Request) if the rnsSourceTeamDtl is not valid,
     * or with status 500 (Internal Server Error) if the rnsSourceTeamDtl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-source-team-dtls")
    @Timed
    public ResponseEntity<RnsSourceTeamDtl> updateRnsSourceTeamDtl(@Valid @RequestBody RnsSourceTeamDtl rnsSourceTeamDtl) throws URISyntaxException {
        log.debug("REST request to update RnsSourceTeamDtl : {}", rnsSourceTeamDtl);
        if (rnsSourceTeamDtl.getId() == null) {
            return createRnsSourceTeamDtl(rnsSourceTeamDtl);
        }
         User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsSourceTeamDtl.setUpdatedUser(user);
        rnsSourceTeamDtl.setLastUpdatedDate(Instant.now());
        RnsSourceTeamDtl result = rnsSourceTeamDtlRepository.save(rnsSourceTeamDtl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsSourceTeamDtl.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-source-team-dtls : get all the rnsSourceTeamDtls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsSourceTeamDtls in body
     */
    @GetMapping("/rns-source-team-dtls")
    @Timed
    public List<RnsSourceTeamDetail> getAllRnsSourceTeamDtls() throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get all RnsSourceTeamDtls");
        List<RnsSourceTeamDetail> rnsSourceTeamDetails = new ArrayList<RnsSourceTeamDetail>();
        List<RnsSourceTeamDtl> listtemps = rnsSourceTeamDtlRepository.findAll();
        Map<Long,RnsSourceTeamMaster> maps = new HashMap<Long,RnsSourceTeamMaster>();
        for(RnsSourceTeamDtl rnsSourceTeamDtl : listtemps){
            RnsSourceTeamDetail detail = new RnsSourceTeamDetail();
            BeanUtils.copyProperties(detail,rnsSourceTeamDtl);
            if(maps.containsKey(detail.getMasterId())){
                detail.setMaster(maps.get(detail.getMasterId()));
            } else{
                RnsSourceTeamMaster master = rnsSourceTeamMasterRepository.findById(detail.getMasterId()).orElse(null);;
                detail.setMaster(master);
                maps.put(detail.getMasterId(),master);
            }
            rnsSourceTeamDetails.add(detail);
        }
        return rnsSourceTeamDetails;
        //return rnsSourceTeamDtlRepository.findAll();
    }

    /**
     * GET  /rns-source-team-dtls/:id : get the "id" rnsSourceTeamDtl.
     *
     * @param id the id of the rnsSourceTeamDtl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsSourceTeamDtl, or with status 404 (Not Found)
     */
    @GetMapping("/rns-source-team-dtls/{id}")
    @Timed
    public ResponseEntity<RnsSourceTeamDtl> getRnsSourceTeamDtl(@PathVariable Long id) {
        log.debug("REST request to get RnsSourceTeamDtl : {}", id);
        Optional<RnsSourceTeamDtl> rnsSourceTeamDtl = rnsSourceTeamDtlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsSourceTeamDtl);
    }

    /**
     * DELETE  /rns-source-team-dtls/:id : delete the "id" rnsSourceTeamDtl.
     *
     * @param id the id of the rnsSourceTeamDtl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-source-team-dtls/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsSourceTeamDtl(@PathVariable Long id) {
        log.debug("REST request to delete RnsSourceTeamDtl : {}", id);
        rnsSourceTeamDtlRepository.deleteById(id);
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
