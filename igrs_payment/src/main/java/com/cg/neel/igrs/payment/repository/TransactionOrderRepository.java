/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.bean.TransactionOrderAccessBean;
import com.cg.neel.igrs.payment.databean.FileIdAccessBean;

/**
 * 
 */
@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrderAccessBean, Long>{


	/**
	 * @param orderId
	 * @return
	 */
	Optional<TransactionOrderAccessBean> findByTransactionValue(String orderId);

}
