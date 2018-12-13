package rightchamps.repository;

import rightchamps.domain.RnsPchMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the RnsPchMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsPchMasterRepository extends JpaRepository<RnsPchMaster, Long> {
    @Query("select rns_pch_master from RnsPchMaster rns_pch_master order by rns_pch_master.id desc")
    List<RnsPchMaster> findAll();

    @Query("select rns_pch_master from RnsPchMaster rns_pch_master where rns_pch_master.pchCode=?1")
    RnsPchMaster findByPchCode(String pchCode);

    /*@Query("select distinct rns_pch_master from RnsPchMaster rns_pch_master left join fetch rns_pch_master.users")
    List<RnsPchMaster> findAllWithEagerRelationships();
    @Query("select distinct rns_pch_master.pchCode from RnsPchMaster rns_pch_master left join rns_pch_master.users user where user.user.login=?1")
    List<String> findAllWithEagerRelationships(String login);

    @Query("select rns_pch_master from RnsPchMaster rns_pch_master left join fetch rns_pch_master.users where rns_pch_master.id =:id")
    RnsPchMaster findOneWithEagerRelationships(@Param("id") Long id);*/

}
