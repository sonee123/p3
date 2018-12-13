package rightchamps.modal;

import rightchamps.domain.RnsQuotationVendors;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class RnsQuotationVariantReportDetails {
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

    private Timestamp rfqCreatedDate;

    private Timestamp rfqPublishedDate;

    private Timestamp rfqExpiredDate;

    private Integer noOfInvites;

    private Integer noOfPortal;

    private Integer noOfSurrogate;

    private Integer noOfTotal;

    private Timestamp auctionCreatedDate;

    private Timestamp auctionPublishedDate;

    private Timestamp auctionStartDate;

    private Timestamp auctionEndDate;

    private Integer auctionNoOfInvites;

    private Integer auctionNoOfPortal;

    private Integer auctionNoOfSurrogate;

    private Integer auctionNoOfTotal;

    private Integer noOfOverTime;

    private String openCosting;

    private String uploadFlag;

    private Float bidStartPrice;

    private List<RnsQuotationVendorsReportDetails> quotationVendors = new ArrayList<RnsQuotationVendorsReportDetails>();

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

    public RnsQuotationVariantReportDetails title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVarDescSpec1() {
        return varDescSpec1;
    }

    public RnsQuotationVariantReportDetails varDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
        return this;
    }

    public void setVarDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
    }

    public String getVarDescSpec1Value() {
        return varDescSpec1Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
        return this;
    }

    public void setVarDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
    }

    public String getVarDescSpec2() {
        return varDescSpec2;
    }

    public RnsQuotationVariantReportDetails varDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
        return this;
    }

    public void setVarDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
    }

    public String getVarDescSpec2Value() {
        return varDescSpec2Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
        return this;
    }

    public void setVarDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
    }

    public String getVarDescSpec3() {
        return varDescSpec3;
    }

    public RnsQuotationVariantReportDetails varDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
        return this;
    }

    public void setVarDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
    }

    public String getVarDescSpec3Value() {
        return varDescSpec3Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
        return this;
    }

    public void setVarDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
    }

    public String getVarDescSpec4() {
        return varDescSpec4;
    }

    public RnsQuotationVariantReportDetails varDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
        return this;
    }

    public void setVarDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
    }

    public String getVarDescSpec4Value() {
        return varDescSpec4Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
        return this;
    }

    public void setVarDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
    }

    public String getVarDescSpec5() {
        return varDescSpec5;
    }

    public RnsQuotationVariantReportDetails varDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
        return this;
    }

    public void setVarDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
    }

    public String getVarDescSpec5Value() {
        return varDescSpec5Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
        return this;
    }

    public void setVarDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
    }

    public String getVarDescSpec6() {
        return varDescSpec6;
    }

    public RnsQuotationVariantReportDetails varDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
        return this;
    }

    public void setVarDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
    }

    public String getVarDescSpec6Value() {
        return varDescSpec6Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
        return this;
    }

    public void setVarDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
    }

    public String getVarDescSpec7() {
        return varDescSpec7;
    }

    public RnsQuotationVariantReportDetails varDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
        return this;
    }

    public void setVarDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
    }

    public String getVarDescSpec7Value() {
        return varDescSpec7Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
        return this;
    }

    public void setVarDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
    }

    public String getVarDescSpec8() {
        return varDescSpec8;
    }

    public RnsQuotationVariantReportDetails varDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
        return this;
    }

    public void setVarDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
    }

    public String getVarDescSpec8Value() {
        return varDescSpec8Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
        return this;
    }

    public void setVarDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
    }

    public String getVarDescSpec9() {
        return varDescSpec9;
    }

    public RnsQuotationVariantReportDetails varDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
        return this;
    }

    public void setVarDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
    }

    public String getVarDescSpec9Value() {
        return varDescSpec9Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
        return this;
    }

    public void setVarDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
    }

    public String getVarDescSpec10() {
        return varDescSpec10;
    }

    public RnsQuotationVariantReportDetails varDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
        return this;
    }

    public void setVarDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
    }

    public String getVarDescSpec10Value() {
        return varDescSpec10Value;
    }

    public RnsQuotationVariantReportDetails varDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
        return this;
    }

    public void setVarDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
    }

    public String getEventDefType() {
        return eventDefType;
    }

    public RnsQuotationVariantReportDetails eventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
        return this;
    }

    public void setEventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
    }

    public String getEventDefCategory() {
        return eventDefCategory;
    }

    public RnsQuotationVariantReportDetails eventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
        return this;
    }

    public void setEventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
    }

    public String getEventDefTechnology() {
        return eventDefTechnology;
    }

    public RnsQuotationVariantReportDetails eventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
        return this;
    }

    public void setEventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
    }

    public String getEventDefDefectCode() {
        return eventDefDefectCode;
    }

    public RnsQuotationVariantReportDetails eventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
        return this;
    }

    public void setEventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
    }

    public String getEventDefText1() {
        return eventDefText1;
    }

    public RnsQuotationVariantReportDetails eventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
        return this;
    }

    public void setEventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
    }

    public LocalDate getDealtermCompletionBy() {
        return dealtermCompletionBy;
    }

    public RnsQuotationVariantReportDetails dealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
        return this;
    }

    public void setDealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
    }

    public LocalDate getDealtermValidUntil() {
        return dealtermValidUntil;
    }

    public RnsQuotationVariantReportDetails dealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
        return this;
    }

    public void setDealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
    }

    public String getDealtermPaymentTerms() {
        return dealtermPaymentTerms;
    }

    public RnsQuotationVariantReportDetails dealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
        return this;
    }

    public void setDealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
    }

    public String getDealtermDeliveryTerms() {
        return dealtermDeliveryTerms;
    }

    public RnsQuotationVariantReportDetails dealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
        return this;
    }

    public void setDealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
    }


    public String getTaxTerms() {
        return taxTerms;
    }
    public RnsQuotationVariantReportDetails taxTerms(String taxTerms) {
        this.taxTerms = taxTerms;
        return this;
    }
    public void setTaxTerms(String taxTerms) {
        this.taxTerms = taxTerms;
    }

    public String getDealtermDeliverAt() {
        return dealtermDeliverAt;
    }

    public RnsQuotationVariantReportDetails dealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
        return this;
    }

    public void setDealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
    }

    public String getDealtermText2() {
        return dealtermText2;
    }

    public RnsQuotationVariantReportDetails dealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
        return this;
    }

    public void setDealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public RnsQuotationVariantReportDetails orderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderUom() {
        return orderUom;
    }

    public RnsQuotationVariantReportDetails orderUom(String orderUom) {
        this.orderUom = orderUom;
        return this;
    }

    public void setOrderUom(String orderUom) {
        this.orderUom = orderUom;
    }

    public String getRemarks() {
        return remarks;
    }

    public RnsQuotationVariantReportDetails remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Float getOverTime() {
        return overTime;
    }

    public RnsQuotationVariantReportDetails overTime(Float overTime) {
        this.overTime = overTime;
        return this;
    }

    public void setOverTime(Float overTime) {
        this.overTime = overTime;
    }

    public List<RnsQuotationVendorsReportDetails> getQuotationVendors() {
        return quotationVendors;
    }

    public void setQuotationVendors(List<RnsQuotationVendorsReportDetails> quotationVendors) {
        this.quotationVendors = quotationVendors;
    }

    public Integer getVariantRank() {
        return variantRank;
    }

    public void setVariantRank(Integer variantRank) {
        this.variantRank = variantRank;
    }

    public String getDealtermsTaxTermsDesc() {
        return dealtermsTaxTermsDesc;
    }

    public RnsQuotationVariantReportDetails dealtermsTaxTermsDesc(String dealtermsTaxTermsDesc) {
        this.dealtermsTaxTermsDesc = dealtermsTaxTermsDesc;
        return this;
    }

    public void setDealtermsTaxTermsDesc(String dealtermsTaxTermsDesc) {
        this.dealtermsTaxTermsDesc = dealtermsTaxTermsDesc;
    }


    public String getDealtermsDelPlaceDesc() {
        return dealtermsDelPlaceDesc;
    }

    public RnsQuotationVariantReportDetails dealtermsDelPlaceDesc(String dealtermsDelPlaceDesc) {
        this.dealtermsDelPlaceDesc = dealtermsDelPlaceDesc;
        return this;
    }

    public void setDealtermsDelPlaceDesc(String dealtermsDelPlaceDesc) {
        this.dealtermsDelPlaceDesc = dealtermsDelPlaceDesc;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Timestamp getRfqCreatedDate() {
        return rfqCreatedDate;
    }

    public void setRfqCreatedDate(Timestamp rfqCreatedDate) {
        this.rfqCreatedDate = rfqCreatedDate;
    }

    public Timestamp getRfqPublishedDate() {
        return rfqPublishedDate;
    }

    public void setRfqPublishedDate(Timestamp rfqPublishedDate) {
        this.rfqPublishedDate = rfqPublishedDate;
    }

    public Timestamp getRfqExpiredDate() {
        return rfqExpiredDate;
    }

    public void setRfqExpiredDate(Timestamp rfqExpiredDate) {
        this.rfqExpiredDate = rfqExpiredDate;
    }

    public Integer getNoOfInvites() {
        return noOfInvites;
    }

    public void setNoOfInvites(Integer noOfInvites) {
        this.noOfInvites = noOfInvites;
    }

    public Integer getNoOfPortal() {
        return noOfPortal;
    }

    public void setNoOfPortal(Integer noOfPortal) {
        this.noOfPortal = noOfPortal;
    }

    public Integer getNoOfSurrogate() {
        return noOfSurrogate;
    }

    public void setNoOfSurrogate(Integer noOfSurrogate) {
        this.noOfSurrogate = noOfSurrogate;
    }

    public Integer getNoOfTotal() {
        return noOfTotal;
    }

    public void setNoOfTotal(Integer noOfTotal) {
        this.noOfTotal = noOfTotal;
    }

    public Timestamp getAuctionCreatedDate() {
        return auctionCreatedDate;
    }

    public void setAuctionCreatedDate(Timestamp auctionCreatedDate) {
        this.auctionCreatedDate = auctionCreatedDate;
    }

    public Timestamp getAuctionPublishedDate() {
        return auctionPublishedDate;
    }

    public void setAuctionPublishedDate(Timestamp auctionPublishedDate) {
        this.auctionPublishedDate = auctionPublishedDate;
    }

    public Timestamp getAuctionStartDate() {
        return auctionStartDate;
    }

    public void setAuctionStartDate(Timestamp auctionStartDate) {
        this.auctionStartDate = auctionStartDate;
    }

    public Timestamp getAuctionEndDate() {
        return auctionEndDate;
    }

    public void setAuctionEndDate(Timestamp auctionEndDate) {
        this.auctionEndDate = auctionEndDate;
    }

    public Integer getAuctionNoOfInvites() {
        return auctionNoOfInvites;
    }

    public void setAuctionNoOfInvites(Integer auctionNoOfInvites) {
        this.auctionNoOfInvites = auctionNoOfInvites;
    }

    public Integer getAuctionNoOfPortal() {
        return auctionNoOfPortal;
    }

    public void setAuctionNoOfPortal(Integer auctionNoOfPortal) {
        this.auctionNoOfPortal = auctionNoOfPortal;
    }

    public Integer getAuctionNoOfSurrogate() {
        return auctionNoOfSurrogate;
    }

    public void setAuctionNoOfSurrogate(Integer auctionNoOfSurrogate) {
        this.auctionNoOfSurrogate = auctionNoOfSurrogate;
    }

    public Integer getAuctionNoOfTotal() {
        return auctionNoOfTotal;
    }

    public void setAuctionNoOfTotal(Integer auctionNoOfTotal) {
        this.auctionNoOfTotal = auctionNoOfTotal;
    }

    public Integer getNoOfOverTime() {
        return noOfOverTime;
    }

    public void setNoOfOverTime(Integer noOfOverTime) {
        this.noOfOverTime = noOfOverTime;
    }

    public String getOpenCosting() { return openCosting; }

    public void setOpenCosting(String openCosting) { this.openCosting = openCosting; }

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
// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsQuotationVariantReportDetails RnsQuotationVariantReportDetails = (RnsQuotationVariantReportDetails) o;
        if (RnsQuotationVariantReportDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), RnsQuotationVariantReportDetails.getId());
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
            "}";
    }
}
