package rightchamps.repository;

import rightchamps.domain.RnsQuotationCrmRequest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsQuotationCrmRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationCrmRequestRepository extends JpaRepository<RnsQuotationCrmRequest, Long> {

}
