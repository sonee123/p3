package rightchamps.repository;

import org.springframework.transaction.annotation.Transactional;
import rightchamps.domain.EmailTemplateBody;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the EmailTemplateBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplateBodyRepository extends JpaRepository<EmailTemplateBody, Long> {
    @Transactional
    @Modifying
    @Query("delete from EmailTemplateBody emailTemplateBody where emailTemplateBody.templateCode=?1")
    void deleteByTemplateCode(String templateCode);

    @Query("select emailTemplateBody from EmailTemplateBody emailTemplateBody where emailTemplateBody.templateCode=?1 order by emailTemplateBody.id")
    List<EmailTemplateBody> findAllByTemplateCode(String templateCode);
}
