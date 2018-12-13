package rightchamps.modal;

import java.time.Instant;
import java.util.Date;

public class AuctionPauseDetailsBean {
    private Long id;

    private Instant pauseStartDate;

    private Instant pauseEndDate;

    private Long variantId;

    private Long quotationId;

    private Instant createdDate;

    private String createdBy;

    private Instant updatedDate;

    private String updatedBy;

    private Date currentDate;

    private Boolean timeAllow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPauseStartDate() {
        return pauseStartDate;
    }

    public void setPauseStartDate(Instant pauseStartDate) {
        this.pauseStartDate = pauseStartDate;
    }

    public Instant getPauseEndDate() {
        return pauseEndDate;
    }

    public void setPauseEndDate(Instant pauseEndDate) {
        this.pauseEndDate = pauseEndDate;
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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Boolean getTimeAllow() { return timeAllow; }

    public void setTimeAllow(Boolean timeAllow) { this.timeAllow = timeAllow; }
}
