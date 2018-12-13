package rightchamps.repository;

import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.RnsQuotation;
import rightchamps.domain.RnsQuotationVariant;
import org.springframework.stereotype.Repository;
import rightchamps.domain.AuctionVrnt;
import org.springframework.data.jpa.repository.*;
import rightchamps.modal.VariantOverTime;

import java.util.List;

/**
 * Spring Data JPA repository for the RnsQuotationVariant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationVariantRepository extends JpaRepository<RnsQuotationVariant, Long> {


	@Query("select auctionVrnt from AuctionVrnt auctionVrnt  join auctionVrnt.variant variant where variant.id=?1")
	List<AuctionVrnt> getAuctionVrntbyVariants(Long id);

    @Query("select rnsQuotationVariant from RnsQuotationVariant rnsQuotationVariant join rnsQuotationVariant.quotation quotation where rnsQuotationVariant.title=?1 and quotation.id=?2")
    RnsQuotationVariant getRnsQuotationVariantByTitleAndqAndQuotation(String title, Long qid);

    @Query("select rnsQuotationVariant from RnsQuotationVariant rnsQuotationVariant join rnsQuotationVariant.quotation quotation where quotation.id=?1 order by rnsQuotationVariant.id")
    List<RnsQuotationVariant> getRnsQuotationVariantByQuotation(Long qid);


    @Query("select quotation from RnsQuotationVariant rnsQuotationVariant join rnsQuotationVariant.quotation quotation where rnsQuotationVariant.id=?1")
    RnsQuotation getRnsQuotationByRnsQuotationVariant(Long qid);

    @Query("select COALESCE(sum(rnsQuotationVariant.overTime), 0) from RnsQuotationVariant rnsQuotationVariant join rnsQuotationVariant.quotation quotation where quotation.id=?1 and rnsQuotationVariant.id<?2")
    Integer getOverTimebyRnsQuotationVariant(Long qid, Long id);

    @Query("select COALESCE(COUNT(rnsQuotationVariant.title), 0) from RnsQuotationVariant rnsQuotationVariant join rnsQuotationVariant.quotation quotation where quotation.id=?1 and rnsQuotationVariant.id<?2")
    Integer getTitlebyRnsQuotationVariant(Long qid, Long id);

    @Modifying
    @Transactional
    @Query("delete from RnsQuotationVariant rnsQuotationVariant where rnsQuotationVariant.id=?1")
    void deleteById(Long id);

}
