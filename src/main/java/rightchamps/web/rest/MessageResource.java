package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.Message;

import rightchamps.domain.MessageBody;
import rightchamps.modal.MessageBean;
import rightchamps.repository.MessageBodyRepository;
import rightchamps.repository.MessageRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

/**
 * REST controller for managing Message.
 */
@RestController
@RequestMapping("/api")
public class MessageResource {

    private final Logger log = LoggerFactory.getLogger(MessageResource.class);

    private static final String ENTITY_NAME = "Message";

    private final MessageRepository messageRepository;

    @Autowired
    private MessageBodyRepository messageBodyRepository;

    @Autowired
    ApplicationProperties applicationProperties;

    public MessageResource(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * POST  /messages : Create a new message.
     *
     * @param message the message to create
     * @return the ResponseEntity with status 201 (Created) and with body the new message, or with status 400 (Bad Request) if the message has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/messages")
    @Timed
    public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message) throws URISyntaxException {
        log.debug("REST request to save Message : {}", message);
        if (message.getId() != null) {
            throw new BadRequestAlertException("A new message cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (message.getCreatedBy() == null) {
            message.setCreatedBy(getCurrentUserLogin());
        }
        message.setCreatedDate(Instant.now());
        String mailBody = message.getMessageBody();
        message.setMessageBody("");
        Message result = messageRepository.save(message);
        List<String> strings = new ArrayList<String>();
        int index = 0;
        while (index < mailBody.length()) {
            strings.add(mailBody.substring(index, Math.min(index + 2000, mailBody.length())));
            index += 2000;
        }
        for(String body : strings){
            MessageBody messageBody = new MessageBody();
            messageBody.setMessageId(result.getId());
            messageBody.setMessageBody(body);
            messageBody.setCreatedBy(getCurrentUserLogin());
            messageBody.setCreatedDate(LocalDate.now());
            messageBodyRepository.save(messageBody);
        }

        return ResponseEntity.created(new URI("/api/messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /messages : Updates an existing message.
     *
     * @param message the message to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated message,
     * or with status 400 (Bad Request) if the message is not valid,
     * or with status 500 (Internal Server Error) if the message couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/messages")
    @Timed
    public ResponseEntity<Message> updateMessage(@Valid @RequestBody Message message) throws URISyntaxException {
        log.debug("REST request to update Message : {}", message);
        if (message.getId() == null) {
            return createMessage(message);
        }
        Message result = messageRepository.save(message);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, message.getId().toString()))
            .body(result);
    }

    /**
     * GET  /messages : get all the messages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of messages in body
     */
    @GetMapping("/messages-inbox")
    @Timed
    public List<MessageBean> getAllMessagesByToMail() throws IllegalAccessException, InvocationTargetException{
        log.debug("REST request to get all Messages");
        List<Message> messages  = messageRepository.findAllByToMail(getCurrentUserLogin());
        List<MessageBean> messageBeans = new ArrayList<MessageBean>();
        for(Message message : messages){
            MessageBean messageBean = new MessageBean();
            BeanUtils.copyProperties(messageBean, message);
            messageBean.setCompany(applicationProperties.getCompany());
            if(messageBean.getCreatedDate()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                messageBean.setCreatedUtilDate(simpleDateFormat.format(Date.from(messageBean.getCreatedDate())));
            }
            messageBeans.add(messageBean);
        }
        return messageBeans;
    }

    /**
     * GET  /messages : get all the messages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of messages in body
     */
    @GetMapping("/messages-sent")
    @Timed
    public List<MessageBean> getAllMessagesByFromMail() throws IllegalAccessException, InvocationTargetException{
        log.debug("REST request to get all Messages");
        List<Message> messages  = messageRepository.findAllByFromMail(getCurrentUserLogin());
        List<MessageBean> messageBeans = new ArrayList<MessageBean>();
        for(Message message : messages){
            MessageBean messageBean = new MessageBean();
            BeanUtils.copyProperties(messageBean, message);
            messageBean.setCompany(applicationProperties.getCompany());
            if(messageBean.getCreatedDate()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                messageBean.setCreatedUtilDate(simpleDateFormat.format(Date.from(messageBean.getCreatedDate())));
            }
            messageBeans.add(messageBean);
        }
        return messageBeans;
    }

    /**
     * GET  /messages : get all the messages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of messages in body
     */
    @GetMapping("/messages")
    @Timed
    public List<Message> getAllMessages() {
        log.debug("REST request to get all Messages");
        return messageRepository.findAll();
    }

    @GetMapping("/messages-quotation/{id}")
    @Timed
    public List<MessageBean> getAllMessagesbyQuotation(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get all Messages");
        List<MessageBean> messages = new ArrayList<MessageBean>();
        List<Message> messagesTemp = messageRepository.findAllByQuotationId(id);
        if(messagesTemp==null){
        } else{
            for(Message message : messagesTemp){
                MessageBean messageBean = new MessageBean();
                BeanUtils.copyProperties(messageBean,message);
                Collection<MessageBody> messageBodySet = messageBodyRepository.findAllByMessageId(message.getId());
                String body="";
                for(MessageBody messageBody : messageBodySet) {
                    body += messageBody.getMessageBody();
                }
                String messagebody = StringEscapeUtils.unescapeHtml3(body);
                messageBean.setMessageBody(messagebody);
                messageBean.setCompany(applicationProperties.getCompany());
                messages.add(messageBean);
            }
        }
        return messages;
    }

    /**
     * GET  /messages/:id : get the "id" message.
     *
     * @param id the id of the message to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the message, or with status 404 (Not Found)
     */
    @GetMapping("/messages/{id}")
    @Timed
    public ResponseEntity<MessageBean> getMessage(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        log.debug("REST request to get Message : {}", id);
        Message message = messageRepository.findById(id).orElse(null);
        if(message.getReadflag()==null){
            message.setReadflag("Y");
            messageRepository.save(message);
        }
        MessageBean messageBean = new MessageBean();
        BeanUtils.copyProperties(messageBean,message);
        Collection<MessageBody> messageBodySet = messageBodyRepository.findAllByMessageId(message.getId());
        String body="";
        for(MessageBody messageBody : messageBodySet) {
            body += messageBody.getMessageBody();
        }
        String messagebody = StringEscapeUtils.unescapeHtml3(body);
        messageBean.setMessageBody(messagebody);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageBean));
    }

    /**
     * DELETE  /messages/:id : delete the "id" message.
     *
     * @param id the id of the message to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        log.debug("REST request to delete Message : {}", id);
        messageRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
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
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }
}
