package rightchamps.repository;

import rightchamps.domain.RnsArticleMaster;
import rightchamps.domain.RnsBuyerMaster;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsArticleMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsArticleMasterRepository extends JpaRepository<RnsArticleMaster, Long> {
	
	@Query("select rns_article_master from RnsArticleMaster rns_article_master order by rns_article_master.id desc")
    List<RnsArticleMaster> findAll();

    @Query("select rnsArticleMaster from RnsArticleMaster rnsArticleMaster where rnsArticleMaster.articleCode=?1")
    RnsArticleMaster findByArticleCode(String articleCode);

}
