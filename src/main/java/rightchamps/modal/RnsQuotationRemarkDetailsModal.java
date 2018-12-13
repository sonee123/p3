package rightchamps.modal;

import rightchamps.domain.RnsEmpMaster;
import rightchamps.domain.RnsQuotationRemarkDetails;
import rightchamps.domain.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class RnsQuotationRemarkDetailsModal implements Serializable {
    private Long quoteId;
    private Long id;
    private LocalDate authDate;
    private User empCode;
    private boolean allowEntry;
    private String allowWorkFlow;
    List<RnsQuotationRemarkDetails> rnsQuotationRemarkDetailsList;
    private String errorMessage;
    private String approvalType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public List<RnsQuotationRemarkDetails> getRnsQuotationRemarkDetailsList() {
        return rnsQuotationRemarkDetailsList;
    }

    public void setRnsQuotationRemarkDetailsList(List<RnsQuotationRemarkDetails> rnsQuotationRemarkDetailsList) {
        this.rnsQuotationRemarkDetailsList = rnsQuotationRemarkDetailsList;
    }

    public LocalDate getAuthDate() {
        return authDate;
    }

    public void setAuthDate(LocalDate authDate) {
        this.authDate = authDate;
    }

    public User getEmpCode() {
        return empCode;
    }

    public void setEmpCode(User empCode) {
        this.empCode = empCode;
    }

    public boolean isAllowEntry() {
        return allowEntry;
    }

    public void setAllowEntry(boolean allowEntry) {
        this.allowEntry = allowEntry;
    }

    public String getAllowWorkFlow() {
        return allowWorkFlow;
    }

    public void setAllowWorkFlow(String allowWorkFlow) {
        this.allowWorkFlow = allowWorkFlow;
    }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }
}
