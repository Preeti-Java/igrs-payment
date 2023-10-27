/**
 * 
 */
package com.cg.neel.igrs.payment.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cg.neel.igrs.payment.databean.FileIdAccessBean;
import com.cg.neel.igrs.payment.utils.DateAudit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Preeti Rani
 * @apiNote this bean store details about user,order,payment etc
 */

@Table(name = "TRANSACTION")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionAccessBean extends DateAudit {

	@Id
	@Column(name = "TRANSACTION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	private Long userId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TRANSACTIONORDER_ID")
	private TransactionOrderAccessBean transactionOrderAccessBean;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TRANSACTIONPAYMENT_ID")
	private TransactionPaymentAccessBean transactionPaymentAccessBean;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FILE_ID")
	private FileIdAccessBean fileIdAccessBean;
	
	
}
