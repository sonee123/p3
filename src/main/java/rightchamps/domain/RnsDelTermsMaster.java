package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsDelTermsMaster.
 */
@Entity
@Table(name = "rns_del_terms_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsDelTermsMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_del_terms_mast_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "del_terms_code")
    private String delTermsCode;

    @Column(name = "del_terms_code_desc")
    private String delTermsCodeDesc;

    @ManyToOne
    private RnsCatgMaster rnsCatgCode;

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

    public String getDelTermsCode() {
        return delTermsCode;
    }

    public RnsDelTermsMaster delTermsCode(String delTermsCode) {
        this.delTermsCode = delTermsCode;
        return this;
    }

    public void setDelTermsCode(String delTermsCode) {
        this.delTermsCode = delTermsCode;
    }

    public String getDelTermsCodeDesc() {
        return delTermsCodeDesc;
    }

    public RnsDelTermsMaster delTermsCodeDesc(String delTermsCodeDesc) {
        this.delTermsCodeDesc = delTermsCodeDesc;
        return this;
    }

    public void setDelTermsCodeDesc(String delTermsCodeDesc) {
        this.delTermsCodeDesc = delTermsCodeDesc;
    }

    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public RnsDelTermsMaster rnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
        return this;
    }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
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
        RnsDelTermsMaster rnsDelTermsMaster = (RnsDelTermsMaster) o;
        if (rnsDelTermsMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsDelTermsMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsDelTermsMaster{" +
            "id=" + getId() +
            ", delTermsCode='" + getDelTermsCode() + "'" +
            ", delTermsCodeDesc='" + getDelTermsCodeDesc() + "'" +
            "}";
    }
}
