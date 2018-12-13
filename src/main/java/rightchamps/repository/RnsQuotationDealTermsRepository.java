package rightchamps.repository;

import rightchamps.domain.RnsQuotationDealTerms;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsQuotationDealTerms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationDealTermsRepository extends JpaRepository<RnsQuotationDealTerms, Long> {

}
