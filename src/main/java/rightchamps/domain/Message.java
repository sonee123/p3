package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="message_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @NotNull
    @Column(name = "from_mail", nullable = false)
    private String fromMail;

    @NotNull
    @Column(name = "to_mail", nullable = false)
    private String toMail;

    @NotNull
    @Size(max = 500)
    @Column(name = "subject", length = 500, nullable = false)
    private String subject;

    @NotNull
    @Size(max = 50000)
    @Column(name = "message_body", length = 2000, nullable = false)
    private String messageBody;

    @NotNull
    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="from_mail", referencedColumnName = "login", insertable = false, updatable = false)
    User fromUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="to_mail", referencedColumnName = "login", insertable = false, updatable = false)
    User toUser;

    @Size(max = 1)
    @Column(name = "readflag", length = 1)
    private String readflag;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromMail() {
        return fromMail;
    }

    public Message fromMail(String fromMail) {
        this.fromMail = fromMail;
        return this;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getToMail() {
        return toMail;
    }

    public Message toMail(String toMail) {
        this.toMail = toMail;
        return this;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return subject;
    }

    public Message subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Message messageBody(String messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public Message quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Message createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Message createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getReadflag() { return readflag; }

    public void setReadflag(String readflag) {
        this.readflag = readflag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", fromMail='" + getFromMail() + "'" +
            ", toMail='" + getToMail() + "'" +
            ", subject='" + getSubject() + "'" +
            ", messageBody='" + getMessageBody() + "'" +
            ", quotationId=" + getQuotationId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
