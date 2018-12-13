package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsUpchargeMaster.
 */
@Entity
@Table(name = "rns_upcharge_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsUpchargeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_upcharge_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @NotNull
    @Size(max = 1)
    @Column(name = "flag", length = 1, nullable = false)
    private String flag;

   /* @Column(name = "created_by")
    private String createdBy;*/

    @Column(name = "created_date")
    private Instant createdDate;

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

    public String getDescription() {
        return description;
    }

    public RnsUpchargeMaster description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public RnsUpchargeMaster flag(String flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    /*public String getCreatedBy() {
        return createdBy;
    }

    public RnsUpchargeMaster createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }*/


    public Instant getCreatedDate() {
        return createdDate;
    }

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

	public RnsUpchargeMaster createdDate(Instant createdDate) {
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
        RnsUpchargeMaster rnsUpchargeMaster = (RnsUpchargeMaster) o;
        if (rnsUpchargeMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsUpchargeMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsUpchargeMaster{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", flag='" + getFlag() + "'" +
            ", createdBy='" + getUser() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ",lastUpdatedBy='" + getUpdateUser() + "'" +
            ",lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
