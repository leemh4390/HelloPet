package kr.co.hellopet.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberCouponVO {
	private int no;
	private int cpNo;
	private String uid;
	private String rdate;
	private int status;
	
	private String cpName;
	private int disprice;
	private int discount;
	private String require;
	private String vaild;
	
	
}
