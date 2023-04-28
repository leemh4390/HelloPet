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
public class MessageVO {
	
	private int msgNo;
	private String uid;
	private String medical;
	private String title;
	private String content;
	private int msgStatus;
	private String msgRdate;
	
	private String medicalName;

}
