package kr.co.hellopet.vo;

import java.util.List;

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
public class DiseaseResultMapVO {
	
	private String c1Name;	
	private List<Disease_cate2VO> cate2VOList;
	
}
