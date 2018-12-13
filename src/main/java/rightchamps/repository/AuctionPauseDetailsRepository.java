package rightchamps.repository;

import rightchamps.domain.AuctionPauseDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuctionPauseDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuctionPauseDetailsRepository extends JpaRepository<AuctionPauseDetails, Long> {

    @Query("select auctionPauseDetails from AuctionPauseDetails auctionPauseDetails where auctionPauseDetails.quotationId=?1 and auctionPauseDetails.pauseEndDate is null")
    AuctionPauseDetails findAuctionPauseDetailsByQuotationId(Long quotationId);
}
