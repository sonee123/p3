package rightchamps.repository;

import rightchamps.domain.RnsRefrMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsRefrMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsRefrMasterRepository extends JpaRepository<RnsRefrMaster, Long> {

}
