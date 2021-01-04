package com.src.voucherPool.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="recipients")
public class Recipients extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id" , nullable=false)
	private Long Id;

	
	@OneToMany ( mappedBy="recp", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true )
	@JsonManagedReference
	private List<Vouchers> voucher = new ArrayList<>();
	
	@Getter @Setter
	@Column(name="SNAME", nullable = true, updatable = true, insertable = true, length = 100)
	private String name;
	
	@Getter @Setter
	@Column(name="SEMAIL", unique = true, nullable = true, updatable = true, insertable = true, length = 100)
	private String email;
	
	public Recipients(){
		 
	 }
	  public Recipients( String name, String email ) {	  
		  
		
	    this.name = name;
	    this.email = email;
	    
	  }

   
	@Override
	public String toString() {
		return "Recipients [email=" + email + ", voucher Code=" + voucher.get(0).getCode() + ", Event= "+ voucher.get(0).getOffers().getEvent() +  "]";
	}
	
	
}
