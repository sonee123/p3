package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.MessageBody;

import rightchamps.repository.MessageBodyRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MessageBody.
 */
@RestController
@RequestMapping("/api")
public class MessageBodyResource {

    private final Logger log = LoggerFactory.getLogger(MessageBodyResource.class);

    private static final String ENTITY_NAME = "Message Body";

    private final MessageBodyRepository messageBodyRepository;

    public MessageBodyResource(MessageBodyRepository messageBodyRepository) {
        this.messageBodyRepository = messageBodyRepository;
    }

    /**
     * POST  /message-bodies : Create a new messageBody.
     *
     * @param messageBody the messageBody to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageBody, or with status 400 (Bad Request) if the messageBody has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/message-bodies")
    @Timed
    public ResponseEntity<MessageBody> createMessageBody(@Valid @RequestBody MessageBody messageBody) throws URISyntaxException {
        log.debug("REST request to save MessageBody : {}", messageBody);
        if (messageBody.getId() != null) {
            throw new BadRequestAlertException("A new messageBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageBody result = messageBodyRepository.save(messageBody);
        return ResponseEntity.created(new URI("/api/message-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /message-bodies : Updates an existing messageBody.
     *
     * @param messageBody the messageBody to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageBody,
     * or with status 400 (Bad Request) if the messageBody is not valid,
     * or with status 500 (Internal Server Error) if the messageBody couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/message-bodies")
    @Timed
    public ResponseEntity<MessageBody> updateMessageBody(@Valid @RequestBody MessageBody messageBody) throws URISyntaxException {
        log.debug("REST request to update MessageBody : {}", messageBody);
        if (messageBody.getId() == null) {
            return createMessageBody(messageBody);
        }
        MessageBody result = messageBodyRepository.save(messageBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageBody.getId().toString()))
            .body(result);
    }

    /**
     * GET  /message-bodies : get all the messageBodies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of messageBodies in body
     */
    @GetMapping("/message-bodies")
    @Timed
    public List<MessageBody> getAllMessageBodies() {
        log.debug("REST request to get all MessageBodies");
        return messageBodyRepository.findAll();
        }

    /**
     * GET  /message-bodies/:id : get the "id" messageBody.
     *
     * @param id the id of the messageBody to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageBody, or with status 404 (Not Found)
     */
    @GetMapping("/message-bodies/{id}")
    @Timed
    public ResponseEntity<MessageBody> getMessageBody(@PathVariable Long id) {
        log.debug("REST request to get MessageBody : {}", id);
        Optional<MessageBody> messageBody = messageBodyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(messageBody);
    }

    /**
     * DELETE  /message-bodies/:id : delete the "id" messageBody.
     *
     * @param id the id of the messageBody to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/message-bodies/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessageBody(@PathVariable Long id) {
        log.debug("REST request to delete MessageBody : {}", id);
        messageBodyRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
