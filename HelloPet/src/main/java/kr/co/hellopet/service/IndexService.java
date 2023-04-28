package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.IndexDAO;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.ListsVO;
/* 
 *  날짜 : 2023/03/13
 *  이름 : 김채영
 *  설명 : HelloPet CS 페이지 기능구현
 */
@Service
public class IndexService {
	
	@Autowired
	private IndexDAO dao;
	
	public List<ListsVO> selectHit(){
		return dao.selectHit();
	}
	public List<ListsVO> selectNew(){
		return dao.selectNew();
	}
	public List<ListsVO> selectReserve(){
		return dao.selectReserve();
	}
	
	public List<CsVO> selectNotice(){
		return dao.selectNotice();
	}
	public List<CsVO> selectFaq(){
		return dao.selectFaq();
	}
	
	public int getCurrentPage(String pg) {
	  int currentPage = 1;
	
	  if(pg != null) {
	      currentPage = Integer.parseInt(pg);
	  }
	  return currentPage;
	}
	
	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}

}
