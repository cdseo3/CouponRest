package com.example.demo.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Coupon;

@Repository
public interface CouponJpaRepo extends JpaRepository<Coupon, Long> {

	//쿠폰 ID존재 유무 확인
	public Boolean existsByCouponId(String couponId);
	
	//사용자에게 할당된 쿠폰이 있는지 유무
	public Boolean existsByUserId(String userId);
	
	//만기가 안되고 쿠폰 할당이 안된 쿠폰 찾기
	public Coupon findFirstByUserIdIsNullAndExpireDtGreaterThanOrderByMskey(String expireDt);
	
	//사용자에게 할당된 쿠폰 목록 조회
	public List<Coupon> findByUserIdNotNullOrderByMskey();
	
	//쿠폰,사용자,만기일 비교 하여 조회
	public Coupon findByCouponIdAndUserIdAndExpireDtGreaterThan(String CouponId,String UserId,String expireDt);
	
	//사용된 쿠폰 조회
	public Coupon findByCouponIdAndUserIdAndUserYn(String CouponId,String UserId,String userYn);
	
	//오늘 만기된 쿠폰 목록 조회
	public List<Coupon> findByExpireDtOrderByMskey(String today);
	
}
