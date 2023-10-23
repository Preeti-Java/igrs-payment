/**
 * 
 */
package com.cg.neel.igrs.payment.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */

@RestController
@RequestMapping("/payment")
public class PaymentCmdImpl implements PaymentCmd{

	@Override
	public ResponseEntity<Map<String, String>> getPayment() {
		// TODO Auto-generated method stub this for conflit
		//This is preeti
		return null;
	}

}
