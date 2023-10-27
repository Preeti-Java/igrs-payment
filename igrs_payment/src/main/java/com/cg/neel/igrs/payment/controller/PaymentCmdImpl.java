/**
 * 
 */
package com.cg.neel.igrs.payment.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.neel.igrs.payment.data.DataService;
import com.cg.neel.igrs.payment.users.UserService;

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

	private final UserService userService;
	
	private final DataService dataService;

	@Override
	public ResponseEntity<Map<String, String>> getPayment(final String fileCode) {
		log.info("Request Received for /payment/info endpoint.");
		
		//Calling User Micro Service
		Long userId = userService.getUserId();
		if(userId == 0L) 
			 return ResponseEntity.ok().body(Collections.emptyMap()); 
		
		//Calling Data Micro Service
		
		
		
		return null;
	}
	

}
