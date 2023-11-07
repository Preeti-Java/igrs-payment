/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.bean.StatusAccessBean;

/**
 * 
 */
@Repository
public interface StatusRepository extends JpaRepository<StatusAccessBean, Long>{

	/**
	 * @param object
	 * @return 
	 */
	Optional<StatusAccessBean> findByStatusValue(String status);

}
