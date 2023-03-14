package kr.co.hellopet.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.MemberDAO;
import kr.co.hellopet.vo.Api_HospitalVO;
import kr.co.hellopet.vo.Api_PharmacyVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;

/*
 * 날짜 : 2023/03/08
 * 담당 : 이민혁
 * 내용 : member 기능구현
 * 
 */

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void insertMember(MemberVO vo) {
		
		vo.setPass(passwordEncoder.encode(vo.getPass2()));
		dao.insertMember(vo);
	};
	
	public void insertMedical(MedicalVO vo) {
		
		vo.setPass(passwordEncoder.encode(vo.getPass2()));
		dao.insertMedical(vo);
	};
	
	public MemberVO selectMember(String uid) {
		return dao.selectMember(uid);
	};
	
	public List<MemberVO> selectMembers() {
		return dao.selectMembers();
	};
	
	public void updateMember(MemberVO vo) {
		dao.updateMember(vo);
	};
	
	public void deleteMember() {
		dao.deleteMember(null);
	};
	
	public List<Api_HospitalVO> selectMedical(String trial, String county, String name){
		return dao.selectMedical(trial, county, name);
	}
	
	public List<Api_PharmacyVO> selectPharmacy(String trial, String county, String name){
		return dao.selectPharmacy(trial, county, name);
	}
	
	public int countUid(String uid) {
		int owner = dao.countUid(uid);
		return owner;
	}
	
	public int countMedicalUid(String uid) {
		int medical = dao.countMedicalUid(uid);
		return medical;
	}
	
	public int countHp(String hp) {
		int result = dao.countHp(hp);
		return result;
	}
	
	public int countMedicalHp(String hp) {
		int result = dao.countMedicalHp(hp);
		return result;
	}
	
	public int countEmail(String email) {
		int result = dao.countEmail(email);
		return result;
	}
	
	public int countMedicalEmail(String email) {
		int result = dao.countMedicalEmail(email);
		return result;
	}
	
	public int countNick(String nick) {
		int result = dao.countNick(nick);
		return result;
	}
	
	public MemberVO selectFindId(String name, String hp) {
		return dao.selectFindId(name, hp);
	}
	
	public MedicalVO selectFindMedicalId(String name, String hp) {
		return dao.selectFindMedicalId(name, hp);
	}
	
	public int selectCountMemberForChangePass(String email, String name, String hp) {
		return dao.selectCountMemberForChangePass(email, name, hp);
	}
	
	public int selectCountMedicalForChangePass(String email, String name, String hp) {
		return dao.selectCountMedicalForChangePass(email, name, hp);
	}
	
	public String makeRandomPass() {
		Random random = new Random();
		String code = String.valueOf(random.nextInt(88888)  + 11111);
		return code;
	}
	
	public void updatePetOwnerPasswordByCodeAndInfo(String code, String email, String name, String hp) {
		String pass = passwordEncoder.encode(code);
		dao.updatePetOwnerPasswordByCodeAndInfo(pass, email, name, hp);
	}
	
	public void updateMedicalPasswordByCodeAndInfo(String code, String email, String name, String hp) {
		String pass = passwordEncoder.encode(code);
		dao.updatePetOwnerPasswordByCodeAndInfo(pass, email, name, hp);
	}
}