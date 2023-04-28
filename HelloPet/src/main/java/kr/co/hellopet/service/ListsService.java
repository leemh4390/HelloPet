package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.ListsDAO;
import kr.co.hellopet.vo.ListsVO;
import kr.co.hellopet.vo.MedicalVO;
/* 
 *  날짜 : 2023/03/14
 *  이름 : 김채영
 *  설명 : HelloPet Lists 페이지 기능구현
 */
@Service
public class ListsService {
	
	@Autowired
	private ListsDAO dao;
	
	public List<ListsVO> selectHit(int start){
		return dao.selectHit(start);
	}
	public List<ListsVO> selectNew(int start){
		return dao.selectNew(start);
	}
	public List<ListsVO> selectReserve(int start){
		return dao.selectReserve(start);
	}
	public int selectCountTotal() {
		return dao.selectCountTotal();
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
