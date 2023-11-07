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

import com.cg.neel.igrs.payment.databean.SubDistrictAccessBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Preeti
 *
 */

@Entity
@Table(name="SubDistrictCode")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubDistrictCodeAccessBean {

	@Id
	@Column(name = "Sno")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sno;
	
	@Column(name = "code")
	private String code;
	
	@OneToOne
	private SubDistrictAccessBean subDistrictAccessBean;
	
}
