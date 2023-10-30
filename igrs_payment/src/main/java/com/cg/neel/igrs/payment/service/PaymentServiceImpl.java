/**
 * 
 */
package com.cg.neel.igrs.payment.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cg.neel.igrs.payment.utils.UserUtils;

/**
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public void createPaymentOrder(Map<String, String> data) throws Exception {
		//Check payment amount and file-Id present or not
		if(!data.containsKey("amount"))
			throw new Exception("Amount Value not found");
		String amount = data.getOrDefault("amount", "200");
		
		if(!data.containsKey("fileId"))
			throw new Exception("Fileid bot found");
		String fileId = data.getOrDefault("fileId", "000");
		
		//Get User Details
		Long userId = UserUtils.getUserDetails();
		
		//Payment API
		
		
		//User Logs
		
		
		//Payment Logs
		
		
	}

}
