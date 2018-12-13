package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A RnsVendorRemarkComment.
 */
@Entity
@Table(name = "rns_vendor_remark_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsVendorRemarkComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_vend_remarks_c_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "remark_text")
    private String remarkText;

    @Column(name = "vendor_id")
    private Integer vendorId;

    @Column(name = "vendor_remark_id")
    private Integer vendorRemarkId;

    @Column(name = "jhi_read")
    private Boolean read;

    @ManyToOne
    private RnsVendorRemark rnsVendorRemark;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarkText() {
        return remarkText;
    }

    public RnsVendorRemarkComment remarkText(String remarkText) {
        this.remarkText = remarkText;
        return this;
    }

    public void setRemarkText(String remarkText) {
        this.remarkText = remarkText;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public RnsVendorRemarkComment vendorId(Integer vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getVendorRemarkId() {
        return vendorRemarkId;
    }

    public RnsVendorRemarkComment vendorRemarkId(Integer vendorRemarkId) {
        this.vendorRemarkId = vendorRemarkId;
        return this;
    }

    public void setVendorRemarkId(Integer vendorRemarkId) {
        this.vendorRemarkId = vendorRemarkId;
    }

    public Boolean isRead() {
        return read;
    }

    public RnsVendorRemarkComment read(Boolean read) {
        this.read = read;
        return this;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public RnsVendorRemark getRnsVendorRemark() {
        return rnsVendorRemark;
    }

    public RnsVendorRemarkComment rnsVendorRemark(RnsVendorRemark rnsVendorRemark) {
        this.rnsVendorRemark = rnsVendorRemark;
        return this;
    }

    public void setRnsVendorRemark(RnsVendorRemark rnsVendorRemark) {
        this.rnsVendorRemark = rnsVendorRemark;
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
        RnsVendorRemarkComment rnsVendorRemarkComment = (RnsVendorRemarkComment) o;
        if (rnsVendorRemarkComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsVendorRemarkComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsVendorRemarkComment{" +
            "id=" + getId() +
            ", remarkText='" + getRemarkText() + "'" +
            ", vendorId=" + getVendorId() +
            ", vendorRemarkId=" + getVendorRemarkId() +
            ", read='" + isRead() + "'" +
            "}";
    }
}
