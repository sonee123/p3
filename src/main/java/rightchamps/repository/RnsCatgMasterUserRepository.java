package rightchamps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.RnsCatgMasterUser;
import rightchamps.domain.RnsCatgMasterUserIdentity;

@SuppressWarnings("unused")
@Repository
public interface RnsCatgMasterUserRepository extends JpaRepository<RnsCatgMasterUser, RnsCatgMasterUserIdentity> {
    @Modifying
    @Transactional
    @Query("delete from RnsCatgMasterUser rnscatgmas0_ where rnscatgmas0_.rnsCatgMasterUserIdentity.usersId=?1")
    void deleteByRnsCatgMasterUserIdentityUsersId(Long userId);
}
