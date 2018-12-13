package rightchamps.repository;

import rightchamps.domain.RnsEmpMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsEmpMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsEmpMasterRepository extends JpaRepository<RnsEmpMaster, Long> {
    @Query("select rnsEmpMaster from RnsEmpMaster rnsEmpMaster where rnsEmpMaster.empCode=?1")
    RnsEmpMaster findByEmpCode(String empCode);
}
