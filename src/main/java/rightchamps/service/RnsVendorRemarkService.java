package rightchamps.service;

import rightchamps.domain.RnsVendorRemark;
import rightchamps.repository.RnsVendorRemarkRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RnsVendorRemark.
 */
@Service
@Transactional
public class RnsVendorRemarkService {

    private final Logger log = LoggerFactory.getLogger(RnsVendorRemarkService.class);

    private final RnsVendorRemarkRepository rnsVendorRemarkRepository;

    public RnsVendorRemarkService(RnsVendorRemarkRepository rnsVendorRemarkRepository) {
        this.rnsVendorRemarkRepository = rnsVendorRemarkRepository;
    }

    /**
     * Save a rnsVendorRemark.
     *
     * @param rnsVendorRemark the entity to save
     * @return the persisted entity
     */
    public RnsVendorRemark save(RnsVendorRemark rnsVendorRemark) {
        log.debug("Request to save RnsVendorRemark : {}", rnsVendorRemark);
        return rnsVendorRemarkRepository.save(rnsVendorRemark);
    }

    /**
     * Get all the rnsVendorRemarks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RnsVendorRemark> findAll(Pageable pageable) {
        log.debug("Request to get all RnsVendorRemarks");
        return rnsVendorRemarkRepository.findAll(pageable);
    }

    /**
     * Get one rnsVendorRemark by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RnsVendorRemark findOne(Long id) {
        log.debug("Request to get RnsVendorRemark : {}", id);
        return rnsVendorRemarkRepository.findById(id).get();
    }

    /**
     * Delete the rnsVendorRemark by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RnsVendorRemark : {}", id);
        rnsVendorRemarkRepository.deleteById(id);
    }
    
    
    /**
     * Delete the rnsVendorRemark by id.
     *
     * @param @id the id of the entity
     * @return 
     */
    public List<RnsVendorRemark> findAllBySort(Long qid, String vendorCode) {
       return rnsVendorRemarkRepository.findAllBySort(qid, vendorCode);
    }
    
    
}
