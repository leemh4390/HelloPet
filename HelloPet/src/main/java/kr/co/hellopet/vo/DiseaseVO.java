package kr.co.hellopet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * 날짜 : 2023/03/09
 * 이름 : 임민지
 * 내용 : HelloPet Disease VO 페이지 기능구현 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiseaseVO {
	private int disNo;
	private String group;
	private int cate1;
	private int cate2;
	private String disName;
	private String description;
	private String department;
	
	// 추가필드
	private String c1Name;
	private String c2Name;
}
