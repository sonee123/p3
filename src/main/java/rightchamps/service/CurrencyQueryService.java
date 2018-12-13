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

import rightchamps.domain.Currency;
import rightchamps.domain.*; // for static metamodels
import rightchamps.repository.CurrencyRepository;
import rightchamps.service.dto.CurrencyCriteria;


/**
 * Service for executing complex queries for Currency entities in the database.
 * The main input is a {@link CurrencyCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Currency} or a {@link Page} of {@link Currency} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurrencyQueryService extends QueryService<Currency> {

    private final Logger log = LoggerFactory.getLogger(CurrencyQueryService.class);


    private final CurrencyRepository currencyRepository;

    public CurrencyQueryService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Return a {@link List} of {@link Currency} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Currency> findByCriteria(CurrencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Currency> specification = createSpecification(criteria);
        return currencyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Currency} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Currency> findByCriteria(CurrencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Currency> specification = createSpecification(criteria);
        return currencyRepository.findAll(specification, page);
    }

    /**
     * Function to convert CurrencyCriteria to a {@link Specifications}
     */
    private Specifications<Currency> createSpecification(CurrencyCriteria criteria) {
        Specifications<Currency> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Currency_.id));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrency(), Currency_.currency));
            }
            if (criteria.getSymbol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymbol(), Currency_.symbol));
            }
            if (criteria.getExchange_rate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExchange_rate(), Currency_.exchange_rate));
            }
        }
        return specification;
    }

}
