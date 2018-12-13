package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsUpchargeDtl.
 */
@Entity
@Table(name = "rns_upcharge_dtl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsUpchargeDtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_upcharge_dtl_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @NotNull
    @Column(name = "upcharge_id", nullable = false)
    private Long upchargeId;

    @Size(max = 255)
    @Column(name = "remarks", length = 255)
    private String remarks;

    @NotNull
    @Size(max = 1)
    @Column(name = "upcharge_type", length = 1, nullable = false)
    private String upchargeType;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Double rate;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private Double value;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public RnsUpchargeDtl vendorId(Long vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getUpchargeId() {
        return upchargeId;
    }

    public RnsUpchargeDtl upchargeId(Long upchargeId) {
        this.upchargeId = upchargeId;
        return this;
    }

    public void setUpchargeId(Long upchargeId) {
        this.upchargeId = upchargeId;
    }

    public String getRemarks() {
        return remarks;
    }

    public RnsUpchargeDtl remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUpchargeType() {
        return upchargeType;
    }

    public RnsUpchargeDtl upchargeType(String upchargeType) {
        this.upchargeType = upchargeType;
        return this;
    }

    public void setUpchargeType(String upchargeType) {
        this.upchargeType = upchargeType;
    }

    public Double getRate() {
        return rate;
    }

    public RnsUpchargeDtl rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getValue() {
        return value;
    }

    public RnsUpchargeDtl value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RnsUpchargeDtl createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public RnsUpchargeDtl createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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
        RnsUpchargeDtl rnsUpchargeDtl = (RnsUpchargeDtl) o;
        if (rnsUpchargeDtl.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsUpchargeDtl.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsUpchargeDtl{" +
            "id=" + getId() +
            ", vendorId=" + getVendorId() +
            ", upchargeId=" + getUpchargeId() +
            ", remarks='" + getRemarks() + "'" +
            ", upchargeType='" + getUpchargeType() + "'" +
            ", rate=" + getRate() +
            ", value=" + getValue() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
