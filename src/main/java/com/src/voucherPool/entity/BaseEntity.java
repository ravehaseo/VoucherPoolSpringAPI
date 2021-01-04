package com.src.voucherPool.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable { 

	protected static final int ALLOC_SIZE=1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@Column (name="TMODDT", nullable=false,updatable=true,insertable=true)
    protected Date modifiedDate;
}
