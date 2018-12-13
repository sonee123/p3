package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.RnsVendorMaster;

import rightchamps.repository.RnsVendorMasterRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsVendorMaster.
 */
@RestController
@RequestMapping("/api")
public class RnsVendorMasterResource {

    private final Logger log = LoggerFactory.getLogger(RnsVendorMasterResource.class);

    private static final String ENTITY_NAME = "Vendor Master";

    private final RnsVendorMasterRepository rnsVendorMasterRepository;

    public RnsVendorMasterResource(RnsVendorMasterRepository rnsVendorMasterRepository) {
        this.rnsVendorMasterRepository = rnsVendorMasterRepository;
    }

    /**
     * POST  /rns-vendor-masters : Create a new rnsVendorMaster.
     *
     * @param rnsVendorMaster the rnsVendorMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsVendorMaster, or with status 400 (Bad Request) if the rnsVendorMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-vendor-masters")
    @Timed
    public ResponseEntity<RnsVendorMaster> createRnsVendorMaster(@RequestBody RnsVendorMaster rnsVendorMaster) throws URISyntaxException {
        log.debug("REST request to save RnsVendorMaster : {}", rnsVendorMaster);
        if (rnsVendorMaster.getId() != null) {
            throw new BadRequestAlertException("A new rnsVendorMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RnsVendorMaster result = rnsVendorMasterRepository.save(rnsVendorMaster);
        return ResponseEntity.created(new URI("/api/rns-vendor-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rns-vendor-masters : Updates an existing rnsVendorMaster.
     *
     * @param rnsVendorMaster the rnsVendorMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsVendorMaster,
     * or with status 400 (Bad Request) if the rnsVendorMaster is not valid,
     * or with status 500 (Internal Server Error) if the rnsVendorMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-vendor-masters")
    @Timed
    public ResponseEntity<RnsVendorMaster> updateRnsVendorMaster(@RequestBody RnsVendorMaster rnsVendorMaster) throws URISyntaxException {
        log.debug("REST request to update RnsVendorMaster : {}", rnsVendorMaster);
        if (rnsVendorMaster.getId() == null) {
            return createRnsVendorMaster(rnsVendorMaster);
        }
        RnsVendorMaster result = rnsVendorMasterRepository.save(rnsVendorMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsVendorMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-vendor-masters : get all the rnsVendorMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsVendorMasters in body
     */
    @GetMapping("/rns-vendor-masters")
    @Timed
    public List<RnsVendorMaster> getAllRnsVendorMasters() {
        log.debug("REST request to get all RnsVendorMasters");
        return rnsVendorMasterRepository.findAll();
        }


    @GetMapping("/rns-vendor-masters-vendor/{search}")
    @Timed
    public List<RnsVendorMaster> getAllVendorUsers(@PathVariable String search) throws InvocationTargetException, IllegalAccessException {
        // final Page<UserDTO> page = userService.getAllUsers(pageable);
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        List<RnsVendorMaster> rnsVendorMasters = rnsVendorMasterRepository.findAllAuthority(search, search.toUpperCase());
        return rnsVendorMasters;
    }

    /**
     * GET  /rns-vendor-masters/:id : get the "id" rnsVendorMaster.
     *
     * @param id the id of the rnsVendorMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsVendorMaster, or with status 404 (Not Found)
     */
    @GetMapping("/rns-vendor-masters/{id}")
    @Timed
    public ResponseEntity<RnsVendorMaster> getRnsVendorMaster(@PathVariable Long id) {
        log.debug("REST request to get RnsVendorMaster : {}", id);
        Optional<RnsVendorMaster> rnsVendorMaster = rnsVendorMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsVendorMaster);
    }


    /**
     * DELETE  /rns-vendor-masters/:id : delete the "id" rnsVendorMaster.
     *
     * @param id the id of the rnsVendorMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-vendor-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsVendorMaster(@PathVariable Long id) {
        log.debug("REST request to delete RnsVendorMaster : {}", id);
        rnsVendorMasterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
