package rightchamps.repository;

import rightchamps.domain.RnsRefrDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsRefrDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsRefrDetailsRepository extends JpaRepository<RnsRefrDetails, Long> {

}
