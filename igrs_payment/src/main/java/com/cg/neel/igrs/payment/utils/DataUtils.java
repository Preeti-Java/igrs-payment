/**
 * 
 */
package com.cg.neel.igrs.payment.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.neel.igrs.payment.bean.SubDistrictCodeAccessBean;
import com.cg.neel.igrs.payment.repository.SubDistrictCodeRepository;

/**
 * @author Preeti
 * @Des For reused method 
 *
 */
@Component
public class DataUtils {
	
	@Autowired
	private SubDistrictCodeRepository subDistrictCodeRepository;
	

	/**
	 * @param fileCode - fileId
	 * @return District Name/Sub District Name
	 */
	public SubDistrictCodeAccessBean districtNameByFileId(String fileCode) {
		//Extract english word from fileID
		String code = firstNumericValue(fileCode);
		
		//find from database
		return subDistrictCodeRepository.findByCode(code);
	}
	
	private String firstNumericValue(String str1) {
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < str1.length(); i++) {
			 if (Character.isAlphabetic(str1.charAt(i)))
				 str.append(str1.charAt(i));
			
		}
		return str.toString();

	}
	
}
	
