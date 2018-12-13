package rightchamps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RnsQuotationVariant.
 */

public class AuctionCustomVm implements Serializable {

    private static final long serialVersionUID = 1L;

     private Long id;

    private String title;

    private String varDescSpec1;

    private String varDescSpec1Value;

    private String varDescSpec2;

    private String varDescSpec2Value;

    private String varDescSpec3;

    private String varDescSpec3Value;

    private String varDescSpec4;

    private String varDescSpec4Value;

    private String varDescSpec5;

    private String varDescSpec5Value;

    private String varDescSpec6;

    private String varDescSpec6Value;

    private String varDescSpec7;

    private String varDescSpec7Value;

    private String varDescSpec8;

    private String varDescSpec8Value;

    private String varDescSpec9;

    private String varDescSpec9Value;

    private String varDescSpec10;

    private String varDescSpec10Value;

    private String eventDefType;

    private String eventDefCategory;

    private String eventDefTechnology;

    private String eventDefDefectCode;

    private String eventDefText1;

    private LocalDate dealtermCompletionBy;

    private LocalDate dealtermValidUntil;

    private String dealtermPaymentTerms;

    private String dealtermDeliveryTerms;

    private String dealtermDeliverAt;

    private String dealtermText2;

    private String orderQuantity;

    private String orderUom;

    private String remarks;

    private Float overTime;

    @OneToMany(mappedBy = "variant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RnsQuotationVendors> quotations = new HashSet<>();

    @ManyToOne
    private RnsQuotation quotation;

    private Boolean applicable;

    private String textOne;

    private String uomOne;

    private Float convFactOne;

    private Float qtyOne;

    private Float priceOne;

    private Float amountOne;

    private String textTwo;

    private String uomTwo;

    private Float convFactTwo;

    private Float qtyTwo;

    private Float priceTwo;

    private Float amountTwo;

    private String textThree;

    private String uomThree;

    private Float convFactThree;

    private Float qtyThree;

    private Float priceThree;

    private Float amountThree;

    private String textFour;

    private String uomFour;

    private Float convFactFour;

    private Float qtyFour;

    private Float priceFour;

    private Float amountFour;

    private String textFive;

    private String uomFive;

    private Float convFactFive;

    private Float qtyFive;

    private Float priceFive;

    private Float amountFive;

    private String textSix;

    private String uomSix;

    private Float convFactSix;

    private Float qtySix;

    private Float priceSix;

    private Float amountSix;

    private String textSeven;

    private String uomSeven;

    private Float convFactSeven;

    private Float qtySeven;

    private Float priceSeven;

    private Float amountSeven;

    private String textEight;

    private String uomEight;

    private Float convFactEight;

    private Float qtyEight;

    private Float priceEight;

    private Float amountEight;

    private String textNine;

    private String uomNine;

    private Float convFactNine;

    private Float qtyNine;

    private Float priceNine;

    private Float amountNine;

    private String textTen;

    private String uomTen;

    private Float convFactTen;

    private Float qtyTen;

    private Float priceTen;

    private Float amountTen;

    @ManyToOne
    private AuctionCustomVm variant;

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

    public AuctionCustomVm title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVarDescSpec1() {
        return varDescSpec1;
    }

    public AuctionCustomVm varDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
        return this;
    }

    public void setVarDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
    }

    public String getVarDescSpec1Value() {
        return varDescSpec1Value;
    }

    public AuctionCustomVm varDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
        return this;
    }

    public void setVarDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
    }

    public String getVarDescSpec2() {
        return varDescSpec2;
    }

    public AuctionCustomVm varDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
        return this;
    }

    public void setVarDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
    }

    public String getVarDescSpec2Value() {
        return varDescSpec2Value;
    }

    public AuctionCustomVm varDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
        return this;
    }

    public void setVarDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
    }

    public String getVarDescSpec3() {
        return varDescSpec3;
    }

    public AuctionCustomVm varDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
        return this;
    }

    public void setVarDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
    }

    public String getVarDescSpec3Value() {
        return varDescSpec3Value;
    }

    public AuctionCustomVm varDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
        return this;
    }

    public void setVarDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
    }

    public String getVarDescSpec4() {
        return varDescSpec4;
    }

    public AuctionCustomVm varDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
        return this;
    }

    public void setVarDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
    }

    public String getVarDescSpec4Value() {
        return varDescSpec4Value;
    }

    public AuctionCustomVm varDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
        return this;
    }

    public void setVarDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
    }

    public String getVarDescSpec5() {
        return varDescSpec5;
    }

    public AuctionCustomVm varDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
        return this;
    }

    public void setVarDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
    }

    public String getVarDescSpec5Value() {
        return varDescSpec5Value;
    }

    public AuctionCustomVm varDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
        return this;
    }

    public void setVarDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
    }

    public String getVarDescSpec6() {
        return varDescSpec6;
    }

    public AuctionCustomVm varDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
        return this;
    }

    public void setVarDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
    }

    public String getVarDescSpec6Value() {
        return varDescSpec6Value;
    }

    public AuctionCustomVm varDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
        return this;
    }

    public void setVarDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
    }

    public String getVarDescSpec7() {
        return varDescSpec7;
    }

    public AuctionCustomVm varDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
        return this;
    }

    public void setVarDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
    }

    public String getVarDescSpec7Value() {
        return varDescSpec7Value;
    }

    public AuctionCustomVm varDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
        return this;
    }

    public void setVarDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
    }

    public String getVarDescSpec8() {
        return varDescSpec8;
    }

    public AuctionCustomVm varDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
        return this;
    }

    public void setVarDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
    }

    public String getVarDescSpec8Value() {
        return varDescSpec8Value;
    }

    public AuctionCustomVm varDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
        return this;
    }

    public void setVarDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
    }

    public String getVarDescSpec9() {
        return varDescSpec9;
    }

    public AuctionCustomVm varDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
        return this;
    }

    public void setVarDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
    }

    public String getVarDescSpec9Value() {
        return varDescSpec9Value;
    }

    public AuctionCustomVm varDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
        return this;
    }

    public void setVarDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
    }

    public String getVarDescSpec10() {
        return varDescSpec10;
    }

    public AuctionCustomVm varDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
        return this;
    }

    public void setVarDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
    }

    public String getVarDescSpec10Value() {
        return varDescSpec10Value;
    }

    public AuctionCustomVm varDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
        return this;
    }

    public void setVarDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
    }

    public String getEventDefType() {
        return eventDefType;
    }

    public AuctionCustomVm eventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
        return this;
    }

    public void setEventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
    }

    public String getEventDefCategory() {
        return eventDefCategory;
    }

    public AuctionCustomVm eventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
        return this;
    }

    public void setEventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
    }

    public String getEventDefTechnology() {
        return eventDefTechnology;
    }

    public AuctionCustomVm eventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
        return this;
    }

    public void setEventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
    }

    public String getEventDefDefectCode() {
        return eventDefDefectCode;
    }

    public AuctionCustomVm eventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
        return this;
    }

    public void setEventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
    }

    public String getEventDefText1() {
        return eventDefText1;
    }

    public AuctionCustomVm eventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
        return this;
    }

    public void setEventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
    }

    public LocalDate getDealtermCompletionBy() {
        return dealtermCompletionBy;
    }

    public AuctionCustomVm dealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
        return this;
    }

    public void setDealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
    }

    public LocalDate getDealtermValidUntil() {
        return dealtermValidUntil;
    }

    public AuctionCustomVm dealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
        return this;
    }

    public void setDealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
    }

    public String getDealtermPaymentTerms() {
        return dealtermPaymentTerms;
    }

    public AuctionCustomVm dealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
        return this;
    }

    public void setDealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
    }

    public String getDealtermDeliveryTerms() {
        return dealtermDeliveryTerms;
    }

    public AuctionCustomVm dealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
        return this;
    }

    public void setDealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
    }

    public String getDealtermDeliverAt() {
        return dealtermDeliverAt;
    }

    public AuctionCustomVm dealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
        return this;
    }

    public void setDealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
    }

    public String getDealtermText2() {
        return dealtermText2;
    }

    public AuctionCustomVm dealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
        return this;
    }

    public void setDealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public AuctionCustomVm orderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderUom() {
        return orderUom;
    }

    public AuctionCustomVm orderUom(String orderUom) {
        this.orderUom = orderUom;
        return this;
    }

    public void setOrderUom(String orderUom) {
        this.orderUom = orderUom;
    }

    public String getRemarks() {
        return remarks;
    }

    public AuctionCustomVm remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Float getOverTime() {
        return overTime;
    }

    public AuctionCustomVm overTime(Float overTime) {
        this.overTime = overTime;
        return this;
    }

    public void setOverTime(Float overTime) {
        this.overTime = overTime;
    }

    public Set<RnsQuotationVendors> getQuotations() {
        return quotations;
    }

    public AuctionCustomVm quotations(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotations = rnsQuotationVendors;
        return this;
    }

    public void setQuotations(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotations = rnsQuotationVendors;
    }

    public RnsQuotation getQuotation() {
        return quotation;
    }

    public AuctionCustomVm quotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
        return this;
    }

    public void setQuotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
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
        AuctionCustomVm auctionCustomVm = (AuctionCustomVm) o;
        if (auctionCustomVm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auctionCustomVm.getId());
    }
    

    public Boolean getApplicable() {
		return applicable;
	}

	public void setApplicable(Boolean applicable) {
		this.applicable = applicable;
	}

	public String getTextOne() {
		return textOne;
	}

	public void setTextOne(String textOne) {
		this.textOne = textOne;
	}

	public String getUomOne() {
		return uomOne;
	}

	public void setUomOne(String uomOne) {
		this.uomOne = uomOne;
	}

	public Float getConvFactOne() {
		return convFactOne;
	}

	public void setConvFactOne(Float convFactOne) {
		this.convFactOne = convFactOne;
	}

	public Float getQtyOne() {
		return qtyOne;
	}

	public void setQtyOne(Float qtyOne) {
		this.qtyOne = qtyOne;
	}

	public Float getPriceOne() {
		return priceOne;
	}

	public void setPriceOne(Float priceOne) {
		this.priceOne = priceOne;
	}

	public Float getAmountOne() {
		return amountOne;
	}

	public void setAmountOne(Float amountOne) {
		this.amountOne = amountOne;
	}

	public String getTextTwo() {
		return textTwo;
	}

	public void setTextTwo(String textTwo) {
		this.textTwo = textTwo;
	}

	public String getUomTwo() {
		return uomTwo;
	}

	public void setUomTwo(String uomTwo) {
		this.uomTwo = uomTwo;
	}

	public Float getConvFactTwo() {
		return convFactTwo;
	}

	public void setConvFactTwo(Float convFactTwo) {
		this.convFactTwo = convFactTwo;
	}

	public Float getQtyTwo() {
		return qtyTwo;
	}

	public void setQtyTwo(Float qtyTwo) {
		this.qtyTwo = qtyTwo;
	}

	public Float getPriceTwo() {
		return priceTwo;
	}

	public void setPriceTwo(Float priceTwo) {
		this.priceTwo = priceTwo;
	}

	public Float getAmountTwo() {
		return amountTwo;
	}

	public void setAmountTwo(Float amountTwo) {
		this.amountTwo = amountTwo;
	}

	public String getTextThree() {
		return textThree;
	}

	public void setTextThree(String textThree) {
		this.textThree = textThree;
	}

	public String getUomThree() {
		return uomThree;
	}

	public void setUomThree(String uomThree) {
		this.uomThree = uomThree;
	}

	public Float getConvFactThree() {
		return convFactThree;
	}

	public void setConvFactThree(Float convFactThree) {
		this.convFactThree = convFactThree;
	}

	public Float getQtyThree() {
		return qtyThree;
	}

	public void setQtyThree(Float qtyThree) {
		this.qtyThree = qtyThree;
	}

	public Float getPriceThree() {
		return priceThree;
	}

	public void setPriceThree(Float priceThree) {
		this.priceThree = priceThree;
	}

	public Float getAmountThree() {
		return amountThree;
	}

	public void setAmountThree(Float amountThree) {
		this.amountThree = amountThree;
	}

	public String getTextFour() {
		return textFour;
	}

	public void setTextFour(String textFour) {
		this.textFour = textFour;
	}

	public String getUomFour() {
		return uomFour;
	}

	public void setUomFour(String uomFour) {
		this.uomFour = uomFour;
	}

	public Float getConvFactFour() {
		return convFactFour;
	}

	public void setConvFactFour(Float convFactFour) {
		this.convFactFour = convFactFour;
	}

	public Float getQtyFour() {
		return qtyFour;
	}

	public void setQtyFour(Float qtyFour) {
		this.qtyFour = qtyFour;
	}

	public Float getPriceFour() {
		return priceFour;
	}

	public void setPriceFour(Float priceFour) {
		this.priceFour = priceFour;
	}

	public Float getAmountFour() {
		return amountFour;
	}

	public void setAmountFour(Float amountFour) {
		this.amountFour = amountFour;
	}

	public String getTextFive() {
		return textFive;
	}

	public void setTextFive(String textFive) {
		this.textFive = textFive;
	}

	public String getUomFive() {
		return uomFive;
	}

	public void setUomFive(String uomFive) {
		this.uomFive = uomFive;
	}

	public Float getConvFactFive() {
		return convFactFive;
	}

	public void setConvFactFive(Float convFactFive) {
		this.convFactFive = convFactFive;
	}

	public Float getQtyFive() {
		return qtyFive;
	}

	public void setQtyFive(Float qtyFive) {
		this.qtyFive = qtyFive;
	}

	public Float getPriceFive() {
		return priceFive;
	}

	public void setPriceFive(Float priceFive) {
		this.priceFive = priceFive;
	}

	public Float getAmountFive() {
		return amountFive;
	}

	public void setAmountFive(Float amountFive) {
		this.amountFive = amountFive;
	}

	public String getTextSix() {
		return textSix;
	}

	public void setTextSix(String textSix) {
		this.textSix = textSix;
	}

	public String getUomSix() {
		return uomSix;
	}

	public void setUomSix(String uomSix) {
		this.uomSix = uomSix;
	}

	public Float getConvFactSix() {
		return convFactSix;
	}

	public void setConvFactSix(Float convFactSix) {
		this.convFactSix = convFactSix;
	}

	public Float getQtySix() {
		return qtySix;
	}

	public void setQtySix(Float qtySix) {
		this.qtySix = qtySix;
	}

	public Float getPriceSix() {
		return priceSix;
	}

	public void setPriceSix(Float priceSix) {
		this.priceSix = priceSix;
	}

	public Float getAmountSix() {
		return amountSix;
	}

	public void setAmountSix(Float amountSix) {
		this.amountSix = amountSix;
	}

	public String getTextSeven() {
		return textSeven;
	}

	public void setTextSeven(String textSeven) {
		this.textSeven = textSeven;
	}

	public String getUomSeven() {
		return uomSeven;
	}

	public void setUomSeven(String uomSeven) {
		this.uomSeven = uomSeven;
	}

	public Float getConvFactSeven() {
		return convFactSeven;
	}

	public void setConvFactSeven(Float convFactSeven) {
		this.convFactSeven = convFactSeven;
	}

	public Float getQtySeven() {
		return qtySeven;
	}

	public void setQtySeven(Float qtySeven) {
		this.qtySeven = qtySeven;
	}

	public Float getPriceSeven() {
		return priceSeven;
	}

	public void setPriceSeven(Float priceSeven) {
		this.priceSeven = priceSeven;
	}

	public Float getAmountSeven() {
		return amountSeven;
	}

	public void setAmountSeven(Float amountSeven) {
		this.amountSeven = amountSeven;
	}

	public String getTextEight() {
		return textEight;
	}

	public void setTextEight(String textEight) {
		this.textEight = textEight;
	}

	public String getUomEight() {
		return uomEight;
	}

	public void setUomEight(String uomEight) {
		this.uomEight = uomEight;
	}

	public Float getConvFactEight() {
		return convFactEight;
	}

	public void setConvFactEight(Float convFactEight) {
		this.convFactEight = convFactEight;
	}

	public Float getQtyEight() {
		return qtyEight;
	}

	public void setQtyEight(Float qtyEight) {
		this.qtyEight = qtyEight;
	}

	public Float getPriceEight() {
		return priceEight;
	}

	public void setPriceEight(Float priceEight) {
		this.priceEight = priceEight;
	}

	public Float getAmountEight() {
		return amountEight;
	}

	public void setAmountEight(Float amountEight) {
		this.amountEight = amountEight;
	}

	public String getTextNine() {
		return textNine;
	}

	public void setTextNine(String textNine) {
		this.textNine = textNine;
	}

	public String getUomNine() {
		return uomNine;
	}

	public void setUomNine(String uomNine) {
		this.uomNine = uomNine;
	}

	public Float getConvFactNine() {
		return convFactNine;
	}

	public void setConvFactNine(Float convFactNine) {
		this.convFactNine = convFactNine;
	}

	public Float getQtyNine() {
		return qtyNine;
	}

	public void setQtyNine(Float qtyNine) {
		this.qtyNine = qtyNine;
	}

	public Float getPriceNine() {
		return priceNine;
	}

	public void setPriceNine(Float priceNine) {
		this.priceNine = priceNine;
	}

	public Float getAmountNine() {
		return amountNine;
	}

	public void setAmountNine(Float amountNine) {
		this.amountNine = amountNine;
	}

	public String getTextTen() {
		return textTen;
	}

	public void setTextTen(String textTen) {
		this.textTen = textTen;
	}

	public String getUomTen() {
		return uomTen;
	}

	public void setUomTen(String uomTen) {
		this.uomTen = uomTen;
	}

	public Float getConvFactTen() {
		return convFactTen;
	}

	public void setConvFactTen(Float convFactTen) {
		this.convFactTen = convFactTen;
	}

	public Float getQtyTen() {
		return qtyTen;
	}

	public void setQtyTen(Float qtyTen) {
		this.qtyTen = qtyTen;
	}

	public Float getPriceTen() {
		return priceTen;
	}

	public void setPriceTen(Float priceTen) {
		this.priceTen = priceTen;
	}

	public Float getAmountTen() {
		return amountTen;
	}

	public void setAmountTen(Float amountTen) {
		this.amountTen = amountTen;
	}

	public AuctionCustomVm getVariant() {
		return variant;
	}

	public void setVariant(AuctionCustomVm variant) {
		this.variant = variant;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "AuctionCustomVm [id=" + id + ", title=" + title + ", varDescSpec1=" + varDescSpec1
				+ ", varDescSpec1Value=" + varDescSpec1Value + ", varDescSpec2=" + varDescSpec2 + ", varDescSpec2Value="
				+ varDescSpec2Value + ", varDescSpec3=" + varDescSpec3 + ", varDescSpec3Value=" + varDescSpec3Value
				+ ", varDescSpec4=" + varDescSpec4 + ", varDescSpec4Value=" + varDescSpec4Value + ", varDescSpec5="
				+ varDescSpec5 + ", varDescSpec5Value=" + varDescSpec5Value + ", varDescSpec6=" + varDescSpec6
				+ ", varDescSpec6Value=" + varDescSpec6Value + ", varDescSpec7=" + varDescSpec7 + ", varDescSpec7Value="
				+ varDescSpec7Value + ", varDescSpec8=" + varDescSpec8 + ", varDescSpec8Value=" + varDescSpec8Value
				+ ", varDescSpec9=" + varDescSpec9 + ", varDescSpec9Value=" + varDescSpec9Value + ", varDescSpec10="
				+ varDescSpec10 + ", varDescSpec10Value=" + varDescSpec10Value + ", eventDefType=" + eventDefType
				+ ", eventDefCategory=" + eventDefCategory + ", eventDefTechnology=" + eventDefTechnology
				+ ", eventDefDefectCode=" + eventDefDefectCode + ", eventDefText1=" + eventDefText1
				+ ", dealtermCompletionBy=" + dealtermCompletionBy + ", dealtermValidUntil=" + dealtermValidUntil
				+ ", dealtermPaymentTerms=" + dealtermPaymentTerms + ", dealtermDeliveryTerms=" + dealtermDeliveryTerms
				+ ", dealtermDeliverAt=" + dealtermDeliverAt + ", dealtermText2=" + dealtermText2 + ", orderQuantity="
				+ orderQuantity + ", orderUom=" + orderUom + ", remarks=" + remarks + ", overTime=" + overTime
				+ ", quotations=" + quotations + ", quotation=" + quotation + ", applicable=" + applicable
				+ ", textOne=" + textOne + ", uomOne=" + uomOne + ", convFactOne=" + convFactOne + ", qtyOne=" + qtyOne
				+ ", priceOne=" + priceOne + ", amountOne=" + amountOne + ", textTwo=" + textTwo + ", uomTwo=" + uomTwo
				+ ", convFactTwo=" + convFactTwo + ", qtyTwo=" + qtyTwo + ", priceTwo=" + priceTwo + ", amountTwo="
				+ amountTwo + ", textThree=" + textThree + ", uomThree=" + uomThree + ", convFactThree=" + convFactThree
				+ ", qtyThree=" + qtyThree + ", priceThree=" + priceThree + ", amountThree=" + amountThree
				+ ", textFour=" + textFour + ", uomFour=" + uomFour + ", convFactFour=" + convFactFour + ", qtyFour="
				+ qtyFour + ", priceFour=" + priceFour + ", amountFour=" + amountFour + ", textFive=" + textFive
				+ ", uomFive=" + uomFive + ", convFactFive=" + convFactFive + ", qtyFive=" + qtyFive + ", priceFive="
				+ priceFive + ", amountFive=" + amountFive + ", textSix=" + textSix + ", uomSix=" + uomSix
				+ ", convFactSix=" + convFactSix + ", qtySix=" + qtySix + ", priceSix=" + priceSix + ", amountSix="
				+ amountSix + ", textSeven=" + textSeven + ", uomSeven=" + uomSeven + ", convFactSeven=" + convFactSeven
				+ ", qtySeven=" + qtySeven + ", priceSeven=" + priceSeven + ", amountSeven=" + amountSeven
				+ ", textEight=" + textEight + ", uomEight=" + uomEight + ", convFactEight=" + convFactEight
				+ ", qtyEight=" + qtyEight + ", priceEight=" + priceEight + ", amountEight=" + amountEight
				+ ", textNine=" + textNine + ", uomNine=" + uomNine + ", convFactNine=" + convFactNine + ", qtyNine="
				+ qtyNine + ", priceNine=" + priceNine + ", amountNine=" + amountNine + ", textTen=" + textTen
				+ ", uomTen=" + uomTen + ", convFactTen=" + convFactTen + ", qtyTen=" + qtyTen + ", priceTen="
				+ priceTen + ", amountTen=" + amountTen + ", variant=" + variant + ", getId()=" + getId()
				+ ", getTitle()=" + getTitle() + ", getVarDescSpec1()=" + getVarDescSpec1()
				+ ", getVarDescSpec1Value()=" + getVarDescSpec1Value() + ", getVarDescSpec2()=" + getVarDescSpec2()
				+ ", getVarDescSpec2Value()=" + getVarDescSpec2Value() + ", getVarDescSpec3()=" + getVarDescSpec3()
				+ ", getVarDescSpec3Value()=" + getVarDescSpec3Value() + ", getVarDescSpec4()=" + getVarDescSpec4()
				+ ", getVarDescSpec4Value()=" + getVarDescSpec4Value() + ", getVarDescSpec5()=" + getVarDescSpec5()
				+ ", getVarDescSpec5Value()=" + getVarDescSpec5Value() + ", getVarDescSpec6()=" + getVarDescSpec6()
				+ ", getVarDescSpec6Value()=" + getVarDescSpec6Value() + ", getVarDescSpec7()=" + getVarDescSpec7()
				+ ", getVarDescSpec7Value()=" + getVarDescSpec7Value() + ", getVarDescSpec8()=" + getVarDescSpec8()
				+ ", getVarDescSpec8Value()=" + getVarDescSpec8Value() + ", getVarDescSpec9()=" + getVarDescSpec9()
				+ ", getVarDescSpec9Value()=" + getVarDescSpec9Value() + ", getVarDescSpec10()=" + getVarDescSpec10()
				+ ", getVarDescSpec10Value()=" + getVarDescSpec10Value() + ", getEventDefType()=" + getEventDefType()
				+ ", getEventDefCategory()=" + getEventDefCategory() + ", getEventDefTechnology()="
				+ getEventDefTechnology() + ", getEventDefDefectCode()=" + getEventDefDefectCode()
				+ ", getEventDefText1()=" + getEventDefText1() + ", getDealtermCompletionBy()="
				+ getDealtermCompletionBy() + ", getDealtermValidUntil()=" + getDealtermValidUntil()
				+ ", getDealtermPaymentTerms()=" + getDealtermPaymentTerms() + ", getDealtermDeliveryTerms()="
				+ getDealtermDeliveryTerms() + ", getDealtermDeliverAt()=" + getDealtermDeliverAt()
				+ ", getDealtermText2()=" + getDealtermText2() + ", getOrderQuantity()=" + getOrderQuantity()
				+ ", getOrderUom()=" + getOrderUom() + ", getRemarks()=" + getRemarks() + ", getOverTime()="
				+ getOverTime() + ", getQuotations()=" + getQuotations() + ", getQuotation()=" + getQuotation()
				+ ", getApplicable()=" + getApplicable() + ", getTextOne()=" + getTextOne() + ", getUomOne()="
				+ getUomOne() + ", getConvFactOne()=" + getConvFactOne() + ", getQtyOne()=" + getQtyOne()
				+ ", getPriceOne()=" + getPriceOne() + ", getAmountOne()=" + getAmountOne() + ", getTextTwo()="
				+ getTextTwo() + ", getUomTwo()=" + getUomTwo() + ", getConvFactTwo()=" + getConvFactTwo()
				+ ", getQtyTwo()=" + getQtyTwo() + ", getPriceTwo()=" + getPriceTwo() + ", getAmountTwo()="
				+ getAmountTwo() + ", getTextThree()=" + getTextThree() + ", getUomThree()=" + getUomThree()
				+ ", getConvFactThree()=" + getConvFactThree() + ", getQtyThree()=" + getQtyThree()
				+ ", getPriceThree()=" + getPriceThree() + ", getAmountThree()=" + getAmountThree() + ", getTextFour()="
				+ getTextFour() + ", getUomFour()=" + getUomFour() + ", getConvFactFour()=" + getConvFactFour()
				+ ", getQtyFour()=" + getQtyFour() + ", getPriceFour()=" + getPriceFour() + ", getAmountFour()="
				+ getAmountFour() + ", getTextFive()=" + getTextFive() + ", getUomFive()=" + getUomFive()
				+ ", getConvFactFive()=" + getConvFactFive() + ", getQtyFive()=" + getQtyFive() + ", getPriceFive()="
				+ getPriceFive() + ", getAmountFive()=" + getAmountFive() + ", getTextSix()=" + getTextSix()
				+ ", getUomSix()=" + getUomSix() + ", getConvFactSix()=" + getConvFactSix() + ", getQtySix()="
				+ getQtySix() + ", getPriceSix()=" + getPriceSix() + ", getAmountSix()=" + getAmountSix()
				+ ", getTextSeven()=" + getTextSeven() + ", getUomSeven()=" + getUomSeven() + ", getConvFactSeven()="
				+ getConvFactSeven() + ", getQtySeven()=" + getQtySeven() + ", getPriceSeven()=" + getPriceSeven()
				+ ", getAmountSeven()=" + getAmountSeven() + ", getTextEight()=" + getTextEight() + ", getUomEight()="
				+ getUomEight() + ", getConvFactEight()=" + getConvFactEight() + ", getQtyEight()=" + getQtyEight()
				+ ", getPriceEight()=" + getPriceEight() + ", getAmountEight()=" + getAmountEight() + ", getTextNine()="
				+ getTextNine() + ", getUomNine()=" + getUomNine() + ", getConvFactNine()=" + getConvFactNine()
				+ ", getQtyNine()=" + getQtyNine() + ", getPriceNine()=" + getPriceNine() + ", getAmountNine()="
				+ getAmountNine() + ", getTextTen()=" + getTextTen() + ", getUomTen()=" + getUomTen()
				+ ", getConvFactTen()=" + getConvFactTen() + ", getQtyTen()=" + getQtyTen() + ", getPriceTen()="
				+ getPriceTen() + ", getAmountTen()=" + getAmountTen() + ", getVariant()=" + getVariant()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}

    
}
