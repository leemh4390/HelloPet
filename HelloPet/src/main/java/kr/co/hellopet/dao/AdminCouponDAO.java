package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.CouponVO;
import kr.co.hellopet.vo.AdminProductVO;
import kr.co.hellopet.vo.MedicalVO;

/* 
 *  날짜 : 2023/03/29
 *  이름 : 김채영
 *  설명 : HelloPet admin/coupon 페이지 기능구현
 */
@Mapper
@Repository
public interface AdminCouponDAO {
	
	/* 현재 로그인한 관리자 가져오기*/
	public MedicalVO selectAdmin(String uid);
	
	
	// 관리자 상품 등록하기
	public int insertAdminCoupon(CouponVO vo);
	
	// 관리자 상품 목록
	public List<CouponVO> selectAdminCoupons(int start);
	
	// 관리자 상품 목록갯수
	public int selectCouponCount();
		
	// 체크목록 삭제
	public int deleteCheck(String cpNo);
	
	
	// 관리자 상품 수정
	public CouponVO selectAdminCoupon(int cpNo);
	public int updateAdminCoupon(CouponVO vo);
	
	public int selectMsg(String uid);
	
}
