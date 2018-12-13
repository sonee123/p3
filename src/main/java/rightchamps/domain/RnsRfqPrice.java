package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsRfqPrice.
 */
@Entity
@Table(name = "rns_rfq_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsRfqPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_rfq_price_seq", allocationSize=1)
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

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

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

    public RnsRfqPrice priceOne(Float priceOne) {
        this.priceOne = priceOne;
        return this;
    }

    public void setPriceOne(Float priceOne) {
        this.priceOne = priceOne;
    }

    public Float getPriceTwo() {
        return priceTwo;
    }

    public RnsRfqPrice priceTwo(Float priceTwo) {
        this.priceTwo = priceTwo;
        return this;
    }

    public void setPriceTwo(Float priceTwo) {
        this.priceTwo = priceTwo;
    }

    public Float getPriceThree() {
        return priceThree;
    }

    public RnsRfqPrice priceThree(Float priceThree) {
        this.priceThree = priceThree;
        return this;
    }

    public void setPriceThree(Float priceThree) {
        this.priceThree = priceThree;
    }

    public Float getPriceFour() {
        return priceFour;
    }

    public RnsRfqPrice priceFour(Float priceFour) {
        this.priceFour = priceFour;
        return this;
    }

    public void setPriceFour(Float priceFour) {
        this.priceFour = priceFour;
    }

    public Float getPriceFive() {
        return priceFive;
    }

    public RnsRfqPrice priceFive(Float priceFive) {
        this.priceFive = priceFive;
        return this;
    }

    public void setPriceFive(Float priceFive) {
        this.priceFive = priceFive;
    }

    public Float getPriceSix() {
        return priceSix;
    }

    public RnsRfqPrice priceSix(Float priceSix) {
        this.priceSix = priceSix;
        return this;
    }

    public void setPriceSix(Float priceSix) {
        this.priceSix = priceSix;
    }

    public Float getPriceSeven() {
        return priceSeven;
    }

    public RnsRfqPrice priceSeven(Float priceSeven) {
        this.priceSeven = priceSeven;
        return this;
    }

    public void setPriceSeven(Float priceSeven) {
        this.priceSeven = priceSeven;
    }

    public Float getPriceEight() {
        return priceEight;
    }

    public RnsRfqPrice priceEight(Float priceEight) {
        this.priceEight = priceEight;
        return this;
    }

    public void setPriceEight(Float priceEight) {
        this.priceEight = priceEight;
    }

    public Float getPriceNine() {
        return priceNine;
    }

    public RnsRfqPrice priceNine(Float priceNine) {
        this.priceNine = priceNine;
        return this;
    }

    public void setPriceNine(Float priceNine) {
        this.priceNine = priceNine;
    }

    public Float getPriceTen() {
        return priceTen;
    }

    public RnsRfqPrice priceTen(Float priceTen) {
        this.priceTen = priceTen;
        return this;
    }

    public void setPriceTen(Float priceTen) {
        this.priceTen = priceTen;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public RnsRfqPrice vendorId(Long vendorId) {
        this.vendorId = vendorId;
        return this;
    }



    public String getCreatedBy() {
        return createdBy;
    }

    public RnsRfqPrice createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public RnsRfqPrice createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsRfqPrice rnsRfqPrice = (RnsRfqPrice) o;
        if (rnsRfqPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsRfqPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsRfqPrice{" +
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
            ", variantId=" + getVendorId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
