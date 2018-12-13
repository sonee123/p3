package rightchamps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
 */
public class RnsQuotationCustomVM implements Serializable {


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

    private String workflowStatus;

    private Boolean rfq;

    private Boolean auction;

    private Boolean auctionValidate;

    private Boolean rfqValidate;

    private String projectTitle;

    private User user;

    private Boolean auctionClose;

    private String approvedBy;

    private Instant approvedDate;

    private String approvedFlag;

    private Boolean rfqApplicable;

    private Boolean auctionApplicable;

    private Boolean rfqActive;
    private String rfqMessage;
    private String rfqStatus;
    private String rfqIcon;

    private Boolean rfbActive;
    private String rfbMessage;
    private String rfbStatus;
    private String rfbIcon;

    private Boolean workflowActive;
    private String workflowMessage;
    private String workflowIcon;

    @OneToMany(mappedBy = "quotation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RnsQuotationVariant> variants = new HashSet<>();

    @OneToMany(mappedBy = "vendorQuotation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RnsQuotationVendors> quotationVendors = new HashSet<>();

    @ManyToOne
    private RnsCatgMaster rnsCatgCode;

    @ManyToOne
    private RnsPchMaster rnsPchMaster;

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

    public RnsQuotationCustomVM  catgName(String catgName) {
        this.catgName = catgName;
        return this;
    }

    public void setCatgName(String catgName) {
        this.catgName = catgName;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public RnsQuotationCustomVM  quoteNumber(String quoteNumber) {
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

    public RnsQuotationCustomVM  validity(Timestamp validity) {
        this.validity = validity;
        return this;
    }

    public void setValidity(Timestamp validity) {
        this.validity = validity;
    }

    public Timestamp getAuctionValidity() {
        return auctionValidity;
    }

    public RnsQuotationCustomVM  auctionValidity(Timestamp auctionValidity) {
        this.auctionValidity = auctionValidity;
        return this;
    }

    public void setAuctionValidity(Timestamp auctionValidity) {
        this.auctionValidity = auctionValidity;
    }

    public String getInternalRemarks() {
        return internalRemarks;
    }

    public RnsQuotationCustomVM  internalRemarks(String internalRemarks) {
        this.internalRemarks = internalRemarks;
        return this;
    }

    public void setInternalRemarks(String internalRemarks) {
        this.internalRemarks = internalRemarks;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public RnsQuotationCustomVM  createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCrmRequestNumber() {
        return crmRequestNumber;
    }

    public RnsQuotationCustomVM  crmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
        return this;
    }

    public void setCrmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public RnsQuotationCustomVM  requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getPchCode() {
        return pchCode;
    }

    public RnsQuotationCustomVM  pchCode(String pchCode) {
        this.pchCode = pchCode;
        return this;
    }

    public void setPchCode(String pchCode) {
        this.pchCode = pchCode;
    }

    public LocalDate getTargetPcd() {
        return targetPcd;
    }

    public RnsQuotationCustomVM  targetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
        return this;
    }

    public void setTargetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public RnsQuotationCustomVM  buyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
        return this;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public RnsQuotationCustomVM  buyerName(String buyerName) {
        this.buyerName = buyerName;
        return this;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getMerchantRemarks() {
        return merchantRemarks;
    }

    public RnsQuotationCustomVM  merchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
        return this;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
    }

    public Instant getDate() {
        return date;
    }

    public RnsQuotationCustomVM  date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public RnsQuotationCustomVM  articleCode(String articleCode) {
        this.articleCode = articleCode;
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public RnsQuotationCustomVM  articleDesc(String articleDesc) {
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

    public RnsQuotationCustomVM  published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRfq() {
        return rfq;
    }

    public RnsQuotationCustomVM  rfq(Boolean rfq) {
        this.rfq = rfq;
        return this;
    }

    public void setRfq(Boolean rfq) {
        this.rfq = rfq;
    }

    public Boolean getAuction() {
        return auction;
    }

    public RnsQuotationCustomVM  auction(Boolean auction) {
        this.auction = auction;
        return this;
    }

    public void setAuction(Boolean auction) {
        this.auction = auction;
    }

    public Boolean getAuctionValidate() {
        return auctionValidate;
    }

    public RnsQuotationCustomVM  auctionValidate(Boolean auctionValidate) {
        this.auctionValidate = auctionValidate;
        return this;
    }

    public void setAuctionValidate(Boolean auctionValidate) {
        this.auctionValidate = auctionValidate;
    }

    public Boolean getRfqValidate() {
        return rfqValidate;
    }

    public RnsQuotationCustomVM  rfqValidate(Boolean rfqValidate) {
        this.rfqValidate = rfqValidate;
        return this;
    }

    public void setRfqValidate(Boolean rfqValidate) {
        this.rfqValidate = rfqValidate;
    }

    public String getTemplate() {
        return template;
    }

    public RnsQuotationCustomVM  template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RnsQuotationCustomVM  createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public RnsQuotationCustomVM  workflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
        return this;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public Set<RnsQuotationVariant> getVariants() {
        return variants;
    }

    public RnsQuotationCustomVM  variants(Set<RnsQuotationVariant> rnsQuotationVariants) {
        this.variants = rnsQuotationVariants;
        return this;
    }


    public void setVariants(Set<RnsQuotationVariant> rnsQuotationVariants) {
        this.variants = rnsQuotationVariants;
    }

    public Set<RnsQuotationVendors> getQuotationVendors() {
        return quotationVendors;
    }

    public RnsQuotationCustomVM  quotationVendors(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotationVendors = rnsQuotationVendors;
        return this;
    }

    public void setQuotationVendors(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotationVendors = rnsQuotationVendors;
    }

    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public RnsQuotationCustomVM  rnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
        return this;
    }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
    }

    public RnsPchMaster getRnsPchMaster() {
        return rnsPchMaster;
    }

    public RnsQuotationCustomVM  rnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
        return this;
    }

    public void setRnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getProjectTitle() {
		return projectTitle;
	}

    public RnsQuotationCustomVM projectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
        return this;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Instant getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Instant approvedDate) {
        this.approvedDate = approvedDate;
    }

    public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

    public Boolean getRfqActive() {
        return rfqActive;
    }

    public void setRfqActive(Boolean rfqActive) {
        this.rfqActive = rfqActive;
    }

    public String getRfqMessage() {
        return rfqMessage;
    }

    public void setRfqMessage(String rfqMessage) {
        this.rfqMessage = rfqMessage;
    }

    public String getRfqStatus() {
        return rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public Boolean getRfbActive() {
        return rfbActive;
    }

    public void setRfbActive(Boolean rfbActive) {
        this.rfbActive = rfbActive;
    }

    public String getRfbMessage() {
        return rfbMessage;
    }

    public void setRfbMessage(String rfbMessage) {
        this.rfbMessage = rfbMessage;
    }

    public String getRfbStatus() {
        return rfbStatus;
    }

    public void setRfbStatus(String rfbStatus) {
        this.rfbStatus = rfbStatus;
    }

    public Boolean getWorkflowActive() {
        return workflowActive;
    }

    public void setWorkflowActive(Boolean workflowActive) {
        this.workflowActive = workflowActive;
    }

    public String getWorkflowMessage() {
        return workflowMessage;
    }

    public void setWorkflowMessage(String workflowMessage) {
        this.workflowMessage = workflowMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsQuotationCustomVM  RnsQuotationCustomVM  = (RnsQuotationCustomVM) o;
        if (RnsQuotationCustomVM.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), RnsQuotationCustomVM.getId());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public Boolean getAuctionClose() {
        return auctionClose;
    }

    public void setAuctionClose(Boolean auctionClose) {
        this.auctionClose = auctionClose;
    }

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

    public String getRfqIcon() {
        return rfqIcon;
    }

    public void setRfqIcon(String rfqIcon) {
        this.rfqIcon = rfqIcon;
    }

    public String getRfbIcon() {
        return rfbIcon;
    }

    public void setRfbIcon(String rfbIcon) {
        this.rfbIcon = rfbIcon;
    }

    public String getWorkflowIcon() {
        return workflowIcon;
    }

    public void setWorkflowIcon(String workflowIcon) {
        this.workflowIcon = workflowIcon;
    }

    @Override
    public String toString() {
        return "RnsQuotation{" +
            "id=" + getId() +
            ", catgName='" + getCatgName() + "'" +
            ", quoteNumber='" + getQuoteNumber() + "'" +
            ", validity='" + getValidity() + "'" +
            ", auctionValidity='" + getAuctionValidity() + "'" +
            ", internalRemarks='" + getInternalRemarks() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", crmRequestNumber='" + getCrmRequestNumber() + "'" +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", pchCode='" + getPchCode() + "'" +
            ", targetPcd='" + getTargetPcd() + "'" +
            ", buyerCode='" + getBuyerCode() + "'" +
            ", buyerName='" + getBuyerName() + "'" +
            ", merchantRemarks='" + getMerchantRemarks() + "'" +
            ", date='" + getDate() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            ", articleDesc='" + getArticleDesc() + "'" +
            ", published='" + isPublished() + "'" +
            ", template='" + getTemplate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", workflowStatus='" + getWorkflowStatus() + "'" +
            ", rfq='" + getRfq() + "'" +
            ", auction='" + getAuction() + "'" +
            ", auctionValidate='" + getAuctionValidate() + "'" +
            ", rfqValidate='" + getRfqValidate() + "'" +
            ", rnsPchMaster='" + getRnsPchMaster() + "'" +
            ", projectTitle='" + getProjectTitle() + "'" +

            "}";
    }
}
