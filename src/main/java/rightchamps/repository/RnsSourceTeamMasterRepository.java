package rightchamps.repository;

import rightchamps.domain.RnsSourceTeamMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsSourceTeamMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsSourceTeamMasterRepository extends JpaRepository<RnsSourceTeamMaster, Long> {
    @Query("select rnsSourceTeamMaster from RnsSourceTeamMaster rnsSourceTeamMaster join RnsSourceTeamDtl rnsSourceTeamDtl on rnsSourceTeamMaster.id=rnsSourceTeamDtl.masterId where rnsSourceTeamMaster.flag='Y' and rnsSourceTeamDtl.teamUser.login=?1 order by rnsSourceTeamMaster.description")
    List<RnsSourceTeamMaster> findAllByActive(String login);

    @Query("select rnsSourceTeamMaster from RnsSourceTeamMaster rnsSourceTeamMaster order by rnsSourceTeamMaster.id desc")
    List<RnsSourceTeamMaster> findAll();
}
