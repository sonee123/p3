package rightchamps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jhi_user_authority")
public class UserAuthority {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "authority_name")
    private String authorityName;

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getAuthorityName() { return authorityName; }

    public void setAuthorityName(String authorityName) { this.authorityName = authorityName; }
}
