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
public class HeartVO {
	private int heart_no;
	private int no;
	private String uid;
	
	
	
	
	// 닉네임 join
	private String nick;
}
