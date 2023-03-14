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
@Table(name= "pet_hospital_pharmacy")
public class MedicalEntity {
	
	private Integer medNo;
	private Integer pharNo;
	
	@Id
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
	private String business;
	private String reserve;
	private int reserveOk;
	private int hit;
	private String wdate;
	private String rdate;
	private String regip;

}
