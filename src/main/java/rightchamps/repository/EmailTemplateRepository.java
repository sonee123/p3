package rightchamps.repository;

import rightchamps.domain.EmailTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the EmailTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    @Query("select emailTemplate from EmailTemplate emailTemplate where emailTemplate.templateCode=?1")
    EmailTemplate findByTemplateCode(String templateCode);

    @Query("select emailTemplate from EmailTemplate emailTemplate order by emailTemplate.id desc")
    List<EmailTemplate> findAll();
}
