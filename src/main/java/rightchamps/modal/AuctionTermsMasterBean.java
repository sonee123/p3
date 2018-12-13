package rightchamps.modal;

import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.RnsSourceTeamMaster;
import rightchamps.domain.RnsTypeMaster;
import rightchamps.domain.User;

import java.time.Instant;

public class AuctionTermsMasterBean {
    private Long id;

    private Long categoryId;

    private String quoteTypeCode;

    private Long sourceTeam;

    private RnsCatgMaster rnsCatgMaster;

    private RnsTypeMaster quoteType;

    private RnsSourceTeamMaster sourceTeamId;

    private String termsBody;

    private User user;

    private Instant createdDate;

    private User updatedUser;

    private Instant lastUpdatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RnsCatgMaster getRnsCatgMaster() {
        return rnsCatgMaster;
    }

    public void setRnsCatgMaster(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgMaster = rnsCatgMaster;
    }

    public RnsTypeMaster getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(RnsTypeMaster quoteType) {
        this.quoteType = quoteType;
    }

    public RnsSourceTeamMaster getSourceTeamId() {
        return sourceTeamId;
    }

    public void setSourceTeamId(RnsSourceTeamMaster sourceTeamId) {
        this.sourceTeamId = sourceTeamId;
    }

    public String getTermsBody() {
        return termsBody;
    }

    public void setTermsBody(String termsBody) {
        this.termsBody = termsBody;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuoteTypeCode() {
        return quoteTypeCode;
    }

    public void setQuoteTypeCode(String quoteTypeCode) {
        this.quoteTypeCode = quoteTypeCode;
    }

    public Long getSourceTeam() {
        return sourceTeam;
    }

    public void setSourceTeam(Long sourceTeam) {
        this.sourceTeam = sourceTeam;
    }
}
