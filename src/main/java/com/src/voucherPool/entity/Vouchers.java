package com.src.voucherPool.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="vouchers")
public class Vouchers extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id" , nullable=false)
	private Long Id;	

	@Getter @Setter
	@Column(name="SCODE" , nullable = true, updatable = true, insertable = true, length = 8)
	private String code;


	@Getter
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "LOFFERID", referencedColumnName = "Id")
	@JsonBackReference
	private Offers offers;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "LRECIPID", referencedColumnName = "Id")
	@JsonBackReference
	private Recipients recp;

	@Getter @Setter
	@Column(name="IISUSED",nullable=true,updatable=true,insertable=true)
	private Integer isUsed;

	@Getter @Setter
	@Column(name="DDTUSED", nullable=true, updatable=true, insertable=true)
	private LocalDate dateUsed;

	@Transient
	private String recpEmail;
	@Transient
	private String recpName;
	
	 public String getRecpEmail() {
		    return recp.getEmail();
		  }
	 
	 public String getRecpName() {
		    return recp.getName();
		  }
	
	public Vouchers(){
		 
	 }
	  public Vouchers( String code, Integer used , LocalDate dtUsed) {	  
		  
		
	    this.code = code;
	    this.isUsed = used;
	    this.dateUsed = dtUsed;
	    
	  }


}
