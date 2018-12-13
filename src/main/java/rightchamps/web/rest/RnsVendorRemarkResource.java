package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.domain.RnsVendorRemark;
import rightchamps.repository.RnsVendorRemarkRepository;
import rightchamps.service.RnsVendorRemarkService;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import rightchamps.web.rest.util.PaginationUtil;
import rightchamps.service.dto.RnsVendorRemarkCriteria;
import rightchamps.service.RnsVendorRemarkQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsVendorRemark.
 */
@RestController
@RequestMapping("/api")
public class RnsVendorRemarkResource {

    private final Logger log = LoggerFactory.getLogger(RnsVendorRemarkResource.class);

    private static final String ENTITY_NAME = "Vendor Remark";

    private final RnsVendorRemarkService rnsVendorRemarkService;

    private final RnsVendorRemarkQueryService rnsVendorRemarkQueryService;

    @Inject
    private RnsVendorRemarkRepository rnsVendorRemarkRepository;

    public RnsVendorRemarkResource(RnsVendorRemarkService rnsVendorRemarkService, RnsVendorRemarkQueryService rnsVendorRemarkQueryService) {
        this.rnsVendorRemarkService = rnsVendorRemarkService;
        this.rnsVendorRemarkQueryService = rnsVendorRemarkQueryService;
    }

    /**
     * POST  /rns-vendor-remarks : Create a new rnsVendorRemark.
     *
     * @param rnsVendorRemark the rnsVendorRemark to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsVendorRemark, or with status 400 (Bad Request) if the rnsVendorRemark has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-vendor-remarks")
    @Timed
    public ResponseEntity<RnsVendorRemark> createRnsVendorRemark(@RequestBody RnsVendorRemark rnsVendorRemark) throws URISyntaxException {
        log.debug("REST request to save RnsVendorRemark : {}", rnsVendorRemark);
        if (rnsVendorRemark.getId() != null) {
            throw new BadRequestAlertException("A new rnsVendorRemark cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsVendorRemark result = rnsVendorRemarkService.save(rnsVendorRemark);
        return ResponseEntity.created(new URI("/api/rns-vendor-remarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-vendor-remarks : Updates an existing rnsVendorRemark.
     *
     * @param rnsVendorRemark the rnsVendorRemark to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsVendorRemark,
     * or with status 400 (Bad Request) if the rnsVendorRemark is not valid,
     * or with status 500 (Internal Server Error) if the rnsVendorRemark couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-vendor-remarks")
    @Timed
    public ResponseEntity<RnsVendorRemark> updateRnsVendorRemark(@RequestBody RnsVendorRemark rnsVendorRemark) throws URISyntaxException {
        log.debug("REST request to update RnsVendorRemark : {}", rnsVendorRemark);
        if (rnsVendorRemark.getId() == null) {
            return createRnsVendorRemark(rnsVendorRemark);
        }
        RnsVendorRemark result = rnsVendorRemarkService.save(rnsVendorRemark);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsVendorRemark.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-vendor-remarks : get all the rnsVendorRemarks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of rnsVendorRemarks in body
     */
    @GetMapping("/rns-vendor-remarks")
    @Timed
    public ResponseEntity<List<RnsVendorRemark>> getAllRnsVendorRemarks(RnsVendorRemarkCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RnsVendorRemarks by criteria: {}", criteria);
        Page<RnsVendorRemark> page = rnsVendorRemarkQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rns-vendor-remarks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/rns-vendor-remarks-quotationid/{quotationId}")
    public List<RnsVendorRemark> getAllRnsVendorRemarksQuotations(@PathVariable Long quotationId) throws URISyntaxException {
        log.debug("REST request to save RnsVendorRemark : {}", quotationId);
        List<RnsVendorRemark> remarks = rnsVendorRemarkRepository.findAllByQuotationSort(quotationId);
        return remarks;
    }

    @GetMapping("/rns-vendor-remarks-byorder/{id}")
    @Timed
    public List<RnsVendorRemark> getAllRnsVendorRemarks(@PathVariable Long id,String vendorCode) {
     List<RnsVendorRemark> rnsVendorRemarkDataList = rnsVendorRemarkService.findAllBySort(id,vendorCode);
     List<RnsVendorRemark> rnsremarkList = new ArrayList<RnsVendorRemark>();
     RnsVendorRemark rnsremark = new RnsVendorRemark();

        for(RnsVendorRemark quotationData : rnsVendorRemarkDataList){

        	rnsremark.setId(quotationData.getId());
        	rnsremark.setFromEmail(quotationData.getFromEmail());
        	rnsremark.setQuotation(quotationData.getQuotation());
        	rnsremark.setRemarkText(quotationData.getRemarkText());
        	rnsremark.setStaffEmail(quotationData.getStaffEmail());
        	rnsremark.setToEmail(quotationData.getToEmail());
        	rnsremark.setVendorEmail(quotationData.getVendorEmail());
        	   rnsremarkList.add(rnsremark);
        }


       log.debug("rnsremarkList:::::::", rnsremarkList);
        return rnsremarkList;
    }


    @GetMapping("/rns-vendor-remarks-by-vendor/{id}")
    @Timed
    public List<RnsVendorRemark> getAllRnsVendorRemarksByVendor(@PathVariable Long id) {
        String vendorCode = getCurrentUserLogin();
        List<RnsVendorRemark> rnsVendorRemarkDataList = rnsVendorRemarkService.findAllBySort(id,vendorCode);
        List<RnsVendorRemark> rnsremarkList = new ArrayList<RnsVendorRemark>();
        RnsVendorRemark rnsremark = new RnsVendorRemark();

        for(RnsVendorRemark quotationData : rnsVendorRemarkDataList){

            rnsremark.setId(quotationData.getId());
            rnsremark.setFromEmail(quotationData.getFromEmail());
            rnsremark.setQuotation(quotationData.getQuotation());
            rnsremark.setRemarkText(quotationData.getRemarkText());
            rnsremark.setStaffEmail(quotationData.getStaffEmail());
            rnsremark.setToEmail(quotationData.getToEmail());
            rnsremark.setVendorEmail(quotationData.getVendorEmail());
            rnsremarkList.add(rnsremark);
        }


        log.debug("rnsremarkList:::::::", rnsremarkList);
        return rnsremarkList;
    }

    /**
     * GET  /rns-vendor-remarks/:id : get the "id" rnsVendorRemark.
     *
     * @param id the id of the rnsVendorRemark to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsVendorRemark, or with status 404 (Not Found)
     */
    @GetMapping("/rns-vendor-remarks/{id}")
    @Timed
    public ResponseEntity<RnsVendorRemark> getRnsVendorRemark(@PathVariable Long id) {
        log.debug("REST request to get RnsVendorRemark : {}", id);
        RnsVendorRemark rnsVendorRemark = rnsVendorRemarkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rnsVendorRemark));
    }

    /**
     * DELETE  /rns-vendor-remarks/:id : delete the "id" rnsVendorRemark.
     *
     * @param id the id of the rnsVendorRemark to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-vendor-remarks/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsVendorRemark(@PathVariable Long id) {
        log.debug("REST request to delete RnsVendorRemark : {}", id);
        rnsVendorRemarkService.delete(id);
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
