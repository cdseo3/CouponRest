package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.NullPointerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import com.example.demo.api.MKRandom;
import com.example.demo.entity.Coupon;
import com.example.demo.repo.CouponJpaRepo;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opencsv.CSVReader;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/coupon")
public class CouponController {
	
	
	@Autowired
	CouponJpaRepo couponJpaRepo;
	
	
	//쿠폰 만들고 저장 하는 로직 
	@PostMapping("/makeCoupon")
	public @ResponseBody Map<String,String> createCoupon(@RequestBody Map<String,String> param){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String input = param.get("input");
		
		Map<String,String> response = new HashMap<String, String>();
		
		//입력값 숫자로 변경 
		int inputi  = Integer.parseInt(input);
		
		SimpleDateFormat dFormat = new SimpleDateFormat ( "yyyyMMdd" );
		//오늘 날짜를 기준 
		Calendar cal = Calendar.getInstance (); 
		//3개월후 날짜 구하기  
		cal.add ( Calendar.MONTH, 3 );
				
		int i = 0 ;
		
		while( i < inputi ){
			
			String tempCouponId = MKRandom.executeGenerate();

			if(!couponJpaRepo.existsByCouponId(tempCouponId)){

				System.out.println(i + "==="+ tempCouponId);
				Coupon coupon = Coupon.builder()
		                .couponId(tempCouponId)
		                .expireDt(dFormat.format (cal.getTime()) )
		                .userYn("N")
		                .build();
				couponJpaRepo.save(coupon);
	
				i ++;
		
			}
		}
		
		response.put("count", String.valueOf(i));
		response.put("status", "OK");
		
		return response;
	}
	
	
	@PatchMapping("/allocate")
	public @ResponseBody Map<String,String> allocateCoupon(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
        String userId = authentication.getName();
		
		Map<String,String> response = new HashMap<String, String>();
	
		SimpleDateFormat dFormat = new SimpleDateFormat ( "yyyyMMdd" );
		//오늘 날짜를 기준 
		Calendar cal = Calendar.getInstance (); 
		
		String today = dFormat.format (cal.getTime());
		
		System.out.println("today"+ today);
		
		if(couponJpaRepo.existsByUserId(userId)) {
			response.put("message", "사용자에게 할당된 쿠폰이 존재합니다.");
			response.put("status", "FALSE");
			
			return response; 
		}
		
		
		Coupon getcoupon = couponJpaRepo.findFirstByUserIdIsNullAndExpireDtGreaterThanOrderByMskey(today);
		
		try {
			if(getcoupon.getCouponId() != null) {
				System.out.println(userId + "==="+ getcoupon.getCouponId());
				
				getcoupon.setUserId(userId);
				
				couponJpaRepo.save(getcoupon);
				
				String chCouponId = getcoupon.getCouponId().substring(0, 4)+"-"
								   +getcoupon.getCouponId().substring(4, 8)+"-"
						           +getcoupon.getCouponId().substring(8, 12);
	
				response.put("message", userId+" 고객님의 쿠폰 번호는 " + chCouponId + "입니다.");
				response.put("status", "OK");
			}
		}catch(Exception e) {
			response.put("status", "FALSE");
			return response;
		}
		
		
		return response;
	}
	
	@GetMapping("/list")
	public @ResponseBody List<Coupon> allocateList(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return couponJpaRepo.findByUserIdNotNullOrderByMskey();
	}
	
	
	//쿠폰 사용
	@PatchMapping("/using")
	public @ResponseBody Map<String,String> usingCoupon(@RequestBody Map<String,String> param){
		String couponId = param.get("input");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
		
		Map<String,String> response = new HashMap<String, String>();
	
		SimpleDateFormat dFormat = new SimpleDateFormat ( "yyyyMMdd" );
		//오늘 날짜를 기준 
		Calendar cal = Calendar.getInstance (); 
		
		String today = dFormat.format (cal.getTime());
		
		System.out.println("today"+ today);
		
		Coupon getcoupon = couponJpaRepo.findByCouponIdAndUserIdAndExpireDtGreaterThan(couponId.replaceAll("-", ""),userId,today);
		
		try {
			if(getcoupon.getCouponId() != null) {
				
				getcoupon.setUserYn("Y");
				
				couponJpaRepo.save(getcoupon);
				
				String chCouponId = getcoupon.getCouponId().substring(0, 4)+"-"
								   +getcoupon.getCouponId().substring(4, 8)+"-"
						           +getcoupon.getCouponId().substring(8, 12);
	
				response.put("message", chCouponId+" 사용이 완료되었습니다.");
				response.put("status", "OK");
			}
		}catch(NullPointerException e) {
			response.put("message", "해당 쿠폰번호가 없습니다.");
			response.put("status", "FALSE");
			return response;
		}catch(Exception e) {
			response.put("message", "오류가 발생하였습니다.");
			response.put("status", "FALSE");
			return response;
		}
		
		
		return response;
	}
	
	//쿠폰 사용 취소 
	@PatchMapping("/cancle")
	public @ResponseBody Map<String,String> cancleCoupon(@RequestBody Map<String,String> param){
		String couponId = param.get("input");
				
		Map<String,String> response = new HashMap<String, String>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();
		
		Coupon getcoupon = couponJpaRepo.findByCouponIdAndUserIdAndUserYn(couponId.replaceAll("-", ""),userId,"Y");
		try {
			
			if(getcoupon.getCouponId() != null) {
				
				getcoupon.setUserYn("N");
				
				couponJpaRepo.save(getcoupon);
				
				String chCouponId = getcoupon.getCouponId().substring(0, 4)+"-"
								   +getcoupon.getCouponId().substring(4, 8)+"-"
						           +getcoupon.getCouponId().substring(8, 12);

				response.put("message", chCouponId+" 사용이 취소되었습니다.");
				response.put("status", "OK");
			}
		}catch(NullPointerException e) {
			response.put("message", "취소할 쿠폰번호가 없습니다.");
			response.put("status", "False");
			return response;
    	}catch(Exception e) {
			response.put("message", "오류가 발생하였습니다.");
			response.put("status", "FALSE");
			return response;
		}
		
		return response;
	}
	
	@GetMapping("/expirelist")
	public @ResponseBody List<Coupon> expireList(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		SimpleDateFormat dFormat = new SimpleDateFormat ( "yyyyMMdd" );
		//오늘 날짜를 기준 
		Calendar cal = Calendar.getInstance (); 
		
		String today = dFormat.format (cal.getTime());
		
		System.out.println("today"+ today);
		
		return couponJpaRepo.findByExpireDtOrderByMskey(today);
	}


	
	//파일을 읽어서 쿠폰 생성 
	@PostMapping("/uploadCoupon")
	public @ResponseBody Map<String,String> uploadCoupon(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Map<String,String> response = new HashMap<String, String>();
		
		int i = 0;
		
		try {
			
			 InputStreamReader is = new InputStreamReader(new FileInputStream("uploadTest.csv"), "UTF-8"); 
			 
			 CSVReader reader = new CSVReader(is,',', '"', 1);
			 
	         List<String[]> list = reader.readAll();
	         
	         for(String[] str : list){
            	 if(!couponJpaRepo.existsByCouponId(str[0].trim())){
            		 
            		 System.out.println("str[0]  = " + str[0].trim() + " str[1]= " + str[1].trim());
            		 Coupon coupon = Coupon.builder()
         	                .couponId(str[0].trim())
         	                .expireDt(str[1].trim() )
         	                .userYn("N")
         	                .build();
         			couponJpaRepo.save(coupon);
         			
         			i ++;
            	 }

	         }
		}catch(Exception e) {
			response.put("message", "업로드중 오류가 발생하였습니다.");
			response.put("status", "False");
			
			e.printStackTrace();
			
			return response;
		}
		
		
		response.put("count", String.valueOf(i));
		response.put("status", "OK");
		
		return response;
	}


}
