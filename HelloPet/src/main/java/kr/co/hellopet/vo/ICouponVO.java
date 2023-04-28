package kr.co.hellopet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/*
 * 이름 : 장인화
 * 내용 : select 에서 쓰는 couponVO
 * 날짜 : 2023-03-29
 * */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ICouponVO {
	
	private int cpNo;
	private int medNo;
	private String cpName;
	private int disprice;
	private int discount;
	private String require;
	private int download;
	private String vaild;
	private String rdate;
	
	// 추가 필드 
	private int no;
	private String uid;
	private int status;
	
	

}
