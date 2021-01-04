package com.src.voucherPool.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.voucherPool.dao.OffersDAO;
import com.src.voucherPool.entity.Offers;

@Service
public class OffersService {
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private OffersDAO off ;
	
	public Offers findByEvent(String event) throws IOException{

		return off.findByEvent(event);
	}


	public Offers saveOffers(final Offers offer) {
		return off.save(offer);
	}

}
