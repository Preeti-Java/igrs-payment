/**
 * 
 */
package com.cg.neel.igrs.payment.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Preeti
 * @apiNote 
 */

@RequestMapping("/default")
public interface PaymentCmd {

	@GetMapping("/info")
	ResponseEntity<Map<String,String>> getPayment(@RequestParam String fileCode);
	
}
