package rightchamps.repository;

import rightchamps.domain.RnsFileUpload;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsFileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsFileUploadRepository extends JpaRepository<RnsFileUpload, Long> {

    @Query("select rnsFileUpload from RnsFileUpload rnsFileUpload where rnsFileUpload.variantId=?1 and rnsFileUpload.uploadType=?2 order by rnsFileUpload.id desc")
    List<RnsFileUpload> findAll(Long variantId, String uploadType);
}
