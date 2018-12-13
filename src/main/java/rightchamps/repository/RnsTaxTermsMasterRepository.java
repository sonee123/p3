package rightchamps.repository;

import rightchamps.domain.RnsTaxTermsMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsTaxTermsMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsTaxTermsMasterRepository extends JpaRepository<RnsTaxTermsMaster, Long> {

	 @Query("select rns_tax_terms_master from RnsTaxTermsMaster rns_tax_terms_master where rns_tax_terms_master.taxTermsCode=?1")
	 RnsTaxTermsMaster findByTaxTermsCode(String taxTermsCode);

    @Query("select rnsTaxTermsMaster from RnsTaxTermsMaster rnsTaxTermsMaster order by rnsTaxTermsMaster.id")
    List<RnsTaxTermsMaster> findAll();
}
