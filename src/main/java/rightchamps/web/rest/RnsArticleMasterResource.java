package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsArticleMaster;
import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.RnsPchMaster;
import rightchamps.domain.RnsQuotation;
import rightchamps.domain.RnsRelation;
import rightchamps.domain.User;
import rightchamps.repository.RnsArticleMasterRepository;
import rightchamps.repository.RnsCatgMasterRepository;
import rightchamps.repository.UserRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

/**
 * REST controller for managing RnsArticleMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsArticleMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsArticleMasterResource.class);

    private static final String ENTITY_NAME = "Article Master";

    private final RnsArticleMasterRepository rnsArticleMasterRepository;
    private final UserRepository userRepository;

    @Inject
    private RnsCatgMasterRepository rnsCatgMasterRepository;

    public RnsArticleMasterResource(RnsArticleMasterRepository rnsArticleMasterRepository,UserRepository userRepository) {
        this.rnsArticleMasterRepository = rnsArticleMasterRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /rns-article-masters : Create a new rnsArticleMaster.
     *
     * @param rnsArticleMaster the rnsArticleMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsArticleMaster, or with status 400 (Bad Request) if the rnsArticleMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-article-masters")
    @Timed
    public ResponseEntity<RnsArticleMaster> createRnsArticleMaster(@RequestBody RnsArticleMaster rnsArticleMaster) throws URISyntaxException {
        log.debug("REST request to save RnsArticleMaster : {}", rnsArticleMaster);
        if (rnsArticleMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsArticleMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsArticleMaster.setUser(user);
        rnsArticleMaster.setCreatedDate(Instant.now());

        RnsArticleMaster articleMaster = rnsArticleMasterRepository.findByArticleCode(rnsArticleMaster.getArticleCode().toUpperCase());
        RnsArticleMaster result = null;
        if (articleMaster != null) {
            result = articleMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(), "Article Code already exist"))
                .body(result);
        } else {
            rnsArticleMaster.setArticleCode(rnsArticleMaster.getArticleCode().toUpperCase());
            result = rnsArticleMasterRepository.save(rnsArticleMaster);
        }
        return ResponseEntity.created(new URI("/api/rns-article-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-article-masters : Updates an existing rnsArticleMaster.
     *
     * @param rnsArticleMaster the rnsArticleMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsArticleMaster,
     * or with status 400 (Bad Request) if the rnsArticleMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsArticleMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-article-masters")
    @Timed
    public ResponseEntity<RnsArticleMaster> updateRnsArticleMaster(@RequestBody RnsArticleMaster rnsArticleMaster) throws URISyntaxException {
        log.debug("REST request to update RnsArticleMaster : {}", rnsArticleMaster);
        if (rnsArticleMaster.getId() == null) {
            return createRnsArticleMaster(rnsArticleMaster);
        }

        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsArticleMaster.setUpdatedUser(user);
        rnsArticleMaster.setLastUpdatedDate(Instant.now());

        RnsArticleMaster result = rnsArticleMasterRepository.save(rnsArticleMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsArticleMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-article-masters : get all the rnsArticleMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsArticleMasters in body
     */
    @GetMapping("/rns-article-masters")
    @Timed
    public List<RnsArticleMaster> getAllRnsArticleMasters() {
       // log.debug("REST request to get all RnsArticleMasters");
        List<RnsArticleMaster> rnsArticleMasterList = new ArrayList<RnsArticleMaster>();
        List<RnsArticleMaster> articleMasterList = rnsArticleMasterRepository.findAll();
        List<RnsCatgMaster> userCatgList = getAllRnsCatgMasters();
        
        for(RnsArticleMaster rnsArticleMasterData : articleMasterList){
        	for(RnsCatgMaster userCatg : userCatgList){
               if(userCatg.getId() == rnsArticleMasterData.getCatgCode().getId()){
                	rnsArticleMasterList.add(rnsArticleMasterData);
                    
                }
            }
            
        }
        return rnsArticleMasterList;
        }


    /**
     * GET  /rns-article-masters : get all the rnsArticleMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsArticleMasters in body
     */
    @GetMapping("/rns-article-masters/findbyCatg/{id}")
    @Timed
    public List<RnsArticleMaster> getAllRnsArticleMasters(@PathVariable Long id) {
        log.debug("REST request to get all RnsArticleMasters by catgMaster");
        List<RnsArticleMaster> rnsArticleMasterList = new ArrayList<RnsArticleMaster>();
        List<RnsArticleMaster> articleMasterList = rnsArticleMasterRepository.findAll();
        //System.out.println(articleMasterList);
        for(RnsArticleMaster rnsArticleMasterData : articleMasterList){
            log.debug("------===---");
            if(id == rnsArticleMasterData.getCatgCode().getId()){
              rnsArticleMasterList.add(rnsArticleMasterData);
            }
        }
        return rnsArticleMasterList;
        }


    public List<RnsCatgMaster> getAllRnsCatgMasters() {
        log.debug("REST request to get all RnsCatgMasters");
        List<RnsCatgMaster> rnsCatgMasterList = rnsCatgMasterRepository.findAllWithEagerRelationshipsList(getCurrentUserLogin());
        return rnsCatgMasterList;
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
     * GET  /rns-article-masters/:id : get the "id" rnsArticleMaster.
     *
     * @param id the id of the rnsArticleMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsArticleMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-article-masters/{id}")
    @Timed
    public ResponseEntity<RnsArticleMaster> getRnsArticleMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsArticleMaster : {}", id);
        Optional<RnsArticleMaster> rnsArticleMaster = rnsArticleMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsArticleMaster);
    }

    /**
     * DELETE  /rns-article-masters/:id : delete the "id" rnsArticleMaster.
     *
     * @param id the id of the rnsArticleMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-article-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsArticleMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsArticleMaster : {}", id);
        rnsArticleMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
