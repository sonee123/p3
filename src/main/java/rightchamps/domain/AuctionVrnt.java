package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A AuctionVrnt.
 */
@Entity
@Table(name = "auction_vrnt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuctionVrnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="auction_vrnt_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "applicable")
    private Boolean applicable;

    @Column(name = "text_one")
    private String textOne;

    @Column(name = "uom_one")
    private String uomOne;

    @Column(name = "conv_fact_one")
    private Float convFactOne;

    @Column(name = "qty_one")
    private Float qtyOne;

    @Column(name = "price_one")
    private Float priceOne;

    @Column(name = "amount_one")
    private Float amountOne;

    @Column(name = "text_two")
    private String textTwo;

    @Column(name = "uom_two")
    private String uomTwo;

    @Column(name = "conv_fact_two")
    private Float convFactTwo;

    @Column(name = "qty_two")
    private Float qtyTwo;

    @Column(name = "price_two")
    private Float priceTwo;

    @Column(name = "amount_two")
    private Float amountTwo;

    @Column(name = "text_three")
    private String textThree;

    @Column(name = "uom_three")
    private String uomThree;

    @Column(name = "conv_fact_three")
    private Float convFactThree;

    @Column(name = "qty_three")
    private Float qtyThree;

    @Column(name = "price_three")
    private Float priceThree;

    @Column(name = "amount_three")
    private Float amountThree;

    @Column(name = "text_four")
    private String textFour;

    @Column(name = "uom_four")
    private String uomFour;

    @Column(name = "conv_fact_four")
    private Float convFactFour;

    @Column(name = "qty_four")
    private Float qtyFour;

    @Column(name = "price_four")
    private Float priceFour;

    @Column(name = "amount_four")
    private Float amountFour;

    @Column(name = "text_five")
    private String textFive;

    @Column(name = "uom_five")
    private String uomFive;

    @Column(name = "conv_fact_five")
    private Float convFactFive;

    @Column(name = "qty_five")
    private Float qtyFive;

    @Column(name = "price_five")
    private Float priceFive;

    @Column(name = "amount_five")
    private Float amountFive;

    @Column(name = "text_six")
    private String textSix;

    @Column(name = "uom_six")
    private String uomSix;

    @Column(name = "conv_fact_six")
    private Float convFactSix;

    @Column(name = "qty_six")
    private Float qtySix;

    @Column(name = "price_six")
    private Float priceSix;

    @Column(name = "amount_six")
    private Float amountSix;

    @Column(name = "text_seven")
    private String textSeven;

    @Column(name = "uom_seven")
    private String uomSeven;

    @Column(name = "conv_fact_seven")
    private Float convFactSeven;

    @Column(name = "qty_seven")
    private Float qtySeven;

    @Column(name = "price_seven")
    private Float priceSeven;

    @Column(name = "amount_seven")
    private Float amountSeven;

    @Column(name = "text_eight")
    private String textEight;

    @Column(name = "uom_eight")
    private String uomEight;

    @Column(name = "conv_fact_eight")
    private Float convFactEight;

    @Column(name = "qty_eight")
    private Float qtyEight;

    @Column(name = "price_eight")
    private Float priceEight;

    @Column(name = "amount_eight")
    private Float amountEight;

    @Column(name = "text_nine")
    private String textNine;

    @Column(name = "uom_nine")
    private String uomNine;

    @Column(name = "conv_fact_nine")
    private Float convFactNine;

    @Column(name = "qty_nine")
    private Float qtyNine;

    @Column(name = "price_nine")
    private Float priceNine;

    @Column(name = "amount_nine")
    private Float amountNine;

    @Column(name = "text_ten")
    private String textTen;

    @Column(name = "uom_ten")
    private String uomTen;

    @Column(name = "conv_fact_ten")
    private Float convFactTen;

    @Column(name = "qty_ten")
    private Float qtyTen;

    @Column(name = "price_ten")
    private Float priceTen;

    @Column(name = "amount_ten")
    private Float amountTen;

    @ManyToOne
    private RnsQuotationVariant variant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public AuctionVrnt title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isApplicable() {
        return applicable;
    }

    public AuctionVrnt applicable(Boolean applicable) {
        this.applicable = applicable;
        return this;
    }

    public void setApplicable(Boolean applicable) {
        this.applicable = applicable;
    }

    public String getTextOne() {
        return textOne;
    }

    public AuctionVrnt textOne(String textOne) {
        this.textOne = textOne;
        return this;
    }

    public void setTextOne(String textOne) {
        this.textOne = textOne;
    }

    public String getUomOne() {
        return uomOne;
    }

    public AuctionVrnt uomOne(String uomOne) {
        this.uomOne = uomOne;
        return this;
    }

    public void setUomOne(String uomOne) {
        this.uomOne = uomOne;
    }

    public Float getConvFactOne() {
        return convFactOne;
    }

    public AuctionVrnt convFactOne(Float convFactOne) {
        this.convFactOne = convFactOne;
        return this;
    }

    public void setConvFactOne(Float convFactOne) {
        this.convFactOne = convFactOne;
    }

    public Float getQtyOne() {
        return qtyOne;
    }

    public AuctionVrnt qtyOne(Float qtyOne) {
        this.qtyOne = qtyOne;
        return this;
    }

    public void setQtyOne(Float qtyOne) {
        this.qtyOne = qtyOne;
    }

    public Float getPriceOne() {
        return priceOne;
    }

    public AuctionVrnt priceOne(Float priceOne) {
        this.priceOne = priceOne;
        return this;
    }

    public void setPriceOne(Float priceOne) {
        this.priceOne = priceOne;
    }

    public Float getAmountOne() {
        return amountOne;
    }

    public AuctionVrnt amountOne(Float amountOne) {
        this.amountOne = amountOne;
        return this;
    }

    public void setAmountOne(Float amountOne) {
        this.amountOne = amountOne;
    }

    public String getTextTwo() {
        return textTwo;
    }

    public AuctionVrnt textTwo(String textTwo) {
        this.textTwo = textTwo;
        return this;
    }

    public void setTextTwo(String textTwo) {
        this.textTwo = textTwo;
    }

    public String getUomTwo() {
        return uomTwo;
    }

    public AuctionVrnt uomTwo(String uomTwo) {
        this.uomTwo = uomTwo;
        return this;
    }

    public void setUomTwo(String uomTwo) {
        this.uomTwo = uomTwo;
    }

    public Float getConvFactTwo() {
        return convFactTwo;
    }

    public AuctionVrnt convFactTwo(Float convFactTwo) {
        this.convFactTwo = convFactTwo;
        return this;
    }

    public void setConvFactTwo(Float convFactTwo) {
        this.convFactTwo = convFactTwo;
    }

    public Float getQtyTwo() {
        return qtyTwo;
    }

    public AuctionVrnt qtyTwo(Float qtyTwo) {
        this.qtyTwo = qtyTwo;
        return this;
    }

    public void setQtyTwo(Float qtyTwo) {
        this.qtyTwo = qtyTwo;
    }

    public Float getPriceTwo() {
        return priceTwo;
    }

    public AuctionVrnt priceTwo(Float priceTwo) {
        this.priceTwo = priceTwo;
        return this;
    }

    public void setPriceTwo(Float priceTwo) {
        this.priceTwo = priceTwo;
    }

    public Float getAmountTwo() {
        return amountTwo;
    }

    public AuctionVrnt amountTwo(Float amountTwo) {
        this.amountTwo = amountTwo;
        return this;
    }

    public void setAmountTwo(Float amountTwo) {
        this.amountTwo = amountTwo;
    }

    public String getTextThree() {
        return textThree;
    }

    public AuctionVrnt textThree(String textThree) {
        this.textThree = textThree;
        return this;
    }

    public void setTextThree(String textThree) {
        this.textThree = textThree;
    }

    public String getUomThree() {
        return uomThree;
    }

    public AuctionVrnt uomThree(String uomThree) {
        this.uomThree = uomThree;
        return this;
    }

    public void setUomThree(String uomThree) {
        this.uomThree = uomThree;
    }

    public Float getConvFactThree() {
        return convFactThree;
    }

    public AuctionVrnt convFactThree(Float convFactThree) {
        this.convFactThree = convFactThree;
        return this;
    }

    public void setConvFactThree(Float convFactThree) {
        this.convFactThree = convFactThree;
    }

    public Float getQtyThree() {
        return qtyThree;
    }

    public AuctionVrnt qtyThree(Float qtyThree) {
        this.qtyThree = qtyThree;
        return this;
    }

    public void setQtyThree(Float qtyThree) {
        this.qtyThree = qtyThree;
    }

    public Float getPriceThree() {
        return priceThree;
    }

    public AuctionVrnt priceThree(Float priceThree) {
        this.priceThree = priceThree;
        return this;
    }

    public void setPriceThree(Float priceThree) {
        this.priceThree = priceThree;
    }

    public Float getAmountThree() {
        return amountThree;
    }

    public AuctionVrnt amountThree(Float amountThree) {
        this.amountThree = amountThree;
        return this;
    }

    public void setAmountThree(Float amountThree) {
        this.amountThree = amountThree;
    }

    public String getTextFour() {
        return textFour;
    }

    public AuctionVrnt textFour(String textFour) {
        this.textFour = textFour;
        return this;
    }

    public void setTextFour(String textFour) {
        this.textFour = textFour;
    }

    public String getUomFour() {
        return uomFour;
    }

    public AuctionVrnt uomFour(String uomFour) {
        this.uomFour = uomFour;
        return this;
    }

    public void setUomFour(String uomFour) {
        this.uomFour = uomFour;
    }

    public Float getConvFactFour() {
        return convFactFour;
    }

    public AuctionVrnt convFactFour(Float convFactFour) {
        this.convFactFour = convFactFour;
        return this;
    }

    public void setConvFactFour(Float convFactFour) {
        this.convFactFour = convFactFour;
    }

    public Float getQtyFour() {
        return qtyFour;
    }

    public AuctionVrnt qtyFour(Float qtyFour) {
        this.qtyFour = qtyFour;
        return this;
    }

    public void setQtyFour(Float qtyFour) {
        this.qtyFour = qtyFour;
    }

    public Float getPriceFour() {
        return priceFour;
    }

    public AuctionVrnt priceFour(Float priceFour) {
        this.priceFour = priceFour;
        return this;
    }

    public void setPriceFour(Float priceFour) {
        this.priceFour = priceFour;
    }

    public Float getAmountFour() {
        return amountFour;
    }

    public AuctionVrnt amountFour(Float amountFour) {
        this.amountFour = amountFour;
        return this;
    }

    public void setAmountFour(Float amountFour) {
        this.amountFour = amountFour;
    }

    public String getTextFive() {
        return textFive;
    }

    public AuctionVrnt textFive(String textFive) {
        this.textFive = textFive;
        return this;
    }

    public void setTextFive(String textFive) {
        this.textFive = textFive;
    }

    public String getUomFive() {
        return uomFive;
    }

    public AuctionVrnt uomFive(String uomFive) {
        this.uomFive = uomFive;
        return this;
    }

    public void setUomFive(String uomFive) {
        this.uomFive = uomFive;
    }

    public Float getConvFactFive() {
        return convFactFive;
    }

    public AuctionVrnt convFactFive(Float convFactFive) {
        this.convFactFive = convFactFive;
        return this;
    }

    public void setConvFactFive(Float convFactFive) {
        this.convFactFive = convFactFive;
    }

    public Float getQtyFive() {
        return qtyFive;
    }

    public AuctionVrnt qtyFive(Float qtyFive) {
        this.qtyFive = qtyFive;
        return this;
    }

    public void setQtyFive(Float qtyFive) {
        this.qtyFive = qtyFive;
    }

    public Float getPriceFive() {
        return priceFive;
    }

    public AuctionVrnt priceFive(Float priceFive) {
        this.priceFive = priceFive;
        return this;
    }

    public void setPriceFive(Float priceFive) {
        this.priceFive = priceFive;
    }

    public Float getAmountFive() {
        return amountFive;
    }

    public AuctionVrnt amountFive(Float amountFive) {
        this.amountFive = amountFive;
        return this;
    }

    public void setAmountFive(Float amountFive) {
        this.amountFive = amountFive;
    }

    public String getTextSix() {
        return textSix;
    }

    public AuctionVrnt textSix(String textSix) {
        this.textSix = textSix;
        return this;
    }

    public void setTextSix(String textSix) {
        this.textSix = textSix;
    }

    public String getUomSix() {
        return uomSix;
    }

    public AuctionVrnt uomSix(String uomSix) {
        this.uomSix = uomSix;
        return this;
    }

    public void setUomSix(String uomSix) {
        this.uomSix = uomSix;
    }

    public Float getConvFactSix() {
        return convFactSix;
    }

    public AuctionVrnt convFactSix(Float convFactSix) {
        this.convFactSix = convFactSix;
        return this;
    }

    public void setConvFactSix(Float convFactSix) {
        this.convFactSix = convFactSix;
    }

    public Float getQtySix() {
        return qtySix;
    }

    public AuctionVrnt qtySix(Float qtySix) {
        this.qtySix = qtySix;
        return this;
    }

    public void setQtySix(Float qtySix) {
        this.qtySix = qtySix;
    }

    public Float getPriceSix() {
        return priceSix;
    }

    public AuctionVrnt priceSix(Float priceSix) {
        this.priceSix = priceSix;
        return this;
    }

    public void setPriceSix(Float priceSix) {
        this.priceSix = priceSix;
    }

    public Float getAmountSix() {
        return amountSix;
    }

    public AuctionVrnt amountSix(Float amountSix) {
        this.amountSix = amountSix;
        return this;
    }

    public void setAmountSix(Float amountSix) {
        this.amountSix = amountSix;
    }

    public String getTextSeven() {
        return textSeven;
    }

    public AuctionVrnt textSeven(String textSeven) {
        this.textSeven = textSeven;
        return this;
    }

    public void setTextSeven(String textSeven) {
        this.textSeven = textSeven;
    }

    public String getUomSeven() {
        return uomSeven;
    }

    public AuctionVrnt uomSeven(String uomSeven) {
        this.uomSeven = uomSeven;
        return this;
    }

    public void setUomSeven(String uomSeven) {
        this.uomSeven = uomSeven;
    }

    public Float getConvFactSeven() {
        return convFactSeven;
    }

    public AuctionVrnt convFactSeven(Float convFactSeven) {
        this.convFactSeven = convFactSeven;
        return this;
    }

    public void setConvFactSeven(Float convFactSeven) {
        this.convFactSeven = convFactSeven;
    }

    public Float getQtySeven() {
        return qtySeven;
    }

    public AuctionVrnt qtySeven(Float qtySeven) {
        this.qtySeven = qtySeven;
        return this;
    }

    public void setQtySeven(Float qtySeven) {
        this.qtySeven = qtySeven;
    }

    public Float getPriceSeven() {
        return priceSeven;
    }

    public AuctionVrnt priceSeven(Float priceSeven) {
        this.priceSeven = priceSeven;
        return this;
    }

    public void setPriceSeven(Float priceSeven) {
        this.priceSeven = priceSeven;
    }

    public Float getAmountSeven() {
        return amountSeven;
    }

    public AuctionVrnt amountSeven(Float amountSeven) {
        this.amountSeven = amountSeven;
        return this;
    }

    public void setAmountSeven(Float amountSeven) {
        this.amountSeven = amountSeven;
    }

    public String getTextEight() {
        return textEight;
    }

    public AuctionVrnt textEight(String textEight) {
        this.textEight = textEight;
        return this;
    }

    public void setTextEight(String textEight) {
        this.textEight = textEight;
    }

    public String getUomEight() {
        return uomEight;
    }

    public AuctionVrnt uomEight(String uomEight) {
        this.uomEight = uomEight;
        return this;
    }

    public void setUomEight(String uomEight) {
        this.uomEight = uomEight;
    }

    public Float getConvFactEight() {
        return convFactEight;
    }

    public AuctionVrnt convFactEight(Float convFactEight) {
        this.convFactEight = convFactEight;
        return this;
    }

    public void setConvFactEight(Float convFactEight) {
        this.convFactEight = convFactEight;
    }

    public Float getQtyEight() {
        return qtyEight;
    }

    public AuctionVrnt qtyEight(Float qtyEight) {
        this.qtyEight = qtyEight;
        return this;
    }

    public void setQtyEight(Float qtyEight) {
        this.qtyEight = qtyEight;
    }

    public Float getPriceEight() {
        return priceEight;
    }

    public AuctionVrnt priceEight(Float priceEight) {
        this.priceEight = priceEight;
        return this;
    }

    public void setPriceEight(Float priceEight) {
        this.priceEight = priceEight;
    }

    public Float getAmountEight() {
        return amountEight;
    }

    public AuctionVrnt amountEight(Float amountEight) {
        this.amountEight = amountEight;
        return this;
    }

    public void setAmountEight(Float amountEight) {
        this.amountEight = amountEight;
    }

    public String getTextNine() {
        return textNine;
    }

    public AuctionVrnt textNine(String textNine) {
        this.textNine = textNine;
        return this;
    }

    public void setTextNine(String textNine) {
        this.textNine = textNine;
    }

    public String getUomNine() {
        return uomNine;
    }

    public AuctionVrnt uomNine(String uomNine) {
        this.uomNine = uomNine;
        return this;
    }

    public void setUomNine(String uomNine) {
        this.uomNine = uomNine;
    }

    public Float getConvFactNine() {
        return convFactNine;
    }

    public AuctionVrnt convFactNine(Float convFactNine) {
        this.convFactNine = convFactNine;
        return this;
    }

    public void setConvFactNine(Float convFactNine) {
        this.convFactNine = convFactNine;
    }

    public Float getQtyNine() {
        return qtyNine;
    }

    public AuctionVrnt qtyNine(Float qtyNine) {
        this.qtyNine = qtyNine;
        return this;
    }

    public void setQtyNine(Float qtyNine) {
        this.qtyNine = qtyNine;
    }

    public Float getPriceNine() {
        return priceNine;
    }

    public AuctionVrnt priceNine(Float priceNine) {
        this.priceNine = priceNine;
        return this;
    }

    public void setPriceNine(Float priceNine) {
        this.priceNine = priceNine;
    }

    public Float getAmountNine() {
        return amountNine;
    }

    public AuctionVrnt amountNine(Float amountNine) {
        this.amountNine = amountNine;
        return this;
    }

    public void setAmountNine(Float amountNine) {
        this.amountNine = amountNine;
    }

    public String getTextTen() {
        return textTen;
    }

    public AuctionVrnt textTen(String textTen) {
        this.textTen = textTen;
        return this;
    }

    public void setTextTen(String textTen) {
        this.textTen = textTen;
    }

    public String getUomTen() {
        return uomTen;
    }

    public AuctionVrnt uomTen(String uomTen) {
        this.uomTen = uomTen;
        return this;
    }

    public void setUomTen(String uomTen) {
        this.uomTen = uomTen;
    }

    public Float getConvFactTen() {
        return convFactTen;
    }

    public AuctionVrnt convFactTen(Float convFactTen) {
        this.convFactTen = convFactTen;
        return this;
    }

    public void setConvFactTen(Float convFactTen) {
        this.convFactTen = convFactTen;
    }

    public Float getQtyTen() {
        return qtyTen;
    }

    public AuctionVrnt qtyTen(Float qtyTen) {
        this.qtyTen = qtyTen;
        return this;
    }

    public void setQtyTen(Float qtyTen) {
        this.qtyTen = qtyTen;
    }

    public Float getPriceTen() {
        return priceTen;
    }

    public AuctionVrnt priceTen(Float priceTen) {
        this.priceTen = priceTen;
        return this;
    }

    public void setPriceTen(Float priceTen) {
        this.priceTen = priceTen;
    }

    public Float getAmountTen() {
        return amountTen;
    }

    public AuctionVrnt amountTen(Float amountTen) {
        this.amountTen = amountTen;
        return this;
    }

    public void setAmountTen(Float amountTen) {
        this.amountTen = amountTen;
    }

    public RnsQuotationVariant getVariant() {
        return variant;
    }

    public AuctionVrnt variant(RnsQuotationVariant rnsQuotationVariant) {
        this.variant = rnsQuotationVariant;
        return this;
    }

    public void setVariant(RnsQuotationVariant rnsQuotationVariant) {
        this.variant = rnsQuotationVariant;
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
        AuctionVrnt auctionVrnt = (AuctionVrnt) o;
        if (auctionVrnt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auctionVrnt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuctionVrnt{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", applicable='" + isApplicable() + "'" +
            ", textOne='" + getTextOne() + "'" +
            ", uomOne='" + getUomOne() + "'" +
            ", convFactOne=" + getConvFactOne() +
            ", qtyOne=" + getQtyOne() +
            ", priceOne=" + getPriceOne() +
            ", amountOne=" + getAmountOne() +
            ", textTwo='" + getTextTwo() + "'" +
            ", uomTwo='" + getUomTwo() + "'" +
            ", convFactTwo=" + getConvFactTwo() +
            ", qtyTwo=" + getQtyTwo() +
            ", priceTwo=" + getPriceTwo() +
            ", amountTwo=" + getAmountTwo() +
            ", textThree='" + getTextThree() + "'" +
            ", uomThree='" + getUomThree() + "'" +
            ", convFactThree=" + getConvFactThree() +
            ", qtyThree=" + getQtyThree() +
            ", priceThree=" + getPriceThree() +
            ", amountThree=" + getAmountThree() +
            ", textFour='" + getTextFour() + "'" +
            ", uomFour='" + getUomFour() + "'" +
            ", convFactFour=" + getConvFactFour() +
            ", qtyFour=" + getQtyFour() +
            ", priceFour=" + getPriceFour() +
            ", amountFour=" + getAmountFour() +
            ", textFive='" + getTextFive() + "'" +
            ", uomFive='" + getUomFive() + "'" +
            ", convFactFive=" + getConvFactFive() +
            ", qtyFive=" + getQtyFive() +
            ", priceFive=" + getPriceFive() +
            ", amountFive=" + getAmountFive() +
            ", textSix='" + getTextSix() + "'" +
            ", uomSix='" + getUomSix() + "'" +
            ", convFactSix=" + getConvFactSix() +
            ", qtySix=" + getQtySix() +
            ", priceSix=" + getPriceSix() +
            ", amountSix=" + getAmountSix() +
            ", textSeven='" + getTextSeven() + "'" +
            ", uomSeven='" + getUomSeven() + "'" +
            ", convFactSeven=" + getConvFactSeven() +
            ", qtySeven=" + getQtySeven() +
            ", priceSeven=" + getPriceSeven() +
            ", amountSeven=" + getAmountSeven() +
            ", textEight='" + getTextEight() + "'" +
            ", uomEight='" + getUomEight() + "'" +
            ", convFactEight=" + getConvFactEight() +
            ", qtyEight=" + getQtyEight() +
            ", priceEight=" + getPriceEight() +
            ", amountEight=" + getAmountEight() +
            ", textNine='" + getTextNine() + "'" +
            ", uomNine='" + getUomNine() + "'" +
            ", convFactNine=" + getConvFactNine() +
            ", qtyNine=" + getQtyNine() +
            ", priceNine=" + getPriceNine() +
            ", amountNine=" + getAmountNine() +
            ", textTen='" + getTextTen() + "'" +
            ", uomTen='" + getUomTen() + "'" +
            ", convFactTen=" + getConvFactTen() +
            ", qtyTen=" + getQtyTen() +
            ", priceTen=" + getPriceTen() +
            ", amountTen=" + getAmountTen() +
            "}";
    }
}
