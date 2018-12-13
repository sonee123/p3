package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsSourceTeamDtl.
 */
@Entity
@Table(name = "rns_source_team_dtl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsSourceTeamDtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_source_team_dtl_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    //@NotNull
    //@Size(max = 255)
    //@Column(name = "user_id", length = 255, nullable = false)
    //private String userId;

    @NotNull
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName="login")
    private User teamUser;

    @NotNull
    @Column(name = "master_id", nullable = false)
    private Long masterId;

    @OneToOne
    @JoinColumn(name="created_by", referencedColumnName="login")
    private User user;

     @Column(name = "created_date")
     private Instant createdDate;

    @OneToOne
    @JoinColumn(name="updated_by", referencedColumnName="login")
    private User updatedUser;

    @Column(name = "last_updated_date")
    private Instant lastUpdatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RnsSourceTeamDtl id(Long id) {
        this.id = id;
        return this;
    }
    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public RnsSourceTeamDtl masterId(Long masterId) {
        this.masterId = masterId;
        return this;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RnsSourceTeamDtl user(User user) {
        this.user = user;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public RnsSourceTeamDtl createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public RnsSourceTeamDtl updatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
        return this;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public RnsSourceTeamDtl lastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public User getTeamUser() {
        return teamUser;
    }

    public void setTeamUser(User teamUser) {
        this.teamUser = teamUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsSourceTeamDtl rnsSourceTeamDtl = (RnsSourceTeamDtl) o;
        if (rnsSourceTeamDtl.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsSourceTeamDtl.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsSourceTeamDtl{" +
            "id=" + getId() +
            ", userId='" + getTeamUser() + "'" +
            ", masterId=" + getMasterId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
