package rightchamps.repository;

import rightchamps.domain.AuctionTermsMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the AuctionTermsMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuctionTermsMasterRepository extends JpaRepository<AuctionTermsMaster, Long> {
    @Override
    @Query("select auctionTermsMaster from AuctionTermsMaster auctionTermsMaster order by auctionTermsMaster.id desc")
    List<AuctionTermsMaster> findAll();

    @Query("select auctionTermsMaster from AuctionTermsMaster auctionTermsMaster where auctionTermsMaster.rnsCatgMaster.id=?1 and auctionTermsMaster.quoteType.typeCode=?2 and auctionTermsMaster.sourceTeamId.id=?3")
    AuctionTermsMaster findByRnsCatgMasterAndQuoteTypeAndSourceTeamId(Long catdId, String quoteType, Long sourceTeamId);
}
