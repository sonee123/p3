package rightchamps.repository;

import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.AuctionVrnt;
import org.springframework.stereotype.Repository;
import rightchamps.domain.RnsQuotationVariant;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuctionVrnt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuctionVrntRepository extends JpaRepository<AuctionVrnt, Long> {

	@Query("select auctionVrnt from AuctionVrnt auctionVrnt join auctionVrnt.variant variant where variant.id=?1")
	AuctionVrnt getAuctionVrntbyVariant(Long id);

    @Modifying
	@Transactional
    @Query("delete from AuctionVrnt auctionVrnt where auctionVrnt.variant.id=?1")
    void deleteAuctionVrntbyVariant(Long variantId);
}
