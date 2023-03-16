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
public class CommunityVO {
	private int no;
	private String uid;
	private int parent;
	private String group;
	private String cate;
	private String title;
	private String content;
	private String img1;
	private String img2;
	private String img3;
	private int hit;
	private int heart;
	private int reply;
	private String regip;
	private String rdate;
	
	// 파일 업로드
	private MultipartFile fileImg1;
	private MultipartFile fileImg2;
	private MultipartFile fileImg3;
	
	// 닉네임 join
	private String nick;
}
