/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.bean.AmountAccessBean;

/**
 * 
 */
@Repository
public interface AmountRepository extends JpaRepository<AmountAccessBean, Long>{

	/**
	 * @param object
	 * @return
	 */
	Optional<AmountAccessBean> findByValue(Long value);

}
