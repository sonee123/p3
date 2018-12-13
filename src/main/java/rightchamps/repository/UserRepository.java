package rightchamps.repository;

import org.springframework.data.jpa.repository.Query;
import rightchamps.domain.Authority;
import rightchamps.domain.User;
import rightchamps.domain.UserAuthority;
import rightchamps.modal.UserBean;
import rightchamps.modal.UserDetails;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.Instant;
import java.util.Set;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);
    
    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);
    
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

    @Query("select user from User user where user.login=?1")
    User findByLogin(String login);

    @Query("select distinct user from User user join UserAuthority userAuthority on user.id=userAuthority.userId where  userAuthority.authorityName not in('ROLE_VENDOR') ORDER BY user.id desc")
    List<User> findAll();

    @Query("select authority from UserAuthority userAuthority join Authority authority on userAuthority.authorityName=authority.name  where userAuthority.userId=?1")
    Set<Authority> findAuthorties(Long userId);

    @Query("select user from User user join UserAuthority userAuthority on user.id=userAuthority.userId where  userAuthority.authorityName='ROLE_VENDOR' ORDER BY user.id desc")
    List<User> findAllVendors();

    @Query("select user from User user join UserAuthority userAuthority on user.id=userAuthority.userId where  userAuthority.authorityName='ROLE_VENDOR' and user.activated=1 and (user.login like %?1% or concat(upper(user.firstName),' ', upper(user.lastName)) like %?2% or upper(user.vendorCode) like %?3% or upper(user.vendorName) like %?4%) ORDER BY user.firstName")
    List<User> findAllAuthority(String login, String firstName, String vendorCode, String VendorName);

    @Query("select user from User user join UserAuthority userAuthority on user.id=userAuthority.userId where  userAuthority.authorityName<>'ROLE_VENDOR' and user.activated=1 and (user.login like %?1% or concat(upper(user.firstName),' ', upper(user.lastName)) like %?2%) ORDER BY user.firstName")
    List<User> findAllUsers(String login, String firstName);

    @Query("select userAuthority from User user join UserAuthority userAuthority on user.id=userAuthority.userId where user.login=?1")
    List<UserAuthority> findAllAuthorityByLogin(String login);

    @Query("select user from User user join UserAuthority userAuthority on user.id=userAuthority.userId where  userAuthority.authorityName='ROLE_VENDOR' and user.activated=1 and user.login in(select rnsVendorFavMaster.vendorCode from RnsVendorFavMaster rnsVendorFavMaster where rnsVendorFavMaster.createdBy=?1) ORDER BY user.firstName")
    List<User> findAllAuthority(String login);

    @Query("select user from User user where user.login in(?1)")
    List<User> findAllByLogins(List<String> logins);
    
    @Query("select user from User user where user.login in(?1)")
    Optional<User> findAllByLogin(String login);
}
