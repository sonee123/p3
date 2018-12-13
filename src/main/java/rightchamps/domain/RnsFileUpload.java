package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsFileUpload.
 */
@Entity
@Table(name = "rns_file_upload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsFileUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "rns_file_upload_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_GEN")
    private Long id;

    @NotNull
    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    @NotNull
    @Size(max = 255)
    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;

    @NotNull
    @Size(max = 255)
    @Column(name = "display_name", length = 255, nullable = false)
    private String displayName;

    @NotNull
    @Size(max = 1)
    @Column(name = "upload_type", length = 1, nullable = false)
    private String uploadType;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
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

    public Long getVariantId() {
        return variantId;
    }

    public RnsFileUpload variantId(Long variantId) {
        this.variantId = variantId;
        return this;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getFileName() {
        return fileName;
    }

    public RnsFileUpload fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public RnsFileUpload displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUploadType() {
        return uploadType;
    }

    public RnsFileUpload uploadType(String uploadType) {
        this.uploadType = uploadType;
        return this;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RnsFileUpload createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public RnsFileUpload createdDate(Instant createdDate) {
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
        RnsFileUpload rnsFileUpload = (RnsFileUpload) o;
        if (rnsFileUpload.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsFileUpload.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsFileUpload{" +
            "id=" + getId() +
            ", variantId=" + getVariantId() +
            ", fileName='" + getFileName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", uploadType='" + getUploadType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
