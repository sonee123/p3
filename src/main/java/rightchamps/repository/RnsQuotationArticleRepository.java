package rightchamps.repository;

import rightchamps.domain.RnsQuotationArticle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsQuotationArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsQuotationArticleRepository extends JpaRepository<RnsQuotationArticle, Long> {

}
