package rightchamps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rightchamps.domain.VndrPriceDelete;

/**
 * Spring Data JPA repository for the VndrPriceDelete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VndrPriceDeleteRepository extends JpaRepository<VndrPriceDelete, Long> {
}
