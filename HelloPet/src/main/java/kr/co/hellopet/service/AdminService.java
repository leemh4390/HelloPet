package kr.co.hellopet.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.hellopet.dao.AdminDAO;
import kr.co.hellopet.vo.AdminReserveVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.MessageVO;

/* 
 *  날짜 : 2023/03/14
 *  이름 : 김채영
 *  설명 : HelloPet Admin 페이지 기능구현
 */
@Service
public class AdminService {
	
	@Autowired
	private AdminDAO dao;

	/* 현재 로그인한 관리자 가져오기*/
	public MedicalVO selectAdmin(String uid) {
		return dao.selectAdmin(uid);
	}
	public MemberVO selectUser(String uid) {
		return dao.selectUser(uid);
	}
	
	/* 관리자 정보 수정 */
	public int updateAdmin(MedicalVO vo) {
		return dao.updateAdmin(vo);
	}
	public int updateUser(MemberVO mem) {
		return dao.updateUser(mem);
	}
	public int deleteWithdrawOwner(@Param("uid") String uid) {
		return dao.deleteWithdrawOwner(uid);
	}
	public int deleteWithdrawAdmin(@Param("uid") String uid) {
		return dao.deleteWithdrawAdmin(uid);
	}
	public int findPwChange(String uid, String pass) {
		return dao.findPwChange(uid, pass);
	}
	public int findPwChangeUser(String uid, String pass) {
		return dao.findPwChangeUser(uid, pass);
	}
	
	/* 예약내역 */
	public List<AdminReserveVO> selectReserves(int start, String medNo, int pageSize){
		return dao.selectReserves(start, medNo, pageSize);
	}
	public int selectCountTotal(String medNo) {
		return dao.selectCountTotal(medNo);
	}
	public AdminReserveVO selectReserve(Integer revNo) {
		return dao.selectReserve(revNo);
	}
	public int updateCouponOwner(String uid) {
		return dao.updateCouponOwner(uid);
	}
	
	/* 예약 상태 변경 */
	public boolean updateConfirm(int revNo) {
		return dao.updateConfirm(revNo);
	}
	public boolean updateReject(int revNo) {
		return dao.updateReject(revNo);
	}
	public int deleteCoupon(int cpNo, String uid) {
		return dao.deleteCoupon(cpNo, uid);
	}
	
	/* 메시지 */
	public int insertMsg(MessageVO vo) {
		return dao.insertMsg(vo);
	}
	
	public int updateReserve(String medNo) {
		return dao.updateReserve(medNo);
	}
	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}
	
	/////////// 페이징 처리 ////////////
	// 현재 페이지 번호
	public int getCurrentPage(String pg) {
	int currentPage = 1;
	
	if(pg != null) {
	    currentPage = Integer.parseInt(pg);
	}
	return currentPage;
	}
	
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
	return (currentPage - 1) * 10;
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(int total) {
	
	int lastpageNum = 0;
	
	if(total % 10 == 0) {
	    lastpageNum = total / 10;
	
	}else {
	    lastpageNum = total / 10 + 1;
	}
	return lastpageNum;
	}
	
	// 페이지 시작 번호
	public int getpageStartNum(int total, int start) {
	return total - start;
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum) {
	
	int groupCurrent = (int) Math.ceil(currentPage / 10.0);
	int groupStart = (groupCurrent - 1) * 10 + 1;
	int groupEnd = groupCurrent * 10;
	
	if(groupEnd > lastPageNum) {
	    groupEnd = lastPageNum;
	}
	
	int[] groups = {groupStart, groupEnd};
	
	return groups;
	}
	
}
