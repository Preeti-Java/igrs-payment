package com.cg.neel.igrs.payment.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SUBDISTRICT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubDistrictAccessBean {

	@Id
	@Column(name = "SNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sno;
	
	@Column(name = "HINDI")
	private String hindi;
	
	@Column(name = "ENGLISH")
	private String english;
	
	@OneToOne(mappedBy = "subDistrictAccessBean")
	private FileIdAccessBean fileIdAccessBean;
	
	@ManyToOne
	@JoinColumn(name = "DISTRICT_ID")
	private DistrictAccessBean districtAccessBean;
	
}
