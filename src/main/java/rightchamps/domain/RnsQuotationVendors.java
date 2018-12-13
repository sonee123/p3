package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsQuotationVendors.
 */
@Entity
@Table(name = "rns_quotation_vendors")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationVendors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quot_vendors_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "payment_terms_charge_type")
    private String paymentTermsChargeType;

    @Column(name = "payment_terms_charge")
    private String paymentTermsCharge;

    @Column(name = "delivery_terms")
    private String deliveryTerms;

    @Column(name = "delivery_terms_charge_type")
    private String deliveryTermsChargeType;

    @Column(name = "delivery_terms_charge")
    private String deliveryTermsCharge;

    @Column(name = "exp_del_date")
    private String expDelDate;

    @Column(name = "conf_del_date")
    private String confDelDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "regular_rate")
    private Float regularRate;

    @Column(name = "dis_rate")
    private Integer disRate;

    @Column(name = "expiry_qty")
    private String expiryQty;

    @Column(name = "quote_qty")
    private String quoteQty;

    @Column(name = "award_qty")
    private String awardQty;

    @Column(name = "vendor_code")
    private String vendorCode;


    @Column(name = "rfq_user_type")
    private String rfqUserType;

    @Column(name = "auction_applicable")
    private Boolean auctionApplicable;

    @Transient
    private Long rank;

    @Transient
    private String submitTime;

    @Transient
    private Integer noRevision;

    @Transient
    private Instant createdOn;


    @ManyToOne
    private User userId;

    @ManyToOne
    private RnsQuotationVariant variant;

    // @ManyToOne
    // private RnsVendorMaster vendor;

    @ManyToOne
    private RnsQuotation vendorQuotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public RnsQuotationVendors paymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
        return this;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }


    public String getPaymentTermsChargeType() {
        return paymentTermsChargeType;
    }

    public RnsQuotationVendors paymentTermsChargeType(String paymentTermsChargeType) {
        this.paymentTermsChargeType = paymentTermsChargeType;
        return this;
    }

    public void setPaymentTermsChargeType(String paymentTermsChargeType) {
        this.paymentTermsChargeType = paymentTermsChargeType;
    }

    public String getPaymentTermsCharge() {
        return paymentTermsCharge;
    }

    public RnsQuotationVendors paymentTermsCharge(String paymentTermsCharge) {
        this.paymentTermsCharge = paymentTermsCharge;
        return this;
    }

    public void setPaymentTermsCharge(String paymentTermsCharge) {
        this.paymentTermsCharge = paymentTermsCharge;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public RnsQuotationVendors deliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
        return this;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public String getDeliveryTermsChargeType() {
        return deliveryTermsChargeType;
    }

    public RnsQuotationVendors deliveryTermsChargeType(String deliveryTermsChargeType) {
        this.deliveryTermsChargeType = deliveryTermsChargeType;
        return this;
    }

    public void setDeliveryTermsChargeType(String deliveryTermsChargeType) {
        this.deliveryTermsChargeType = deliveryTermsChargeType;
    }


    public String getDeliveryTermsCharge() {
        return deliveryTermsCharge;
    }

    public RnsQuotationVendors deliveryTermsCharge(String deliveryTermsCharge) {
        this.deliveryTermsCharge = deliveryTermsCharge;
        return this;
    }

    public void setDeliveryTermsCharge(String deliveryTermsCharge) {
        this.deliveryTermsCharge = deliveryTermsCharge;
    }

    public String getExpDelDate() {
        return expDelDate;
    }

    public RnsQuotationVendors expDelDate(String expDelDate) {
        this.expDelDate = expDelDate;
        return this;
    }

    public void setExpDelDate(String expDelDate) {
        this.expDelDate = expDelDate;
    }

    public String getConfDelDate() {
        return confDelDate;
    }

    public RnsQuotationVendors confDelDate(String confDelDate) {
        this.confDelDate = confDelDate;
        return this;
    }

    public void setConfDelDate(String confDelDate) {
        this.confDelDate = confDelDate;
    }

    public String getCurrency() {
        return currency;
    }

    public RnsQuotationVendors currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getRegularRate() {
        return regularRate;
    }

    public RnsQuotationVendors regularRate(Float regularRate) {
        this.regularRate = regularRate;
        return this;
    }

    public void setRegularRate(Float regularRate) {
        this.regularRate = regularRate;
    }

    public Integer getDisRate() {
        return disRate;
    }

    public RnsQuotationVendors disRate(Integer disRate) {
        this.disRate = disRate;
        return this;
    }

    public void setDisRate(Integer disRate) {
        this.disRate = disRate;
    }

    public String getExpiryQty() {
        return expiryQty;
    }

    public RnsQuotationVendors expiryQty(String expiryQty) {
        this.expiryQty = expiryQty;
        return this;
    }

    public void setExpiryQty(String expiryQty) {
        this.expiryQty = expiryQty;
    }

    public String getQuoteQty() {
        return quoteQty;
    }

    public RnsQuotationVendors quoteQty(String quoteQty) {
        this.quoteQty = quoteQty;
        return this;
    }

    public void setQuoteQty(String quoteQty) {
        this.quoteQty = quoteQty;
    }

    public String getAwardQty() {
        return awardQty;
    }

    public RnsQuotationVendors awardQty(String awardQty) {
        this.awardQty = awardQty;
        return this;
    }

    public void setAwardQty(String awardQty) {
        this.awardQty = awardQty;
    }

    public User getUserId() {
        return userId;
    }

    public RnsQuotationVendors userId(User user) {
        this.userId = user;
        return this;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public RnsQuotationVariant getVariant() {
        return variant;
    }

    public RnsQuotationVendors variant(RnsQuotationVariant rnsQuotationVariant) {
        this.variant = rnsQuotationVariant;
        return this;
    }

    public void setVariant(RnsQuotationVariant rnsQuotationVariant) {
        this.variant = rnsQuotationVariant;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public RnsQuotationVendors vendorCode(String rnsVendorMaster) {
        this.vendorCode = rnsVendorMaster;
        return this;
    }

    public void setVendorCode(String rnsVendorMaster) {
        this.vendorCode = rnsVendorMaster;
    }

    public RnsQuotation getVendorQuotation() {
        return vendorQuotation;
    }

    public RnsQuotationVendors vendorQuotation(RnsQuotation rnsQuotation) {
        this.vendorQuotation = rnsQuotation;
        return this;
    }

    public void setVendorQuotation(RnsQuotation rnsQuotation) {
        this.vendorQuotation = rnsQuotation;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getRfqUserType() {
        return rfqUserType;
    }

    public void setRfqUserType(String rfqUserType) {
        this.rfqUserType = rfqUserType;
    }

    public Boolean getAuctionApplicable() {
        return auctionApplicable;
    }

    public void setAuctionApplicable(Boolean auctionApplicable) {
        this.auctionApplicable = auctionApplicable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsQuotationVendors rnsQuotationVendors = (RnsQuotationVendors) o;
        if (rnsQuotationVendors.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationVendors.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /*@Override
    public String toString() {
        return "RnsQuotationVendors{" +
            "id=" + getId() +
            ", paymentTerms='" + getPaymentTerms() + "'" +
            ", paymentTermsChargeType='" + getPaymentTermsChargeType() + "'" +
            ", paymentTermsCharge='" + getPaymentTermsCharge() + "'" +
            ", deliveryTermsChargeType='" + getDeliveryTermsChargeType() + "'" +
            ", paymentTermsCharge='" + getDeliveryTermsCharge() + "'" +
            ", deliveryTerms='" + getDeliveryTerms() + "'" +
            ", expDelDate='" + getExpDelDate() + "'" +
            ", confDelDate='" + getConfDelDate() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", regularRate=" + getRegularRate() +
            ", disRate=" + getDisRate() +
            ", expiryQty='" + getExpiryQty() + "'" +
            ", quoteQty='" + getQuoteQty() + "'" +
            ", awardQty='" + getAwardQty() + "'" +
            ", rfqUserType='" + getRfqUserType() + "'" +
            "}";
    }*/
}
