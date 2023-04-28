package kr.co.hellopet.vo;

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
public class ProductVO {
	
	private int prodNo;
	private int medNo;
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
	
	
	private String cate1;
	private String cate2;
	private String c1Name;
	private String c2Name;
}
