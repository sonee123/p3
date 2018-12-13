package rightchamps.repository;

import rightchamps.domain.Feedback;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select feedback from Feedback feedback order by feedback.id desc")
    List<Feedback> findAll();

    @Query("select feedback from Feedback feedback where feedback.createdBy=?1 order by feedback.id desc")
    List<Feedback> findAllByCreatedBy(String createdBy);
}
