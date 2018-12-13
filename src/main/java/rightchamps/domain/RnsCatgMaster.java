package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RnsCatgMaster.
 */
@Entity
@Table(name = "rns_catg_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsCatgMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "rns_catg_master_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_GEN")
    private Long id;

    @Column(name = "catg_code")
    private String catgCode;

    @Column(name = "catg_code_desc")
    private String catgCodeDesc;

    @Column(name = "show_crm")
    private Boolean showCrm;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rns_catg_master_user",
        joinColumns = @JoinColumn(name = "rns_catg_masters_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<RnsRelation> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public RnsCatgMaster catgCode(String catgCode) {
        this.catgCode = catgCode;
        return this;
    }

    public String getCatgCodeDesc() {
        return catgCodeDesc;
    }

    public void setCatgCodeDesc(String catgCodeDesc) {
        this.catgCodeDesc = catgCodeDesc;
    }

    public RnsCatgMaster catgCodeDesc(String catgCodeDesc) {
        this.catgCodeDesc = catgCodeDesc;
        return this;
    }

    public Boolean isShowCrm() {
        return showCrm;
    }

    public RnsCatgMaster showCrm(Boolean showCrm) {
        this.showCrm = showCrm;
        return this;
    }

    public Set<RnsRelation> getUsers() {
        return users;
    }

    public void setUsers(Set<RnsRelation> rnsRelations) {
        this.users = rnsRelations;
    }

    public RnsCatgMaster users(Set<RnsRelation> rnsRelations) {
        this.users = rnsRelations;
        return this;
    }

    public RnsCatgMaster addUser(RnsRelation rnsRelation) {
        this.users.add(rnsRelation);
        return this;
    }

    public RnsCatgMaster removeUser(RnsRelation rnsRelation) {
        this.users.remove(rnsRelation);
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

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

    public Boolean getShowCrm() {
        return showCrm;
    }

    public void setShowCrm(Boolean showCrm) {
        this.showCrm = showCrm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RnsCatgMaster rnsCatgMaster = (RnsCatgMaster) o;
        if (rnsCatgMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsCatgMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsCatgMaster{" +
            "id=" + getId() +
            ", catgCode='" + getCatgCode() + "'" +
            ", catgCodeDesc='" + getCatgCodeDesc() + "'" +
            ", showCrm='" + isShowCrm() + "'" +
            "}";
    }
}
