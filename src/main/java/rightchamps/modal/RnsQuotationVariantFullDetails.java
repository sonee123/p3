package rightchamps.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import rightchamps.domain.Auction;
import rightchamps.domain.AuctionVrnt;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RnsQuotationVariant.
 */
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationVariantFullDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String title;

    private Integer variantRank;

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

    private String dealtermPaymentTermsDesc;

    private String dealtermDeliveryTerms;

    private String taxTerms;
    
    private String dealtermsTaxTermsDesc;

    private String dealtermDeliveryTermsDesc;

    private String dealtermDeliverAt;
    
    private String dealtermsDelPlaceDesc;

    private String dealtermText2;

    private String orderQuantity;

    private String orderUom;

    private String remarks;

    private Float overTime;

    private Float historicalPrice;

    private String currency;

    private String natureOfPrice;

    private Instant lotStartTime;

    private Instant lotEndTime;

    private String lotStartTimeDateFormat;

    private String lotEndTimeDateFormat;

    private AuctionVrnt auctionVrnt;

    RnsQuotationFullDetails quotationFullDetails;
    
    private String openCosting;
    private String uploadFlag;
    private Float bidStartPrice;

    private Boolean showInRfqOneRequired;
    private Boolean showInRfqTwoRequired;
    private Boolean showInRfqThreeRequired;
    private Boolean showInRfqFourRequired;
    private Boolean showInRfqFiveRequired;
    private Boolean showInRfqSixRequired;
    private Boolean showInRfqSevenRequired;
    private Boolean showInRfqEightRequired;
    private Boolean showInRfqNineRequired;
    private Boolean showInRfqTenRequired;

    private Boolean showInAuctionOneRequired;
    private Boolean showInAuctionTwoRequired;
    private Boolean showInAuctionThreeRequired;
    private Boolean showInAuctionFourRequired;
    private Boolean showInAuctionFiveRequired;
    private Boolean showInAuctionSixRequired;
    private Boolean showInAuctionSevenRequired;
    private Boolean showInAuctionEightRequired;
    private Boolean showInAuctionNineRequired;
    private Boolean showInAuctionTenRequired;


    @OneToMany
    private Set<RnsQuotationVendorsBean> vendors = new HashSet<>();

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

    public RnsQuotationVariantFullDetails title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVarDescSpec1() {
        return varDescSpec1;
    }

    public RnsQuotationVariantFullDetails varDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
        return this;
    }

    public void setVarDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
    }

    public String getVarDescSpec1Value() {
        return varDescSpec1Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
        return this;
    }

    public void setVarDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
    }

    public String getVarDescSpec2() {
        return varDescSpec2;
    }

    public RnsQuotationVariantFullDetails varDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
        return this;
    }

    public void setVarDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
    }

    public String getVarDescSpec2Value() {
        return varDescSpec2Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
        return this;
    }

    public void setVarDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
    }

    public String getVarDescSpec3() {
        return varDescSpec3;
    }

    public RnsQuotationVariantFullDetails varDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
        return this;
    }

    public void setVarDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
    }

    public String getVarDescSpec3Value() {
        return varDescSpec3Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
        return this;
    }

    public void setVarDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
    }

    public String getVarDescSpec4() {
        return varDescSpec4;
    }

    public RnsQuotationVariantFullDetails varDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
        return this;
    }

    public void setVarDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
    }

    public String getVarDescSpec4Value() {
        return varDescSpec4Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
        return this;
    }

    public void setVarDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
    }

    public String getVarDescSpec5() {
        return varDescSpec5;
    }

    public RnsQuotationVariantFullDetails varDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
        return this;
    }

    public void setVarDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
    }

    public String getVarDescSpec5Value() {
        return varDescSpec5Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
        return this;
    }

    public void setVarDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
    }

    public String getVarDescSpec6() {
        return varDescSpec6;
    }

    public RnsQuotationVariantFullDetails varDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
        return this;
    }

    public void setVarDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
    }

    public String getVarDescSpec6Value() {
        return varDescSpec6Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
        return this;
    }

    public void setVarDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
    }

    public String getVarDescSpec7() {
        return varDescSpec7;
    }

    public RnsQuotationVariantFullDetails varDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
        return this;
    }

    public void setVarDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
    }

    public String getVarDescSpec7Value() {
        return varDescSpec7Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
        return this;
    }

    public void setVarDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
    }

    public String getVarDescSpec8() {
        return varDescSpec8;
    }

    public RnsQuotationVariantFullDetails varDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
        return this;
    }

    public void setVarDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
    }

    public String getVarDescSpec8Value() {
        return varDescSpec8Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
        return this;
    }

    public void setVarDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
    }

    public String getVarDescSpec9() {
        return varDescSpec9;
    }

    public RnsQuotationVariantFullDetails varDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
        return this;
    }

    public void setVarDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
    }

    public String getVarDescSpec9Value() {
        return varDescSpec9Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
        return this;
    }

    public void setVarDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
    }

    public String getVarDescSpec10() {
        return varDescSpec10;
    }

    public RnsQuotationVariantFullDetails varDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
        return this;
    }

    public void setVarDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
    }

    public String getVarDescSpec10Value() {
        return varDescSpec10Value;
    }

    public RnsQuotationVariantFullDetails varDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
        return this;
    }

    public void setVarDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
    }

    public String getEventDefType() {
        return eventDefType;
    }

    public RnsQuotationVariantFullDetails eventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
        return this;
    }

    public void setEventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
    }

    public String getEventDefCategory() {
        return eventDefCategory;
    }

    public RnsQuotationVariantFullDetails eventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
        return this;
    }

    public void setEventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
    }

    public String getEventDefTechnology() {
        return eventDefTechnology;
    }

    public RnsQuotationVariantFullDetails eventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
        return this;
    }

    public void setEventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
    }

    public String getEventDefDefectCode() {
        return eventDefDefectCode;
    }

    public RnsQuotationVariantFullDetails eventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
        return this;
    }

    public void setEventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
    }

    public String getEventDefText1() {
        return eventDefText1;
    }

    public RnsQuotationVariantFullDetails eventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
        return this;
    }

    public void setEventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
    }

    public LocalDate getDealtermCompletionBy() {
        return dealtermCompletionBy;
    }

    public RnsQuotationVariantFullDetails dealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
        return this;
    }

    public void setDealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
    }

    public LocalDate getDealtermValidUntil() {
        return dealtermValidUntil;
    }

    public RnsQuotationVariantFullDetails dealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
        return this;
    }

    public void setDealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
    }

    public String getDealtermPaymentTerms() {
        return dealtermPaymentTerms;
    }

    public RnsQuotationVariantFullDetails dealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
        return this;
    }

    public void setDealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
    }

    public String getDealtermDeliveryTerms() {
        return dealtermDeliveryTerms;
    }

    public RnsQuotationVariantFullDetails dealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
        return this;
    }

    public void setDealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
    }


    public String getTaxTerms() {
		return taxTerms;
	}
    public RnsQuotationVariantFullDetails taxTerms(String taxTerms) {
    	this.taxTerms = taxTerms;
    	return this;
    }
	public void setTaxTerms(String taxTerms) {
		this.taxTerms = taxTerms;
	}

	public String getDealtermDeliverAt() {
        return dealtermDeliverAt;
    }

    public RnsQuotationVariantFullDetails dealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
        return this;
    }

    public void setDealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
    }

    public String getDealtermText2() {
        return dealtermText2;
    }

    public RnsQuotationVariantFullDetails dealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
        return this;
    }

    public void setDealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public RnsQuotationVariantFullDetails orderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderUom() {
        return orderUom;
    }

    public RnsQuotationVariantFullDetails orderUom(String orderUom) {
        this.orderUom = orderUom;
        return this;
    }

    public void setOrderUom(String orderUom) {
        this.orderUom = orderUom;
    }

    public String getRemarks() {
        return remarks;
    }

    public RnsQuotationVariantFullDetails remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Float getOverTime() {
        return overTime;
    }

    public RnsQuotationVariantFullDetails overTime(Float overTime) {
        this.overTime = overTime;
        return this;
    }

    public void setOverTime(Float overTime) {
        this.overTime = overTime;
    }

    public Set<RnsQuotationVendorsBean> getVendors() {
        return vendors;
    }

    public void setVendors(Set<RnsQuotationVendorsBean> vendors) {
        this.vendors = vendors;
    }

    public Integer getVariantRank() {
        return variantRank;
    }

    public void setVariantRank(Integer variantRank) {
        this.variantRank = variantRank;
    }

    public Instant getLotStartTime() {
        return lotStartTime;
    }

    public void setLotStartTime(Instant lotStartTime) {
        this.lotStartTime = lotStartTime;
    }

    public Instant getLotEndTime() {
        return lotEndTime;
    }

    public void setLotEndTime(Instant lotEndTime) {
        this.lotEndTime = lotEndTime;
    }
    
    

    public String getDealtermsTaxTermsDesc() {
		return dealtermsTaxTermsDesc;
	}
    
    public RnsQuotationVariantFullDetails dealtermsTaxTermsDesc(String dealtermsTaxTermsDesc) {
    	this.dealtermsTaxTermsDesc = dealtermsTaxTermsDesc;
    	return this;
    }

	public void setDealtermsTaxTermsDesc(String dealtermsTaxTermsDesc) {
		this.dealtermsTaxTermsDesc = dealtermsTaxTermsDesc;
	}

	
	public String getDealtermsDelPlaceDesc() {
		return dealtermsDelPlaceDesc;
	}

	public RnsQuotationVariantFullDetails dealtermsDelPlaceDesc(String dealtermsDelPlaceDesc) {
		this.dealtermsDelPlaceDesc = dealtermsDelPlaceDesc;
		return this;
	}
	
	public void setDealtermsDelPlaceDesc(String dealtermsDelPlaceDesc) {
		this.dealtermsDelPlaceDesc = dealtermsDelPlaceDesc;
	}
	
	

	public String getOpenCosting() {
		return openCosting;
	}

	public void setOpenCosting(String openCosting) {
		this.openCosting = openCosting;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDealtermPaymentTermsDesc() { return dealtermPaymentTermsDesc; }

    public void setDealtermPaymentTermsDesc(String dealtermPaymentTermsDesc) { this.dealtermPaymentTermsDesc = dealtermPaymentTermsDesc; }

    public String getDealtermDeliveryTermsDesc() { return dealtermDeliveryTermsDesc; }

    public void setDealtermDeliveryTermsDesc(String dealtermDeliveryTermsDesc) { this.dealtermDeliveryTermsDesc = dealtermDeliveryTermsDesc; }

    public Float getHistoricalPrice() { return historicalPrice; }

    public void setHistoricalPrice(Float historicalPrice) { this.historicalPrice = historicalPrice; }

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public String getNatureOfPrice() { return natureOfPrice; }

    public void setNatureOfPrice(String natureOfPrice) { this.natureOfPrice = natureOfPrice; }

    public AuctionVrnt getAuctionVrnt() { return auctionVrnt; }

    public void setAuctionVrnt(AuctionVrnt auctionVrnt) { this.auctionVrnt = auctionVrnt; }

    public RnsQuotationFullDetails getQuotationFullDetails() { return quotationFullDetails; }

    public void setQuotationFullDetails(RnsQuotationFullDetails quotationFullDetails) { this.quotationFullDetails = quotationFullDetails; }

    public Boolean getShowInRfqOneRequired() {
        return showInRfqOneRequired;
    }

    public void setShowInRfqOneRequired(Boolean showInRfqOneRequired) {
        this.showInRfqOneRequired = showInRfqOneRequired;
    }

    public Boolean getShowInRfqTwoRequired() {
        return showInRfqTwoRequired;
    }

    public void setShowInRfqTwoRequired(Boolean showInRfqTwoRequired) {
        this.showInRfqTwoRequired = showInRfqTwoRequired;
    }

    public Boolean getShowInRfqThreeRequired() {
        return showInRfqThreeRequired;
    }

    public void setShowInRfqThreeRequired(Boolean showInRfqThreeRequired) {
        this.showInRfqThreeRequired = showInRfqThreeRequired;
    }

    public Boolean getShowInRfqFourRequired() {
        return showInRfqFourRequired;
    }

    public void setShowInRfqFourRequired(Boolean showInRfqFourRequired) {
        this.showInRfqFourRequired = showInRfqFourRequired;
    }

    public Boolean getShowInRfqFiveRequired() {
        return showInRfqFiveRequired;
    }

    public void setShowInRfqFiveRequired(Boolean showInRfqFiveRequired) {
        this.showInRfqFiveRequired = showInRfqFiveRequired;
    }

    public Boolean getShowInRfqSixRequired() {
        return showInRfqSixRequired;
    }

    public void setShowInRfqSixRequired(Boolean showInRfqSixRequired) {
        this.showInRfqSixRequired = showInRfqSixRequired;
    }

    public Boolean getShowInRfqSevenRequired() {
        return showInRfqSevenRequired;
    }

    public void setShowInRfqSevenRequired(Boolean showInRfqSevenRequired) {
        this.showInRfqSevenRequired = showInRfqSevenRequired;
    }

    public Boolean getShowInRfqEightRequired() {
        return showInRfqEightRequired;
    }

    public void setShowInRfqEightRequired(Boolean showInRfqEightRequired) {
        this.showInRfqEightRequired = showInRfqEightRequired;
    }

    public Boolean getShowInRfqNineRequired() {
        return showInRfqNineRequired;
    }

    public void setShowInRfqNineRequired(Boolean showInRfqNineRequired) {
        this.showInRfqNineRequired = showInRfqNineRequired;
    }

    public Boolean getShowInRfqTenRequired() {
        return showInRfqTenRequired;
    }

    public void setShowInRfqTenRequired(Boolean showInRfqTenRequired) {
        this.showInRfqTenRequired = showInRfqTenRequired;
    }

    public Boolean getShowInAuctionOneRequired() {
        return showInAuctionOneRequired;
    }

    public void setShowInAuctionOneRequired(Boolean showInAuctionOneRequired) {
        this.showInAuctionOneRequired = showInAuctionOneRequired;
    }

    public Boolean getShowInAuctionTwoRequired() {
        return showInAuctionTwoRequired;
    }

    public void setShowInAuctionTwoRequired(Boolean showInAuctionTwoRequired) {
        this.showInAuctionTwoRequired = showInAuctionTwoRequired;
    }

    public Boolean getShowInAuctionThreeRequired() {
        return showInAuctionThreeRequired;
    }

    public void setShowInAuctionThreeRequired(Boolean showInAuctionThreeRequired) {
        this.showInAuctionThreeRequired = showInAuctionThreeRequired;
    }

    public Boolean getShowInAuctionFourRequired() {
        return showInAuctionFourRequired;
    }

    public void setShowInAuctionFourRequired(Boolean showInAuctionFourRequired) {
        this.showInAuctionFourRequired = showInAuctionFourRequired;
    }

    public Boolean getShowInAuctionFiveRequired() {
        return showInAuctionFiveRequired;
    }

    public void setShowInAuctionFiveRequired(Boolean showInAuctionFiveRequired) {
        this.showInAuctionFiveRequired = showInAuctionFiveRequired;
    }

    public Boolean getShowInAuctionSixRequired() {
        return showInAuctionSixRequired;
    }

    public void setShowInAuctionSixRequired(Boolean showInAuctionSixRequired) {
        this.showInAuctionSixRequired = showInAuctionSixRequired;
    }

    public Boolean getShowInAuctionSevenRequired() {
        return showInAuctionSevenRequired;
    }

    public void setShowInAuctionSevenRequired(Boolean showInAuctionSevenRequired) {
        this.showInAuctionSevenRequired = showInAuctionSevenRequired;
    }

    public Boolean getShowInAuctionEightRequired() {
        return showInAuctionEightRequired;
    }

    public void setShowInAuctionEightRequired(Boolean showInAuctionEightRequired) {
        this.showInAuctionEightRequired = showInAuctionEightRequired;
    }

    public Boolean getShowInAuctionNineRequired() {
        return showInAuctionNineRequired;
    }

    public void setShowInAuctionNineRequired(Boolean showInAuctionNineRequired) {
        this.showInAuctionNineRequired = showInAuctionNineRequired;
    }

    public Boolean getShowInAuctionTenRequired() {
        return showInAuctionTenRequired;
    }

    public void setShowInAuctionTenRequired(Boolean showInAuctionTenRequired) {
        this.showInAuctionTenRequired = showInAuctionTenRequired;
    }

    public String getLotStartTimeDateFormat() {
        return lotStartTimeDateFormat;
    }

    public void setLotStartTimeDateFormat(String lotStartTimeDateFormat) {
        this.lotStartTimeDateFormat = lotStartTimeDateFormat;
    }

    public String getLotEndTimeDateFormat() {
        return lotEndTimeDateFormat;
    }

    public void setLotEndTimeDateFormat(String lotEndTimeDateFormat) {
        this.lotEndTimeDateFormat = lotEndTimeDateFormat;
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
        RnsQuotationVariantFullDetails rnsQuotationVariantFullDetails = (RnsQuotationVariantFullDetails) o;
        if (rnsQuotationVariantFullDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationVariantFullDetails.getId());
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public Float getBidStartPrice() {
        return bidStartPrice;
    }

    public void setBidStartPrice(Float bidStartPrice) {
        this.bidStartPrice = bidStartPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsQuotationVariant{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", varDescSpec1='" + getVarDescSpec1() + "'" +
            ", varDescSpec1Value='" + getVarDescSpec1Value() + "'" +
            ", varDescSpec2='" + getVarDescSpec2() + "'" +
            ", varDescSpec2Value='" + getVarDescSpec2Value() + "'" +
            ", varDescSpec3='" + getVarDescSpec3() + "'" +
            ", varDescSpec3Value='" + getVarDescSpec3Value() + "'" +
            ", varDescSpec4='" + getVarDescSpec4() + "'" +
            ", varDescSpec4Value='" + getVarDescSpec4Value() + "'" +
            ", varDescSpec5='" + getVarDescSpec5() + "'" +
            ", varDescSpec5Value='" + getVarDescSpec5Value() + "'" +
            ", varDescSpec6='" + getVarDescSpec6() + "'" +
            ", varDescSpec6Value='" + getVarDescSpec6Value() + "'" +
            ", varDescSpec7='" + getVarDescSpec7() + "'" +
            ", varDescSpec7Value='" + getVarDescSpec7Value() + "'" +
            ", varDescSpec8='" + getVarDescSpec8() + "'" +
            ", varDescSpec8Value='" + getVarDescSpec8Value() + "'" +
            ", varDescSpec9='" + getVarDescSpec9() + "'" +
            ", varDescSpec9Value='" + getVarDescSpec9Value() + "'" +
            ", varDescSpec10='" + getVarDescSpec10() + "'" +
            ", varDescSpec10Value='" + getVarDescSpec10Value() + "'" +
            ", eventDefType='" + getEventDefType() + "'" +
            ", eventDefCategory='" + getEventDefCategory() + "'" +
            ", eventDefTechnology='" + getEventDefTechnology() + "'" +
            ", eventDefDefectCode='" + getEventDefDefectCode() + "'" +
            ", eventDefText1='" + getEventDefText1() + "'" +
            ", dealtermCompletionBy='" + getDealtermCompletionBy() + "'" +
            ", dealtermValidUntil='" + getDealtermValidUntil() + "'" +
            ", dealtermPaymentTerms='" + getDealtermPaymentTerms() + "'" +
            ", dealtermDeliveryTerms='" + getDealtermDeliveryTerms() + "'" +
            ", dealtermDeliverAt='" + getDealtermDeliverAt() + "'" +
            ", dealtermText2='" + getDealtermText2() + "'" +
            ", orderQuantity='" + getOrderQuantity() + "'" +
            ", orderUom='" + getOrderUom() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", taxTerms='" + getTaxTerms() + "'" +
            ", dealtermsTaxTermsDesc='" + getDealtermsTaxTermsDesc() + "'" +
            ", dealtermsDelPlaceDesc='" + getDealtermsDelPlaceDesc() + "'" +
            ", overTime=" + getOverTime() +
            ", openCosting=" + getOpenCosting() +
            "}";
    }
} 
