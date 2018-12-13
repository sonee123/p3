package rightchamps.repository;

import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.RnsVendorFavMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsVendorFavMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsVendorFavMasterRepository extends JpaRepository<RnsVendorFavMaster, Long> {

    @Query("select rnsVendorFavMaster.vendorCode from RnsVendorFavMaster rnsVendorFavMaster where rnsVendorFavMaster.createdBy=?1 order by rnsVendorFavMaster.vendorCode")
    List<String> getRnsVendorFavMasterByCreatedBy(String createdBy);

    @Transactional
    @Modifying
    @Query("delete from RnsVendorFavMaster rnsVendorFavMaster where rnsVendorFavMaster.vendorCode=?1 and rnsVendorFavMaster.createdBy=?2")
    void deleteByVendorCodeAndAndCreatedBy(String vendorCode, String createdBy);

}
