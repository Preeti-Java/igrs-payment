/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.bean.TransactionOrderAccessBean;

/**
 * 
 */
@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrderAccessBean, Long>{

}
