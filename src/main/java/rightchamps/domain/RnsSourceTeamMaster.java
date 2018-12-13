package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsSourceTeamMaster.
 */
@Entity
@Table(name = "rns_source_team_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsSourceTeamMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_source_team_master_seq", allocationSize=1)
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

    public String getDescription() {
        return description;
    }

    public RnsSourceTeamMaster description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public RnsSourceTeamMaster flag(String flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }



    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Instant getCreatedDate() {
        return createdDate;
    }

    public RnsSourceTeamMaster createdDate(Instant createdDate) {
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
        RnsSourceTeamMaster rnsSourceTeamMaster = (RnsSourceTeamMaster) o;
        if (rnsSourceTeamMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsSourceTeamMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsSourceTeamMaster{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", flag='" + getFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
