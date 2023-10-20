/**
 * 
 */
package com.cg.neel.igrs.payment.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Preeti
 * @apiNote 
 */

@RequestMapping("/default")
public interface PaymentCmd {

	ResponseEntity<Map<String,String>> getPayment();
}
