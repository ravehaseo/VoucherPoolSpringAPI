package com.src.voucherPool.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.src.voucherPool.entity.Offers;
import com.src.voucherPool.entity.Recipients;
import com.src.voucherPool.entity.Vouchers;
import com.src.voucherPool.exception.ResourceNotFoundException;
import com.src.voucherPool.service.OffersService;
import com.src.voucherPool.service.RecipientsService;
import com.src.voucherPool.service.VoucherService;

@RestController
//@RequestMapping(value="/api")
public class VoucherController {

	private final Logger log = LoggerFactory.getLogger(this.getClass()); 

	@Autowired
	private VoucherService vouServ;

	@Autowired
	private OffersService offServ;

	@Autowired
	private RecipientsService recpServ;


	//Get Voucher based on Voucher No
	@GetMapping("/voucherNo/{scode}")
	public List<Vouchers> getVoucherNo(@PathVariable( value= "scode") String code)throws IOException{
		return vouServ.getVoucherNo(code);
	}

	//Get Voucher based on Email
	@GetMapping("/vouchers/{semail}" )
	@ResponseBody
	public List<Vouchers> getVouchers(@PathVariable( value= "semail") String email) throws IOException {
		return recpServ.getVouchers(email);
	}

	//Get Recipient email , Voucher No and Event Name based on Email
	@GetMapping("/recipient/{semail}" )
	@ResponseBody
	public String getRecipient(@PathVariable( value= "semail") String email) throws IOException {
		return recpServ.getRecipientInfoByEmail(email);
	}

	// Validate if Voucher No is expired
	@GetMapping("/validation" )
	@ResponseBody
	public String validateVoucher(@RequestParam( value= "voucherNo") String code) throws IOException {
		LocalDate currentUtilDate = LocalDate.now();

		
		boolean expired = vouServ.checkVouchers(code, currentUtilDate);

		if(!expired) {
			return "Voucher is still valid";

		}else {
			return "Voucher is expired";
		}
	}

	//validate if voucher has been used
	@GetMapping("/usage" )
	@ResponseBody
	public String validateVoucherUsage(@RequestParam( value= "voucherNo") String code) throws IOException {

		Integer usage = vouServ.checkUsedVouchers(code);

		if(usage == 0) {
			return "Voucher is still available";

		}else {
			return "Voucher has been used";
		}
	}

	//Create new offer , voucher no , recipient
	@PostMapping(value= "/offers/create")
	public ResponseEntity save(@RequestParam("event")String event,@RequestParam("discount")Integer discount,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("expires") LocalDate expiryDate,
			@RequestParam("name1")String name1, @RequestParam("email1")String email1, @RequestParam("name2")String name2, @RequestParam("email2")String email2) throws ResourceNotFoundException {

		List<Vouchers> voucherArr = new ArrayList<>();
		boolean exist = false;
		Random random = new Random();
		Vouchers vc = new Vouchers();
		Offers off = new Offers();
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 8;

		try {

			Map recpMap = new HashMap();			
			recpMap.put(name1, email1);
			recpMap.put(name2, email2);

			//If Offer is already available use existing event to add more vouchers
			if(offServ.findByEvent(event) != null) {
				off = offServ.findByEvent(event);
				exist = true;
			}else {

				off.setEvent(event);
				off.setDiscount(discount);
				off.setExpiryDate(expiryDate);
			}

			Iterator iterator = recpMap.entrySet().iterator();
			while (iterator.hasNext()) {

				Recipients rep = new Recipients();
				vc = new Vouchers();

				Map.Entry repEnt = (Map.Entry) iterator.next();

				String em = (String) repEnt.getValue();
				String name = (String) repEnt.getKey();

				String emailCheck = recpServ.findEmail(em);

				//Check for if email existed
				if(emailCheck != null && emailCheck.length() >0) {
					throw new ResourceNotFoundException("Email already existed : "+em+"");
				}

				//Creating Random Voucher Number 
				String voucherNo = random.ints(leftLimit, rightLimit + 1)
						.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
						.limit(targetStringLength)
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						.toString();

				rep.setName(name);
				rep.setEmail(em);

				vc.setCode(voucherNo);
				vc.setIsUsed(0);
				vc.setOffers(off);
				vc.setRecp(rep);

				voucherArr.add(vc);

			}

			if(!exist)
				off.setVoucher(voucherArr);

		}catch(Exception ex) {
			throw new ResourceNotFoundException(ex.getMessage());

		}

		if(!exist) {
			return ResponseEntity.ok(offServ.saveOffers(off));
		}else {
			return ResponseEntity.ok(vouServ.saveAllVoucher(voucherArr));

		}
	}

	//This is for the purpose of redeeming vouchers
	@PostMapping("/redeem")
	public String updateVouchers(@RequestParam("voucherNo")String voucherNo) throws ResourceNotFoundException{
		LocalDate currentUtilDate = LocalDate.now();
		Vouchers vou = new Vouchers();
		try {

			//Validating vouchers
			vou = vouServ.getVoucher(voucherNo);			
			if(vou.equals(null)) {
				return "Voucher "+voucherNo+" does no exist.";
			}

			//Validating if vouchers are expired
			boolean expired = vouServ.checkVouchers(voucherNo, currentUtilDate);			
			if(expired) {
				return "Voucher "+voucherNo+" is already expired.";
			}

			//Validating if vouchers is used
			Integer usage = vouServ.checkUsedVouchers(voucherNo);			
			if(usage != 0) {
				return "Voucher "+voucherNo+" has already been used.";
			}


			vou.setIsUsed(1);
			vou.setDateUsed(currentUtilDate);

			vouServ.saveVoucher(vou);
		}catch(Exception ex) {
			throw new ResourceNotFoundException(ex.getMessage());
		}

		return "Discount of " +vou.getOffers().getDiscount()+ " for voucher " +voucherNo+ " has just been used. ";
	}

		
}
