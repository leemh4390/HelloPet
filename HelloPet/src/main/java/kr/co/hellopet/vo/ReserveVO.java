package kr.co.hellopet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReserveVO {
	
	private int revNo;
	private String uid;
	private int medNo;
	private String oPh;
	private String oName;
	private String petNum;
	private String petType1;
	private String petType2;
	private String petName1;
	private String petName2;
	private int petAge1;
	private String petAge2;
	private String department;
	private String memo;
	private String revDate;
	private String revTime;
	private String rdate;
	private String status;
	
	// 추가필드
	private String medicalName; 
	private String hosName; 
	private String coupon;
	
}
