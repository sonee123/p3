package rightchamps.repository;

import rightchamps.domain.Template;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Template entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Query("select remarks from Template remarks where rnsCatgCode.id=?1")
    List<Template> getTemplateByCatg(Long CatgCode);

    @Query("select template from Template template where template.rnsCatgCode.id=?1 and template.flag='1'")
    List<Template> getTemplateByCatgActivated(Long CatgCode);

    @Query("select template from Template template where template.id=?1")
    Template findTemplateById(Long id);

    @Query("select template from Template template where template.templateName=?1")
    Template findTemplateByTemplateName(String templateName);

    @Query("select template from Template template order by template.id desc")
    List<Template> findAll();

}
