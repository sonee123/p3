package rightchamps.repository;

import rightchamps.domain.RnsRfqPrice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsRfqPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsRfqPriceRepository extends JpaRepository<RnsRfqPrice, Long> {
    @Query("select rnsRfqPrice from RnsRfqPrice rnsRfqPrice where rnsRfqPrice.vendorId=?1")
    RnsRfqPrice getByVendorId(Long vendorId);
}
