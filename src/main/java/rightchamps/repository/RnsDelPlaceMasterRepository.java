package rightchamps.repository;

import rightchamps.domain.RnsDelPlaceMaster;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsDelPlaceMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsDelPlaceMasterRepository extends JpaRepository<RnsDelPlaceMaster, Long> {

	@Query("select rns_del_place_master from RnsDelPlaceMaster rns_del_place_master where rns_del_place_master.code=?1")
	RnsDelPlaceMaster findByDelPlaceCode(String code);

	@Query("select rns_del_place_master from RnsDelPlaceMaster rns_del_place_master order by rns_del_place_master.id desc")
    List<RnsDelPlaceMaster> findAll();

}
