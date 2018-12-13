package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A AuctionTermsMaster.
 */
@Entity
@Table(name = "auction_terms_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuctionTermsMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="auction_terms_master_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @OneToOne
    @JoinColumn(name="category_id", referencedColumnName="id")
    private RnsCatgMaster rnsCatgMaster;

    @OneToOne
    @JoinColumn(name="quote_type", referencedColumnName="type_code")
    private RnsTypeMaster quoteType;

    @OneToOne
    @JoinColumn(name="source_team_id", referencedColumnName="id")
    private RnsSourceTeamMaster sourceTeamId;

    @Column(name = "terms_body")
    private String termsBody;

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
}
