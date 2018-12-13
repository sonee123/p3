package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A RnsArticleMaster.
 */
@Entity
@Table(name = "rns_article_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsArticleMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "rns_article_master_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_GEN")
    private Long id;

    @Column(name = "article_code")
    private String articleCode;


    @Column(name = "article_desc")
    private String articleDesc;

    @ManyToOne
    private RnsCatgMaster catgCode;

    @OneToOne
    @JoinColumn(name = "created_by", referencedColumnName = "login")
    private User user;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "login")
    private User updatedUser;

    @Column(name = "last_updated_date")
    private Instant lastUpdatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public RnsArticleMaster articleCode(String articleCode) {
        this.articleCode = articleCode;
        return this;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public RnsArticleMaster articleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
        return this;
    }

    public RnsCatgMaster getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.catgCode = rnsCatgMaster;
    }

    public RnsArticleMaster catgCode(RnsCatgMaster rnsCatgMaster) {
        this.catgCode = rnsCatgMaster;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }


    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant instant) {
        this.createdDate = instant;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant instant) {
        this.lastUpdatedDate = instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsArticleMaster rnsArticleMaster = (RnsArticleMaster) o;
        if (rnsArticleMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsArticleMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsArticleMaster{" +
            "id=" + getId() +
            ", articleCode='" + getArticleCode() + "'" +
            ", articleDesc='" + getArticleDesc() + "'" +
            "}";
    }
}
