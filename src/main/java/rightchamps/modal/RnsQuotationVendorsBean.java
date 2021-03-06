package rightchamps.modal;

import rightchamps.domain.*;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.time.Instant;
import java.util.List;

public class RnsQuotationVendorsBean {
    private Long id;

    private String paymentTerms;

    private String paymentTermsChargeType;

    private String paymentTermsCharge;

    private String deliveryTerms;

    private String deliveryTermsChargeType;

    private String deliveryTermsCharge;

    private String expDelDate;

    private String confDelDate;

    private String currency;

    private Float regularRate;

    private Integer disRate;

    private String expiryQty;

    private String quoteQty;

    private String awardQty;

    private String vendorCode;

    private Long rank;

    private String submitTime;

    private Integer noRevision;

    private Instant createdOn;

    private Boolean auctionApplicable;

    private User userId;

    private RnsQuotationVariant variant;

    private RnsQuotation vendorQuotation;

    private User vendor;

    private List<VndrPriceCustom> revisionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getPaymentTermsChargeType() {
        return paymentTermsChargeType;
    }

    public void setPaymentTermsChargeType(String paymentTermsChargeType) {
        this.paymentTermsChargeType = paymentTermsChargeType;
    }

    public String getPaymentTermsCharge() {
        return paymentTermsCharge;
    }

    public void setPaymentTermsCharge(String paymentTermsCharge) {
        this.paymentTermsCharge = paymentTermsCharge;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public String getDeliveryTermsChargeType() {
        return deliveryTermsChargeType;
    }

    public void setDeliveryTermsChargeType(String deliveryTermsChargeType) {
        this.deliveryTermsChargeType = deliveryTermsChargeType;
    }

    public String getDeliveryTermsCharge() {
        return deliveryTermsCharge;
    }

    public void setDeliveryTermsCharge(String deliveryTermsCharge) {
        this.deliveryTermsCharge = deliveryTermsCharge;
    }

    public String getExpDelDate() {
        return expDelDate;
    }

    public void setExpDelDate(String expDelDate) {
        this.expDelDate = expDelDate;
    }

    public String getConfDelDate() {
        return confDelDate;
    }

    public void setConfDelDate(String confDelDate) {
        this.confDelDate = confDelDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getRegularRate() {
        return regularRate;
    }

    public void setRegularRate(Float regularRate) {
        this.regularRate = regularRate;
    }

    public Integer getDisRate() {
        return disRate;
    }

    public void setDisRate(Integer disRate) {
        this.disRate = disRate;
    }

    public String getExpiryQty() {
        return expiryQty;
    }

    public void setExpiryQty(String expiryQty) {
        this.expiryQty = expiryQty;
    }

    public String getQuoteQty() {
        return quoteQty;
    }

    public void setQuoteQty(String quoteQty) {
        this.quoteQty = quoteQty;
    }

    public String getAwardQty() {
        return awardQty;
    }

    public void setAwardQty(String awardQty) {
        this.awardQty = awardQty;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getNoRevision() {
        return noRevision;
    }

    public void setNoRevision(Integer noRevision) {
        this.noRevision = noRevision;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public RnsQuotationVariant getVariant() {
        return variant;
    }

    public void setVariant(RnsQuotationVariant variant) {
        this.variant = variant;
    }

    public RnsQuotation getVendorQuotation() {
        return vendorQuotation;
    }

    public void setVendorQuotation(RnsQuotation vendorQuotation) {
        this.vendorQuotation = vendorQuotation;
    }

    public List<VndrPriceCustom> getRevisionList() {
        return revisionList;
    }

    public void setRevisionList(List<VndrPriceCustom> revisionList) {
        this.revisionList = revisionList;
    }

    public Boolean getAuctionApplicable() {
        return auctionApplicable;
    }

    public void setAuctionApplicable(Boolean auctionApplicable) {
        this.auctionApplicable = auctionApplicable;
    }

    public User getVendor() {
        return vendor;
    }

    public void setVendor(User vendor) {
        this.vendor = vendor;
    }
}
