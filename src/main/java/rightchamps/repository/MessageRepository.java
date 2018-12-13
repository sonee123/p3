package rightchamps.repository;

import rightchamps.domain.Message;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select message from Message message where message.quotationId=?1 order by message.id desc")
    List<Message> findAllByQuotationId(Long quotationId);

    @Query("select message from Message message where message.toMail=?1 order by message.id desc")
    List<Message> findAllByToMail(String toMail);

    @Query("select message from Message message where message.fromMail=?1 order by message.id desc")
    List<Message> findAllByFromMail(String fromMail);

    @Query("select message from Message message where message.createdDate between ?1 and ?2 and message.fromMail=?3 order by message.id desc")
    List<Message> findAllByCreatedDateBetweenAndFromMail(Instant fromDate, Instant toDate, String login);

    @Query("select message from Message message where message.createdDate between ?1 and ?2 and message.toMail=?3 order by message.id desc")
    List<Message> findAllByCreatedDateBetweenAndToMail(Instant fromDate, Instant toDate, String login);
}
