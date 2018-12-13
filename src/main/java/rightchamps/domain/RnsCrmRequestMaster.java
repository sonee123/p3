package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A RnsCrmRequestMaster.
 */
@Entity
@Table(name = "rns_crm_request_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsCrmRequestMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_crm_req_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "crm_code")
    private String crmCode;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "target_pcd")
    private Instant targetPcd;

    @Column(name = "merchant_remarks")
    private String merchantRemarks;

    @Column(name = "jhi_date")
    private Instant date;

    @ManyToOne
    private RnsPchMaster rnsPchMaster;

    @ManyToOne
    private RnsArticleMaster rnsArticleMaster;

    @ManyToOne
    private RnsBuyerMaster buyerCode;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToOne
    @JoinColumn(name="created_by", referencedColumnName="login")
    private User user;

    @Column(name="last_updated_date")
    private Instant lastUpdatedDate;

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

    public String getCrmCode() {
        return crmCode;
    }

    public RnsCrmRequestMaster crmCode(String crmCode) {
        this.crmCode = crmCode;
        return this;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public RnsCrmRequestMaster requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Instant getTargetPcd() {
        return targetPcd;
    }

    public RnsCrmRequestMaster targetPcd(Instant targetPcd) {
        this.targetPcd = targetPcd;
        return this;
    }

    public void setTargetPcd(Instant targetPcd) {
        this.targetPcd = targetPcd;
    }

    public String getMerchantRemarks() {
        return merchantRemarks;
    }

    public RnsCrmRequestMaster merchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
        return this;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
    }

    public Instant getDate() {
        return date;
    }

    public RnsCrmRequestMaster date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public RnsPchMaster getRnsPchMaster() {
        return rnsPchMaster;
    }

    public RnsCrmRequestMaster rnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
        return this;
    }

    public void setRnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
    }

    public RnsBuyerMaster getBuyerCode() {
        return buyerCode;
    }

    public RnsCrmRequestMaster buyerCode(RnsBuyerMaster rnsBuyerMaster) {
        this.buyerCode = rnsBuyerMaster;
        return this;
    }

    public void setBuyerCode(RnsBuyerMaster rnsBuyerMaster) {
        this.buyerCode = rnsBuyerMaster;
    }

    public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Instant getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Instant lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public RnsArticleMaster getRnsArticleMaster() {
		return rnsArticleMaster;
	}

	public void setRnsArticleMaster(RnsArticleMaster rnsArticleMaster) {
		this.rnsArticleMaster = rnsArticleMaster;
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
        RnsCrmRequestMaster rnsCrmRequestMaster = (RnsCrmRequestMaster) o;
        if (rnsCrmRequestMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsCrmRequestMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "RnsCrmRequestMaster [" +
				"id=" + getId() +
				", crmCode='" + getCrmCode() + "'" +
	            ", requestedBy='" + getRequestedBy() + "'" +
	            ", targetPcd='" + getTargetPcd() + "'" +
	            ", merchantRemarks='" + getMerchantRemarks() + "'" +
	            ", date='" + getDate() + "'" +
	            ", rnsPchMaster='" + getRnsPchMaster() + "'" +
	            ", rnsArticleMaster='" + getRnsArticleMaster() + "'" +
	            ", buyerCode='" + getBuyerCode() + "'" +
	            // ", createdBy='" + user.getLogin() +"'" +
	            ", createdDate='" + getCreatedDate() + "'" +
	            // ", lastModifiedBy ='" + updateUser.getLogin() + "'" +
	            ", lastModifiedDate='" + getLastUpdatedDate() + "'" +
				"]";
	}
}
