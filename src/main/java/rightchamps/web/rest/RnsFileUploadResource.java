package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.RnsFileUpload;

import rightchamps.domain.RnsQuotationVariant;
import rightchamps.modal.RnsUploadBean;
import rightchamps.repository.RnsFileUploadRepository;
import rightchamps.repository.RnsQuotationVariantRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RnsFileUpload.
 */
@RestController
@RequestMapping("/api")
public class RnsFileUploadResource {

    private final Logger log = LoggerFactory.getLogger(RnsFileUploadResource.class);

    private static final String ENTITY_NAME = "File Upload";

    private final RnsFileUploadRepository rnsFileUploadRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private ApplicationProperties applicationProperties;

    public RnsFileUploadResource(RnsFileUploadRepository rnsFileUploadRepository) {
        this.rnsFileUploadRepository = rnsFileUploadRepository;
    }

    /**
     * POST  /rns-file-uploads : Create a new rnsFileUpload.
     *
     * @param @rnsFileUpload the rnsFileUpload to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsFileUpload, or with status 400 (Bad Request) if the rnsFileUpload has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/rns-file-uploads" , consumes = {"multipart/form-data"})
    @Timed
    public List<RnsFileUpload> createRnsFileUpload(@RequestParam(required = false) MultipartFile file, String variantId, String uploadType) throws URISyntaxException {
        String UPLOADED_FOLDER = applicationProperties.getUploadPath();
        RnsFileUpload rnsFileUpload = new RnsFileUpload();
        rnsFileUpload.setVariantId(new Long(variantId));
        rnsFileUpload.setFileName("DEMO");
        rnsFileUpload.setDisplayName("DEMO");
        rnsFileUpload.setUploadType(uploadType);
        rnsFileUpload.setCreatedBy(getCurrentUserLogin());
        rnsFileUpload.setCreatedDate(Instant.now());
        RnsFileUpload result = rnsFileUploadRepository.save(rnsFileUpload);
        try {
            if (file != null) {
                String extn = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
                result.setFileName(result.getId() + extn);
                result.setDisplayName(file.getOriginalFilename());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + "quotation/" + result.getId() + extn);
                Files.write(path, bytes);
                result = rnsFileUploadRepository.save(result);
            }
        } catch(IOException e){
            System.out.println("FeedbackResource createFeedback() "+ e.getMessage());
        } catch(Exception e){
            System.out.println("FeedbackResource createFeedback() "+ e.getMessage());
        }
        RnsUploadBean rnsUploadBean = new RnsUploadBean();
        rnsUploadBean.setVariantId(new Long(variantId));
        rnsUploadBean.setUploadType(uploadType);
        List<RnsFileUpload> rnsFileUploads = this.rnsFileUploadList(rnsUploadBean);
        if(rnsFileUploads != null && rnsFileUploads.size()>0) {
            if(uploadType != null && uploadType.equalsIgnoreCase("L")) {
                RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(new Long(variantId)).orElse(null);
                rnsQuotationVariant.setUploadFlag("Y");
                rnsQuotationVariantRepository.save(rnsQuotationVariant);
            }
        }
        return rnsFileUploads;
    }

    @GetMapping("/rns-file-uploads-download/{id}")
    @Timed
    public ResponseEntity<Object> getFileDownload(@PathVariable Long id) throws FileNotFoundException, IOException{
        log.debug("REST request to get Feedback : {}", id);
        String UPLOADED_FOLDER = applicationProperties.getUploadPath();
        RnsFileUpload rnsFileUpload = rnsFileUploadRepository.findById(id).orElse(null);;
        File file = new File(UPLOADED_FOLDER + "quotation/"+rnsFileUpload.getFileName());
        Path path = Paths.get(UPLOADED_FOLDER + "quotation/" + rnsFileUpload.getFileName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        String mimeType = Files.probeContentType(path);
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType(mimeType)).body(resource);
        return responseEntity;
    }

    /**
     * PUT  /rns-file-uploads : Updates an existing rnsFileUpload.
     *
     * @param @rnsFileUpload the rnsFileUpload to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rnsFileUpload,
     * or with status 400 (Bad Request) if the rnsFileUpload is not valid,
     * or with status 500 (Internal Server Error) if the rnsFileUpload couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rns-file-uploads")
    @Timed
    public ResponseEntity<RnsFileUpload> updateRnsFileUpload(@Valid @RequestBody RnsFileUpload rnsFileUpload) throws URISyntaxException {
        log.debug("REST request to update RnsFileUpload : {}", rnsFileUpload);
        if (rnsFileUpload.getId() == null) {
            // return createRnsFileUpload(rnsFileUpload);
        }
        RnsFileUpload result = rnsFileUploadRepository.save(rnsFileUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rnsFileUpload.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rns-file-uploads : get all the rnsFileUploads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rnsFileUploads in body
     */
    @GetMapping("/rns-file-uploads")
    @Timed
    public List<RnsFileUpload> getAllRnsFileUploads() {
        log.debug("REST request to get all RnsFileUploads");
        return rnsFileUploadRepository.findAll();
        }

    /**
     * POST  /rns-file-uploads-list : Create a new rnsFileUpload.
     *
     * @param @rnsFileUpload the rnsFileUpload to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsFileUpload, or with status 400 (Bad Request) if the rnsFileUpload has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rns-file-uploads-list")
    @Timed
    public List<RnsFileUpload> rnsFileUploadList(@Valid @RequestBody RnsUploadBean rnsUploadBean) throws URISyntaxException {
        log.debug("REST request to save RnsFileUpload : {}", rnsUploadBean);
        List<RnsFileUpload> rnsFileUploads = rnsFileUploadRepository.findAll(rnsUploadBean.getVariantId(), rnsUploadBean.getUploadType());
        if(rnsFileUploads!=null)
            return rnsFileUploads;
        else
            return new ArrayList<RnsFileUpload>();
    }

    /**
     * GET  /rns-file-uploads/:id : get the "id" rnsFileUpload.
     *
     * @param id the id of the rnsFileUpload to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rnsFileUpload, or with status 404 (Not Found)
     */
    @GetMapping("/rns-file-uploads/{id}")
    @Timed
    public ResponseEntity<RnsFileUpload> getRnsFileUpload(@PathVariable Long id) {
        log.debug("REST request to get RnsFileUpload : {}", id);
        Optional<RnsFileUpload> rnsFileUpload = rnsFileUploadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rnsFileUpload);
    }

    /**
     * DELETE  /rns-file-uploads/:id : delete the "id" rnsFileUpload.
     *
     * @param id the id of the rnsFileUpload to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rns-file-uploads/{id}")
    @Timed
    public ResponseEntity<Void> deleteRnsFileUpload(@PathVariable Long id) {
        log.debug("REST request to delete RnsFileUpload : {}", id);
        RnsFileUpload rnsFileUpload = rnsFileUploadRepository.findById(id).orElse(null);
        rnsFileUploadRepository.deleteById(id);
        List<RnsFileUpload> rnsFileUploads = rnsFileUploadRepository.findAll(rnsFileUpload.getVariantId(), rnsFileUpload.getUploadType());
        if(rnsFileUploads != null && rnsFileUploads.size()>0) {
            if(rnsFileUpload.getUploadType() != null && rnsFileUpload.getUploadType().equalsIgnoreCase("L")) {
                RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(rnsFileUpload.getVariantId()).orElse(null);
                rnsQuotationVariant.setUploadFlag("Y");
                rnsQuotationVariantRepository.save(rnsQuotationVariant);
            }
        } else {
            if(rnsFileUpload.getUploadType() != null && rnsFileUpload.getUploadType().equalsIgnoreCase("L")) {
                RnsQuotationVariant rnsQuotationVariant = rnsQuotationVariantRepository.findById(rnsFileUpload.getVariantId()).orElse(null);
                rnsQuotationVariant.setUploadFlag(null);
                rnsQuotationVariantRepository.save(rnsQuotationVariant);
            }
        }
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
