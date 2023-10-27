package com.cg.neel.igrs.payment.databean;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="DISTRICT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictAccessBean{
	
	@Id
	@Column(name = "SNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sno;
	
	@Column(name = "HINDI")
	private String hindi;
	
	@Column(name = "ENGLISH")
	private String english;
	
	@OneToOne(mappedBy = "districtAccessBean")
	private FileIdAccessBean fileIdAccessBean;
	
	@OneToMany(mappedBy = "districtAccessBean")
	private List<SubDistrictAccessBean> subDistrictAccessBeans;
	
}
