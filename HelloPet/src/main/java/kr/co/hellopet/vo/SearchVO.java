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
public class SearchVO {
	
	private String hosNo;
	private String hosName;
	private String hosAddr;
	
	private String pharNo;
	private String pharName;
	private String pharAddr;
	
	private String x;
	private String y;
	private String tel;
	private String zip;
	
	private String search;
	private int total;
	private int pg;
	private int currentPage;
}
