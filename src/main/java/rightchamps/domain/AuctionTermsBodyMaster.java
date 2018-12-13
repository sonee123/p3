package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A AuctionTermsBodyMaster.
 */
@Entity
@Table(name = "auction_terms_body_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuctionTermsBodyMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="auction_terms_body_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Size(max = 2000)
    @Column(name = "terms_body", length = 2000)
    private String termsBody;

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

    public Long getTermId() {
        return termId;
    }

    public AuctionTermsBodyMaster termId(Long termId) {
        this.termId = termId;
        return this;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTermsBody() {
        return termsBody;
    }

    public AuctionTermsBodyMaster termsBody(String termsBody) {
        this.termsBody = termsBody;
        return this;
    }

    public void setTermsBody(String termsBody) {
        this.termsBody = termsBody;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AuctionTermsBodyMaster createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public AuctionTermsBodyMaster createdDate(Instant createdDate) {
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
        AuctionTermsBodyMaster auctionTermsBodyMaster = (AuctionTermsBodyMaster) o;
        if (auctionTermsBodyMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auctionTermsBodyMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuctionTermsBodyMaster{" +
            "id=" + getId() +
            ", termId=" + getTermId() +
            ", termsBody='" + getTermsBody() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
