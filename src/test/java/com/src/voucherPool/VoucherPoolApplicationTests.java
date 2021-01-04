package com.src.voucherPool;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import com.src.voucherPool.entity.Offers;

@SpringBootTest(classes = VoucherPoolApplication.class)
class VoucherPoolApplicationTests {

	
	@Test
	void contextLoads() {
	}
	
	  

}
