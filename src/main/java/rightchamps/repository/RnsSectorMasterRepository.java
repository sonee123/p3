package rightchamps.repository;

import rightchamps.domain.RnsSectorMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsSectorMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsSectorMasterRepository extends JpaRepository<RnsSectorMaster, Long> {

}
