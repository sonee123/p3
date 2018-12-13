package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A RnsUomMaster.
 */
@Entity
@Table(name = "rns_uom_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsUomMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_uom_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "uom_code")
    private String uomCode;

    @Column(name = "uom_name")
    private String uomName;

   /* @Column(name="created_by")
    private String createdBy;*/

    @Column(name="created_date")
    private Instant createdDate;

/*    @Column(name="last_modifiedBy_by")
    private String lastModifiedBy;*/

    @Column(name="last_updated_date")
    private Instant lastUpdatedDate;

    @OneToOne
    @JoinColumn(name="created_by", referencedColumnName="login")
    private User user;

    @OneToOne
    @JoinColumn(name="last_updated_by", referencedColumnName="login")
    private User updateUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUomCode() {
        return uomCode;
    }

    public RnsUomMaster uomCode(String uomCode) {
        this.uomCode = uomCode;
        return this;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomName() {
        return uomName;
    }

    public RnsUomMaster uomName(String uomName) {
        this.uomName = uomName;
        return this;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

   /* public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
*/
	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

/*	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}*/

	public Instant getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Instant lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
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
        RnsUomMaster rnsUomMaster = (RnsUomMaster) o;
        if (rnsUomMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsUomMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsUomMaster{" +
            "id=" + getId() +
            ", uomCode='" + getUomCode() + "'" +
            ", uomName='" + getUomName() + "'" +
            ", createdBy='" + getUser() +"'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy ='" + getUpdateUser() + "'" +
            ", lastModifiedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
