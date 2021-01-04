package com.src.voucherPool.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.src.voucherPool.entity.Offers;

public interface OffersDAO extends JpaRepository<Offers, Long> {
	
	public Offers findByEvent(String event);

}
