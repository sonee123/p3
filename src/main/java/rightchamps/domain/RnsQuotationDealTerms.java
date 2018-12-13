package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A RnsQuotationDealTerms.
 */
@Entity
@Table(name = "rns_quotation_deal_terms")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationDealTerms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quot_deal_terms_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")

    private Long id;

    @Column(name = "completion_by")
    private LocalDate completionBy;

    @Column(name = "valid_until")
    private Integer validUntil;

    @Column(name = "deliver_at")
    private String deliverAt;

    @Column(name = "text_2")
    private String text2;

    @ManyToOne
    private RnsQuotation quotationDealTerms;

    @ManyToOne
    private RnsPayTermsMaster paymentTerms;

    @ManyToOne
    private RnsDelPlaceMaster deliveryTerms;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCompletionBy() {
        return completionBy;
    }

    public RnsQuotationDealTerms completionBy(LocalDate completionBy) {
        this.completionBy = completionBy;
        return this;
    }

    public void setCompletionBy(LocalDate completionBy) {
        this.completionBy = completionBy;
    }

    public Integer getValidUntil() {
        return validUntil;
    }

    public RnsQuotationDealTerms validUntil(Integer validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(Integer validUntil) {
        this.validUntil = validUntil;
    }

    public String getDeliverAt() {
        return deliverAt;
    }

    public RnsQuotationDealTerms deliverAt(String deliverAt) {
        this.deliverAt = deliverAt;
        return this;
    }

    public void setDeliverAt(String deliverAt) {
        this.deliverAt = deliverAt;
    }

    public String getText2() {
        return text2;
    }

    public RnsQuotationDealTerms text2(String text2) {
        this.text2 = text2;
        return this;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public RnsQuotation getQuotationDealTerms() {
        return quotationDealTerms;
    }

    public RnsQuotationDealTerms quotationDealTerms(RnsQuotation rnsQuotation) {
        this.quotationDealTerms = rnsQuotation;
        return this;
    }

    public void setQuotationDealTerms(RnsQuotation rnsQuotation) {
        this.quotationDealTerms = rnsQuotation;
    }

    public RnsPayTermsMaster getPaymentTerms() {
        return paymentTerms;
    }

    public RnsQuotationDealTerms paymentTerms(RnsPayTermsMaster rnsPayTermsMaster) {
        this.paymentTerms = rnsPayTermsMaster;
        return this;
    }

    public void setPaymentTerms(RnsPayTermsMaster rnsPayTermsMaster) {
        this.paymentTerms = rnsPayTermsMaster;
    }

    public RnsDelPlaceMaster getDeliveryTerms() {
        return deliveryTerms;
    }

    public RnsQuotationDealTerms deliveryTerms(RnsDelPlaceMaster rnsDelPlaceMaster) {
        this.deliveryTerms = rnsDelPlaceMaster;
        return this;
    }

    public void setDeliveryTerms(RnsDelPlaceMaster rnsDelPlaceMaster) {
        this.deliveryTerms = rnsDelPlaceMaster;
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
        RnsQuotationDealTerms rnsQuotationDealTerms = (RnsQuotationDealTerms) o;
        if (rnsQuotationDealTerms.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationDealTerms.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsQuotationDealTerms{" +
            "id=" + getId() +
            ", completionBy='" + getCompletionBy() + "'" +
            ", validUntil=" + getValidUntil() +
            ", deliverAt='" + getDeliverAt() + "'" +
            ", text2='" + getText2() + "'" +
            "}";
    }
}
