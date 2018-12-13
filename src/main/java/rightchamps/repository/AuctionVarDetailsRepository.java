package rightchamps.repository;

import rightchamps.domain.AuctionVarDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the AuctionVarDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuctionVarDetailsRepository extends JpaRepository<AuctionVarDetails, Long> {
    @Query("select auctionVarDetails from AuctionVarDetails auctionVarDetails where auctionVarDetails.quotationId=?1 order by auctionVarDetails.id")
    List<AuctionVarDetails> getAuctionVarDetailsByQuotation(Long qid);

    @Query("select auctionVarDetails from AuctionVarDetails auctionVarDetails where auctionVarDetails.quotationId=?1 and auctionVarDetails.variantId>=?2 order by auctionVarDetails.id")
    List<AuctionVarDetails> getAuctionVarDetailsByQuotationAndVariant(Long qid, Long vid);

    @Query("select auctionVarDetails from AuctionVarDetails auctionVarDetails where auctionVarDetails.variantId=?1 order by auctionVarDetails.id")
    AuctionVarDetails getAuctionVarDetailsByVariant(Long vid);

}
