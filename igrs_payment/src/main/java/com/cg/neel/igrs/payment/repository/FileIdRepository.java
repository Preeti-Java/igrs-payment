/**
 * 
 */
package com.cg.neel.igrs.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.neel.igrs.payment.databean.FileIdAccessBean;

/**
 * 
 */
@Repository
public interface FileIdRepository extends JpaRepository<FileIdAccessBean, Long>{

	/**
	 * @param fileId
	 * @return FileIdAccessBean
	 */
	Optional<FileIdAccessBean> findByValue(String fileId);

}
