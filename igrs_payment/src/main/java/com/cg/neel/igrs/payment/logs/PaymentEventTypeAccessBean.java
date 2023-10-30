/**
 * 
 */
package com.cg.neel.igrs.payment.logs;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name= "PAYMENTEVENTTYPE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEventTypeAccessBean {
	
	@Id
	@Column(name = "PAYMENTEVENTTYPE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentEventTypeId;
	
	@OneToMany(mappedBy = "paymentEventTypeAccessBean")
	private List<PaymentLogAccessBean> paymentLogAccessBeans;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	

}
