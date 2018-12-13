package rightchamps.repository;

import rightchamps.domain.RnsVendorRemark;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RnsVendorRemark entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RnsVendorRemarkRepository extends JpaRepository<RnsVendorRemark, Long>, JpaSpecificationExecutor<RnsVendorRemark> {

	@Query("select remark from RnsVendorRemark remark, User user where remark.vendorEmail=user.email and remark.quotation.id=?1 and user.login=?2 order by remark.id desc")
	List<RnsVendorRemark> findAllBySort(Long qid,String vendorCode);

    @Query("select remark from RnsVendorRemark remark where remark.quotation.id=?1 order by remark.id desc")
    List<RnsVendorRemark> findAllByQuotationSort(Long qid);
}
