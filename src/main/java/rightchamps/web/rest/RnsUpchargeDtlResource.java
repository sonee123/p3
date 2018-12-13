package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.RnsUpchargeDtl;

import rightchamps.modal.RnsUpchargeDtlBean;
import rightchamps.repository.RnsUpchargeDtlRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsUpchargeDtl.
 */
@RestController
@RequestMapping("/api")
public class RnsUpchargeDtlResource {

    private final Logger log = LoggerFactory.getLogger(RnsUpchargeDtlResource.class);

    private static final String ENTITY_NAME = "Upcharge Details";

    private final RnsUpchargeDtlRepository rnsUpchargeDtlRepository;

    public RnsUpchargeDtlResource(RnsUpchargeDtlRepository rnsUpchargeDtlRepository) {
        this.rnsUpchargeDtlRepository = rnsUpchargeDtlRepository;
    }

    /**
     * POST  /rns-upcharge-dtls : Create a new rnsUpchargeDtl.
     *
     * @param rnsUpchargeDtl the rnsUpchargeDtl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsUpchargeDtl, or with status 400 (Bad Request) if the rnsUpchargeDtl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-upcharge-dtls")
    @Timed
    public ResponseEntity<RnsUpchargeDtl> createRnsUpchargeDtl(@Valid @RequestBody RnsUpchargeDtl rnsUpchargeDtl) throws URISyntaxException {
        log.debug("REST request to save RnsUpchargeDtl : {}", rnsUpchargeDtl);
        if (rnsUpchargeDtl.getId() != null) {
            throw new BadRequestAlertException("A new rnsUpchargeDtl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsUpchargeDtl result = rnsUpchargeDtlRepository.save(rnsUpchargeDtl);
        return ResponseEntity.created(new URI("/api/rns-upcharge-dtls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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


    @PostMapping("/rns-upcharge-dtls-multi")
    @Timed
    public ResponseEntity<List<RnsUpchargeDtl>> createRnsUpchargeDtlMulti(@Valid @RequestBody RnsUpchargeDtlBean[] rnsUpchargeDtls) throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        log.debug("REST request to save RnsUpchargeDtl : {}", rnsUpchargeDtls);
        Long vendorid = null;
        for(RnsUpchargeDtlBean rnsUpchargeDtlBean : rnsUpchargeDtls) {
            if(rnsUpchargeDtlBean.getUpchargeId()!=null && rnsUpchargeDtlBean.getUpchargeType()!=null && rnsUpchargeDtlBean.getUpchargeType().length()>0 && rnsUpchargeDtlBean.getRate()!=null) {
                RnsUpchargeDtl rnsUpchargeDtl = new RnsUpchargeDtl();
                BeanUtils.copyProperties(rnsUpchargeDtl, rnsUpchargeDtlBean);
                rnsUpchargeDtl.setCreatedDate(Instant.now());
                rnsUpchargeDtl.setCreatedBy(getCurrentUserLogin());
                RnsUpchargeDtl result = rnsUpchargeDtlRepository.save(rnsUpchargeDtl);
            } else if(rnsUpchargeDtlBean.getId() != null) {
                rnsUpchargeDtlRepository.deleteById(rnsUpchargeDtlBean.getId());
            }
            if(vendorid==null)
                vendorid = rnsUpchargeDtlBean.getVendorId();
        }
        List<RnsUpchargeDtl> rnsUpchargeDtls1 = rnsUpchargeDtlRepository.findAllByVendorId(vendorid);
        System.out.println(rnsUpchargeDtls1.size());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, vendorid.toString()))
            .body(rnsUpchargeDtls1);
    }

    /**
     * PUT  /rns-upcharge-dtls : Updates an existing rnsUpchargeDtl.
     *
     * @param rnsUpchargeDtl the rnsUpchargeDtl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsUpchargeDtl,
     * or with status 400 (Bad Request) if the rnsUpchargeDtl is not valid,
     * or with status 500 (Internal Server Error) if the rnsUpchargeDtl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-upcharge-dtls")
    @Timed
    public ResponseEntity<RnsUpchargeDtl> updateRnsUpchargeDtl(@Valid @RequestBody RnsUpchargeDtl rnsUpchargeDtl) throws URISyntaxException {
        log.debug("REST request to update RnsUpchargeDtl : {}", rnsUpchargeDtl);
        if (rnsUpchargeDtl.getId() == null) {
            return createRnsUpchargeDtl(rnsUpchargeDtl);
        }
        RnsUpchargeDtl result = rnsUpchargeDtlRepository.save(rnsUpchargeDtl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsUpchargeDtl.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-upcharge-dtls : get all the rnsUpchargeDtls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsUpchargeDtls in body
     */
    @GetMapping("/rns-upcharge-dtls")
    @Timed
    public List<RnsUpchargeDtl> getAllRnsUpchargeDtls() {
        log.debug("REST request to get all RnsUpchargeDtls");
        return rnsUpchargeDtlRepository.findAll();
        }


    /**
     * GET  /rns-upcharge-dtls : get all the rnsUpchargeDtls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsUpchargeDtls in body
     */
    @GetMapping("/rns-upcharge-dtls-vendors/{vendorId}")
    @Timed
    public List<RnsUpchargeDtl> getAllRnsUpchargeDtlsByVendorId(@PathVariable Long vendorId) {
        log.debug("REST request to get all RnsUpchargeDtls");
        return rnsUpchargeDtlRepository.findAllByVendorId(vendorId);
    }

    /**
     * GET  /rns-upcharge-dtls/:id : get the "id" rnsUpchargeDtl.
     *
     * @param id the id of the rnsUpchargeDtl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsUpchargeDtl, or with status 404 (Not Found)
     */
    @GetMapping("/rns-upcharge-dtls/{id}")
    @Timed
    public ResponseEntity<RnsUpchargeDtl> getRnsUpchargeDtl(@PathVariable Long id) {
        log.debug("REST request to get RnsUpchargeDtl : {}", id);
        Optional<RnsUpchargeDtl> rnsUpchargeDtl = rnsUpchargeDtlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsUpchargeDtl);
    }

    /**
     * GET  /rns-upcharge-dtls/:id : get the "id" rnsUpchargeDtl.
     *
     * @param id the id of the rnsUpchargeDtl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsUpchargeDtl, or with status 404 (Not Found)
     */
    @GetMapping("/rns-upcharge-dtls-select/{id}")
    @Timed
    public ResponseEntity<RnsUpchargeDtl> getRnsUpchargeDtlSelect(@PathVariable Long id) {
        log.debug("REST request to get RnsUpchargeDtl : {}", id);
        RnsUpchargeDtl rnsUpchargeDtl = new RnsUpchargeDtl();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsUpchargeDtl));
    }

    /**
     * DELETE  /rns-upcharge-dtls/:id : delete the "id" rnsUpchargeDtl.
     *
     * @param id the id of the rnsUpchargeDtl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-upcharge-dtls/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsUpchargeDtl(@PathVariable Long id) {
        log.debug("REST request to delete RnsUpchargeDtl : {}", id);
        rnsUpchargeDtlRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
