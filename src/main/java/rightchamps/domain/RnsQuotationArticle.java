package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A RnsQuotationArticle.
 */
@Entity
@Table(name = "rns_quotation_article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsQuotationArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_quot_article_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "article_code")
    private String articleCode;

    @Column(name = "article_name")
    private String articleName;

    @ManyToOne
    private RnsBuyerMaster buyerCode;

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

    public RnsQuotationArticle articleCode(String articleCode) {
        this.articleCode = articleCode;
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleName() {
        return articleName;
    }

    public RnsQuotationArticle articleName(String articleName) {
        this.articleName = articleName;
        return this;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public RnsBuyerMaster getBuyerCode() {
        return buyerCode;
    }

    public RnsQuotationArticle buyerCode(RnsBuyerMaster rnsBuyerMaster) {
        this.buyerCode = rnsBuyerMaster;
        return this;
    }

    public void setBuyerCode(RnsBuyerMaster rnsBuyerMaster) {
        this.buyerCode = rnsBuyerMaster;
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
        RnsQuotationArticle rnsQuotationArticle = (RnsQuotationArticle) o;
        if (rnsQuotationArticle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsQuotationArticle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsQuotationArticle{" +
            "id=" + getId() +
            ", articleCode='" + getArticleCode() + "'" +
            ", articleName='" + getArticleName() + "'" +
            "}";
    }
}
