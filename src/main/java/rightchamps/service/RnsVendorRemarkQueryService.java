package rightchamps.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import rightchamps.domain.RnsVendorRemark;
import rightchamps.domain.*; // for static metamodels
import rightchamps.repository.RnsVendorRemarkRepository;
import rightchamps.service.dto.RnsVendorRemarkCriteria;


/**
 * Service for executing complex queries for RnsVendorRemark entities in the database.
 * The main input is a {@link RnsVendorRemarkCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RnsVendorRemark} or a {@link Page} of {@link RnsVendorRemark} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RnsVendorRemarkQueryService extends QueryService<RnsVendorRemark> {

    private final Logger log = LoggerFactory.getLogger(RnsVendorRemarkQueryService.class);


    private final RnsVendorRemarkRepository rnsVendorRemarkRepository;

    public RnsVendorRemarkQueryService(RnsVendorRemarkRepository rnsVendorRemarkRepository) {
        this.rnsVendorRemarkRepository = rnsVendorRemarkRepository;
    }

    /**
     * Return a {@link List} of {@link RnsVendorRemark} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RnsVendorRemark> findByCriteria(RnsVendorRemarkCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<RnsVendorRemark> specification = createSpecification(criteria);
        return rnsVendorRemarkRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RnsVendorRemark} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RnsVendorRemark> findByCriteria(RnsVendorRemarkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<RnsVendorRemark> specification = createSpecification(criteria);
        return rnsVendorRemarkRepository.findAll(specification, page);
    }

    /**
     * Function to convert RnsVendorRemarkCriteria to a {@link Specifications}
     */
    private Specifications<RnsVendorRemark> createSpecification(RnsVendorRemarkCriteria criteria) {
        Specifications<RnsVendorRemark> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RnsVendorRemark_.id));
            }
            if (criteria.getRemarkText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarkText(), RnsVendorRemark_.remarkText));
            }
            if (criteria.getVendorEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorEmail(), RnsVendorRemark_.vendorEmail));
            }
            if (criteria.getStaffEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStaffEmail(), RnsVendorRemark_.staffEmail));
            }
            if (criteria.getFromEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFromEmail(), RnsVendorRemark_.fromEmail));
            }
            if (criteria.getToEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToEmail(), RnsVendorRemark_.toEmail));
            }
            if (criteria.getRead() != null) {
                specification = specification.and(buildSpecification(criteria.getRead(), RnsVendorRemark_.read));
            }
            if (criteria.getQuotationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getQuotationId(), RnsVendorRemark_.quotation, RnsQuotation_.id));
            }
        }
        return specification;
    }

}
