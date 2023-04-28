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
public class AdminProductVO {
	private int prodNo;
	private int prodCate1;
	private int prodCate2;
	private String group;
	private String prodName;
	private String descript;
	private String medicalName;
	private int price;
	private int discount;
	private String thumb1;
	private String detail;
	private int hit;
	private String regip;
	private String rdate;
	
	
	private int medNo;
	
	// 파일 업로드
	private MultipartFile fileImg1;
	private MultipartFile fileImg2;
	
	
}
