package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsPayTermsMaster;
import rightchamps.domain.User;
import rightchamps.repository.RnsPayTermsMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rightchamps.repository.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.Entity;

/**
 * REST controller for managing RnsPayTermsMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsPayTermsMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsPayTermsMasterResource.class);

    private static final String ENTITY_NAME = "Pay Terms Master";
    @Inject
    private final UserRepository userRepository;

    private final RnsPayTermsMasterRepository rnsPayTermsMasterRepository;

    public RnsPayTermsMasterResource(RnsPayTermsMasterRepository rnsPayTermsMasterRepository,UserRepository userRepository) {
        this.rnsPayTermsMasterRepository = rnsPayTermsMasterRepository;
        this.userRepository=userRepository;
    }

    /**
     * POST  /rns-pay-terms-masters : Create a new rnsPayTermsMaster.
     *
     * @param rnsPayTermsMaster the rnsPayTermsMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsPayTermsMaster, or with status 400 (Bad Request) if the rnsPayTermsMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-pay-terms-masters")
    @Timed
    public ResponseEntity<RnsPayTermsMaster> createRnsPayTermsMaster(@RequestBody RnsPayTermsMaster rnsPayTermsMaster) throws URISyntaxException {
        log.debug("REST request to save RnsPayTermsMaster : {}", rnsPayTermsMaster);

        if (rnsPayTermsMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsPayTermsMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsPayTermsMaster.setUser(user);
        rnsPayTermsMaster.setCreatedDate(Instant.now());

        RnsPayTermsMaster payTermsMaster = rnsPayTermsMasterRepository.findByPayTermsCode(rnsPayTermsMaster.getPayTermsCode().toUpperCase());
        RnsPayTermsMaster result = null;
        if(payTermsMaster!=null){
            result = payTermsMaster;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(),"Pay Terms Code already exist"))
                .body(result);
        } else{
            rnsPayTermsMaster.setPayTermsCode(rnsPayTermsMaster.getPayTermsCode().toUpperCase());
            result = rnsPayTermsMasterRepository.save(rnsPayTermsMaster);
        }


        return ResponseEntity.created(new URI("/api/rns-pay-terms-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-pay-terms-masters : Updates an existing rnsPayTermsMaster.
     *
     * @param rnsPayTermsMaster the rnsPayTermsMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsPayTermsMaster,
     * or with status 400 (Bad Request) if the rnsPayTermsMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsPayTermsMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-pay-terms-masters")
    @Timed
    public ResponseEntity<RnsPayTermsMaster> updateRnsPayTermsMaster(@RequestBody RnsPayTermsMaster rnsPayTermsMaster) throws URISyntaxException {
        log.debug("REST request to update RnsPayTermsMaster : {}", rnsPayTermsMaster);
        if (rnsPayTermsMaster.getId() == null) {
            return createRnsPayTermsMaster(rnsPayTermsMaster);
        }

        User user = userRepository.findByLogin(getCurrentUserLogin());
        rnsPayTermsMaster.setUpdatedUser(user);
        rnsPayTermsMaster.setLastUpdatedDate(Instant.now());
        RnsPayTermsMaster result = rnsPayTermsMasterRepository.save(rnsPayTermsMaster);
      //post to vendor Portal

        /*final String AuthenticationURL = "http://localhost:8090/api/authenticate";
        final String uri = "http://localhost:8090/api/rns-pay-terms-masters/"+rnsPayTermsMaster.getId().toString();
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
            formDetailsJson.put("payTermsCode", rnsPayTermsMaster.getPayTermsCode());
            formDetailsJson.put("payTermsCodeDesc", rnsPayTermsMaster.getPayTermsCodeDesc());
            formDetailsJson.put("catgCode", rnsPayTermsMaster.getCatgCode().getCatgCode());
            formDetailsJson.put("quotationPayTermsId", rnsPayTermsMaster.getId());


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
    } */


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsPayTermsMaster.getId().toString()))
            .body(result);
    }



    /**
     * GET  /rns-pay-terms-masters : get all the rnsPayTermsMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsPayTermsMasters in body
     */
    @GetMapping("/rns-pay-terms-masters")
    @Timed
    public List<RnsPayTermsMaster> getAllRnsPayTermsMasters() {
        log.debug("REST request to get all RnsPayTermsMasters");
        return rnsPayTermsMasterRepository.findSortBy();
        }

    /**
     * GET  /rns-pay-terms-masters/:id : get the "id" rnsPayTermsMaster.
     *
     * @param id the id of the rnsPayTermsMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsPayTermsMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-pay-terms-masters/{id}")
    @Timed
    public ResponseEntity<RnsPayTermsMaster> getRnsPayTermsMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsPayTermsMaster : {}", id);
        Optional<RnsPayTermsMaster> rnsPayTermsMaster = rnsPayTermsMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsPayTermsMaster);
    }

    /**
     * DELETE  /rns-pay-terms-masters/:id : delete the "id" rnsPayTermsMaster.
     *
     * @param id the id of the rnsPayTermsMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
	@DeleteMapping("/rns-pay-terms-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsPayTermsMaster(@PathVariable Long id) {
         log.debug("REST request to Delete RnsPayTermsMaster : {}", id);
         rnsPayTermsMasterRepository.deleteById(id);
		/*//post to vendor Portal

	        final String AuthenticationURL = "http://localhost:8090/api/authenticate";
	        final String uri = "http://localhost:8090/api/rns-pay-terms-masters-delete/"+rnsPayTermsMaster.getId().toString();
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
	            formDetailsJson.put("quotationPayTermsId", rnsPayTermsMaster.getId());
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

