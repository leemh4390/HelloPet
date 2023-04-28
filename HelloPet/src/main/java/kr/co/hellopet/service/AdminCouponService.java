package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.AdminCouponDAO;
import kr.co.hellopet.vo.CouponVO;
import kr.co.hellopet.vo.MedicalVO;

/* 
 *  날짜 : 2023/03/29
 *  이름 : 김채영
 *  설명 : HelloPet admin/Coupon 페이지 기능구현
 */
@Service
public class AdminCouponService {
	
	@Autowired
	private AdminCouponDAO dao;

	/* 현재 로그인한 관리자 가져오기*/
	public MedicalVO selectAdmin(String uid) {
		return dao.selectAdmin(uid);
	}
	
	// 관리자 상품 등록하기
	public int insertAdminCoupon(CouponVO vo) {
		int result = dao.insertAdminCoupon(vo);
		return result;
	}
	
	// 관리자 상품 수정하기
	public int updateAdminCoupon(CouponVO vo) {
		
		return dao.updateAdminCoupon(vo);
	}
	public CouponVO selectAdminCoupon(int cpNo) {
		return dao.selectAdminCoupon(cpNo);
	}
	
	// 관리자 상품 목록
	public List<CouponVO> selectAdminCoupons(int start){
		return dao.selectAdminCoupons(start);
	}
	
	// 관리자 상품 목록갯수
	public int selectCouponCount() {
		return dao.selectCouponCount();
	}
	
	// 체크목록 삭제
	public int deleteCheck(String cpNo) {
		return dao.deleteCheck(cpNo);
	}
	
	
	// 페이징
	public int getLastPageNum(int total) {
		
		int lastPageNum = 0;
		
		if(total % 10 == 0){
			lastPageNum = total / 10;
		}else{
			lastPageNum = total / 10 + 1;
		}
		
		return lastPageNum;
	}
	
	public int[] getPageGroupNum(int currentPage, int lastPageNum) {
		int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
		int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
		int pageGroupEnd = currentPageGroup * 10;
		
		if(pageGroupEnd > lastPageNum){
			pageGroupEnd = lastPageNum;
		}
		
		int[] result = {pageGroupStart, pageGroupEnd};
		
		return result;
	}
	
	public int getPageStartNum(int total, int currentPage) {
		int start = (currentPage - 1) * 10;
		return total - start;
	}
	
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null){
			currentPage = Integer.parseInt(pg);	
		}
		
		return currentPage;
	}
	
	public int getStartNum(int currentPage) {
		return (currentPage - 1) * 10;
	}

	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}


}
	
	
	

