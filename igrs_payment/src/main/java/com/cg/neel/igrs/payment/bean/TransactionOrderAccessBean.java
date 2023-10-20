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
 * @apiNote this bean used for store payment order 
 */

@Table( name  = "TRANSACTIONORDER")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOrderAccessBean extends DateAudit {
	
	@Id
	@Column(name = "TRANSACTIONORDER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionOrderId;
	
	@Column(name = "TRANSACTIONVALUE")
	private Long transactionValue;

	@OneToOne(mappedBy = "transactionOrderAccessBean")
	private TransactionAccessBean transactionAccessBean;
	
}
