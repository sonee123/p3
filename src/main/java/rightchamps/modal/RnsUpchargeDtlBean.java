package rightchamps.modal;

import java.time.Instant;

public class RnsUpchargeDtlBean {
    private Long id;
    private Long vendorId;
    private Long upchargeId;
    private String remarks;
    private String upchargeType;
    private Double rate;
    private Double value;
    private String createdBy;
    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getUpchargeId() {
        return upchargeId;
    }

    public void setUpchargeId(Long upchargeId) {
        this.upchargeId = upchargeId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUpchargeType() {
        return upchargeType;
    }

    public void setUpchargeType(String upchargeType) {
        this.upchargeType = upchargeType;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
