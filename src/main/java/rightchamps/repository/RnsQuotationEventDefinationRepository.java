package rightchamps.repository;

import rightchamps.domain.RnsQuotationEventDefination;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsQuotationEventDefination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationEventDefinationRepository extends JpaRepository<RnsQuotationEventDefination, Long> {

}
