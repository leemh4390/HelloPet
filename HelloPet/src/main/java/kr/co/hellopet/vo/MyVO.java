package kr.co.hellopet.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyVO {

	private String uid;
	private String pass1;
	private String pass2;
	private String pass;
	private String name;
	private String hp;
	private String nick;
	private String email;
	private String type;
	private String level;
	private String zip;
	private String addr1;
	private String addr2;
	private String regip;
	private String wdate;
	private String rdate;
	
	public String getRdate() {
		return rdate.substring(2, 10);
	}
	
	//추가필드
}

