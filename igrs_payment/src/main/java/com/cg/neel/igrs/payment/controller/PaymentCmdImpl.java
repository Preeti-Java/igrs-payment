/**
 * 
 */
package com.cg.neel.igrs.payment.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.neel.igrs.payment.exception.OrderFailedException;
import com.cg.neel.igrs.payment.service.PaymentService;
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
	public ResponseEntity<Map<String, String>> createOrder(final Map<String, String> map) {
		//Check Map  is empty or not
		if(map.size() == 0)
			throw new OrderFailedException("Order Details is empty");
		
		 Map<String, String> data = paymentService.createPaymentOrder(map);
		return ResponseEntity.ok().body(data);
	}

	@Override
	public ResponseEntity<?>  updateOrder(Map<String, String> map) {
		//Check Map is empty or not
		if(map.size() == 0)
			throw new OrderFailedException("Order Update Details is empty");
		
		paymentService.updatePaymentOrder(map);
		return ResponseEntity.ok().body("Updated");
	}


}
