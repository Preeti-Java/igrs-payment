/**
 * 
 */
package com.cg.neel.igrs.payment.logs;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Preeti
 *
 */

@Entity
@Table(name= "PAYMENTLOG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLogAccessBean {
	
	@Id
	@Column(name = "PAYMENTLOG_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentLogId;
	
	//@ManyToOne
	//@JoinColumn(name = "userId")
	private Long userId;
	
	@OneToMany(mappedBy = "paymentLogAccessBean")
	private List<PaymentLogDetailsAccessBean> paymentLogDetailsAccessBeans;
	
	@ManyToOne
	@JoinColumn( name= "paymentEventTypeId")
	private PaymentEventTypeAccessBean paymentEventTypeAccessBean;
	
	@Column(name = "LOGTIME")
	private Date logTime;
	
	

}
