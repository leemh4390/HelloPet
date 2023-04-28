package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.SearchDAO;
import kr.co.hellopet.vo.Api_HospitalVO;
import kr.co.hellopet.vo.ICouponVO;
import kr.co.hellopet.vo.IMember_couponVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.MessageVO;
import kr.co.hellopet.vo.ProductVO;
import kr.co.hellopet.vo.ReserveVO;
import kr.co.hellopet.vo.SearchVO;
/*
 * 날짜 : 2023-03-09~
 * 이름 : 장인화
 * 내용 : SearchService 작성
 * */
@Service
public class SearchService {
	
	@Autowired
	private SearchDAO dao;
	
	//SearchHs 검색기능
	public List<SearchVO> SearchHs(String search, int start){
		return dao.SearchHs(search, start);
	}
	
	public List<SearchVO> SearchHsAddr(String search, int start){
		return dao.SearchHsAddr(search, start);
	}
	
	public List<SearchVO> SearchHsName(String search, int start){
		return dao.SearchHsName(search, start);
	}
	
	
	public List<MedicalVO> searchHsJoin(){
		return dao.searchHsJoin();
	}
	
	
	public int selectSearchHsTotal(String search) {
		return dao.selectSearchHsTotal(search);
	}
	
	public int selectSearchHsTotalName(String search) {
		return dao.selectSearchHsTotalName(search);
	}
	
	public int selectSearchHsTotalAddr(String search) {
		return dao.selectSearchHsTotalAddr(search);
	}
	
	
	// hit
	public int updatePhHit(String medNo) {
		return dao.updatePhHit(medNo);
	}
	
	// searchPh 검색기능
	public List<SearchVO> SearchPh(String search, int start){
		return dao.SearchPh(search, start);
	}
	
	public List<SearchVO> SearchPhAddr(String search, int start){
		return dao.SearchPhAddr(search, start);
	}
	
	public List<SearchVO> SearchPhName(String search, int start){
		return dao.SearchPhName(search, start);
	}
	
	public int selectSearchPhTotal(String search) {
		return dao.selectSearchPhTotal(search);
	}
	
	public int selectSearchPhTotalName(String search) {
		return dao.selectSearchPhTotalName(search);
	}
	
	public int selectSearchPhTotalAddr(String search) {
		return dao.selectSearchPhTotalAddr(search);
	}
	
	
	
	
	// view
	public SearchVO selectViewHs(String hosNo){
		return dao.selectViewHs(hosNo);
	}
	
	public SearchVO selectViewPh(String pharNo){
		return dao.selectViewPh(pharNo);
	}
	
	
	//view2
	public MemberVO selectView2(String uid){
		return dao.selectView2(uid);
	}
	
	
	//reserve
	public int insertReserve(ReserveVO vo) {
		return dao.insertReserve(vo);
	}
	public MedicalVO selectHospital(String medNo) {
		return dao.selectHospital(medNo);
	}
	
	public ProductVO selectProductOne(String prodNo) {
		return dao.selectProductOne(prodNo);
	}
	
	// 예약하기 할 때 쿠폰 적용
	public List<IMember_couponVO> selectMemberCoupon(String uid) {
		return dao.selectMemberCoupon(uid);
	}
	
	public List<ICouponVO> selectCoupon(String uid,String hosNo) {
		return dao.selectCoupon(uid,hosNo);
	}
	
	// complete
	public ReserveVO selectComplete(String uid) {
		return dao.selectComplete(uid);
	}
	
	
	// 페이징 처리 시작 ///////////////////////////////////////////////////////
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
		
    // 페이징 처리 끝 ///////////////////////////////////////////////////////
	
	// 메시지
	public int insertMsg(MessageVO vo) {
		return dao.insertMsg(vo);
	}
	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}
}
