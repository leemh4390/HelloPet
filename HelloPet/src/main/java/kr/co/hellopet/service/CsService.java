package kr.co.hellopet.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hellopet.dao.CsDAO;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MemberCouponVO;
import kr.co.hellopet.vo.MessageVO;

/* 
 *  날짜 : 2023/03/09
 *  이름 : 김채영
 *  설명 : HelloPet CS 페이지 기능구현
 */

@Service
public class CsService {
	
	@Autowired
	private CsDAO dao;
	
	/* notice */
	public int insertNotice(CsVO vo) {
		int result = 0;
		// 썸네일 이미지 경로 지정
		String newFile = imgUpload(vo);
		
		if(newFile == null) {
	        vo.setImg(null);
	    } else {
	        vo.setImg("/HelloPet/file/" + newFile);
	    }
		
		result = dao.insertNotice(vo);
		return result;
	}
	public CsVO selectNotice(int no) {
		return dao.selectNotice(no);
	}
	public List<CsVO> selectNotices(int start, int pageSize){
		return dao.selectNotices(start, pageSize);
	}
	public int selectCountTotalNotice() {
		return dao.selectCountTotalNotice();
	}
	
	public CsVO getPrev(String rdate) {
		return dao.getPrev(rdate);
	}
	public CsVO getNext( String rdate) {
		return dao.getNext(rdate);
	}
	public int insertCoupon(MemberCouponVO vo) {
		return dao.insertCoupon(vo);
	}
	
	public int countCoupon(String cpNo, String uid) {
		return dao.countCoupon(cpNo, uid);
	}
	
	public int updateDownload(int cpNo) {
		return dao.updateDownload(cpNo);
	}
	
	public int updateCouponOwner(String uid) {
		return dao.updateCouponOwner(uid);
	}
	
	/* qna */
	public int insertQna(CsVO vo) {
		int result = dao.insertQna(vo);
		return result;
	}
	
	public List<CsVO> selectQnas(int start, int pageSize){
		return dao.selectQnas(start, pageSize);
	}
	public int selectCountTotalQna() {
		return dao.selectCountTotalQna();
	}
	
	/* qna - reply*/
	public int insertReply(CsVO vo) {
		int result = dao.insertReply(vo);
		return result;
	}
	public List<CsVO> selectReply(int no){
		return dao.selectReply(no);
	}
	public int deleteReply(int no) {
		return dao.deleteReply(no);
	}
	public int updateReply(int no) {
		return dao.updateReply(no);
	}
	public int updateDelReply(int no) {
		return dao.updateDelReply(no);
	}
	
	/* faq */
	public int insertFaq(CsVO vo) {
		return dao.insertFaq(vo);
	}
	public List<CsVO> selectFaqs(){
		return dao.selectFaqs();
	}
	
	/* 공통 */
	public CsVO selectArticle(int no) {
		return dao.selectArticle(no);
	}
	
	public int updateArticleHit(int no) {
		return dao.updateArticleHit(no);
	}
	public int updateArticle(CsVO vo) {
		return dao.updateArticle(vo);
	}
    public int deleteArticle(int no) {
    	return dao.deleteArticle(no);
    }
    
    /* message */
    public int insertMsg(MessageVO vo) {
    	return dao.insertMsg(vo);
    }
    public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}
	
	// 파일 이미지
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	public String imgUpload(CsVO vo) {
		 MultipartFile img = vo.getFileimg();
		 String newFile = null;

 		if(!img.isEmpty()) {
 			String path = new File(uploadPath).getAbsolutePath();
             
             // 새 파일명 생성
         	 String oName = img.getOriginalFilename();
         	 int idx = oName.lastIndexOf(".");
             String ext = oName.substring(idx); // 확장자
             String nName = UUID.randomUUID().toString()+ext;
             
             try {
             	img.transferTo(new File(path, nName));
             	newFile = nName;
             } catch (IllegalStateException e) {
                 e.printStackTrace();
             } catch (IOException e) {
             	e.printStackTrace();
 			}
 		}
		return newFile;
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
