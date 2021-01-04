package com.src.voucherPool.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.src.voucherPool.dao.OffersDAO;
import com.src.voucherPool.dao.RecipientsDAO;
import com.src.voucherPool.dao.VoucherDAO;
import com.src.voucherPool.entity.Offers;
import com.src.voucherPool.entity.Recipients;
import com.src.voucherPool.entity.Vouchers;

@Service
public class VoucherService {
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 

	@Autowired
	private VoucherDAO vou;


	public List<Vouchers> getVoucherNo( String code)throws IOException{	
		return vou.findAllByCode(code);
	}

	public Vouchers getVoucher (String code) throws IOException{		
		return vou.findByCode(code);
	}


	public ResponseEntity<List<Vouchers>> getAllVoucherNo() throws IOException{		
		List<Vouchers> data = vou.findAll();		
		return ResponseEntity.ok(data);
	}

	public Vouchers saveVoucher(Vouchers voucher) {
		return vou.save(voucher);
	}

	public List<Vouchers> saveAllVoucher(List<Vouchers> voucher) {
		return vou.saveAll(voucher);
	}

	public boolean checkVouchers(String voucherNo, LocalDate currDt)throws IOException{

		boolean expired = true;
		List<Vouchers> ls = vou.voucherExpiredValidation(currDt, voucherNo);

		if(ls != null && ls.size()>0) {
			expired = false;
		}
		return expired;
	}

	public Integer checkUsedVouchers(String voucherNo)throws IOException{
		Integer usage  = vou.voucherUsedValidation(voucherNo);		
		return usage;
	}
	
	
	
}

