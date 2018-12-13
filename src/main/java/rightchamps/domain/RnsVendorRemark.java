package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A RnsVendorRemark.
 */
@Entity
@Table(name = "remark")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsVendorRemark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    
    @SequenceGenerator(name="SEQ_GEN", sequenceName="remark_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    
    private Long id;

    @Column(name = "remark_text")
    private String remarkText;

    @Column(name = "vendor_email")
    private String vendorEmail;

    @Column(name = "staff_email")
    private String staffEmail;

    @Column(name = "from_email")
    private String fromEmail;

    @Column(name = "to_email")
    private String toEmail;

    @Column(name = "jhi_read")
    private Boolean read;

    @ManyToOne
    private RnsQuotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarkText() {
        return remarkText;
    }

    public RnsVendorRemark remarkText(String remarkText) {
        this.remarkText = remarkText;
        return this;
    }

    public void setRemarkText(String remarkText) {
        this.remarkText = remarkText;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public RnsVendorRemark vendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
        return this;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public RnsVendorRemark staffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
        return this;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public RnsVendorRemark fromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
        return this;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public RnsVendorRemark toEmail(String toEmail) {
        this.toEmail = toEmail;
        return this;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public Boolean isRead() {
        return read;
    }

    public RnsVendorRemark read(Boolean read) {
        this.read = read;
        return this;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public RnsQuotation getQuotation() {
        return quotation;
    }

    public RnsVendorRemark quotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
        return this;
    }

    public void setQuotation(RnsQuotation rnsQuotation) {
        this.quotation = rnsQuotation;
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
        RnsVendorRemark rnsVendorRemark = (RnsVendorRemark) o;
        if (rnsVendorRemark.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsVendorRemark.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsVendorRemark{" +
            "id=" + getId() +
            ", remarkText='" + getRemarkText() + "'" +
            ", vendorEmail='" + getVendorEmail() + "'" +
            ", staffEmail='" + getStaffEmail() + "'" +
            ", fromEmail='" + getFromEmail() + "'" +
            ", toEmail='" + getToEmail() + "'" +
            ", read='" + isRead() + "'" +
            "}";
    }
}
