package rightchamps.repository;

import rightchamps.domain.RnsQuotationVariant;
import rightchamps.domain.RnsQuotationVendors;
import rightchamps.domain.RnsVendorRemark;
import org.springframework.stereotype.Repository;
import rightchamps.domain.RnsQuotation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RnsQuotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationRepository extends JpaRepository<RnsQuotation, Long> {

    @Query("select quotation from RnsQuotation quotation where quotation.id=?1")
    List<RnsQuotation> getallRnsVendorMaster(Long id);

    @Query("select quotation from RnsQuotation quotation order by quotation.id desc")
    List<RnsQuotation> findAllBySort();

    @Query("select remarks from RnsVendorRemark remarks where quotation.id=?1")
    List<RnsVendorRemark> getVendorRemarks(Long quotationId);

    @Query("select variant from RnsQuotationVariant variant join variant.quotation quotation where quotation.id=?1  order by variant.id")
    List<RnsQuotationVariant> getRnsQuotationVariantsList(Long id);


    @Query("select quotationVendors from RnsQuotationVendors quotationVendors where quotationVendors.vendorCode=?1")
    List<RnsQuotationVariant> getRnsQuotationVariantsByVendorId();


    @Query("select quotationVendors from RnsQuotationVendors quotationVendors join quotationVendors.vendorQuotation vendorQuotation where vendorQuotation.id=?1 order by quotationVendors.variant.id")
    List<RnsQuotationVendors> getRnsQuotationVendorsList(Long id);

    @Query("select quotationVendors from RnsQuotationVendors quotationVendors join quotationVendors.variant variant where variant.id=?1")
    List<RnsQuotationVendors> getRnsQuotationVendorsByVariantList(Long id);


    @Query("select quotation from RnsQuotationVendors quotationVendors join quotationVendors.vendorQuotation quotation where quotationVendors.vendorCode=?1 and quotation.rfq=1 order by quotationVendors.vendorCode.id desc")
    List<RnsQuotation> findByVendorId(String vendorCode);

    @Query("select  quotation from RnsQuotationVendors quotationVendors join quotationVendors.vendorQuotation quotation  where quotationVendors.vendorCode=?1 and quotation.auction=1 order by quotation.auction.id desc")
    List<RnsQuotation> findAuctionByVendorId(String vendorCode);


    @Query("select quotation from RnsQuotation quotation where quotation.id like ?1 and quotation.projectTitle like ?2 order by quotation.id desc")
    List<RnsQuotation> findAllBySort(String id, String projectTitle);

    @Query("select count(quotation) from RnsQuotation quotation where quotation.template=?1")
    Long countByTemplate(String template);
}
