package rightchamps.repository;

import rightchamps.domain.RnsUpchargeMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsUpchargeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsUpchargeMasterRepository extends JpaRepository<RnsUpchargeMaster, Long> {
    @Query("select rnsUpchargeMaster from RnsUpchargeMaster rnsUpchargeMaster order by rnsUpchargeMaster.id desc")
    List<RnsUpchargeMaster> findAll();
}
