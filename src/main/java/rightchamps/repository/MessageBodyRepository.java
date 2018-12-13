package rightchamps.repository;

import rightchamps.domain.MessageBody;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MessageBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageBodyRepository extends JpaRepository<MessageBody, Long> {
    @Query("select messageBody from MessageBody messageBody where messageBody.messageId=?1 order by messageBody.id")
    List<MessageBody> findAllByMessageId(Long messageId);
}
