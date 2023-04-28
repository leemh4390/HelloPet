package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.AdminProductVO;
import kr.co.hellopet.vo.MedicalVO;

/* 
 *  날짜 : 2023/03/21
 *  이름 : 김경준
 *  설명 : HelloPet admin/product 페이지 기능구현
 */
@Mapper
@Repository
public interface AdminProductDAO {
	
	/* 현재 로그인한 관리자 가져오기*/
	public MedicalVO selectAdmin(String uid);
	
	
	// 관리자 상품 등록하기
	public int insertAdminProduct(AdminProductVO vo);
	
	// 관리자 상품 목록
	public List<AdminProductVO> selectAdminProducts(int start);
	
	// 관리자 상품 목록갯수
	public int selectProductCount();
		
	// 체크목록 삭제
	public int deleteCheck(String prodNo);
	
	
	// 관리자 상품 수정
	public AdminProductVO selectAdminProduct(int prodNo);
	public int updateAdminProduct(AdminProductVO vo);
	
	public int selectMsg(String uid);
	
}
