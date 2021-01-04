package com.src.voucherPool.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.src.voucherPool.entity.Vouchers;

@Repository
public interface VoucherDAO extends JpaRepository<Vouchers, Long> {

	public List<Vouchers> findAllByCode(String code);
	
	public Vouchers findByCode (String code);
	
	@Query(value="SELECT vc FROM Vouchers vc join vc.offers off  WHERE :currentDt <= off.expiryDate and vc.code = :voucherNo")
	public List<Vouchers> voucherExpiredValidation(@Param("currentDt")LocalDate currDt, @Param("voucherNo")String voucherNo);
	
	@Query(value="SELECT vc.isUsed FROM Vouchers vc  WHERE vc.code = :voucherNo")
	public Integer voucherUsedValidation(@Param("voucherNo")String voucherNo);
}
