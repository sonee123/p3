package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A AuctionVarDetails.
 */
@Entity
@Table(name = "auction_var_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuctionVarDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="auction_var_details_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "lot_start_time")
    private Instant lotStartTime;

    @Column(name = "lot_end_time")
    private Instant lotEndTime;

    @Column(name = "overtime_minutes")
    private Long overtimeMinutes;

    @Column(name = "variant_id")
    private Long variantId;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLotStartTime() {
        return lotStartTime;
    }

    public AuctionVarDetails lotStartTime(Instant lotStartTime) {
        this.lotStartTime = lotStartTime;
        return this;
    }

    public void setLotStartTime(Instant lotStartTime) {
        this.lotStartTime = lotStartTime;
    }

    public Instant getLotEndTime() {
        return lotEndTime;
    }

    public AuctionVarDetails lotEndTime(Instant lotEndTime) {
        this.lotEndTime = lotEndTime;
        return this;
    }

    public void setLotEndTime(Instant lotEndTime) {
        this.lotEndTime = lotEndTime;
    }

    public Long getOvertimeMinutes() {
        return overtimeMinutes;
    }

    public AuctionVarDetails overtimeMinutes(Long overtimeMinutes) {
        this.overtimeMinutes = overtimeMinutes;
        return this;
    }

    public void setOvertimeMinutes(Long overtimeMinutes) {
        this.overtimeMinutes = overtimeMinutes;
    }

    public Long getVariantId() {
        return variantId;
    }

    public AuctionVarDetails variantId(Long variantId) {
        this.variantId = variantId;
        return this;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public AuctionVarDetails quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AuctionVarDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public AuctionVarDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public AuctionVarDetails updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public AuctionVarDetails updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
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
        AuctionVarDetails auctionVarDetails = (AuctionVarDetails) o;
        if (auctionVarDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auctionVarDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuctionVarDetails{" +
            "id=" + getId() +
            ", lotStartTime='" + getLotStartTime() + "'" +
            ", lotEndTime='" + getLotEndTime() + "'" +
            ", overtimeMinutes=" + getOvertimeMinutes() +
            ", variantId=" + getVariantId() +
            ", quotationId=" + getQuotationId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
