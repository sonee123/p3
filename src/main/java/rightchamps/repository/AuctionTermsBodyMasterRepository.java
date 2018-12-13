package rightchamps.repository;

import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.AuctionTermsBodyMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the AuctionTermsBodyMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuctionTermsBodyMasterRepository extends JpaRepository<AuctionTermsBodyMaster, Long> {

    @Transactional
    @Modifying
    @Query("delete from AuctionTermsBodyMaster auctionTermsBodyMaster where auctionTermsBodyMaster.termId=?1")
    void deleteByTermId(Long termId);

    @Query("select auctionTermsBodyMaster from AuctionTermsBodyMaster auctionTermsBodyMaster where auctionTermsBodyMaster.termId=?1 order by auctionTermsBodyMaster.id")
    List<AuctionTermsBodyMaster> findAllByTermId(Long termId);
}
