package rightchamps.repository;

import rightchamps.domain.RnsEmpMaster;
import rightchamps.domain.RnsQuotation;
import rightchamps.domain.RnsQuotationRemarkDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;


/**
 * Spring Data JPA repository for the RnsQuotationRemarkDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationRemarkDetailsRepository extends JpaRepository<RnsQuotationRemarkDetails, Long> {

    @Query("select rnsQuotationRemarkDetails from RnsQuotationRemarkDetails rnsQuotationRemarkDetails where rnsQuotationRemarkDetails.quoteId=?1 and rnsQuotationRemarkDetails.flowType=?2 and rnsQuotationRemarkDetails.forwardCode is not null order by id")
    List<RnsQuotationRemarkDetails> getRnsQuotationRemarkDetailsByQuoteId(Long quoteId, String flowType);

    @Query("select rnsQuotationRemarkDetails from RnsQuotationRemarkDetails rnsQuotationRemarkDetails where rnsQuotationRemarkDetails.quoteId=?1 and rnsQuotationRemarkDetails.flowType=?2 and (rnsQuotationRemarkDetails.forwardCode is null or rnsQuotationRemarkDetails.forwardCode=\'\') order by id")
    RnsQuotationRemarkDetails getEntryRnsQuotationRemarkDetailsByQuoteId(Long quoteId, String flowType);

    @Query("select coalesce(max(rnsQuotationRemarkDetails.id),0) from RnsQuotationRemarkDetails rnsQuotationRemarkDetails where rnsQuotationRemarkDetails.quoteId=?1 and rnsQuotationRemarkDetails.flowType=?2 and rnsQuotationRemarkDetails.forwardCode is not null")
    Long getMaxRnsQuotationRemarkDetailsByQuoteId(Long quoteId, String flowType);

    @Query("select rnsQuotationRemarkDetails from RnsQuotationRemarkDetails rnsQuotationRemarkDetails where rnsQuotationRemarkDetails.id=?1")
    RnsQuotationRemarkDetails getEntryRnsQuotationRemarkDetailsById(Long id);

    @Query("select rnsQuotation from RnsQuotation rnsQuotation where rnsQuotation.id=?1")
    RnsQuotation getRnsQuotationById(Long id);

    @Query("select rnsEmpMaster from RnsEmpMaster rnsEmpMaster where rnsEmpMaster.empCode=?1")
    RnsEmpMaster getRnsEmpMasterByEmpCode(String empCode);

    @Modifying
    @Transactional
    @Query("update RnsQuotation rnsQuotation  set rnsQuotation.workflowStatus=?1 where rnsQuotation.id=?2")
    void updateRnsQuotationWorkflowStatus(String status, Long id);
}
