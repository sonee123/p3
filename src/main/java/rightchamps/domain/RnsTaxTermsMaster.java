package rightchamps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RnsTaxTermsMaster.
 */
@Entity
@Table(name = "tax_terms_mast")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsTaxTermsMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="tax_terms_mast_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "tax_terms_code", length = 20, nullable = false)
    private String taxTermsCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "tax_terms_desc", length = 255, nullable = false)
    private String taxTermsDesc;

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

    public String getTaxTermsCode() {
        return taxTermsCode;
    }

    public RnsTaxTermsMaster taxTermsCode(String taxTermsCode) {
        this.taxTermsCode = taxTermsCode;
        return this;
    }

    public void setTaxTermsCode(String taxTermsCode) {
        this.taxTermsCode = taxTermsCode;
    }

    public String getTaxTermsDesc() {
        return taxTermsDesc;
    }

    public RnsTaxTermsMaster taxTermsDesc(String taxTermsDesc) {
        this.taxTermsDesc = taxTermsDesc;
        return this;
    }

    public void setTaxTermsDesc(String taxTermsDesc) {
        this.taxTermsDesc = taxTermsDesc;
    }



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

	public RnsCatgMaster getRnsCatgCode() { return rnsCatgCode; }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgCode) { this.rnsCatgCode = rnsCatgCode;  }

    @Override
    public String toString() {
        return "RnsTaxTermsMaster{" +
            "id=" + getId() +
            ", taxTermsCode='" + getTaxTermsCode() + "'" +
            ", taxTermsDesc='" + getTaxTermsDesc() + "'" +
            "}";
    }
}
