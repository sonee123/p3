package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsDelTermsMaster;

import rightchamps.repository.RnsDelTermsMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import rightchamps.repository.RnsDelTermsMasterRepository;
import org.springframework.security.core.userdetails.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rightchamps.repository.UserRepository;
import rightchamps.domain.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsDelTermsMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsDelTermsMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsDelTermsMasterResource.class);

    private static final String ENTITY_NAME = "Delivery Terms Master";

    private final RnsDelTermsMasterRepository rnsDelTermsMasterRepository;

    private final UserRepository userRepository;

    public RnsDelTermsMasterResource(RnsDelTermsMasterRepository rnsDelTermsMasterRepository,UserRepository userRepository) {
        this.rnsDelTermsMasterRepository = rnsDelTermsMasterRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /rns-del-terms-masters : Create a new rnsDelTermsMaster.
     *
     * @param rnsDelTermsMaster the rnsDelTermsMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsDelTermsMaster, or with status 400 (Bad Request) if the rnsDelTermsMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-del-terms-masters")
    @Timed
    public ResponseEntity<RnsDelTermsMaster> createRnsDelTermsMaster(@RequestBody RnsDelTermsMaster rnsDelTermsMaster) throws URISyntaxException {
        log.debug("REST request to save RnsDelTermsMaster : {}", rnsDelTermsMaster);
        if (rnsDelTermsMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsDelTermsMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

           User user = userRepository.findByLogin(getCurrentUserLogin());
           rnsDelTermsMaster.setUser(user);
           rnsDelTermsMaster.setCreatedDate(Instant.now());
        //RnsDelTermsMaster result = rnsDelTermsMasterRepository.save(rnsDelTermsMaster);

        RnsDelTermsMaster delTermsMaster = rnsDelTermsMasterRepository.findByDelTermsCode(rnsDelTermsMaster.getDelTermsCode().toUpperCase());
        RnsDelTermsMaster result = null;
        if(delTermsMaster!=null){
            result = delTermsMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Delivery Term Code already exist"))
                .body(result);
        } else{
            rnsDelTermsMaster.setDelTermsCode(rnsDelTermsMaster.getDelTermsCode().toUpperCase());
            result = rnsDelTermsMasterRepository.save(rnsDelTermsMaster);
        }

        return ResponseEntity.created(new URI("/api/rns-del-terms-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-del-terms-masters : Updates an existing rnsDelTermsMaster.
     *
     * @param rnsDelTermsMaster the rnsDelTermsMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsDelTermsMaster,
     * or with status 400 (Bad Request) if the rnsDelTermsMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsDelTermsMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-del-terms-masters")
    @Timed
    public ResponseEntity<RnsDelTermsMaster> updateRnsDelTermsMaster(@RequestBody RnsDelTermsMaster rnsDelTermsMaster) throws URISyntaxException {
        log.debug("REST request to update RnsDelTermsMaster : {}", rnsDelTermsMaster);
        if (rnsDelTermsMaster.getId() == null) {
            return createRnsDelTermsMaster(rnsDelTermsMaster);
        }

         User user = userRepository.findByLogin(getCurrentUserLogin());
         rnsDelTermsMaster.setUpdatedUser(user);
         rnsDelTermsMaster.setLastUpdatedDate(Instant.now());

        RnsDelTermsMaster result = rnsDelTermsMasterRepository.save(rnsDelTermsMaster);
      //post to vendor Portal
/*
      //post to vendor Portal

        final String AuthenticationURL = "http://localhost:8090/api/authenticate";
        final String uri = "http://localhost:8090/api/rns-del-terms-masters/"+rnsDelTermsMaster.getId().toString();
                // Prepare header
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
        //JSONArray authJsonArray = new JSONArray();
        JSONObject authDetailJson = new JSONObject();
        authDetailJson.put("username", "admin");
        authDetailJson.put("password", "admin");
        authDetailJson.put("rememberMe", "false");
        //authJsonArray.put(authDetailJson);

        // Pass the new person and header
        HttpEntity<String> authEntity = new HttpEntity<String>(authDetailJson.toString(), headers);
        log.debug("Json Object : "+authEntity);

        RestTemplate authrestTemplate = new RestTemplate();
        ResponseEntity<String> result1 = authrestTemplate.exchange(AuthenticationURL, HttpMethod.POST, authEntity, String.class);
        log.debug("------------------------------------");
         //log.debug(result1);
         JSONObject jsondata = new JSONObject(result1.getBody());
         if(jsondata.get("id_token") != null){
            String idToken = jsondata.get("id_token").toString();
            //String idToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUxNTkwOTU5MX0.hLGl8wMI9bqXd2QDhyLRaNOMWGinUxLC2xf5h8M8GCjvPRQ0v0pV4E9C7Z7zy8P6lZynpNgqewnkLpJrmbbjfg";
            headers.add("Authorization", "Bearer " + idToken);

            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("delTermsCode", rnsDelTermsMaster.getDelTermsCode());
            formDetailsJson.put("delTermsCodeDesc", rnsDelTermsMaster.getDelTermsCodeDesc());
            formDetailsJson.put("catgCode", rnsDelTermsMaster.getRnsCatgCode().getCatgCode());
            formDetailsJson.put("quotationDelTermsId", rnsDelTermsMaster.getId());


            // Pass the new person and header
            HttpEntity<String> entity = new HttpEntity<String>(formDetailsJson.toString(), headers);
            log.debug("Json Object : "+entity);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result2 = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
            log.debug("---ResponseEntity<String>----------",result2);

             }else{
                log.debug("Error in Authorization, ID_TOKEN Not Retured");
             }

     } catch (JSONException e) {
        //some exception handler code.
        //log.debug(e);
    	 log.debug("JSONException",e);
    }

    */

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsDelTermsMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-del-terms-masters : get all the rnsDelTermsMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsDelTermsMasters in body
     */
    @GetMapping("/rns-del-terms-masters")
    @Timed
    public List<RnsDelTermsMaster> getAllRnsDelTermsMasters() {
        log.debug("REST request to get all RnsDelTermsMasters");
        return rnsDelTermsMasterRepository.findSortBy();
        }

    /**
     * GET  /rns-del-terms-masters/:id : get the "id" rnsDelTermsMaster.
     *
     * @param id the id of the rnsDelTermsMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsDelTermsMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-del-terms-masters/{id}")
    @Timed
    public ResponseEntity<RnsDelTermsMaster> getRnsDelTermsMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsDelTermsMaster : {}", id);
        Optional<RnsDelTermsMaster> rnsDelTermsMaster = rnsDelTermsMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsDelTermsMaster);
    }

    /**
     * DELETE  /rns-del-terms-masters/:id : delete the "id" rnsDelTermsMaster.
     *
     * @param id the id of the rnsDelTermsMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-del-terms-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsDelTermsMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsDelTermsMaster : {}", id);
        rnsDelTermsMasterRepository.deleteById(id);
      //post to vendor Portal
/*
        final String AuthenticationURL = "http://localhost:8090/api/authenticate";
        final String uri = "http://localhost:8090/api/rns-del-terms-masters-delete/"+rnsDelTermsMaster.getId().toString();
                // Prepare header
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
        //JSONArray authJsonArray = new JSONArray();
        JSONObject authDetailJson = new JSONObject();
        authDetailJson.put("username", "admin");
        authDetailJson.put("password", "admin");
        authDetailJson.put("rememberMe", "false");
        //authJsonArray.put(authDetailJson);

        // Pass the new person and header
        HttpEntity<String> authEntity = new HttpEntity<String>(authDetailJson.toString(), headers);
        log.debug("Json Object : "+authEntity);

        RestTemplate authrestTemplate = new RestTemplate();
        ResponseEntity<String> result1 = authrestTemplate.exchange(AuthenticationURL, HttpMethod.POST, authEntity, String.class);
        log.debug("------------------------------------");
         //log.debug(result1);
         JSONObject jsondata = new JSONObject(result1.getBody());
         if(jsondata.get("id_token") != null){
            String idToken = jsondata.get("id_token").toString();
            //String idToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUxNTkwOTU5MX0.hLGl8wMI9bqXd2QDhyLRaNOMWGinUxLC2xf5h8M8GCjvPRQ0v0pV4E9C7Z7zy8P6lZynpNgqewnkLpJrmbbjfg";
            headers.add("Authorization", "Bearer " + idToken);

            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("quotationDelTermsId", rnsDelTermsMaster.getId());
            // Pass the new person and header
            HttpEntity<String> entity = new HttpEntity<String>(formDetailsJson.toString(), headers);
            log.debug("Json Object : "+entity);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result2 = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
            log.debug("------------------------------------",result2);
            //log.debug(result2);

             }else{
                log.debug("Error in Authorization, ID_TOKEN Not Retured");
             }

     } catch (JSONException e) {
        //some exception handler code.
        //log.debug(e);
    	 log.debug("",e);
    } */
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
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
}
