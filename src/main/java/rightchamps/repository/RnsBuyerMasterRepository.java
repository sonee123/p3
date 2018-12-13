package rightchamps.repository;

import rightchamps.domain.RnsBuyerMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsBuyerMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsBuyerMasterRepository extends JpaRepository<RnsBuyerMaster, Long> {
    @Query("select rnsBuyerMaster from RnsBuyerMaster rnsBuyerMaster order by rnsBuyerMaster.id desc")
    List<RnsBuyerMaster> findAll();

    @Query("select rnsBuyerMaster from RnsBuyerMaster rnsBuyerMaster where rnsBuyerMaster.buyerCode=?1")
    RnsBuyerMaster findByBuyerCode(String buyerCode);
}
