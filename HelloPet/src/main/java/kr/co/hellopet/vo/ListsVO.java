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
public class ListsVO {
	
	private int medNo;
	private String uid;
	private String pass;
	private String pass1;
	private String pass2;
	private String name;
	private String hp;
	private String medicalName;
	private String email;
	private String tel;
	private int type;
	private int level;
	private String zip;
	private String addr1;
	private String addr2;
	private String ceo;
	private String ceoHp;
	private int reserveOk;
	private String reserve;
	private int hit;
	private String wdate;
	private String rdate;
	private String regip;
	
	private int hosNo;
	private String hosName;
	private String hosAddr;

}
