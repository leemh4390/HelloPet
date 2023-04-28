package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
 * 날짜 : 2023-03-09 ~
 * 이름 : 장인화
 * 내용 : search dao 작성 
 * */

@Mapper
@Repository
public interface SearchDAO {
	
	//searchHs 검색 후 목록 출력하기
	public List<SearchVO> SearchHs(String search, int start);
	public List<SearchVO> SearchHsName(String search, int start);
	public List<SearchVO> SearchHsAddr(String search, int start);
	
	public List<MedicalVO> searchHsJoin();
	
	
	public int selectSearchHsTotal(String search);
	public int selectSearchHsTotalName(String search);
	public int selectSearchHsTotalAddr(String search);
	
	//searchPh 검색 후 목록 출력하기
	public List<SearchVO> SearchPh(String search, int start);
	public List<SearchVO> SearchPhName(String search, int start);
	public List<SearchVO> SearchPhAddr(String search, int start);
	public int selectSearchPhTotal(String search);
	public int selectSearchPhTotalName(String search);
	public int selectSearchPhTotalAddr(String search);

	// hit 올리기
	public int updatePhHit(String medNo);
	
	//view
	public SearchVO selectViewHs(String hosNo);
	public SearchVO selectViewPh(String pharNo);
	public MedicalVO selectHospital(String medNo);
	
	//view2
	public MemberVO selectView2(String uid);
	
	
	//reserve (병원예약하기)
	public ProductVO selectProductOne(String prodNo);
	public int insertReserve(ReserveVO vo);
	
	// 병원예약 시 쿠폰 적용
	public List<IMember_couponVO> selectMemberCoupon(String uid);
	public List<ICouponVO> selectCoupon(String uid, String hosNo);
	
	
	//complete (예약결과 가져오기)
	public ReserveVO selectComplete(String uid);
	
	// 메시지 보내기
	public int insertMsg(MessageVO vo);
	public int selectMsg(String uid);

}
