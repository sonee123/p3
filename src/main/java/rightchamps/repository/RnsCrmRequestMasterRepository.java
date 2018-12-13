package rightchamps.repository;

import rightchamps.domain.RnsCrmRequestMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RnsCrmRequestMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsCrmRequestMasterRepository extends JpaRepository<RnsCrmRequestMaster, Long> {
    @Query("select rnsCrmRequestMaster from RnsCrmRequestMaster rnsCrmRequestMaster order by rnsCrmRequestMaster.id desc")
    List<RnsCrmRequestMaster> findAll();

    @Query("select rnsCrmRequestMaster from RnsCrmRequestMaster rnsCrmRequestMaster where rnsCrmRequestMaster.crmCode=?1")
    RnsCrmRequestMaster findByCrmCode(String crmCode);


}
