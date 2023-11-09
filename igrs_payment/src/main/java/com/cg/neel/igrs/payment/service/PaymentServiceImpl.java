/**
 * 
 */
package com.cg.neel.igrs.payment.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cg.neel.igrs.payment.bean.AmountAccessBean;
import com.cg.neel.igrs.payment.bean.StatusAccessBean;
import com.cg.neel.igrs.payment.bean.SubDistrictCodeAccessBean;
import com.cg.neel.igrs.payment.bean.TransactionAccessBean;
import com.cg.neel.igrs.payment.bean.TransactionOrderAccessBean;
import com.cg.neel.igrs.payment.bean.TransactionPaymentAccessBean;
import com.cg.neel.igrs.payment.databean.DistrictAccessBean;
import com.cg.neel.igrs.payment.databean.FileIdAccessBean;
import com.cg.neel.igrs.payment.databean.SubDistrictAccessBean;
import com.cg.neel.igrs.payment.exception.OrderFailedException;
import com.cg.neel.igrs.payment.external.service.PaymentRazorPayService;
import com.cg.neel.igrs.payment.repository.AmountRepository;
import com.cg.neel.igrs.payment.repository.FileIdRepository;
import com.cg.neel.igrs.payment.repository.StatusRepository;
import com.cg.neel.igrs.payment.repository.TransactionOrderRepository;
import com.cg.neel.igrs.payment.repository.TransactionRepository;
import com.cg.neel.igrs.payment.utils.DataUtils;
import com.cg.neel.igrs.payment.utils.UserUtils;
import com.razorpay.Order;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private final DataUtils dataUtils;
	
	private final PaymentRazorPayService paymentRazorPayService;
	
	private final StatusRepository statusRepository;
	
	private final AmountRepository amountRepository;
	
	private final TransactionRepository transactionRepository;
	
	private final TransactionOrderRepository transactionOrderRepository;
	
	private final FileIdRepository fileIdRepository;

	@Override
	public Map<String, String> createPaymentOrder(Map<String, String> map) {
		//Check payment amount and file-Id present or not
		boolean check = map.values().stream().allMatch(value -> !value.isEmpty() && value != null);
		
		if(!check)
			throw new OrderFailedException("Order value not found");
		
		String amount = map.getOrDefault("amount", "20000");
		String fileId = map.getOrDefault("fileId", "000");
		
		//Get User Details
		Long userId = UserUtils.getUserDetails();
		
		//Payment API
		Order order = paymentRazorPayService.getOrderId(amount,fileId);
		if(order == null)
			throw new OrderFailedException("Order Failed");
		//Save in db
		TransactionAccessBean transactionOrder = saveOrder(order,fileId,userId);
		
		//convert into return map value		
		Map<String,String> data = convertIntoMap(transactionOrder);
		data.put("txAmount", amount);
		//User Logs
		
		
		//Payment Logs
		
		return data;
		
	}

	/**
	 * @param transactionOrder
	 * @return map
	 */
	private Map<String, String> convertIntoMap(TransactionAccessBean transaction) {
		
		Map<String,String> map = new HashMap<>();
		map.put("txId", transaction.getTransactionOrderAccessBean().getTransactionValue());
		map.put("txReceipt", transaction.getTransactionOrderAccessBean().getTransactionReceipt());
		
		return map;
	}

	/**
	 * @param order
	 * @param fileId
	 * @param userId 
	 * @return 
	 */
	private TransactionAccessBean saveOrder(Order order, String fileId, Long userId) {
		
		//set Many payment
		TransactionPaymentAccessBean transactionPaymentAccessBean = setTxPayment(order);
	
		//Set Many FileId
		FileIdAccessBean fileIdAccessBean = setTxFileId(fileId);
		
		//Set Many order
		TransactionOrderAccessBean transactionOrderAccessBean = setTxOrder(order);
		
		//Final Join in TRanscation table
		TransactionAccessBean transactionAccessBean = new TransactionAccessBean();
		transactionAccessBean.setFileIdAccessBean(fileIdAccessBean);
		transactionAccessBean.setTransactionOrderAccessBean(transactionOrderAccessBean);
		transactionAccessBean.setTransactionPaymentAccessBean(transactionPaymentAccessBean);
		transactionAccessBean.setUserId(userId);
		
		//save
		transactionRepository.save(transactionAccessBean);
		
		return transactionAccessBean;
		
	}

	/**
	 * @param order
	 * @return TransactionOrderAccessBean
	 */
	private TransactionOrderAccessBean setTxOrder(Order order) {
		TransactionOrderAccessBean transactionOrderAccessBean = new TransactionOrderAccessBean();
		transactionOrderAccessBean.setTransactionValue(order.get("id"));
		transactionOrderAccessBean.setTransactionReceipt(order.get("receipt"));
		return transactionOrderAccessBean;
	}

	/**
	 * @param fileId
	 * @return FileIdAccessBean
	 * @apiNote Duplicate not allowed
	 */
	private FileIdAccessBean setTxFileId(String fileId) {
		
		//Verify data already exist in db or not
		return fileIdRepository.findByValue(fileId).orElseGet(
				() -> {  
					//First find by code
					SubDistrictCodeAccessBean subDistrictCodeAccessBean = dataUtils.districtNameByFileId(fileId);
					//Get SubDistrict
					SubDistrictAccessBean subDistrictAccessBean = subDistrictCodeAccessBean.getSubDistrictAccessBean();
					//Get District
					DistrictAccessBean districtAccessBean = subDistrictCodeAccessBean.getSubDistrictAccessBean().getDistrictAccessBean();
					
					FileIdAccessBean fileIdAccessBean = new FileIdAccessBean();
					fileIdAccessBean.setValue(fileId);
					//set district
					fileIdAccessBean.setDistrictAccessBean(districtAccessBean);
					//set subDistrict
					fileIdAccessBean.setSubDistrictAccessBean(subDistrictAccessBean);
					
					return fileIdAccessBean;
				});
		
	}

	/**
	 * @param order
	 * @return transactionPaymentAccessBean
	 */
	private TransactionPaymentAccessBean setTxPayment(Order order) {
		
		TransactionPaymentAccessBean transactionPaymentAccessBean = new TransactionPaymentAccessBean();
		
		//Get Status
		Optional<StatusAccessBean> statusDetails = statusRepository.findByStatusValue(order.get("status"));
		
		//Get Amount
		Integer amount = order.get("amount");
		Long i = new Long(amount);
		Optional<AmountAccessBean> amountDetails = amountRepository.findByValue(i);
		
		//set status
		if(statusDetails.isPresent())
			transactionPaymentAccessBean.setStatusAccessBean(statusDetails.get());
		//set amount
		if(amountDetails.isPresent())
			transactionPaymentAccessBean.setAmountAccessBean(amountDetails.get());
				
		return transactionPaymentAccessBean;
	}

	/**
	 * @param order_id,payment_id,status
	 * @return transactionPaymentAccessBean
	 */
	@Override
	public void updatePaymentOrder(Map<String, String> map) {
		
		boolean flag = map.values().stream().allMatch(value -> !value.isEmpty() && value == null);
		if(flag)
			throw new OrderFailedException("Order update value not found");
		
		String orderId = map.get("txId");
		String paymentId = map.get("txPaymentId");
		String status = map.get("txStatus");
		
		try {
		  updateTxOrder(orderId,paymentId,status);
		}
		catch(Exception e) {
			throw new OrderFailedException("Order is not update successfully");
		}
	}

	/**
	 * @param order_id
	 * @param payment_id
	 * @param status
	 * @return
	 */
	private void updateTxOrder(String orderId, String paymentId, String status) {
		
		TransactionOrderAccessBean transactionOrderAccessBean = transactionOrderRepository.findByTransactionValue(orderId).orElseThrow(OrderFailedException::new);
		
		TransactionPaymentAccessBean transactionPaymentAccessBean = transactionOrderAccessBean.getTransactionAccessBean().getTransactionPaymentAccessBean();

		transactionPaymentAccessBean.setStatusAccessBean(statusRepository.findByStatusValue(status).get());
		transactionPaymentAccessBean.setPaymentId(paymentId);
		
		transactionOrderRepository.save(transactionOrderAccessBean);
		
	}

}
