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
	 * @return Create payment order
	 * @throws Exception 
	 */
	 Map<String, String> createPaymentOrder(Map<String, String> data);

	/**
	 * @param map
	 * @return Update payment order
	 */
	 void updatePaymentOrder(Map<String, String> map);

	/**
	 * @param fileId
	 * @param userId
	 * @return
	 */
	boolean verifyTxStatusByFileIdAndUserId(String fileId, String userId);

}
