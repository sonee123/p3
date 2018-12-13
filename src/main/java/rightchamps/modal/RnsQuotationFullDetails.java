package rightchamps.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import rightchamps.domain.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RnsQuotation.
 * @param <Rfq>
 */
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationFullDetails<Rfq> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String catgName;

    private String quoteNumber;

    private RnsTypeMaster rnsTypeMaster;

    private Timestamp validity;

    private Timestamp auctionValidity;

    private String internalRemarks;

    private Instant createdOn;

    private String crmRequestNumber;

    private String requestedBy;

    private String pchCode;

    private LocalDate targetPcd;

    private String buyerCode;

    private String buyerName;

    private String merchantRemarks;

    private Instant date;

    private String articleCode;

    private String articleDesc;

    private Boolean published;

    private String template;

    private String createdBy;

    private String createdByName;

    private String workflowStatus;

    private Boolean rfq;

    private Boolean auction;

    private String displayVariant;

    private String eventType;

    private String projectTitle;
    private long noOfLot;

    private User user;

    private Instant rfqPublishDate;

    private Instant auctionPublishDate;

    private String rfqStatus;

    private boolean paused;

    private AuctionPauseDetails auctionPauseDetails;

    private Boolean auctionClose;

    private String sourceTeam;

    private User updatedUser;

    private Instant updatedDate;

    private String approvedFlag;

    private Boolean rfqApplicable;

    private Boolean auctionApplicable;

    @OneToMany
    private Set<RnsQuotationVariantFullDetails> variants = new HashSet<>();

    @ManyToOne
    private RnsCatgMaster rnsCatgCode;

    @ManyToOne
    private RnsPchMaster rnsPchMaster;

    private Auction auctionDetails;

    private  Rfq rfqDetails;

    private String errorMessage;

    private String approvedBy;

    private Instant approvedDate;

    private String closedBy;

    private Instant closedDate;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatgName() {
        return catgName;
    }

    public RnsQuotationFullDetails catgName(String catgName) {
        this.catgName = catgName;
        return this;
    }

    public void setCatgName(String catgName) {
        this.catgName = catgName;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public RnsQuotationFullDetails quoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
        return this;
    }

    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public RnsTypeMaster getRnsTypeMaster() {
        return rnsTypeMaster;
    }

    public void setRnsTypeMaster(RnsTypeMaster rnsTypeMaster) {
        this.rnsTypeMaster = rnsTypeMaster;
    }

    public Timestamp getValidity() {
        return validity;
    }

    public RnsQuotationFullDetails validity(Timestamp validity) {
        this.validity = validity;
        return this;
    }

    public void setValidity(Timestamp validity) {
        this.validity = validity;
    }

    public Timestamp getAuctionValidity() {
        return auctionValidity;
    }

    public RnsQuotationFullDetails auctionValidity(Timestamp auctionValidity) {
        this.auctionValidity = auctionValidity;
        return this;
    }

    public void setAuctionValidity(Timestamp auctionValidity) {
        this.auctionValidity = auctionValidity;
    }

    public String getInternalRemarks() {
        return internalRemarks;
    }

    public RnsQuotationFullDetails internalRemarks(String internalRemarks) {
        this.internalRemarks = internalRemarks;
        return this;
    }

    public void setInternalRemarks(String internalRemarks) {
        this.internalRemarks = internalRemarks;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public RnsQuotationFullDetails createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCrmRequestNumber() {
        return crmRequestNumber;
    }

    public RnsQuotationFullDetails crmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
        return this;
    }

    public void setCrmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public RnsQuotationFullDetails requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getPchCode() {
        return pchCode;
    }

    public RnsQuotationFullDetails pchCode(String pchCode) {
        this.pchCode = pchCode;
        return this;
    }

    public void setPchCode(String pchCode) {
        this.pchCode = pchCode;
    }

    public LocalDate getTargetPcd() {
        return targetPcd;
    }

    public RnsQuotationFullDetails targetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
        return this;
    }

    public void setTargetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public RnsQuotationFullDetails buyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
        return this;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public RnsQuotationFullDetails buyerName(String buyerName) {
        this.buyerName = buyerName;
        return this;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getMerchantRemarks() {
        return merchantRemarks;
    }

    public RnsQuotationFullDetails merchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
        return this;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
    }

    public Instant getDate() {
        return date;
    }

    public RnsQuotationFullDetails date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public RnsQuotationFullDetails articleCode(String articleCode) {
        this.articleCode = articleCode;
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public RnsQuotationFullDetails articleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
        return this;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public Boolean isPublished() {
        return published;
    }

    public Boolean getPublished() {
        return published;
    }

    public RnsQuotationFullDetails published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRfq() {
        return rfq;
    }

    public RnsQuotationFullDetails rfq(Boolean rfq) {
        this.rfq = rfq;
        return this;
    }

    public void setRfq(Boolean rfq) {
        this.rfq = rfq;
    }

    public Boolean getAuction() {
        return auction;
    }

    public RnsQuotationFullDetails auction(Boolean auction) {
        this.auction = auction;
        return this;
    }

    public void setAuction(Boolean auction) {
        this.auction = auction;
    }

    public String getTemplate() {
        return template;
    }

    public RnsQuotationFullDetails template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RnsQuotationFullDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public RnsQuotationFullDetails workflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
        return this;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public Set<RnsQuotationVariantFullDetails> getVariants() {
        return variants;
    }

    public RnsQuotationFullDetails variants(Set<RnsQuotationVariantFullDetails> rnsQuotationVariants) {
        this.variants = rnsQuotationVariants;
        return this;
    }


    public void setVariants(Set<RnsQuotationVariantFullDetails> rnsQuotationVariants) {
        this.variants = rnsQuotationVariants;
    }

    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgCode) {
        this.rnsCatgCode = rnsCatgCode;
    }

    public RnsPchMaster getRnsPchMaster() {
        return rnsPchMaster;
    }

    public void setRnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
    }

    public Auction getAuctionDetails() {
        return auctionDetails;
    }

    public void setAuctionDetails(Auction auctionDetails) {
        this.auctionDetails = auctionDetails;
    }

    public String getDisplayVariant() { return displayVariant; }

    public void setDisplayVariant(String displayVariant) { this.displayVariant = displayVariant; }

    public String getEventType() { return eventType; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

    public long getNoOfLot() {
        return noOfLot;
    }

    public void setNoOfLot(long noOfLot) {
        this.noOfLot = noOfLot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getRfqPublishDate() {
        return rfqPublishDate;
    }

    public void setRfqPublishDate(Instant rfqPublishDate) {
        this.rfqPublishDate = rfqPublishDate;
    }
    
    

    public Rfq getRfqDetails() {
		return rfqDetails;
	}

	public void setRfqDetails(Rfq rfqDetails) {
		this.rfqDetails = rfqDetails;
	}

	public Instant getAuctionPublishDate() { return auctionPublishDate; }

    public void setAuctionPublishDate(Instant auctionPublishDate) { this.auctionPublishDate = auctionPublishDate; }

    public String getRfqStatus() { return rfqStatus; }

    public void setRfqStatus(String rfqStatus) { this.rfqStatus = rfqStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsQuotationFullDetails rnsQuotation = (RnsQuotationFullDetails) o;
        if (rnsQuotation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public AuctionPauseDetails getAuctionPauseDetails() {
        return auctionPauseDetails;
    }

    public void setAuctionPauseDetails(AuctionPauseDetails auctionPauseDetails) {
        this.auctionPauseDetails = auctionPauseDetails;
    }

    public Boolean getAuctionClose() {
        return auctionClose;
    }

    public void setAuctionClose(Boolean auctionClose) {
        this.auctionClose = auctionClose;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getApprovedBy() { return approvedBy; }

    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public Instant getApprovedDate() { return approvedDate; }

    public void setApprovedDate(Instant approvedDate) { this.approvedDate = approvedDate; }

    public String getSourceTeam() {
        return sourceTeam;
    }

    public void setSourceTeam(String sourceTeam) {
        this.sourceTeam = sourceTeam;
    }

    public User getUpdatedUser() { return updatedUser; }

    public void setUpdatedUser(User updatedUser) { this.updatedUser = updatedUser; }

    public Instant getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(Instant updatedDate) { this.updatedDate = updatedDate; }

    public String getApprovedFlag() {
        return approvedFlag;
    }

    public void setApprovedFlag(String approvedFlag) {
        this.approvedFlag = approvedFlag;
    }

    public Boolean getRfqApplicable() {
        return rfqApplicable;
    }

    public void setRfqApplicable(Boolean rfqApplicable) {
        this.rfqApplicable = rfqApplicable;
    }

    public Boolean getAuctionApplicable() {
        return auctionApplicable;
    }

    public void setAuctionApplicable(Boolean auctionApplicable) {
        this.auctionApplicable = auctionApplicable;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public Instant getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Instant closedDate) {
        this.closedDate = closedDate;
    }

    @Override
	public String toString() {
		return "RnsQuotationFullDetails"
				+ " [id=" + id + ", catgName=" + catgName + ", quoteNumber=" + quoteNumber
				+ ", validity=" + validity + ", auctionValidity=" + auctionValidity
				+ ", internalRemarks=" + internalRemarks + ", createdOn=" + createdOn + ", crmRequestNumber="
				+ crmRequestNumber + ", requestedBy=" + requestedBy + ", pchCode=" + pchCode + ", targetPcd="
				+ targetPcd + ", buyerCode=" + buyerCode + ", buyerName=" + buyerName + ", merchantRemarks="
				+ merchantRemarks + ", date=" + date + ", articleCode=" + articleCode + ", articleDesc=" + articleDesc
				+ ", published=" + published + ", template=" + template + ", createdBy=" + createdBy
				+ ", workflowStatus=" + workflowStatus + ", rfq=" + rfq + ", auction=" + auction + ", displayVariant="
				+ displayVariant + ", eventType=" + eventType + ", projectTitle=" + projectTitle + ", variants="
				+ variants + ", rnsCatgCode=" + rnsCatgCode + ", rnsPchMaster=" + rnsPchMaster + ", auctionDetails="
				+ auctionDetails + "]";
	}
}
