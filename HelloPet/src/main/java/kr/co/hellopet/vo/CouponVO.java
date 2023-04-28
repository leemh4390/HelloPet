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
public class CouponVO {
	private int cpNo;
	private String cpName;
	private int disprice;
	private int discount;
	private String require;
	private String vaild;
	private String rdate;
	private int download;
	private int grade; 
	private int medNo;
	
	
	
}
