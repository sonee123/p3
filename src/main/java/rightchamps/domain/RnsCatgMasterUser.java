package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rns_catg_master_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RnsCatgMasterUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RnsCatgMasterUserIdentity rnsCatgMasterUserIdentity;

    public RnsCatgMasterUserIdentity getRnsCatgMasterUserIdentity() {
        return rnsCatgMasterUserIdentity;
    }

    public void setRnsCatgMasterUserIdentity(RnsCatgMasterUserIdentity rnsCatgMasterUserIdentity) {
        this.rnsCatgMasterUserIdentity = rnsCatgMasterUserIdentity;
    }
}
