package rightchamps.repository;

import rightchamps.domain.RnsTypeMaster;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsTypeMasterRepository extends JpaRepository<RnsTypeMaster, Long> {

	@Query("select rns_type_master from RnsTypeMaster  rns_type_master order by rns_type_master.id desc")
	List<RnsTypeMaster> findAllBySort();

	@Query("select rnsTypeMaster from RnsTypeMaster rnsTypeMaster where rnsTypeMaster.typeCode=?1 and rnsTypeMaster.rnsCatgCode.id=?2")
    RnsTypeMaster findByTypeCodeAndRnsCatgCode(String typeCode, Long catgId);

	@Query("select rnsTypeMaster from RnsTypeMaster rnsTypeMaster where rnsTypeMaster.rnsCatgCode.id=?1")
	List<RnsTypeMaster> findAllByRnsCatgCode(Long catgId);

    @Query("select rnsTypeMaster from RnsTypeMaster rnsTypeMaster where rnsTypeMaster.typeCode=?1")
    List<RnsTypeMaster> findByTypeCode(String typeCode);
}
