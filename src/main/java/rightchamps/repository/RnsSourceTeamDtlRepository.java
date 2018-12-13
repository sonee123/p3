package rightchamps.repository;

import rightchamps.domain.RnsSourceTeamDtl;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsSourceTeamDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsSourceTeamDtlRepository extends JpaRepository<RnsSourceTeamDtl, Long> {

    @Query("select rnsSourceTeamDtl from RnsSourceTeamDtl rnsSourceTeamDtl order by rnsSourceTeamDtl.id desc")
    List<RnsSourceTeamDtl> findAll();

    @Query("select rnsSourceTeamDtl from RnsSourceTeamDtl rnsSourceTeamDtl where rnsSourceTeamDtl.teamUser.login=?1 and rnsSourceTeamDtl.masterId=?2")
    RnsSourceTeamDtl findByUserIdAndTeam(String login, Long masterId);
}
