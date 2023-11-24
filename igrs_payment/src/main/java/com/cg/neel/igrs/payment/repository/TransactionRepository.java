/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.bean.TransactionAccessBean;

/**
 * 
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionAccessBean, Long>{

	/**
	 * @param fileId
	 * @param userId
	 * @param string
	 * @return
	 */
	@Query(value = "select statusvalue from igrspayment.status where status_id=("
			+ "select status_id from igrspayment.transactionpayment where TRANSACTIONPAYMENT_ID = ("
			+ "SELECT TRANSACTIONPAYMENT_ID FROM igrspayment.transaction where"
			+ " userid = :userId and FILE_ID = (select FILE_ID from igrspayment.fileid where value= :fileId) order by TRANSACTIONPAYMENT_ID desc LIMIT 1))", nativeQuery = true)
	String verifyTxStatusByFileIdAndUserIdAndStatus(String fileId, String userId);

}
