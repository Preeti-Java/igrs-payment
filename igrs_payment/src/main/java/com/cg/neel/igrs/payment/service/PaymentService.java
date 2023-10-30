/**
 * 
 */
package com.cg.neel.igrs.payment.service;

import java.util.Map;

/**
 * this service call for payment
 */
public interface PaymentService {

	/**
	 * @param data
	 * @throws Exception 
	 */
	void createPaymentOrder(Map<String, String> data) throws Exception;

}
