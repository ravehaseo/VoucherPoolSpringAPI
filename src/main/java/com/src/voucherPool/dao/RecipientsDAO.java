package com.src.voucherPool.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.src.voucherPool.entity.Recipients;
import com.src.voucherPool.entity.Vouchers;

public interface RecipientsDAO extends JpaRepository<Recipients , Long> {
	
	@Query(value="SELECT vc FROM Recipients rec join rec.voucher vc  WHERE rec.email = :email")
	public List<Vouchers> findVouchersByEmail(@Param("email")String email);
	
	public Recipients findByEmail(@Param("email")String email);
}
