package rightchamps.web.rest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import rightchamps.config.Constants;
import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.Authority;
import rightchamps.domain.User;
import rightchamps.domain.UserAuthority;
import rightchamps.modal.UserBean;
import rightchamps.modal.UserDetails;
import rightchamps.repository.RnsVendorFavMasterRepository;
import rightchamps.repository.UserRepository;
import rightchamps.security.AuthoritiesConstants;
import rightchamps.service.MailService;
import rightchamps.service.UserService;
import rightchamps.service.dto.UserDTO;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.errors.EmailAlreadyUsedException;
import rightchamps.web.rest.errors.LoginAlreadyUsedException;
import rightchamps.web.rest.util.HeaderUtil;
import rightchamps.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    @Inject
    private RnsVendorFavMasterRepository rnsVendorFavMasterRepository;

    public UserResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("A user is updated with identifier " + userDTO.getLogin(), userDTO.getLogin()));
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    public List<UserDetails> getAllUsers(Pageable pageable) throws IllegalAccessException, InvocationTargetException{
        // final Page<UserDTO> page = userService.getAllUsers(pageable);
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        List<User> userListTemp = userRepository.findAll();
        List<UserDetails> userList = new ArrayList<UserDetails>();
        for(User user : userListTemp){
            UserDetails userDetails = new UserDetails();
            BeanUtils.copyProperties(userDetails, user);
            Set<Authority> userAuthorities = userRepository.findAuthorties(user.getId());
            userDetails.setAuthorities(userAuthorities);
            userList.add(userDetails);
        }
        return userList;
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users-vendor-all")
    @Timed
    public List<User> getAllVendors(Pageable pageable) {
        // final Page<UserDTO> page = userService.getAllVendors(pageable);
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        return userRepository.findAllVendors();
    }

    @GetMapping("/users-vendor/{search}")
    @Timed
    public List<UserDetails> getAllVendorUsers(@PathVariable String search) throws InvocationTargetException, IllegalAccessException {
        // final Page<UserDTO> page = userService.getAllUsers(pageable);
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        List<User> users = userRepository.findAllAuthority(search, search.toUpperCase(), search.toUpperCase(), search.toUpperCase());

        List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
        if(users!=null && users.size()>0){
            List<String> favourites = rnsVendorFavMasterRepository.getRnsVendorFavMasterByCreatedBy(getCurrentUserLogin());
            if(favourites!=null && favourites.size()>0){
                for(User user : users) {
                    UserDetails userDetails = new UserDetails();
                    BeanUtils.copyProperties(userDetails, user);
                    if(favourites.contains(userDetails.getLogin()))
                        userDetails.setFavourite(true);
                    else
                        userDetails.setFavourite(false);
                    userDetailsList.add(userDetails);
                }
            } else{
                for(User user : users) {
                    UserDetails userDetails = new UserDetails();
                    BeanUtils.copyProperties(userDetails, user);
                    userDetailsList.add(userDetails);
                }
            }
        }
        return userDetailsList;
    }

    @PostMapping("/users-search-users")
    @Timed
    public List<User> getAllUsers(@RequestBody UserBean userBean) throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get User : {}", userBean.getFirstName());
        return userRepository.findAllUsers(userBean.getFirstName(), userBean.getFirstName().toUpperCase());
    }

    @GetMapping("/users-vendor-favourite")
    @Timed
    public List<UserDetails> getAllVendorUsers() throws InvocationTargetException, IllegalAccessException {
        // final Page<UserDTO> page = userService.getAllUsers(pageable);
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        List<User> users = userRepository.findAllAuthority(getCurrentUserLogin());

        List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
        if(users!=null && users.size()>0){
            List<String> favourites = rnsVendorFavMasterRepository.getRnsVendorFavMasterByCreatedBy(getCurrentUserLogin());
            for(User user : users) {
                UserDetails userDetails = new UserDetails();
                BeanUtils.copyProperties(userDetails, user);
                userDetails.setFavourite(true);
                userDetailsList.add(userDetails);
            }
        }
        return userDetailsList;
    }


    @DeleteMapping("/users-vendor-delete/{search}")
    @Timed
    public ResponseEntity<Void> deleteFavourite(@PathVariable String search) throws InvocationTargetException, IllegalAccessException {

        rnsVendorFavMasterRepository.deleteByVendorCodeAndAndCreatedBy(search, getCurrentUserLogin());
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "Vendor is deleted with identifier ", search)).build();
    }

    //  @GetMapping("/users")
    // @Timed
    // public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
    //     final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
    //     HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
    //     return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    // }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) throws InvocationTargetException, IllegalAccessException{
        log.debug("REST request to get User : {}", login);
        User user = userService.getUserWithAuthoritiesByLogin(login).orElse(new User());
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDTO, user);
        return ResponseUtil.wrapOrNotFound(Optional.of(userDTO));
    }


    /**
     * GET /users/:login : get the "login" user.
     *
     * @param @login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @PostMapping("/users-email")
    @Timed
    public ResponseEntity<UserDTO> getUserByEmail(@RequestBody UserBean userBean) {
        log.debug("REST request to get User : {}", userBean.getLogin());
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(userBean.getLogin())
                .map(UserDTO::new));
    }


    /**
     * GET /users/:login : get the "login" user.
     *
     * @param @login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/current-users")
    @Timed
    public ResponseEntity<UserDTO> getCurrentUser() {
        log.debug("REST request to get User : {}");
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(getCurrentUserLogin())
                .map(UserDTO::new));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + login, login)).build();
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
                org.springframework.security.core.userdetails.UserDetails springSecurityUser = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }
}
