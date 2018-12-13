package rightchamps.modal;

import java.time.Instant;

public class AuctionVarDetailsBean {
    private Long id;

    private Instant lotStartTime;

    private Instant lotEndTime;

    private Long overtimeMinutes;

    private Long variantId;

    private Long quotationId;

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;

    private String labelName;

    private String type;

    private Long lotMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOvertimeMinutes() {
        return overtimeMinutes;
    }

    public void setOvertimeMinutes(Long overtimeMinutes) {
        this.overtimeMinutes = overtimeMinutes;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Long getLotMinutes() {
        return lotMinutes;
    }

    public void setLotMinutes(Long lotMinutes) {
        this.lotMinutes = lotMinutes;
    }
}
