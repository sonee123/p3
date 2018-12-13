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
@Entity
@Table(name = "rns_quotation_variant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationVariant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quotation_variant_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "var_desc_spec_1")
    private String varDescSpec1;

    @Column(name = "var_desc_spec_1_value")
    private String varDescSpec1Value;

    @Column(name = "var_desc_spec_2")
    private String varDescSpec2;

    @Column(name = "var_desc_spec_2_value")
    private String varDescSpec2Value;

    @Column(name = "var_desc_spec_3")
    private String varDescSpec3;

    @Column(name = "var_desc_spec_3_value")
    private String varDescSpec3Value;

    @Column(name = "var_desc_spec_4")
    private String varDescSpec4;

    @Column(name = "var_desc_spec_4_value")
    private String varDescSpec4Value;

    @Column(name = "var_desc_spec_5")
    private String varDescSpec5;

    @Column(name = "var_desc_spec_5_value")
    private String varDescSpec5Value;

    @Column(name = "var_desc_spec_6")
    private String varDescSpec6;

    @Column(name = "var_desc_spec_6_value")
    private String varDescSpec6Value;

    @Column(name = "var_desc_spec_7")
    private String varDescSpec7;

    @Column(name = "var_desc_spec_7_value")
    private String varDescSpec7Value;

    @Column(name = "var_desc_spec_8")
    private String varDescSpec8;

    @Column(name = "var_desc_spec_8_value")
    private String varDescSpec8Value;

    @Column(name = "var_desc_spec_9")
    private String varDescSpec9;

    @Column(name = "var_desc_spec_9_value")
    private String varDescSpec9Value;

    @Column(name = "var_desc_spec_10")
    private String varDescSpec10;

    @Column(name = "var_desc_spec_10_value")
    private String varDescSpec10Value;

    @Column(name = "event_def_type")
    private String eventDefType;

    @Column(name = "event_def_category")
    private String eventDefCategory;

    @Column(name = "event_def_technology")
    private String eventDefTechnology;

    @Column(name = "event_def_defect_code")
    private String eventDefDefectCode;

    @Column(name = "event_def_text_1")
    private String eventDefText1;

    @Column(name = "dealterm_completion_by")
    private LocalDate dealtermCompletionBy;

    @Column(name = "dealterm_valid_until")
    private LocalDate dealtermValidUntil;

    @Column(name = "dealterm_payment_terms")
    private String dealtermPaymentTerms;

    @Column(name = "tax_terms")
    private String taxTerms;
    
    @Column(name = "dealterm_delivery_terms")
    private String dealtermDeliveryTerms;

    @Column(name = "dealterm_deliver_at")
    private String dealtermDeliverAt;

    @Column(name = "dealterm_text_2")
    private String dealtermText2;

    @Column(name = "order_quantity")
    private String orderQuantity;

    @Column(name = "order_uom")
    private String orderUom;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "over_time")
    private Float overTime;
    
    @Column(name = "historical_price")
    private Float historicalPrice;
    
    @Column(name = "currency")
    private String currency;

    @Column(name = "nature_of_price")
    private String natureOfPrice;
    
    @Column(name = "open_costing")
    private String openCosting;

    @Column(name = "upload_flag")
    private String uploadFlag;

    @Column(name = "bid_start_price")
    private Float bidStartPrice;

    @OneToMany(mappedBy = "variant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RnsQuotationVendors> quotations = new HashSet<>();

    @ManyToOne
    private RnsQuotation quotation;

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

    public RnsQuotationVariant title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVarDescSpec1() {
        return varDescSpec1;
    }

    public RnsQuotationVariant varDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
        return this;
    }

    public void setVarDescSpec1(String varDescSpec1) {
        this.varDescSpec1 = varDescSpec1;
    }

    public String getVarDescSpec1Value() {
        return varDescSpec1Value;
    }

    public RnsQuotationVariant varDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
        return this;
    }

    public void setVarDescSpec1Value(String varDescSpec1Value) {
        this.varDescSpec1Value = varDescSpec1Value;
    }

    public String getVarDescSpec2() {
        return varDescSpec2;
    }

    public RnsQuotationVariant varDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
        return this;
    }

    public void setVarDescSpec2(String varDescSpec2) {
        this.varDescSpec2 = varDescSpec2;
    }

    public String getVarDescSpec2Value() {
        return varDescSpec2Value;
    }

    public RnsQuotationVariant varDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
        return this;
    }

    public void setVarDescSpec2Value(String varDescSpec2Value) {
        this.varDescSpec2Value = varDescSpec2Value;
    }

    public String getVarDescSpec3() {
        return varDescSpec3;
    }

    public RnsQuotationVariant varDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
        return this;
    }

    public void setVarDescSpec3(String varDescSpec3) {
        this.varDescSpec3 = varDescSpec3;
    }

    public String getVarDescSpec3Value() {
        return varDescSpec3Value;
    }

    public RnsQuotationVariant varDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
        return this;
    }

    public void setVarDescSpec3Value(String varDescSpec3Value) {
        this.varDescSpec3Value = varDescSpec3Value;
    }

    public String getVarDescSpec4() {
        return varDescSpec4;
    }

    public RnsQuotationVariant varDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
        return this;
    }

    public void setVarDescSpec4(String varDescSpec4) {
        this.varDescSpec4 = varDescSpec4;
    }

    public String getVarDescSpec4Value() {
        return varDescSpec4Value;
    }

    public RnsQuotationVariant varDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
        return this;
    }

    public void setVarDescSpec4Value(String varDescSpec4Value) {
        this.varDescSpec4Value = varDescSpec4Value;
    }

    public String getVarDescSpec5() {
        return varDescSpec5;
    }

    public RnsQuotationVariant varDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
        return this;
    }

    public void setVarDescSpec5(String varDescSpec5) {
        this.varDescSpec5 = varDescSpec5;
    }

    public String getVarDescSpec5Value() {
        return varDescSpec5Value;
    }

    public RnsQuotationVariant varDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
        return this;
    }

    public void setVarDescSpec5Value(String varDescSpec5Value) {
        this.varDescSpec5Value = varDescSpec5Value;
    }

    public String getVarDescSpec6() {
        return varDescSpec6;
    }

    public RnsQuotationVariant varDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
        return this;
    }

    public void setVarDescSpec6(String varDescSpec6) {
        this.varDescSpec6 = varDescSpec6;
    }

    public String getVarDescSpec6Value() {
        return varDescSpec6Value;
    }

    public RnsQuotationVariant varDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
        return this;
    }

    public void setVarDescSpec6Value(String varDescSpec6Value) {
        this.varDescSpec6Value = varDescSpec6Value;
    }

    public String getVarDescSpec7() {
        return varDescSpec7;
    }

    public RnsQuotationVariant varDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
        return this;
    }

    public void setVarDescSpec7(String varDescSpec7) {
        this.varDescSpec7 = varDescSpec7;
    }

    public String getVarDescSpec7Value() {
        return varDescSpec7Value;
    }

    public RnsQuotationVariant varDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
        return this;
    }

    public void setVarDescSpec7Value(String varDescSpec7Value) {
        this.varDescSpec7Value = varDescSpec7Value;
    }

    public String getVarDescSpec8() {
        return varDescSpec8;
    }

    public RnsQuotationVariant varDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
        return this;
    }

    public void setVarDescSpec8(String varDescSpec8) {
        this.varDescSpec8 = varDescSpec8;
    }

    public String getVarDescSpec8Value() {
        return varDescSpec8Value;
    }

    public RnsQuotationVariant varDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
        return this;
    }

    public void setVarDescSpec8Value(String varDescSpec8Value) {
        this.varDescSpec8Value = varDescSpec8Value;
    }

    public String getVarDescSpec9() {
        return varDescSpec9;
    }

    public RnsQuotationVariant varDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
        return this;
    }

    public void setVarDescSpec9(String varDescSpec9) {
        this.varDescSpec9 = varDescSpec9;
    }

    public String getVarDescSpec9Value() {
        return varDescSpec9Value;
    }

    public RnsQuotationVariant varDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
        return this;
    }

    public void setVarDescSpec9Value(String varDescSpec9Value) {
        this.varDescSpec9Value = varDescSpec9Value;
    }

    public String getVarDescSpec10() {
        return varDescSpec10;
    }

    public RnsQuotationVariant varDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
        return this;
    }

    public void setVarDescSpec10(String varDescSpec10) {
        this.varDescSpec10 = varDescSpec10;
    }

    public String getVarDescSpec10Value() {
        return varDescSpec10Value;
    }

    public RnsQuotationVariant varDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
        return this;
    }

    public void setVarDescSpec10Value(String varDescSpec10Value) {
        this.varDescSpec10Value = varDescSpec10Value;
    }

    public String getEventDefType() {
        return eventDefType;
    }

    public RnsQuotationVariant eventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
        return this;
    }

    public void setEventDefType(String eventDefType) {
        this.eventDefType = eventDefType;
    }

    public String getEventDefCategory() {
        return eventDefCategory;
    }

    public RnsQuotationVariant eventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
        return this;
    }

    public void setEventDefCategory(String eventDefCategory) {
        this.eventDefCategory = eventDefCategory;
    }

    public String getEventDefTechnology() {
        return eventDefTechnology;
    }

    public RnsQuotationVariant eventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
        return this;
    }

    public void setEventDefTechnology(String eventDefTechnology) {
        this.eventDefTechnology = eventDefTechnology;
    }

    public String getEventDefDefectCode() {
        return eventDefDefectCode;
    }

    public RnsQuotationVariant eventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
        return this;
    }

    public void setEventDefDefectCode(String eventDefDefectCode) {
        this.eventDefDefectCode = eventDefDefectCode;
    }

    public String getEventDefText1() {
        return eventDefText1;
    }

    public RnsQuotationVariant eventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
        return this;
    }

    public void setEventDefText1(String eventDefText1) {
        this.eventDefText1 = eventDefText1;
    }

    public LocalDate getDealtermCompletionBy() {
        return dealtermCompletionBy;
    }

    public RnsQuotationVariant dealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
        return this;
    }

    public void setDealtermCompletionBy(LocalDate dealtermCompletionBy) {
        this.dealtermCompletionBy = dealtermCompletionBy;
    }

    public LocalDate getDealtermValidUntil() {
        return dealtermValidUntil;
    }

    public RnsQuotationVariant dealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
        return this;
    }

    public void setDealtermValidUntil(LocalDate dealtermValidUntil) {
        this.dealtermValidUntil = dealtermValidUntil;
    }

    public String getDealtermPaymentTerms() {
        return dealtermPaymentTerms;
    }

    public RnsQuotationVariant dealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
        return this;
    }

    public void setDealtermPaymentTerms(String dealtermPaymentTerms) {
        this.dealtermPaymentTerms = dealtermPaymentTerms;
    }
    
    public String getTaxTerms() {
		return taxTerms;
	}
    
    public RnsQuotationVariant taxTerms(String taxTerms) {
		this.taxTerms = taxTerms;
		return this;
	}

	public void setTaxTerms(String taxTerms) {
		this.taxTerms = taxTerms;
	}

	public String getDealtermDeliveryTerms() {
        return dealtermDeliveryTerms;
    }

    public RnsQuotationVariant dealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
        return this;
    }

    public void setDealtermDeliveryTerms(String dealtermDeliveryTerms) {
        this.dealtermDeliveryTerms = dealtermDeliveryTerms;
    }

    public String getDealtermDeliverAt() {
        return dealtermDeliverAt;
    }

    public RnsQuotationVariant dealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
        return this;
    }

    public void setDealtermDeliverAt(String dealtermDeliverAt) {
        this.dealtermDeliverAt = dealtermDeliverAt;
    }

    public String getDealtermText2() {
        return dealtermText2;
    }

    public RnsQuotationVariant dealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
        return this;
    }

    public void setDealtermText2(String dealtermText2) {
        this.dealtermText2 = dealtermText2;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public RnsQuotationVariant orderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderUom() {
        return orderUom;
    }

    public RnsQuotationVariant orderUom(String orderUom) {
        this.orderUom = orderUom;
        return this;
    }

    public void setOrderUom(String orderUom) {
        this.orderUom = orderUom;
    }

    public String getRemarks() {
        return remarks;
    }

    public RnsQuotationVariant remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Float getOverTime() {
        return overTime;
    }

    public RnsQuotationVariant overTime(Float overTime) {
        this.overTime = overTime;
        return this;
    }

    public void setOverTime(Float overTime) {
        this.overTime = overTime;
    }

    public Set<RnsQuotationVendors> getQuotations() {
        return quotations;
    }

    public RnsQuotationVariant quotations(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotations = rnsQuotationVendors;
        return this;
    }

    public RnsQuotationVariant addQuotation(RnsQuotationVendors rnsQuotationVendors) {
        this.quotations.add(rnsQuotationVendors);
        rnsQuotationVendors.setVariant(this);
        return this;
    }

    public RnsQuotationVariant removeQuotation(RnsQuotationVendors rnsQuotationVendors) {
        this.quotations.remove(rnsQuotationVendors);
        rnsQuotationVendors.setVariant(null);
        return this;
    }

    public void setQuotations(Set<RnsQuotationVendors> rnsQuotationVendors) {
        this.quotations = rnsQuotationVendors;
    }

    public RnsQuotation getQuotation() {
        return quotation;
    }

    public RnsQuotationVariant quotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
        return this;
    }

    public void setQuotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
    }

    public Float getHistoricalPrice() {
		return historicalPrice;
	}

	public void setHistoricalPrice(Float historicalPrice) {
		this.historicalPrice = historicalPrice;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getNatureOfPrice() {
		return natureOfPrice;
	}

	public void setNatureOfPrice(String natureOfPrice) {
		this.natureOfPrice = natureOfPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getOpenCosting() {
		return openCosting;
	}

	public void setOpenCosting(String openCosting) {
		this.openCosting = openCosting;
	}

    public Float getBidStartPrice() { return bidStartPrice; }

    public void setBidStartPrice(Float bidStartPrice) { this.bidStartPrice = bidStartPrice; }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsQuotationVariant rnsQuotationVariant = (RnsQuotationVariant) o;
        if (rnsQuotationVariant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationVariant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }
/*@Override
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
            ", overTime=" + getOverTime() +
            ", historicalPrice=" + getHistoricalPrice() +
            ", currency=" + getCurrency() +
            ", natureOfPrice=" + getNatureOfPrice() +
            "}";
    }*/
}
