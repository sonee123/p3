package rightchamps.repository;

import rightchamps.domain.RnsUpchargeDtl;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsUpchargeDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsUpchargeDtlRepository extends JpaRepository<RnsUpchargeDtl, Long> {

    @Query("select rnsUpchargeDtl from RnsUpchargeDtl rnsUpchargeDtl where rnsUpchargeDtl.vendorId=?1 order by rnsUpchargeDtl.id")
    List<RnsUpchargeDtl> findAllByVendorId(Long vendorId);

    @Query("select coalesce(sum(rnsUpchargeDtl.value),0) from RnsUpchargeDtl rnsUpchargeDtl where rnsUpchargeDtl.vendorId=?1")
    Float getValuesByVendorId(Long vendorId);
}
