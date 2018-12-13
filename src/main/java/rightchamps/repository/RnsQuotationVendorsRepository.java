package rightchamps.repository;

import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.RnsQuotationVendors;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the RnsQuotationVendors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationVendorsRepository extends JpaRepository<RnsQuotationVendors, Long> {

    @Query("select rns_quotation_vendors from RnsQuotationVendors rns_quotation_vendors where rns_quotation_vendors.vendorCode = ?#{principal.username}")
    List<RnsQuotationVendors> findByUserIdIsCurrentUser();

    @Query("select rns_quotation_vendors from RnsQuotationVendors rns_quotation_vendors where rns_quotation_vendors.vendorQuotation.id = ?1 and rns_quotation_vendors.vendorCode = ?#{principal.username} order by rns_quotation_vendors.id")
    List<RnsQuotationVendors> findByAucionAndUserIdIsCurrentUser(Long id);

    @Query("select rns_quotation_vendors from RnsQuotationVendors rns_quotation_vendors where rns_quotation_vendors.vendorQuotation.id = ?1 order by rns_quotation_vendors.variant.id")
    List<RnsQuotationVendors> findByVendorQuotationId(Long id);

    @Query("select rns_quotation_vendors from RnsQuotationVendors rns_quotation_vendors where rns_quotation_vendors.vendorQuotation.id = ?1 order by rns_quotation_vendors.vendorCode, rns_quotation_vendors.variant.id")
    List<RnsQuotationVendors> findByVendorQuotationIdOrderByVendorCode(Long id);

    @Query("select rns_quotation_vendors from RnsQuotationVendors rns_quotation_vendors where rns_quotation_vendors.vendorQuotation.id = ?1 and rns_quotation_vendors.auctionApplicable=1 order by rns_quotation_vendors.variant.id")
    List<RnsQuotationVendors> findByAcceptedVendorsByQuotationId(Long id);

    @Query("select rnsQuotationVendors from RnsQuotationVendors rnsQuotationVendors where rnsQuotationVendors.variant.id=?1 and rnsQuotationVendors.vendorQuotation.id=?2 and rnsQuotationVendors.vendorCode=?3")
    RnsQuotationVendors getRnsQuotationVendorsByVariantAndVendorQuotationAndVendorCode(Long variantId, Long qid, String vendorCode);

    @Query("select rnsQuotationVendors from RnsQuotationVendors rnsQuotationVendors where rnsQuotationVendors.variant.id=?1")
    List<RnsQuotationVendors> getRnsQuotationVendorsByVariant(Long variantId);

    @Modifying
    @Transactional
    @Query("delete from RnsQuotationVendors rnsQuotationVendors where rnsQuotationVendors.variant.id=?1")
    void deleteRnsQuotationVendorsByVariant(Long variantId);

}
