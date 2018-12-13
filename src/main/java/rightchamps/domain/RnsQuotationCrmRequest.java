package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A RnsQuotationCrmRequest.
 */
@Entity
@Table(name = "rns_quotation_crm_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationCrmRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quot_crm_req_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "crm_request_number")
    private String crmRequestNumber;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "target_pcd")
    private LocalDate targetPcd;

    @Column(name = "merchant_remarks")
    private String merchantRemarks;

    @Column(name = "jhi_date")
    private Instant date;

    @ManyToOne
    private RnsBuyerMaster buyerCode;

    @ManyToOne
    private RnsPchMaster rnsPchMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrmRequestNumber() {
        return crmRequestNumber;
    }

    public RnsQuotationCrmRequest crmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
        return this;
    }

    public void setCrmRequestNumber(String crmRequestNumber) {
        this.crmRequestNumber = crmRequestNumber;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public RnsQuotationCrmRequest requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDate getTargetPcd() {
        return targetPcd;
    }

    public RnsQuotationCrmRequest targetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
        return this;
    }

    public void setTargetPcd(LocalDate targetPcd) {
        this.targetPcd = targetPcd;
    }

    public String getMerchantRemarks() {
        return merchantRemarks;
    }

    public RnsQuotationCrmRequest merchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
        return this;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        this.merchantRemarks = merchantRemarks;
    }

    public Instant getDate() {
        return date;
    }

    public RnsQuotationCrmRequest date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public RnsBuyerMaster getBuyerCode() {
        return buyerCode;
    }

    public RnsQuotationCrmRequest buyerCode(RnsBuyerMaster rnsBuyerMaster) {
        this.buyerCode = rnsBuyerMaster;
        return this;
    }

    public void setBuyerCode(RnsBuyerMaster rnsBuyerMaster) {
        this.buyerCode = rnsBuyerMaster;
    }

    public RnsPchMaster getRnsPchMaster() {
        return rnsPchMaster;
    }

    public RnsQuotationCrmRequest rnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
        return this;
    }

    public void setRnsPchMaster(RnsPchMaster rnsPchMaster) {
        this.rnsPchMaster = rnsPchMaster;
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
        RnsQuotationCrmRequest rnsQuotationCrmRequest = (RnsQuotationCrmRequest) o;
        if (rnsQuotationCrmRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationCrmRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsQuotationCrmRequest{" +
            "id=" + getId() +
            ", crmRequestNumber='" + getCrmRequestNumber() + "'" +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", targetPcd='" + getTargetPcd() + "'" +
            ", merchantRemarks='" + getMerchantRemarks() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
