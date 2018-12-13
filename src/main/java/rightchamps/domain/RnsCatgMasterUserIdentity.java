package rightchamps.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class RnsCatgMasterUserIdentity implements Serializable {
    @NotNull
    @Column(name = "users_id")
    private Long usersId;

    @NotNull
    @Column(name = "rns_catg_masters_id")
    private Long rnsCatgMastersId;

    public RnsCatgMasterUserIdentity() {

    }

    public RnsCatgMasterUserIdentity(Long usersId, Long rnsCatgMastersId) {
        this.usersId = usersId;
        this.rnsCatgMastersId = rnsCatgMastersId;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public Long getRnsCatgMastersId() {
        return rnsCatgMastersId;
    }

    public void setRnsCatgMastersId(Long rnsCatgMastersId) {
        this.rnsCatgMastersId = rnsCatgMastersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RnsCatgMasterUserIdentity that = (RnsCatgMasterUserIdentity) o;

        if (!usersId.equals(that.usersId)) return false;
        return rnsCatgMastersId.equals(that.rnsCatgMastersId);
    }

    @Override
    public int hashCode() {
        int result = usersId.hashCode();
        result = 31 * result + rnsCatgMastersId.hashCode();
        return result;
    }
}
