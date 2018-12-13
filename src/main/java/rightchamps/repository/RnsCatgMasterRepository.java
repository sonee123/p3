package rightchamps.repository;

import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.RnsQuotation;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the RnsCatgMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsCatgMasterRepository extends JpaRepository<RnsCatgMaster, Long> {
    @Query("select distinct rns_catg_master from RnsCatgMaster rns_catg_master left join fetch rns_catg_master.users user where user.user.login=?1")
    List<RnsCatgMaster> findAllWithEagerRelationshipsList(String login);

    @Query("select distinct rns_catg_master.id from RnsCatgMaster rns_catg_master left join rns_catg_master.users user where user.user.login=?1")
    List<Long> findAllWithEagerRelationships(String login);

    @Query("select rns_catg_master from RnsCatgMaster rns_catg_master left join fetch rns_catg_master.users where rns_catg_master.id =:id")
    RnsCatgMaster findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select rns_catg_master from RnsCatgMaster rns_catg_master order by rns_catg_master.id desc")
    List<RnsCatgMaster> findAllBySort();

    @Query("select rns_catg_master from RnsCatgMaster rns_catg_master where rns_catg_master.catgCode=?1")
    RnsCatgMaster findBycatgCode(String catgCode);

    @Query("select rnsCatgMaster from RnsCatgMaster rnsCatgMaster order by rnsCatgMaster.id desc")
    List<RnsCatgMaster> findAll();
}
