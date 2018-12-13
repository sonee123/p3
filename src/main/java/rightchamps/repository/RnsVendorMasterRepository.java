package rightchamps.repository;

import rightchamps.domain.RnsVendorMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the RnsVendorMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsVendorMasterRepository extends JpaRepository<RnsVendorMaster, Long> {

    /*@Query("select rns_vendor_master from RnsVendorMaster rns_vendor_master where rns_vendor_master.user.login = ?#{principal.username}")
    List<RnsVendorMaster> findByUserIsCurrentUser();*/


    @Query("select rnsVendorMaster from RnsVendorMaster rnsVendorMaster where rnsVendorMaster.vendorCode is not null order by rnsVendorMaster.id desc")
    List<RnsVendorMaster> findAll();

    @Query("select rns_vendor_master from RnsVendorMaster rns_vendor_master where (rns_vendor_master.vendorCode like %?1% or rns_vendor_master.vendorName like %?2%) and rns_vendor_master.vendorCode is not null ORDER BY rns_vendor_master.vendorName")
    List<RnsVendorMaster> findAllAuthority(String login, String firstName);

}
