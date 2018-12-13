package rightchamps.repository;

import rightchamps.domain.Auction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Auction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
	 @Query("select auction from Auction auction where auction.quotationId=?1")
    Auction getAuctionByQuotationId(Long id);


}
 