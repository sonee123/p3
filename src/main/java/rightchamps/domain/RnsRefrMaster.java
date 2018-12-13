package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsRefrMaster.
 */
@Entity
@Table(name = "rns_refr_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsRefrMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_refr_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "subcode")
    private String subcode;

    @Column(name = "sub_code_desc")
    private String subCodeDesc;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    private RnsRefrDetails rnsRefrDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubcode() {
        return subcode;
    }

    public RnsRefrMaster subcode(String subcode) {
        this.subcode = subcode;
        return this;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public String getSubCodeDesc() {
        return subCodeDesc;
    }

    public RnsRefrMaster subCodeDesc(String subCodeDesc) {
        this.subCodeDesc = subCodeDesc;
        return this;
    }

    public void setSubCodeDesc(String subCodeDesc) {
        this.subCodeDesc = subCodeDesc;
    }

    public String getStatus() {
        return status;
    }

    public RnsRefrMaster status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RnsRefrMaster createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public RnsRefrMaster createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public RnsRefrMaster lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public RnsRefrDetails getRnsRefrDetails() {
        return rnsRefrDetails;
    }

    public RnsRefrMaster rnsRefrDetails(RnsRefrDetails rnsRefrDetails) {
        this.rnsRefrDetails = rnsRefrDetails;
        return this;
    }

    public void setRnsRefrDetails(RnsRefrDetails rnsRefrDetails) {
        this.rnsRefrDetails = rnsRefrDetails;
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
        RnsRefrMaster rnsRefrMaster = (RnsRefrMaster) o;
        if (rnsRefrMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsRefrMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsRefrMaster{" +
            "id=" + getId() +
            ", subcode='" + getSubcode() + "'" +
            ", subCodeDesc='" + getSubCodeDesc() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
