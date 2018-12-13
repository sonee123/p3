package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A EmailTemplate.
 */
@Entity
@Table(name = "email_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="email_template_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "template_code", length = 50, nullable = false)
    private String templateCode;

    @NotNull
    @Size(max = 500)
    @Column(name = "mail_subject", length = 500, nullable = false)
    private String mailSubject;

    @NotNull
    @Size(max = 50000)
    @Column(name = "mail_body", length = 500, nullable = false)
    private String mailBody;

    @NotNull
    @Column(name = "notification", nullable = false)
    private Boolean notification;

    @NotNull
    @Column(name = "email", nullable = false)
    private Boolean email;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

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

    public EmailTemplate templateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public EmailTemplate mailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
        return this;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public EmailTemplate mailBody(String mailBody) {
        this.mailBody = mailBody;
        return this;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public Boolean isNotification() {
        return notification;
    }

    public EmailTemplate notification(Boolean notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public Boolean isEmail() {
        return email;
    }

    public EmailTemplate email(Boolean email) {
        this.email = email;
        return this;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmailTemplate createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public EmailTemplate createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public EmailTemplate lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public EmailTemplate lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
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
        EmailTemplate emailTemplate = (EmailTemplate) o;
        if (emailTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
            "id=" + getId() +
            ", templateCode='" + getTemplateCode() + "'" +
            ", mailSubject='" + getMailSubject() + "'" +
            ", mailBody1='" + getMailBody() + "'" +
            ", notification='" + isNotification() + "'" +
            ", email='" + isEmail() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
