package rightchamps.repository;

import rightchamps.domain.RnsRelation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import rightchamps.web.rest.RnsRelationResource;


/**
 * Spring Data JPA repository for the RnsRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsRelationRepository extends JpaRepository<RnsRelation, Long> {

    @Query("select rnsRelation from RnsRelation rnsRelation where rnsRelation.user.id = ?1")
    RnsRelation findByUserId(Long userId);

}
