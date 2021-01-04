package com.src.voucherPool.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.src.voucherPool.dao.RecipientsDAO;
import com.src.voucherPool.entity.Recipients;
import com.src.voucherPool.entity.Vouchers;

@Service
public class RecipientsService {
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 

	@Autowired
	private RecipientsDAO rep;

	public List<Vouchers> getVouchers(String email) throws IOException {
		List<Vouchers> emailLs = rep.findVouchersByEmail(email);
		return emailLs;
	}

	public String findEmail(String email) throws IOException{

		Recipients recp = rep.findByEmail(email);
		String emailRes = "" ;
		if(recp!= null) {
			emailRes = recp.getEmail();
		}
		return emailRes;		

	}

	public ResponseEntity<Recipients> getRecipient(String email) throws IOException {
		Recipients types = rep.findByEmail(email);
		return ResponseEntity.ok(types);
	}

	public String getRecipientInfoByEmail(String email) throws IOException {
		Recipients recp = rep.findByEmail(email);
		return recp.toString();
	}

	public void saveRecipients(final Recipients recip) {
		rep.save(recip);
	}

	

}
