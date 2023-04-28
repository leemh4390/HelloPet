package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.MessageDAO;
import kr.co.hellopet.vo.MessageVO;

@Service
public class MessageService {

	@Autowired
	private MessageDAO dao;
	
	public List<MessageVO> selectMsgs(int start, String uid){
		return dao.selectMsgs(start, uid);
	}
	public int selectCountTotal(String uid) {
		return dao.selectCountTotal(uid);
	}
	
	public MessageVO selectMsg(String msgNo) {
		return dao.selectMsg(msgNo);
	}
	
	public int updateMsg(String msgNo) {
		return dao.updateMsg(msgNo);
	}
	public int selectMsgTotal(String uid) {
		return dao.selectMsgTotal(uid);
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
