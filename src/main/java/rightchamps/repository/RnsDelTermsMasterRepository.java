package rightchamps.repository;

import rightchamps.domain.RnsDelTermsMaster;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsDelTermsMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsDelTermsMasterRepository extends JpaRepository<RnsDelTermsMaster, Long> {

	@Query("select rns_del_terms_master from RnsDelTermsMaster rns_del_terms_master order by rns_del_terms_master.id desc")
	List<RnsDelTermsMaster> findSortBy();

    @Query("select rns_del_terms_master from RnsDelTermsMaster rns_del_terms_master where rns_del_terms_master.delTermsCode=?1")
    RnsDelTermsMaster findByDelTermsCode(String delTermsCode);

}
