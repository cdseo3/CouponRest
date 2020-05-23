package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Table(name = "Coupon") 
@Builder 
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long mskey;
    
	@Column(nullable = false, unique = true, length = 20) 
	private String couponId;
	
	@Column(nullable = true, unique = true, length = 20)
	private String userId;
	
	@Column(nullable = true, length = 1)
	private String userYn;
	
	@Column( length = 8)
	private String expireDt;
	
	@Column
	private LocalDateTime firstDt;
	
			
	@PrePersist
    public void firstDt() {
        this.firstDt = LocalDateTime.now();
    }
	

}