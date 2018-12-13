package rightchamps.repository;

import rightchamps.domain.RnsPayTermsMaster;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsPayTermsMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsPayTermsMasterRepository extends JpaRepository<RnsPayTermsMaster, Long> {

	@Query("select rns_pay_terms_master from RnsPayTermsMaster rns_pay_terms_master order by rns_pay_terms_master.id desc")
	List<RnsPayTermsMaster> findSortBy();

    @Query("select rns_pay_terms_master from RnsPayTermsMaster rns_pay_terms_master where rns_pay_terms_master.payTermsCode=?1")
    RnsPayTermsMaster findByPayTermsCode(String payTermsCode);

}
