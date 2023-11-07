/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.databean.DistrictAccessBean;

/**
 * 
 */
@Repository
public interface DistrictRepository extends JpaRepository<DistrictAccessBean, Long>{


}
