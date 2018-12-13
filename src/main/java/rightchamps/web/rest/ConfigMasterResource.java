package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.ConfigMaster;
import rightchamps.domain.RnsArticleMaster;
import rightchamps.domain.RnsRelation;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import rightchamps.domain.RnsDelPlaceMaster;
import rightchamps.repository.RnsDelPlaceMasterRepository;

import rightchamps.domain.RnsPayTermsMaster;
import rightchamps.repository.RnsPayTermsMasterRepository;

import rightchamps.domain.RnsTypeMaster;
import rightchamps.repository.RnsTypeMasterRepository;

import rightchamps.domain.RnsDelTermsMaster;
import rightchamps.repository.RnsDelTermsMasterRepository;

import rightchamps.repository.RnsCatgMasterRepository;
import rightchamps.repository.RnsArticleMasterRepository;

import rightchamps.domain.RnsUomMaster;
import rightchamps.repository.RnsUomMasterRepository;

import rightchamps.domain.Template;
import rightchamps.repository.TemplateRepository;

import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;

/**
 * REST controller for managing RnsCatgMaster.
 */
@RestController
@RequestMapping("/api")
public class ConfigMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsCatgMasterResource.class);

    private static final String ENTITY_NAME = "Category Master";

    @Inject
    private RnsCatgMasterRepository rnsCatgMasterRepository;

    @Inject
    private RnsArticleMasterRepository rnsArticleMasterRepository;

    @Inject
    private RnsDelPlaceMasterRepository rnsDelPlaceMasterRepository;

    @Inject
    private RnsDelTermsMasterRepository rnsDelTermsMasterRepository;

    @Inject
    private RnsPayTermsMasterRepository rnsPayTermsMasterRepository;

    @Inject
    private RnsTypeMasterRepository rnsTypeMasterRepository;

    @Inject
    private RnsUomMasterRepository rnsUomMasterRepository;

    @Inject
    private TemplateRepository templateRepository;
    /**
     * GET  /rns-catg-masters : get all the rnsCatgMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsCatgMasters in body
     */
    @GetMapping("/config-master/{catgId}")
    @Timed
    public ConfigMaster getAllConfigMasters(@PathVariable Long catgId) {
        log.debug("REST request to get all RnsCatgMasters");
        ConfigMaster configMast = new ConfigMaster();

        List<RnsCatgMaster> rnsCatgMasterList =  rnsCatgMasterRepository.findAllWithEagerRelationshipsList(getCurrentUserLogin());
        configMast.setCatgCode(rnsCatgMasterList);

        List<RnsArticleMaster> rnsArticleMasterList = new ArrayList<RnsArticleMaster>();
        List<RnsArticleMaster> articleMasterList = rnsArticleMasterRepository.findAll();
        for(RnsArticleMaster rnsArticleMasterData : articleMasterList){
            if(catgId == rnsArticleMasterData.getCatgCode().getId()){
              rnsArticleMasterList.add(rnsArticleMasterData);
            }
        }
        configMast.setArticleCode(rnsArticleMasterList);

        List<RnsDelPlaceMaster> rnsDelPlaceMasterList = rnsDelPlaceMasterRepository.findAll();
        configMast.setRnsDelPlaceMaster(rnsDelPlaceMasterList);

        List<RnsDelTermsMaster> rnsDelTermsMasterList = rnsDelTermsMasterRepository.findAll();
        configMast.setRnsDelTermsMaster(rnsDelTermsMasterList);

        List<RnsPayTermsMaster> rnsPayTermsMasterList = rnsPayTermsMasterRepository.findAll();
        configMast.setRnsPayTermsMaster(rnsPayTermsMasterList);

        List<RnsTypeMaster> rnsTypeMasterList = rnsTypeMasterRepository.findAll();
        configMast.setRnsTypeMaster(rnsTypeMasterList);

        List<RnsUomMaster> rnsUomMasterList = rnsUomMasterRepository.findAll();
        configMast.setRnsUomMaster(rnsUomMasterList);

        List<Template> templateList = templateRepository.findAll();
        configMast.setTemplate(templateList);


        return configMast;
    }


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

}
