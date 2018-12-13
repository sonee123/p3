package rightchamps.modal;

import rightchamps.domain.RnsSourceTeamMaster;
import rightchamps.domain.User;

import java.time.Instant;

public class RnsSourceTeamDetail {
    private Long id;

    private String userId;

    private Long masterId;

    private String createdBy;

    private Instant createdDate;

    private User user;

    private User updatedUser;

    private Instant lastUpdatedDate;

    private RnsSourceTeamMaster master;

    private User teamUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
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

    public RnsSourceTeamMaster getMaster() {
        return master;
    }

    public void setMaster(RnsSourceTeamMaster master) {
        this.master = master;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public User getUpdatedUser() { return updatedUser; }

    public void setUpdatedUser(User updatedUser) { this.updatedUser = updatedUser; }

    public Instant getLastUpdatedDate() { return lastUpdatedDate; }

    public void setLastUpdatedDate(Instant lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }

    public User getTeamUser() {
        return teamUser;
    }

    public void setTeamUser(User teamUser) {
        this.teamUser = teamUser;
    }
}
