package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A VndrPrice.
 */
@Entity
@Table(name = "vndr_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VndrPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="vndr_price_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "price_one")
    private Float priceOne;

    @Column(name = "price_two")
    private Float priceTwo;

    @Column(name = "price_three")
    private Float priceThree;

    @Column(name = "price_four")
    private Float priceFour;

    @Column(name = "price_five")
    private Float priceFive;

    @Column(name = "price_six")
    private Float priceSix;

    @Column(name = "price_seven")
    private Float priceSeven;

    @Column(name = "price_eight")
    private Float priceEight;

    @Column(name = "price_nine")
    private Float priceNine;

    @Column(name = "price_ten")
    private Float priceTen;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "surrogate")
    private boolean surrogate = false;

    @ManyToOne
    private RnsQuotationVariant variant;

    @ManyToOne
    private RnsQuotationVendors vndrQuotation;

    @ManyToOne
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPriceOne() {
        return priceOne;
    }

    public VndrPrice priceOne(Float priceOne) {
        this.priceOne = priceOne;
        return this;
    }

    public void setPriceOne(Float priceOne) {
        this.priceOne = priceOne;
    }

    public Float getPriceTwo() {
        return priceTwo;
    }

    public VndrPrice priceTwo(Float priceTwo) {
        this.priceTwo = priceTwo;
        return this;
    }

    public void setPriceTwo(Float priceTwo) {
        this.priceTwo = priceTwo;
    }

    public Float getPriceThree() {
        return priceThree;
    }

    public VndrPrice priceThree(Float priceThree) {
        this.priceThree = priceThree;
        return this;
    }

    public void setPriceThree(Float priceThree) {
        this.priceThree = priceThree;
    }

    public Float getPriceFour() {
        return priceFour;
    }

    public VndrPrice priceFour(Float priceFour) {
        this.priceFour = priceFour;
        return this;
    }

    public void setPriceFour(Float priceFour) {
        this.priceFour = priceFour;
    }

    public Float getPriceFive() {
        return priceFive;
    }

    public VndrPrice priceFive(Float priceFive) {
        this.priceFive = priceFive;
        return this;
    }

    public void setPriceFive(Float priceFive) {
        this.priceFive = priceFive;
    }

    public Float getPriceSix() {
        return priceSix;
    }

    public VndrPrice priceSix(Float priceSix) {
        this.priceSix = priceSix;
        return this;
    }

    public void setPriceSix(Float priceSix) {
        this.priceSix = priceSix;
    }

    public Float getPriceSeven() {
        return priceSeven;
    }

    public VndrPrice priceSeven(Float priceSeven) {
        this.priceSeven = priceSeven;
        return this;
    }

    public void setPriceSeven(Float priceSeven) {
        this.priceSeven = priceSeven;
    }

    public Float getPriceEight() {
        return priceEight;
    }

    public VndrPrice priceEight(Float priceEight) {
        this.priceEight = priceEight;
        return this;
    }

    public void setPriceEight(Float priceEight) {
        this.priceEight = priceEight;
    }

    public Float getPriceNine() {
        return priceNine;
    }

    public VndrPrice priceNine(Float priceNine) {
        this.priceNine = priceNine;
        return this;
    }

    public void setPriceNine(Float priceNine) {
        this.priceNine = priceNine;
    }

    public Float getPriceTen() {
        return priceTen;
    }

    public VndrPrice priceTen(Float priceTen) {
        this.priceTen = priceTen;
        return this;
    }

    public void setPriceTen(Float priceTen) {
        this.priceTen = priceTen;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public VndrPrice vendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public RnsQuotationVariant getVariant() {
        return variant;
    }

    public VndrPrice variant(RnsQuotationVariant rnsQuotationVariant) {
        this.variant = rnsQuotationVariant;
        return this;
    }

    public void setVariant(RnsQuotationVariant rnsQuotationVariant) {
        this.variant = rnsQuotationVariant;
    }

    public RnsQuotationVendors getVndrQuotation() {
        return vndrQuotation;
    }

    public VndrPrice vndrQuotation(RnsQuotationVendors rnsQuotationVendors) {
        this.vndrQuotation = rnsQuotationVendors;
        return this;
    }

    public void setVndrQuotation(RnsQuotationVendors rnsQuotationVendors) {
        this.vndrQuotation = rnsQuotationVendors;
    }

    public Currency getCurrency() {
        return currency;
    }

    public VndrPrice currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
    public Instant getCreatedOn() {
        return createdOn;
    }

    public VndrPrice createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public boolean isSurrogate() { return surrogate; }

    public void setSurrogate(boolean surrogate) { this.surrogate = surrogate; }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VndrPrice vndrPrice = (VndrPrice) o;
        if (vndrPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vndrPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VndrPrice{" +
            "id=" + getId() +
            ", priceOne=" + getPriceOne() +
            ", priceTwo=" + getPriceTwo() +
            ", priceThree=" + getPriceThree() +
            ", priceFour=" + getPriceFour() +
            ", priceFive=" + getPriceFive() +
            ", priceSix=" + getPriceSix() +
            ", priceSeven=" + getPriceSeven() +
            ", priceEight=" + getPriceEight() +
            ", priceNine=" + getPriceNine() +
            ", priceTen=" + getPriceTen() +
            ", vendorCode='" + getVendorCode() + "'" +
            "}";
    }
}
