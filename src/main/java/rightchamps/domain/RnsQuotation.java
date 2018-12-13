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
@Entity
@Table(name = "rns_quotation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quotation_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "catg_name")
    private String catgName;

    @Column(name = "quote_number")
    private String quoteNumber;

    @Column(name = "quote_type", insertable = false, updatable = false)
    private String quoteType;

    @Column(name = "validity")
    private Timestamp validity;

    @Column(name = "auction_validity")
    private Timestamp auctionValidity;

    @Column(name = "internal_remarks")
    private String internalRemarks;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "crm_request_number")
    private String crmRequestNumber;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "pch_code")
    private String pchCode;

    @Column(name = "target_pcd")
    private LocalDate targetPcd;

    @Column(name = "buyer_code")
    private String buyerCode;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "merchant_remarks")
    private String merchantRemarks;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "article_code")
    private String articleCode;

    @Column(name = "article_desc")
    private String articleDesc;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "template")
    private String template;

    //@Column(name = "created_by")
    //private String createdBy;

    @Column(name = "workflow_status")
    private String workflowStatus;

    @Column(name = "rfq")
    private Boolean rfq;

    @Column(name = "auction")
    private Boolean auction;

     @Column(name = "project_title")
    private String projectTitle;

     @Column(name = "event_type")
     private String eventType;

     @Column(name = "source_team")
     private String sourceTeam;

    @Column(name = "rfq_publish_date")
    private Instant rfqPublishDate;

    @Column(name = "auction_publish_date")
    private Instant auctionPublishDate;

    @Column(name = "auction_close")
    private Boolean auctionClose;

    @Column(name = "approved_flag")
    private String approvedFlag;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_date")
    private Instant approvedDate;

    @Column(name = "is_rfq")
    private Boolean rfqApplicable;

    @Column(name = "is_auction")
    private Boolean auctionApplicable;

    @OneToOne
    @JoinColumn(name="updatedBy", referencedColumnName="login")
    private User updatedUser;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "closed_by")
    private String closedBy;

    @Column(name = "closed_date")
    private Instant closedDate;

    @OneToMany(mappedBy = "quotation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    @OneToOne
    @JoinColumn(name="quote_type", referencedColumnName="type_code")
    private RnsTypeMaster rnsTypeMaster;

    @OneToOne
    @JoinColumn(name="createdBy", referencedColumnName="login")
    private User user;

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

    public RnsQuotation catgName(String catgName) {
        this.catgName = catgName;
        return this;
    }

    public void setCatgName(String catgName) {
        this.catgName = catgName;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public RnsQuotation quoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
        return this;
    }

    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public RnsQuotation quoteType(String quoteType) {
        this.quoteType = quoteType;
        return this;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public Timestamp getValidity() {
        return validity;
    }

    public RnsQuotation validity(Timestamp validity) {
        this.validity = validity;
        return this;
    }

    public void setValidity(Timestamp validity) {
        this.validity = validity;
    }

    public Timestamp getAuctionValidity() {
        return auctionValidity;
    }

    public RnsQuotation auctionValidity(Timestamp auctionValidity) {
        this.auctionValidity = auctionValidity;
        return this;
    }

    public void setAuctionValidity(Timestamp auctionValidity) {
        this.auctionValidity = auctionValidity;
    }

    public String getInternalRemarks() {
        return internalRemarks;
    }

    public RnsQuotation internalRemarks(String internalRemarks) {
        this.internalRemarks = internalRemarks;
        return this;
    }

    public void setInternalRemarks(String internalRemarks) {
        this.internalRemarks = internalRemarks;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public RnsQuotation createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCrmRequestNumber() {
        return crmRequestNumber;
    }

    public RnsQuotation crmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
        return this;
    }

    public void setCrmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public RnsQuotation requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getPchCode() {
        return pchCode;
    }

    public RnsQuotation pchCode(String pchCode) {
        this.pchCode = pchCode;
        return this;
    }

    public void setPchCode(String pchCode) {
        this.pchCode = pchCode;
    }

    public LocalDate getTargetPcd() {
        return targetPcd;
    }

    public RnsQuotation targetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
        return this;
    }

    public void setTargetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public RnsQuotation buyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
        return this;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public RnsQuotation buyerName(String buyerName) {
        this.buyerName = buyerName;
        return this;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getMerchantRemarks() {
        return merchantRemarks;
    }

    public RnsQuotation merchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
        return this;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
    }

    public Instant getDate() {
        return date;
    }

    public RnsQuotation date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public RnsQuotation articleCode(String articleCode) {
        this.articleCode = articleCode;
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public RnsQuotation articleDesc(String articleDesc) {
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

    public RnsQuotation published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRfq() {
        return rfq;
    }

    public RnsQuotation rfq(Boolean rfq) {
        this.rfq = rfq;
        return this;
    }

    public void setRfq(Boolean rfq) {
        this.rfq = rfq;
    }

    public Boolean getAuction() {
        return auction;
    }

    public RnsQuotation auction(Boolean auction) {
        this.auction = auction;
        return this;
    }

    public void setAuction(Boolean auction) {
        this.auction = auction;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public RnsQuotation projectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
        return this;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }


    public String getTemplate() {
        return template;
    }

    public RnsQuotation template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    /*public String getCreatedBy() {
        return createdBy;
    }

    public RnsQuotation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }*/

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public RnsQuotation workflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
        return this;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public Set<RnsQuotationVariant> getVariants() {
        return variants;
    }

    public RnsQuotation variants(Set<RnsQuotationVariant> rnsQuotationVariants) {
        this.variants = rnsQuotationVariants;
        return this;
    }

    public RnsQuotation addVariants(RnsQuotationVariant rnsQuotationVariant) {
        this.variants.add(rnsQuotationVariant);
        rnsQuotationVariant.setQuotation(this);
        return this;
    }

    public RnsQuotation removeVariants(RnsQuotationVariant rnsQuotationVariant) {
        this.variants.remove(rnsQuotationVariant);
        rnsQuotationVariant.setQuotation(null);
        return this;
    }

    public void setVariants(Set<RnsQuotationVariant> rnsQuotationVariants) {
        this.variants = rnsQuotationVariants;
    }

    public Set<RnsQuotationVendors> getQuotationVendors() {
        return quotationVendors;
    }

    public RnsQuotation quotationVendors(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotationVendors = rnsQuotationVendors;
        return this;
    }

    public RnsQuotation addQuotationVendors(RnsQuotationVendors rnsQuotationVendors) {
        this.quotationVendors.add(rnsQuotationVendors);
        rnsQuotationVendors.setVendorQuotation(this);
        return this;
    }

    public RnsQuotation removeQuotationVendors(RnsQuotationVendors rnsQuotationVendors) {
        this.quotationVendors.remove(rnsQuotationVendors);
        rnsQuotationVendors.setVendorQuotation(null);
        return this;
    }

    public void setQuotationVendors(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotationVendors = rnsQuotationVendors;
    }

    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public RnsQuotation rnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
        return this;
    }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
    }

    public RnsPchMaster getRnsPchMaster() {
        return rnsPchMaster;
    }

    public RnsQuotation rnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
        return this;
    }

    public void setRnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
    }


    public String getSourceTeam() {
		return sourceTeam;
	}

	public void setSourceTeam(String sourceTeam) {
		this.sourceTeam = sourceTeam;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

    public Instant getRfqPublishDate() {
        return rfqPublishDate;
    }

    public void setRfqPublishDate(Instant rfqPublishDate) {
        this.rfqPublishDate = rfqPublishDate;
    }

    public Instant getAuctionPublishDate() { return auctionPublishDate; }

    public void setAuctionPublishDate(Instant auctionPublishDate) { this.auctionPublishDate = auctionPublishDate; }

    public RnsTypeMaster getRnsTypeMaster() { return rnsTypeMaster; }

    public void setRnsTypeMaster(RnsTypeMaster rnsTypeMaster) { this.rnsTypeMaster = rnsTypeMaster; }

    public Boolean getAuctionClose() {
        return auctionClose;
    }

    public void setAuctionClose(Boolean auctionClose) {
        this.auctionClose = auctionClose;
    }

    public String getApprovedBy() { return approvedBy; }

    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public Instant getApprovedDate() { return approvedDate; }

    public void setApprovedDate(Instant approvedDate) { this.approvedDate = approvedDate; }

    public User getUpdatedUser() { return updatedUser; }

    public void setUpdatedUser(User updatedUser) { this.updatedUser = updatedUser; }

    public Instant getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(Instant updatedDate) { this.updatedDate = updatedDate; }


// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsQuotation rnsQuotation = (RnsQuotation) o;
        if (rnsQuotation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public String getApprovedFlag() { return approvedFlag; }

    public void setApprovedFlag(String approvedFlag) { this.approvedFlag = approvedFlag; }

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
        return "RnsQuotation{" +
            "id=" + getId() +
            ", catgName='" + getCatgName() + "'" +
            ", quoteNumber='" + getQuoteNumber() + "'" +
            //", quoteType='" + getQuoteType() + "'" +
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
//            ", createdBy='" + user.getLogin() + "'" +
            ", workflowStatus='" + getWorkflowStatus() + "'" +
            ", rfq='" + getRfq() + "'" +
            ", auction='" + getAuction() + "'" +
            ", projectTitle='" + getProjectTitle() + "'" +
            ", eventType='" + getEventType() + "'" +
            "}";
    }
}
