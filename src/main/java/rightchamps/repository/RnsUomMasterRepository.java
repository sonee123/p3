package rightchamps.repository;

import rightchamps.domain.RnsUomMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsUomMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsUomMasterRepository extends JpaRepository<RnsUomMaster, Long> {
    @Query("select rnsUomMaster from RnsUomMaster rnsUomMaster order by rnsUomMaster.id desc")
    List<RnsUomMaster> findAll();

    @Query("select rnsUomMaster from RnsUomMaster rnsUomMaster where rnsUomMaster.uomCode=?1")
    RnsUomMaster findByUomCode(String uomCode);
}
