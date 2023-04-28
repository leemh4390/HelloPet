package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.MyDAO;
import kr.co.hellopet.vo.CommunityVO;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MemberCouponVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.ReserveVO;

/*
 * 날짜 : 2023/03/17 
 * 내용 : HelloPet My 페이지 기능구현
 * 담당 : 이민혁
 * 
 */

@Service
public class MyService {
	
	@Autowired
	private MyDAO dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 회원 조회
	public MemberVO selectUser(String uid) {
		return dao.selectUser(uid);
	}
	
	// 커뮤니티 게시글 조회
	public List<CommunityVO> selectMyArticle(String uid, int start) {
		return dao.selectMyArticle(uid, start);
	}
	
	// Qna 게시글 조회
	public List<CsVO> selectMyQna(String uid, int start) {
		return dao.selectMyQna(uid, start);
	}
	
	// 예약목록 조회하기
	public List<ReserveVO> selectMyReserve(String uid, int start) {
		return dao.selectMyReserve(uid, start);
	}
	
	public List<ReserveVO> selectMyReserves(String uid){
		return dao.selectMyReserves(uid);
	};
	
	public List<MemberCouponVO> selectMyCoupon(String uid, int start){
		return dao.selectMyCoupon(uid, start);
	}
	// myArticle total 구하기 
	public int selectCountMyArticle(String uid) {
		return dao.selectCountMyArticle(uid);
	}
	
	// myQna total 구하기
	public int selectCountMyQna(String uid) {
		return dao.selectCountMyQna(uid);
	}
	
	public int selectCountMyReserve(String uid) {
		return dao.selectCountMyReserve(uid);
	}
	public int selectCountMyCoupon(String uid) {
		return dao.selectCountMyCoupon(uid);
	}
	
	
	
	// 회원 수정
	public void updateInfoModify(String name, String email, String nick, String hp, String uid) {
		dao.updateInfoModify(name, email, nick, hp, uid);
	}
	
	public int deleteWithdrawMember(String uid) {
		int result = dao.deleteWithdrawMember(uid);
		return result;
	}
	public int updatePw(String uid, String encodedPass) {
	    return dao.updatePw(uid, encodedPass);
	}
	
	// 예약목록 삭제하기
	public int deleteMyReserve(int no) {
		return dao.deleteMyReserve(no);
	}
	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}
	
	// pagin 작업
	
	// 현재 페이지 번호
	public int getCurrentPage(String pg) {
		
		// 시작 페이지는 1
		int currentPage = 1;
		
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		
		return currentPage;
	}
	
	// 페이지 시작 값
	public int getLimitStart(int currentPage) {
		
		return (currentPage - 1) * 7;
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(int total) {
		
		int lastPageNum = 0;
		
		if(total % 7 == 0) {
			// 토탈 갯수가 10 으로 나눠서 나머지가 0이면 페이지 번호는 10 으로 나눈 값
			lastPageNum = total / 7;
		}else {
			// 나머지가 0 이 아니라면 + 1
			lastPageNum = total / 7 + 1;
		}
		return lastPageNum;
	}
	
	public int getPageStartNum(int total, int start) {
		// 게시글은 역순으로 나타내야함
		return total - start;
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum) {
		
		int groupCurrent = (int) Math.ceil(currentPage / 5.0);
		int groupStart = (currentPage - 1) / 5 * 5 + 1;
		int groupEnd = groupStart + 4;
		
		if(groupEnd > lastPageNum) {
			groupEnd = lastPageNum;
		}
		
		int[] groups = {groupStart, groupEnd};
		
		return groups;
	}
}
