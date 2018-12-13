package rightchamps.repository;

import rightchamps.domain.RnsForwardTypeMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsForwardTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsForwardTypeMasterRepository extends JpaRepository<RnsForwardTypeMaster, Long> {
    RnsForwardTypeMaster findByCode(String code);
}
