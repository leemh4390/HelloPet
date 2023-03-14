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
public class CsVO {
	private int id;
	private int no;
	private String uid;
	private int parent;
	private int reply;
	private String group;
	private String title;
	private String content;
	private String img;
	private int hit;
	private String regip;
	private String rdate;
	
	private MultipartFile fileimg;
	private String nick;
}
