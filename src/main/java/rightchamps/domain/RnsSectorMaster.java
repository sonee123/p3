package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A RnsSectorMaster.
 */
@Entity
@Table(name = "rns_sector_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsSectorMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name="SEQ_GEN", sequenceName="rns_sect_mast_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    private Long id;

    @Column(name = "sector_code")
    private String sectorCode;

    @Column(name = "sector_code_desc")
    private String sectorCodeDesc;

    @ManyToOne
    private RnsCatgMaster rnsCatgCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public RnsSectorMaster sectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
        return this;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getSectorCodeDesc() {
        return sectorCodeDesc;
    }

    public RnsSectorMaster sectorCodeDesc(String sectorCodeDesc) {
        this.sectorCodeDesc = sectorCodeDesc;
        return this;
    }

    public void setSectorCodeDesc(String sectorCodeDesc) {
        this.sectorCodeDesc = sectorCodeDesc;
    }

    public RnsCatgMaster getRnsCatgCode() {
        return rnsCatgCode;
    }

    public RnsSectorMaster rnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
        return this;
    }

    public void setRnsCatgCode(RnsCatgMaster rnsCatgMaster) {
        this.rnsCatgCode = rnsCatgMaster;
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
        RnsSectorMaster rnsSectorMaster = (RnsSectorMaster) o;
        if (rnsSectorMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rnsSectorMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RnsSectorMaster{" +
            "id=" + getId() +
            ", sectorCode='" + getSectorCode() + "'" +
            ", sectorCodeDesc='" + getSectorCodeDesc() + "'" +
            "}";
    }
}
