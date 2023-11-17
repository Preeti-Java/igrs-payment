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
	
	
	//Exception
	private static final String ORDER_VALUE_NOT_FOUND = "Order value not found";
	private static final String ORDER_FAILED = "Order Failed";
	private static final String ORDER_UPDATE_VALUE_NOT_FOUND = "Order update value not found";
	private static final String ORDER_IS_NOT_UPDATE_SUCCESSFULLY = "Order is not update successfully";
	
	//Map Key
	private static final String TRANSACTION_ID = "txId";
	private static final String TRANSACTION_PAYMENT_ID = "txPaymentId";
	private static final String TRANSACTION_STATUS = "txStatus";
	private static final String TRANSACTION_AMOUNT = "txAmount";
	private static final String TRANSACTION_RECEIPT ="txReceipt";
	private static final String STATUS = "status";
	private static final String AMOUNT = "amount";
	private static final String ID = "id";
	private static final String FILE_ID = "fileId";
	private static final String RECEIPT = "receipt";

	@Override
	public Map<String, String> createPaymentOrder(Map<String, String> map) {
		//Check payment amount and file-Id present or not
		boolean check = map.values().stream().allMatch(value -> !value.isEmpty() && value != null);
		
		if(!check)
			throw new OrderFailedException(ORDER_VALUE_NOT_FOUND);
		
		String amount = map.getOrDefault(AMOUNT, "20000");
		String fileId = map.getOrDefault(FILE_ID, "000");
		
		//Get User Details
		Long userId = UserUtils.getUserDetails();
		
		//Payment API
		Order order = paymentRazorPayService.getOrderId(amount,fileId);
		if(order == null)
			throw new OrderFailedException(ORDER_FAILED);
		//Save in db
		TransactionAccessBean transactionOrder = saveOrder(order,fileId,userId);
		
		//convert into return map value		
		Map<String,String> data = convertIntoMap(transactionOrder);
		data.put(TRANSACTION_AMOUNT, amount);
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
		map.put(TRANSACTION_ID, transaction.getTransactionOrderAccessBean().getTransactionValue());
		map.put(TRANSACTION_RECEIPT, transaction.getTransactionOrderAccessBean().getTransactionReceipt());
		
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
		transactionOrderAccessBean.setTransactionValue(order.get(ID));
		transactionOrderAccessBean.setTransactionReceipt(order.get(RECEIPT));
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
					SubDistrictCodeAccessBean subDistrictCodeAccessBean = dataUtils.districtNameByFileCode(fileId);
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
		Optional<StatusAccessBean> statusDetails = statusRepository.findByStatusValue(order.get(STATUS));
		
		//Get Amount
		Integer amount = order.get(AMOUNT);
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
			throw new OrderFailedException(ORDER_UPDATE_VALUE_NOT_FOUND);
		
		String orderId = map.get(TRANSACTION_ID);
		String paymentId = map.get(TRANSACTION_PAYMENT_ID);
		String status = map.get(TRANSACTION_STATUS);
		
		try {
		  updateTxOrder(orderId,paymentId,status);
		}
		catch(Exception e) {
			throw new OrderFailedException(ORDER_IS_NOT_UPDATE_SUCCESSFULLY);
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
