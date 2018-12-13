package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsQuotationRemarkDetails.
 */
@Entity
@Table(name = "rns_quotation_remark_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationRemarkDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quot_remark_dtl_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Column(name = "quote_id", nullable = false)
    private Long quoteId;

    @OneToOne
    @JoinColumn(name="emp_code", referencedColumnName="login")
    private User empCode;

    @OneToOne
    @JoinColumn(name="forward_code", referencedColumnName="login")
    private User forwardCode;

    @Size(max = 1000)
    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Size(max = 1)
    @Column(name = "auth_type", length = 1, nullable = true)
    private String authType;

    @Column(name = "auth_date", nullable = true)
    private Instant authDate;

    @Column(name = "flow_type", nullable = true)
    private String flowType;

    @Column(name = "approval_type", nullable = true)
    private String approvalType;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="auth_type", referencedColumnName = "code", insertable = false, updatable = false)
    private RnsForwardTypeMaster rnsForwardTypeMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public RnsQuotationRemarkDetails quoteId(Long quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }


    public String getRemarks() {
        return remarks;
    }

    public RnsQuotationRemarkDetails remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAuthType() {
        return authType;
    }

    public RnsQuotationRemarkDetails authType(String authType) {
        this.authType = authType;
        return this;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public Instant getAuthDate() {
        return authDate;
    }

    public RnsQuotationRemarkDetails authDate(Instant authDate) {
        this.authDate = authDate;
        return this;
    }

    public void setAuthDate(Instant authDate) {
        this.authDate = authDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public User getEmpCode() {
        return empCode;
    }

    public void setEmpCode(User empCode) {
        this.empCode = empCode;
    }

    public User getForwardCode() {
        return forwardCode;
    }

    public void setForwardCode(User forwardCode) {
        this.forwardCode = forwardCode;
    }

    public RnsForwardTypeMaster getRnsForwardTypeMaster() {
        return rnsForwardTypeMaster;
    }

    public void setRnsForwardTypeMaster(RnsForwardTypeMaster rnsForwardTypeMaster) {
        this.rnsForwardTypeMaster = rnsForwardTypeMaster;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
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
        RnsQuotationRemarkDetails rnsQuotationRemarkDetails = (RnsQuotationRemarkDetails) o;
        if (rnsQuotationRemarkDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationRemarkDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsQuotationRemarkDetails{" +
            "id=" + getId() +
            ", quoteId=" + getQuoteId() +
            ", empCode='" + getEmpCode() + "'" +
            ", forwardCode='" + getForwardCode() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", authType='" + getAuthType() + "'" +
            ", authDate='" + getAuthDate() + "'" +
            "}";
    }
}
