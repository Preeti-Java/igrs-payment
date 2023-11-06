/**
 * 
 */
package com.cg.neel.igrs.payment.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.neel.igrs.payment.service.PaymentService;
import com.cg.neel.igrs.payment.users.UserService;
import com.cg.neel.igrs.payment.utils.UserUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

@RestController
@RequestMapping("/payment")
@Slf4j
@AllArgsConstructor
public class PaymentCmdImpl implements PaymentCmd{

	private final PaymentService paymentService;

	//Work in Future
	@Override
	public ResponseEntity<Map<String, String>> getPayment(final String fileCode) {
		log.info("Request Received for /payment/info endpoint.");
		
		//Get User Details
		Long userId = UserUtils.getUserDetails();
		if(userId == 0L) 
			 return ResponseEntity.ok().body(Collections.emptyMap()); 
		
		//Calling Data Micro Service
		
		
		
		return null;
	}

	@Override
	public ResponseEntity<String> paidPayment(String str) {
		return null;
	}

	@Override
	public ResponseEntity<String> createOrder(final Map<String, String> data) throws Exception {
		//Check Map  is empty or not
		if(data.size() == 0)
			return ResponseEntity.ok("Order Details is empty");
		//Call Service
		paymentService.createPaymentOrder(data);
		
		return null;
	}


}
