/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.databean.SubDistrictAccessBean;

/**
 * 
 */
@Repository
public interface SubDistrictRepository extends JpaRepository<SubDistrictAccessBean, Long>{

}
