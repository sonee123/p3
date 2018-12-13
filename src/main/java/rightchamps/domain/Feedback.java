package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="feedback_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "your_email_id")
    private String yourEmailId;

    @Size(max = 2000)
    @Column(name = "remarks", length = 2000)
    private String remarks;

    @Column(name = "attach_file")
    private String attachFile;

    @Column(name = "display_attach_file")
    private String displayAttachFile;

    @NotNull
    @Size(max = 50)
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "ccd_response_date", nullable = false)
    private LocalDate ccdResponseDate;

    @Size(max = 2000)
    @Column(name = "ccd_response", length = 2000)
    private String ccdResponse;

   

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYourEmailId() {
        return yourEmailId;
    }

    public Feedback yourEmailId(String yourEmailId) {
        this.yourEmailId = yourEmailId;
        return this;
    }

    public void setYourEmailId(String yourEmailId) {
        this.yourEmailId = yourEmailId;
    }

    public String getRemarks() {
        return remarks;
    }

    public Feedback remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public Feedback attachFile(String attachFile) {
        this.attachFile = attachFile;
        return this;
    }

    public String getDisplayAttachFile() {
        return displayAttachFile;
    }

    public void setDisplayAttachFile(String displayAttachFile) {
        this.displayAttachFile = displayAttachFile;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }
    
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getCcdResponse() {
		return ccdResponse;
	}

	public void setCcdResponse(String ccdResponse) {
		this.ccdResponse = ccdResponse;
	}

	public LocalDate getCcdResponseDate() {
		return ccdResponseDate;
	}

	public void setCcdResponseDate(LocalDate ccdResponseDate) {
		this.ccdResponseDate = ccdResponseDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        if (feedback.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedback.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + getId() +
            ", yourEmailId='" + getYourEmailId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", attachFile='" + getAttachFile() + "'" +
            "}";
    }
}
