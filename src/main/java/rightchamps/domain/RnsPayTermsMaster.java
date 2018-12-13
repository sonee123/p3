package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsPayTermsMaster.
 */
@Entity
@Table(name = "rns_pay_terms_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsPayTermsMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_pay_terms_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "pay_terms_code")
    private String payTermsCode;

    @Column(name = "pay_terms_code_desc")
    private String payTermsCodeDesc;

    @ManyToOne
    private RnsCatgMaster catgCode;

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

    public String getPayTermsCode() {
        return payTermsCode;
    }

    public RnsPayTermsMaster payTermsCode(String payTermsCode) {
        this.payTermsCode = payTermsCode;
        return this;
    }

    public void setPayTermsCode(String payTermsCode) {
        this.payTermsCode = payTermsCode;
    }

    public String getPayTermsCodeDesc() {
        return payTermsCodeDesc;
    }

    public RnsPayTermsMaster payTermsCodeDesc(String payTermsCodeDesc) {
        this.payTermsCodeDesc = payTermsCodeDesc;
        return this;
    }

    public void setPayTermsCodeDesc(String payTermsCodeDesc) {
        this.payTermsCodeDesc = payTermsCodeDesc;
    }

    public RnsCatgMaster getCatgCode() {
        return catgCode;
    }

    public RnsPayTermsMaster catgCode(RnsCatgMaster rnsCatgMaster) {
        this.catgCode = rnsCatgMaster;
        return this;
    }

    public void setCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.catgCode = rnsCatgMaster;
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
        RnsPayTermsMaster rnsPayTermsMaster = (RnsPayTermsMaster) o;
        if (rnsPayTermsMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsPayTermsMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsPayTermsMaster{" +
            "id=" + getId() +
            ", payTermsCode='" + getPayTermsCode() + "'" +
            ", payTermsCodeDesc='" + getPayTermsCodeDesc() + "'" +
            "}";
    }
}
