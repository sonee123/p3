package rightchamps.repository;

import rightchamps.domain.RnsEmpLinkMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsEmpLinkMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsEmpLinkMasterRepository extends JpaRepository<RnsEmpLinkMaster, Long> {
    @Query("select linkmaster from RnsEmpLinkMaster linkmaster where linkmaster.empCode.login=?1 and linkmaster.forwardEmpType=?2")
    List<RnsEmpLinkMaster> getRnsEmpLinkMastersByEmpCodeWithFlag(String empCode, String forwardEmpType);

    @Query("select linkmaster from RnsEmpLinkMaster linkmaster where linkmaster.empCode.login=?1 and linkmaster.forwardEmpType=?2 and  linkmaster.forwardEmpCode.login=?3")
    RnsEmpLinkMaster getRnsEmpLinkMastersByEmpCodeWithFlag(String empCode, String forwardEmpType, String forwardEmpCode);

    @Query("select distinct rnsEmpLinkMaster.forwardEmpType, rnsForwardTypeMaster.description FROM RnsEmpLinkMaster rnsEmpLinkMaster, RnsForwardTypeMaster rnsForwardTypeMaster where rnsEmpLinkMaster.forwardEmpType=rnsForwardTypeMaster.code and rnsEmpLinkMaster.empCode.login =?1")
    List<Object[]> getDistinctByForwardEmpTypeByEmpCode(String empCode);

    @Query("select rnsEmpLinkMaster from RnsEmpLinkMaster rnsEmpLinkMaster order by rnsEmpLinkMaster.id desc")
    List<RnsEmpLinkMaster> findAll();
}
