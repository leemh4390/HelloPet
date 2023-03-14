package kr.co.hellopet.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name= "pet_owner")
public class MemberEntity {
	@Id
	private String uid;
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

}
