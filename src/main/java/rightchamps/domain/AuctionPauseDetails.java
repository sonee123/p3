package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A AuctionPauseDetails.
 */
@Entity
@Table(name = "auction_pause_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuctionPauseDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="auction_pause_details_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "pause_start_date")
    private Instant pauseStartDate;

    @Column(name = "pause_end_date")
    private Instant pauseEndDate;

    @NotNull
    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    @NotNull
    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Size(max = 50)
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPauseStartDate() {
        return pauseStartDate;
    }

    public AuctionPauseDetails pauseStartDate(Instant pauseStartDate) {
        this.pauseStartDate = pauseStartDate;
        return this;
    }

    public void setPauseStartDate(Instant pauseStartDate) {
        this.pauseStartDate = pauseStartDate;
    }

    public Instant getPauseEndDate() {
        return pauseEndDate;
    }

    public AuctionPauseDetails pauseEndDate(Instant pauseEndDate) {
        this.pauseEndDate = pauseEndDate;
        return this;
    }

    public void setPauseEndDate(Instant pauseEndDate) {
        this.pauseEndDate = pauseEndDate;
    }

    public Long getVariantId() {
        return variantId;
    }

    public AuctionPauseDetails variantId(Long variantId) {
        this.variantId = variantId;
        return this;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public AuctionPauseDetails quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public AuctionPauseDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AuctionPauseDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public AuctionPauseDetails updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public AuctionPauseDetails updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
        AuctionPauseDetails auctionPauseDetails = (AuctionPauseDetails) o;
        if (auctionPauseDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auctionPauseDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuctionPauseDetails{" +
            "id=" + getId() +
            ", pauseStartDate='" + getPauseStartDate() + "'" +
            ", pauseEndDate='" + getPauseEndDate() + "'" +
            ", variantId=" + getVariantId() +
            ", quotationId=" + getQuotationId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
