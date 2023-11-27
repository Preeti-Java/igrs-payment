/**
 * 
 */
package com.cg.neel.igrs.payment.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/paid")
	ResponseEntity<String> paidPayment(@RequestParam String str);
	
	@PostMapping("/create_order")
	ResponseEntity<Map<String, String>> createOrder(@RequestBody Map<String,String> data);
	
	@PostMapping("/update_order")
	ResponseEntity<?> updateOrder(@RequestBody Map<String,String> data);
	
	@PostMapping("/txStatusByFileIdAndUserId")
	boolean verifyTxStatusByFileIdAndUserId(@RequestParam String fileid, @RequestParam Long userId);
}
