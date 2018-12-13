package rightchamps.repository;

import rightchamps.domain.RnsVendorRemarkComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsVendorRemarkComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsVendorRemarkCommentRepository extends JpaRepository<RnsVendorRemarkComment, Long> {

}
