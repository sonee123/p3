package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RnsPchMaster.
 */
@Entity
@Table(name = "rns_pch_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsPchMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_pch_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "pch_code")
    private String pchCode;

    @Column(name = "pch_name")
    private String pchName;

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

    /*@ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rns_pch_master_user",
               joinColumns = @JoinColumn(name="rns_pch_masters_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<RnsRelation> users = new HashSet<>();*/

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPchCode() {
        return pchCode;
    }

    public RnsPchMaster pchCode(String pchCode) {
        this.pchCode = pchCode;
        return this;
    }

    public void setPchCode(String pchCode) {
        this.pchCode = pchCode;
    }

    public String getPchName() {
        return pchName;
    }

    public RnsPchMaster pchName(String pchName) {
        this.pchName = pchName;
        return this;
    }

    public void setPchName(String pchName) {
        this.pchName = pchName;
    }

    /*public Set<RnsRelation> getUsers() {
        return users;
    }

    public RnsPchMaster users(Set<RnsRelation> rnsRelations) {
        this.users = rnsRelations;
        return this;
    }

    public RnsPchMaster addUser(RnsRelation rnsRelation) {
        this.users.add(rnsRelation);
        return this;
    }

    public RnsPchMaster removeUser(RnsRelation rnsRelation) {
        this.users.remove(rnsRelation);
        return this;
    }

    public void setUsers(Set<RnsRelation> rnsRelations) {
        this.users = rnsRelations;
    }*/


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
        RnsPchMaster rnsPchMaster = (RnsPchMaster) o;
        if (rnsPchMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsPchMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsPchMaster{" +
            "id=" + getId() +
            ", pchCode='" + getPchCode() + "'" +
            ", pchName='" + getPchName() + "'" +
            "}";
    }
}
