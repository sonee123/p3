package rightchamps.repository;

import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.VndrPrice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VndrPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VndrPriceRepository extends JpaRepository<VndrPrice, Long> {

	@Query("select vndrPrice from VndrPrice vndrPrice join vndrPrice.variant variant where variant.id=?1")
	VndrPrice getbyVariant(Long id);

	@Query("select vndrPrice from VndrPrice vndrPrice join vndrPrice.variant variant where variant.id=?1 order by vndrPrice.id")
	List<VndrPrice> getAllbyVariant(Long id);

    @Query("select vndrPrice from VndrPrice vndrPrice join vndrPrice.variant variant where variant.id=?1 and vndrPrice.vendorCode=?2 order by vndrPrice.id")
    List<VndrPrice> getAllbyVariantandVendorCode(Long id, String vendorCode);

    @Query("select vndrPrice from VndrPrice vndrPrice where vndrPrice.id in(select max(vndrPrice.id) from VndrPrice vndrPrice join vndrPrice.variant variant where variant.id=?1 and vndrPrice.vendorCode=?2)")
    VndrPrice getAllbyVariantandVendorCodeMaxPrice(Long id, String vendorCode);

    @Modifying
    @Transactional
    @Query("delete from VndrPrice vndrPrice where vndrPrice.id in(select max(vndrPrice.id) from VndrPrice vndrPrice join vndrPrice.variant variant where variant.id=?1 and vndrPrice.vendorCode=?2)")
    void deleteByVariantandVendorCodeLastPrice(Long id, String vendorCode);
}
