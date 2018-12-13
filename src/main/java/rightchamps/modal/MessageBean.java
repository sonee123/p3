package rightchamps.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import rightchamps.domain.MessageBody;
import rightchamps.domain.User;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MessageBean {
    private Long id;

    private String fromMail;

    private String toMail;

    private String subject;

    private String messageBody;

    private Long quotationId;

    private String createdBy;

    private Instant createdDate;

    private String createdUtilDate;

    private String company;

    private String readflag;

    User fromUser;

    User toUser;

    List<MessageBody> messageBodyList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

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

    public List<MessageBody> getMessageBodyList() {
        return messageBodyList;
    }

    public void setMessageBodyList(List<MessageBody> messageBodyList) {
        this.messageBodyList = messageBodyList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getReadflag() { return readflag; }

    public void setReadflag(String readflag) { this.readflag = readflag; }

    public String getCreatedUtilDate() { return createdUtilDate; }

    public void setCreatedUtilDate(String createdUtilDate) { this.createdUtilDate = createdUtilDate; }
}
