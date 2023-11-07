/**
 * 
 */
package com.cg.neel.igrs.payment.service;

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
import com.cg.neel.igrs.payment.repository.DistrictRepository;
import com.cg.neel.igrs.payment.repository.StatusRepository;
import com.cg.neel.igrs.payment.repository.SubDistrictRepository;
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

	@Override
	public void createPaymentOrder(Map<String, String> data) throws Exception {
		//Check payment amount and file-Id present or not
		if(!data.containsKey("amount"))
			throw new Exception("Amount Value not found");
		String amount = data.getOrDefault("amount", "200");
		
		if(!data.containsKey("fileId"))
			throw new Exception("Fileid not found");
		String fileId = data.getOrDefault("fileId", "000");
		
		//Get User Details
		Long userId = UserUtils.getUserDetails();
		
		//Payment API
		Order order = paymentRazorPayService.getOrderId(amount,fileId);
		if(order == null)
			throw new OrderFailedException("Order Failed");
		//Save in db
		saveOrder(order,fileId,userId);
		
		
		
		//User Logs
		
		
		//Payment Logs
		
		
	}

	/**
	 * @param order
	 * @param fileId
	 * @param userId 
	 */
	private void saveOrder(Order order, String fileId, Long userId) {
		
		//Get Status
		Optional<StatusAccessBean> statusDetails = statusRepository.findByStatusValue(order.get("status"));
		
		//Get Amount
		Optional<AmountAccessBean> amountDetails = amountRepository.findByValue(new Long(order.get("amount")));
		
		//set Many payment
		TransactionPaymentAccessBean transactionPaymentAccessBean = new TransactionPaymentAccessBean();
		//set status
		if(statusDetails.isPresent())
			transactionPaymentAccessBean.setStatusAccessBean(statusDetails.get());
		//set amount
		if(amountDetails.isPresent())
			transactionPaymentAccessBean.setAmountAccessBean(amountDetails.get());
		
		//First find by code
		SubDistrictCodeAccessBean subDistrictCodeAccessBean = dataUtils.districtNameByFileId(fileId);
		//Get SubDistrict
		SubDistrictAccessBean subDistrictAccessBean = subDistrictCodeAccessBean.getSubDistrictAccessBean();
		//Get District
		DistrictAccessBean districtAccessBean = subDistrictCodeAccessBean.getSubDistrictAccessBean().getDistrictAccessBean();
		
		//Set Many FileId
		FileIdAccessBean fileIdAccessBean = new FileIdAccessBean();
		//set district
		fileIdAccessBean.setDistrictAccessBean(districtAccessBean);
		//set subDistrict
		fileIdAccessBean.setSubDistrictAccessBean(subDistrictAccessBean);
		
		//Set Many order
		TransactionOrderAccessBean transactionOrderAccessBean = new TransactionOrderAccessBean();
		transactionOrderAccessBean.setTransactionValue(order.get("id"));
		transactionOrderAccessBean.setTransactionReceipt(order.get("receipt"));
		
		
		//Final Join in TRanscation table
		TransactionAccessBean transactionAccessBean = new TransactionAccessBean();
		transactionAccessBean.setFileIdAccessBean(fileIdAccessBean);
		transactionAccessBean.setTransactionOrderAccessBean(transactionOrderAccessBean);
		transactionAccessBean.setTransactionPaymentAccessBean(transactionPaymentAccessBean);
		transactionAccessBean.setUserId(userId);
		
		//save
		transactionRepository.save(transactionAccessBean);
		
	}

}
