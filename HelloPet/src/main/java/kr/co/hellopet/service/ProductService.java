package kr.co.hellopet.service;
/*
 * 날짜 : 2023-03-23
 * 이름 : 장인화
 * 내용 : product service
 * 
 * */


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.ProductDAO;
import kr.co.hellopet.vo.Cate1VO;
import kr.co.hellopet.vo.Cate2VO;
import kr.co.hellopet.vo.ICouponVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO dao;
	
	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}
	
	// cate 불러오기
	public List<Cate1VO> Cate1(){
		return dao.Cate1();
	}
	
	public List<Cate2VO> Cate2(){
		return dao.Cate2();
	}
	
	public List<Cate2VO> SelectCate2(String cate1){
		return dao.SelectCate2(cate1);
	}
	
	public int SelectCountTotal(String cate1, String cate2) {
		return dao.SelectCountTotal(cate1, cate2);
	}
	
	// product 불러오기
	
	public List<ProductVO> SelectProduct(String cate1, String cate2, int start){
		return dao.SelectProduct(cate1, cate2, start);
	}
	
	public List<ProductVO> SelectProductDog(String cate1, String cate2,int start){
		return dao.SelectProductDog(cate1, cate2, start);
	}
	public List<ProductVO> SelectProductCat(String cate1, String cate2,int start){
		return dao.SelectProductCat(cate1, cate2, start);
	}
	
	
	public int SelectCountDog(String cate1, String cate2) {
		return dao.SelectCountDog(cate1, cate2);
	}
	
	public int SelectCountCat(String cate1, String cate2) {
		return dao.SelectCountCat(cate1, cate2);
	}
	
	
	// view
	
	public ProductVO SelectProductView(String cate1, String cate2, String prodNo) {
		return dao.SelectProductView(cate1, cate2, prodNo);
	}
	
	public MedicalVO SelectProductMap(String prodNo) {
		return dao.SelectProductMap(prodNo);
	}
	
	public int SelectCountTotalType(String cate1, String cate2, String type) {
		return dao.SelectCountTotalType(cate1, cate2, type);
	}
	
	// hit
	public int updateHit(String prodNo) {
		return dao.updateHit(prodNo);
	}
	
	// 쿠폰할인가
	public ICouponVO selectMaxCoupon(String uid) {
		return dao.selectMaxCoupon(uid);
	}
	
	public ICouponVO selectMedicalCoupon(String uid, String prodNo) {
		return dao.selectMedicalCoupon(uid, prodNo);
	}
	
	/////// list 페이징 처리
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
	  return (currentPage - 1) * 9;
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(int total) {
	
	  int lastpageNum = 0;
	
	  if(total % 9 == 0) {
	      lastpageNum = total / 9;
	
	  }else {
	      lastpageNum = total / 9 + 1;
	  }
	  return lastpageNum;
	}
	
	// 페이지 시작 번호
	public int getpageStartNum(int total, int start) {
	  return total - start;
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum) {
	
	  int groupCurrent = (int) Math.ceil(currentPage / 9.0);
	  int groupStart = (groupCurrent - 1) * 9 + 1;
	  int groupEnd = groupCurrent * 9;
	
	  if(groupEnd > lastPageNum) {
	      groupEnd = lastPageNum;
	  }
	
	  int[] groups = {groupStart, groupEnd};
	
	  return groups;
	}
	
}
