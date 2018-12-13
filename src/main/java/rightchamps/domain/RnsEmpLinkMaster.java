package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsEmpLinkMaster.
 */
@Entity
@Table(name = "rns_emp_link_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsEmpLinkMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_emp_link_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "forward_emp_type", length = 1, nullable = false)
    private String forwardEmpType;

    @OneToOne
    @JoinColumn(name="empCode", referencedColumnName="login")
    private User empCode;

    @OneToOne
    @JoinColumn(name="forward_emp_code", referencedColumnName="login")
    private User forwardEmpCode;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="forward_emp_type", referencedColumnName = "code", insertable = false, updatable = false)
    private RnsForwardTypeMaster rnsForwardTypeMaster;

     @OneToOne
    @JoinColumn(name="created_by", referencedColumnName="login")
    private User user;

     @Column(name = "created_date")
     private Instant createdDate;

    @OneToOne
    @JoinColumn(name="updated_by", referencedColumnName="login")
    private User updatedUser;

    @Column(name = "last_updated_date")
    private Instant lastUpdatedDate;

    @Column(name = "mail_applicable")
    private Boolean mailApplicable;

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Instant getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Instant lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForwardEmpType() {
        return forwardEmpType;
    }

    public RnsEmpLinkMaster forwardEmpType(String forwardEmpType) {
        this.forwardEmpType = forwardEmpType;
        return this;
    }

    public RnsForwardTypeMaster getRnsForwardTypeMaster() {
        return rnsForwardTypeMaster;
    }

    public void setRnsForwardTypeMaster(RnsForwardTypeMaster rnsForwardTypeMaster) {
        this.rnsForwardTypeMaster = rnsForwardTypeMaster;
    }

    public void setForwardEmpType(String forwardEmpType) {
        this.forwardEmpType = forwardEmpType;
    }

    public User getEmpCode() {
        return empCode;
    }

    public void setEmpCode(User empCode) {
        this.empCode = empCode;
    }

    public User getForwardEmpCode() {
        return forwardEmpCode;
    }

    public void setForwardEmpCode(User forwardEmpCode) {
        this.forwardEmpCode = forwardEmpCode;
    }

    public Boolean getMailApplicable() { return mailApplicable; }

    public void setMailApplicable(Boolean mailApplicable) { this.mailApplicable = mailApplicable; }
// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsEmpLinkMaster rnsEmpLinkMaster = (RnsEmpLinkMaster) o;
        if (rnsEmpLinkMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsEmpLinkMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsEmpLinkMaster{" +
            "id=" + getId() +
            ", empCode='" + getEmpCode() + "'" +
            ", forwardEmpCode='" + getForwardEmpCode() + "'" +
            ", forwardEmpType='" + getForwardEmpType() + "'" +
            "}";
    }
}
