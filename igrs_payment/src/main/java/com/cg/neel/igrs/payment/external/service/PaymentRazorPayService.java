/**
 * 
 */
package com.cg.neel.igrs.payment.external.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

/**
 *  this class used for razor pay code
 */


@Configuration
@PropertySource("classpath:payment.properties")
public class PaymentRazorPayService {
	
	@Value("${payment.igr.key_id}")
	private String keyId;
	
	
	@Value("${payment.igr.key_secret}")
	private String keySecret;
	
	@Value("${payment.igr.currency}")
	private String currency;
	
	@Value("${payment.igr.receipt}")
	private String receipt;
	
	@Value("${payment.igr.notes_key_1}")
	private String notesKey;

	/**
	 * @param amount
	 * @param fileId
	 * @return order
	 */
	public Order getOrderId(String amount, String fileId) {
		try {
			RazorpayClient razorPay = new RazorpayClient(keyId,keySecret);
			
			//Information
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount);
			orderRequest.put("currency", currency);
			orderRequest.put("receipt", createDynamicReceipt(receipt,fileId));
			
			//Notes
			JSONObject notes = new JSONObject();
			notes.put("notes_key_1", notesKey); // Create multiple notes
			orderRequest.put("notes", notes);
			
			return razorPay.orders.create(orderRequest);
			
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param receipt
	 * @param fileId 
	 * @return number
	 */
	private String createDynamicReceipt(String receipt, String fileId) {
		return receipt+LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)+LocalTime.now()+"#"+fileId;
	}

}
