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

import com.cg.neel.igrs.payment.utils.DateAudit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Preeti Rani
 * @apiNote this bean used for store amount and status
 */

@Table(name = "transactionPayment")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPaymentAccessBean extends DateAudit {
	
	@Id
	@Column(name = "TRANSACTIONPAYMENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionPaymentId;
	
	@Column(name = "PAYMENTID")
	private String paymentId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AMOUNT_ID")
	private AmountAccessBean amountAccessBean;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATUS_ID")
	private StatusAccessBean statusAccessBean;
	
	@Column(name = "DISCRIPTION")
	private String discription;

}
