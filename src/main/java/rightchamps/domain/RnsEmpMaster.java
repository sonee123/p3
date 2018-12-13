package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsEmpMaster.
 */
@Entity
@Table(name = "rns_emp_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsEmpMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_emp_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "emp_code")
    private String empCode;

    @Column(name = "emp_name")
    private String empName;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpCode() {
        return empCode;
    }

    public RnsEmpMaster empCode(String empCode) {
        this.empCode = empCode;
        return this;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public RnsEmpMaster empName(String empName) {
        this.empName = empName;
        return this;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsEmpMaster rnsEmpMaster = (RnsEmpMaster) o;
        if (rnsEmpMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsEmpMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsEmpMaster{" +
            "id=" + getId() +
            ", empCode='" + getEmpCode() + "'" +
            ", empName='" + getEmpName() + "'" +
            "}";
    }
}
