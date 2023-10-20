/**
 * 
 */
package com.cg.neel.igrs.payment.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cg.neel.igrs.payment.utils.DateAudit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Preeti Rani
 * @apiNote this bean used for store amount and when update the amount
 * 
 */

@Entity
@Table(name = "AMOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmountAccessBean extends DateAudit{

	@Id
	@Column(name = "AMOUNT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long amountId;
	
	@Column(name = "VALUE")
	private Long value;
	
	@OneToOne(mappedBy = "amountAccessBean")
	private TransactionPaymentAccessBean transactionPaymentAccessBean;
	
}
