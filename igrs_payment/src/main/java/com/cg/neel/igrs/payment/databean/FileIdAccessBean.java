/**
 * 
 */
package com.cg.neel.igrs.payment.databean;

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

import com.cg.neel.igrs.payment.bean.TransactionAccessBean;

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
@Table(name = "FILEID")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileIdAccessBean{

	@Id
	@Column(name = "FILE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileId;
	
	@Column(name = "VALUE")
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "DISTRICT_ID")
	private DistrictAccessBean districtAccessBean;
	
	@ManyToOne
	@JoinColumn(name = "SUBDISTRICT_ID")
	private SubDistrictAccessBean subDistrictAccessBean;
	
	@OneToMany(mappedBy = "fileIdAccessBean")
	private List<TransactionAccessBean> transactionAccessBean;
}
