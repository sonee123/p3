package rightchamps.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsTypeMaster.
 */
@Entity
@Table(name = "rns_type_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsTypeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_type_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "type_code_desc")
    private String typeCodeDesc;

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

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @ManyToOne
    private RnsCatgMaster rnsCatgCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public RnsTypeMaster typeCode(String typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCodeDesc() {
        return typeCodeDesc;
    }

    public RnsTypeMaster typeCodeDesc(String typeCodeDesc) {
        this.typeCodeDesc = typeCodeDesc;
        return this;
    }

    public void setTypeCodeDesc(String typeCodeDesc) {
        this.typeCodeDesc = typeCodeDesc;
    }

    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public RnsTypeMaster rnsCatgCode(RnsCatgMaster rnsCatgMaster) {
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
        RnsTypeMaster rnsTypeMaster = (RnsTypeMaster) o;
        if (rnsTypeMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsTypeMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsTypeMaster{" +
            "id=" + getId() +
            ", typeCode='" + getTypeCode() + "'" +
            ", typeCodeDesc='" + getTypeCodeDesc() + "'" +
            "}";
    }
}
