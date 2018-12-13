package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A RnsQuotationEventDefination.
 */
@Entity
@Table(name = "rns_quotation_event_defination")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationEventDefination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quot_event_def_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "technology")
    private String technology;

    @Column(name = "defect_code")
    private String defectCode;

    @Column(name = "text_1")
    private String text1;

    @ManyToOne
    private RnsTypeMaster rnsType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTechnology() {
        return technology;
    }

    public RnsQuotationEventDefination technology(String technology) {
        this.technology = technology;
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getDefectCode() {
        return defectCode;
    }

    public RnsQuotationEventDefination defectCode(String defectCode) {
        this.defectCode = defectCode;
        return this;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    public String getText1() {
        return text1;
    }

    public RnsQuotationEventDefination text1(String text1) {
        this.text1 = text1;
        return this;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public RnsTypeMaster getRnsType() {
        return rnsType;
    }

    public RnsQuotationEventDefination rnsType(RnsTypeMaster rnsTypeMaster) {
        this.rnsType = rnsTypeMaster;
        return this;
    }

    public void setRnsType(RnsTypeMaster rnsTypeMaster) {
        this.rnsType = rnsTypeMaster;
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
        RnsQuotationEventDefination rnsQuotationEventDefination = (RnsQuotationEventDefination) o;
        if (rnsQuotationEventDefination.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationEventDefination.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsQuotationEventDefination{" +
            "id=" + getId() +
            ", technology='" + getTechnology() + "'" +
            ", defectCode='" + getDefectCode() + "'" +
            ", text1='" + getText1() + "'" +
            "}";
    }
}
