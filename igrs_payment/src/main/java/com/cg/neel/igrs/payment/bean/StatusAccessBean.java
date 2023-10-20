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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Preeti Rani
 * @apiNote this class used for store status type of payment
 * 
 */

@Table(name = "STATUS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccessBean {
	
	@Id
	@Column(name = "STATUS_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long statusId;
	
	@Column(name = "STATUSVALUE")
	private String statusValue;
	
	@Column(name = "STATUSDESCRIPTION")
	private String statusDescription;

	@OneToOne(mappedBy = "statusAccessBean")
	private TransactionPaymentAccessBean transactionPaymentAccessBean;
}
