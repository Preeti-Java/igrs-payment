/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.bean.TransactionAccessBean;

/**
 * 
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionAccessBean, Long>{

}
