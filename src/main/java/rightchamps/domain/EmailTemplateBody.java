package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A EmailTemplateBody.
 */
@Entity
@Table(name = "email_template_body")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailTemplateBody implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="email_template_body_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "template_code", length = 50, nullable = false)
    private String templateCode;

    @NotNull
    @Size(max = 1000)
    @Column(name = "mail_body", length = 1000, nullable = false)
    private String mailBody;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public EmailTemplateBody templateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getMailBody() {
        return mailBody;
    }

    public EmailTemplateBody mailBody(String mailBody) {
        this.mailBody = mailBody;
        return this;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmailTemplateBody createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public EmailTemplateBody createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
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
        EmailTemplateBody emailTemplateBody = (EmailTemplateBody) o;
        if (emailTemplateBody.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailTemplateBody.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailTemplateBody{" +
            "id=" + getId() +
            ", templateCode='" + getTemplateCode() + "'" +
            ", mailBody='" + getMailBody() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
