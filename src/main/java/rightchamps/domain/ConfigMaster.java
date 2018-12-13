package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.List;


/**
 * A ConfigMaster.
 */
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConfigMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @OneToMany
    private List<RnsCatgMaster> catgCode;

    @OneToMany
    private List<RnsArticleMaster> articleCode;

    @OneToMany
    private List<RnsDelPlaceMaster> rnsDelPlaceMaster;

    @OneToMany
    private List<RnsDelTermsMaster> rnsDelTermsMaster;

    @OneToMany
    private List<RnsPayTermsMaster> rnsPayTermsMaster;

    @OneToMany
    private List<RnsTypeMaster> rnsTypeMaster;

    @OneToMany
    private List<RnsUomMaster> rnsUomMaster;

    @OneToMany
    private List<Template> template;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RnsCatgMaster> getCatgCode() {
        return catgCode;
    }

    public ConfigMaster catgCode(List<RnsCatgMaster> rnsCatgMaster) {
        this.catgCode = rnsCatgMaster;
        return this;
    }

    public void setCatgCode(List<RnsCatgMaster> rnsCatgMaster) {
        this.catgCode = rnsCatgMaster;
    }


    public List<RnsArticleMaster> getArticleCode() {
        return articleCode;
    }

    public ConfigMaster articleCode(List<RnsArticleMaster> articleCode) {
        this.articleCode = articleCode;
        return this;
    }

    public void setArticleCode(List<RnsArticleMaster> articleCode) {
        this.articleCode = articleCode;
    }

     public List<RnsDelPlaceMaster> getRnsDelPlaceMaster() {
        return rnsDelPlaceMaster;
    }

    public ConfigMaster rnsDelPlaceMaster(List<RnsDelPlaceMaster> rnsDelPlaceMaster) {
        this.rnsDelPlaceMaster = rnsDelPlaceMaster;
        return this;
    }

    public void setRnsDelPlaceMaster(List<RnsDelPlaceMaster> rnsDelPlaceMaster) {
        this.rnsDelPlaceMaster = rnsDelPlaceMaster;
    }

    public List<RnsDelTermsMaster> getRnsDelTermsMaster() {
        return rnsDelTermsMaster;
    }

    public ConfigMaster rnsDelTermsMaster(List<RnsDelTermsMaster> rnsDelTermsMaster) {
        this.rnsDelTermsMaster = rnsDelTermsMaster;
        return this;
    }

    public void setRnsDelTermsMaster(List<RnsDelTermsMaster> rnsDelTermsMaster) {
        this.rnsDelTermsMaster = rnsDelTermsMaster;
    }
    

    public List<RnsPayTermsMaster> getRnsPayTermsMaster() {
        return rnsPayTermsMaster;
    }

    public ConfigMaster rnsPayTermsMaster(List<RnsPayTermsMaster> rnsPayTermsMaster) {
        this.rnsPayTermsMaster = rnsPayTermsMaster;
        return this;
    }

    public void setRnsPayTermsMaster(List<RnsPayTermsMaster> rnsPayTermsMaster) {
        this.rnsPayTermsMaster = rnsPayTermsMaster;
    }


    public List<RnsTypeMaster> getRnsTypeMaster() {
        return rnsTypeMaster;
    }

    public ConfigMaster rnsTypeMaster(List<RnsTypeMaster> rnsTypeMaster) {
        this.rnsTypeMaster = rnsTypeMaster;
        return this;
    }

    public void setRnsTypeMaster(List<RnsTypeMaster> rnsTypeMaster) {
        this.rnsTypeMaster = rnsTypeMaster;
    }

    public List<RnsUomMaster> getRnsUomMaster() {
        return rnsUomMaster;
    }

    public ConfigMaster rnsUomMaster(List<RnsUomMaster> rnsUomMaster) {
        this.rnsUomMaster = rnsUomMaster;
        return this;
    }

    public void setRnsUomMaster(List<RnsUomMaster> rnsUomMaster) {
        this.rnsUomMaster = rnsUomMaster;
    }

    public List<Template> getTemplate() {
        return template;
    }

    public ConfigMaster template(List<Template> template) {
        this.template = template;
        return this;
    }

    public void setTemplate(List<Template> template) {
        this.template = template;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConfigMaster catgCode = (ConfigMaster) o;
        if (catgCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), catgCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsArticleMaster{" +
            "id=" + getId() +
            ", catgCode='" + getCatgCode() + "'" +
            "}";
    }
}
