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
	
	private String hosName;
	private String hosAddr;
	private String x;
	private String y;
	private String tel;
	
	private String search;
	private int total;
}
