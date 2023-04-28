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
public class IMember_couponVO {
	
	private int no;
	private int cpNo;
	private String uid;
	private String rdate;
	private int status;
}
