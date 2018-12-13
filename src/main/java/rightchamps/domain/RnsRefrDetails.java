package rightchamps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RnsRefrDetails.
 */
@Entity
@Table(name = "rns_refr_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsRefrDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_refr_details_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "sub_code")
    private String subCode;

    @Column(name = "sub_code_desc")
    private String subCodeDesc;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private Instant lastModifiedBy;

    @OneToMany(mappedBy = "rnsRefrDetails")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RnsRefrMaster> rnsRefrMasters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCode() {
        return subCode;
    }

    public RnsRefrDetails subCode(String subCode) {
        this.subCode = subCode;
        return this;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubCodeDesc() {
        return subCodeDesc;
    }

    public RnsRefrDetails subCodeDesc(String subCodeDesc) {
        this.subCodeDesc = subCodeDesc;
        return this;
    }

    public void setSubCodeDesc(String subCodeDesc) {
        this.subCodeDesc = subCodeDesc;
    }

    public String getStatus() {
        return status;
    }

    public RnsRefrDetails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RnsRefrDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public RnsRefrDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedBy() {
        return lastModifiedBy;
    }

    public RnsRefrDetails lastModifiedBy(Instant lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(Instant lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<RnsRefrMaster> getRnsRefrMasters() {
        return rnsRefrMasters;
    }

    public RnsRefrDetails rnsRefrMasters(Set<RnsRefrMaster> rnsRefrMasters) {
        this.rnsRefrMasters = rnsRefrMasters;
        return this;
    }

    public RnsRefrDetails addRnsRefrMaster(RnsRefrMaster rnsRefrMaster) {
        this.rnsRefrMasters.add(rnsRefrMaster);
        rnsRefrMaster.setRnsRefrDetails(this);
        return this;
    }

    public RnsRefrDetails removeRnsRefrMaster(RnsRefrMaster rnsRefrMaster) {
        this.rnsRefrMasters.remove(rnsRefrMaster);
        rnsRefrMaster.setRnsRefrDetails(null);
        return this;
    }

    public void setRnsRefrMasters(Set<RnsRefrMaster> rnsRefrMasters) {
        this.rnsRefrMasters = rnsRefrMasters;
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
        RnsRefrDetails rnsRefrDetails = (RnsRefrDetails) o;
        if (rnsRefrDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsRefrDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsRefrDetails{" +
            "id=" + getId() +
            ", subCode='" + getSubCode() + "'" +
            ", subCodeDesc='" + getSubCodeDesc() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
