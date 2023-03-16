package kr.co.hellopet.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hellopet.dao.CommunityDAO;
import kr.co.hellopet.vo.CommunityVO;

/*
 * 날짜 : 2023/03/09
 * 이름 : 김경준
 * 설명 : HelloPet 커뮤니티 페이지 기능구현
 */

@Service
public class CommunityService {

	@Autowired
	private CommunityDAO dao;
	
	// tip 글쓰기
	public int insertTipArticle(CommunityVO vo) {
		int result = 0;
		
		
		// DB에 저장할 이미지 경로 지정
		String names = imgUpload(vo);
		
		// 이미지 첨부x
		if(names == null) {
			vo.setImg1(null);
		// 이미지 첨부o
		}else {
			vo.setImg1("/HelloPet/file/"+names);
		}
		
		
		result = dao.insertTipArticle(vo);
		
		return result;
	}
	
	
	// 가상 업로드 경로
    private String uploadPath = "file/";
	
	public String imgUpload(CommunityVO vo) {
		// 첨부파일
		MultipartFile imgs = vo.getFileImg1();
        String names = null;

        	
        			if(!imgs.isEmpty()) {
        			
        			// 시스템 경로
        			String path = new File(uploadPath).getAbsolutePath();
                    
                    // 새 파일명 생성(중복방지)
                	String oName = imgs.getOriginalFilename();
                	int idx = oName.lastIndexOf(".");
                    String ext = oName.substring(idx); // 확장자
                    String nName = UUID.randomUUID().toString()+ext;
                    
                    // 파일저장
                    try {
                    	imgs.transferTo(new File(path, nName));
                    	names = nName;
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                    	e.printStackTrace();
        			}
    			}
            
        
        
        return names;
    }
	
	// tip 글목록
	public List<CommunityVO> selectTipArticles(int start){
		
		return dao.selectTipArticles(start);
	}
	
	// tip 글목록 갯수
	public int selectTipCount(){
		return dao.selectTipCount();
	}
	
	// tip 글보기
	public CommunityVO selectTipView(int no) {
		
		//조회수+1
		dao.updateHit(no);
		return dao.selectTipView(no);
	}
	
	// tip 글수정
		public CommunityVO selectTipModify(int no) {
			
			
			return dao.selectTipModify(no);
		}
	
	// tip 글수정 폼
	public int updateTipArticle(CommunityVO vo) {
		return dao.updateTipArticle(vo);
	}
	
	// tip 글 삭제
	public int deleteArticle(int no) {
		return dao.deleteArticle(no);
	}
	
	// talk 글쓰기
	public int insertTalkArticle(CommunityVO vo) {
		int result = 0;
		
		
		// DB에 저장할 이미지 경로 지정
		List<String> names = imgsUpload(vo);
		
		vo.setImg1("/HelloPet/file/"+names.get(0));
		vo.setImg2("/HelloPet/file/"+names.get(1));
		vo.setImg3("/HelloPet/file/"+names.get(2));
		
		
		
		
		
		
		
		
			result = dao.insertTalkArticle(vo);
		
		return result;
	}
	
	
	// 가상 업로드 경로
    private String uploadsPath = "file/";
	
	public List<String> imgsUpload(CommunityVO vo) {
		// 첨부파일
		MultipartFile[] imgs = {vo.getFileImg1(), vo.getFileImg2(), vo.getFileImg3()};
        List<String> names = new ArrayList<>();

        	for(MultipartFile img : imgs) {
        		if(!img.isEmpty()) {
        			
        			// 시스템 경로
        			String path = new File(uploadPath).getAbsolutePath();
                    
                    // 새 파일명 생성(중복방지)
                	String oName = img.getOriginalFilename();
                	int idx = oName.lastIndexOf(".");
                    String ext = oName.substring(idx); // 확장자
                    String nName = UUID.randomUUID().toString()+ext;
                    
                    // 파일저장
                    try {
                    	img.transferTo(new File(path, nName));
                    	names.add(nName);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                    	e.printStackTrace();
        			}
        		}
            }
        
        
        return names;
    }
	
	// talk 골목록
	public List<CommunityVO> selectTalkArticles(int start, String cate, String sort){
		return dao.selectTalkArticles(start, cate, sort);
	}
	
	// talk 글 좋아요순 3위까지 목록
	public List<CommunityVO> selectTalkRanks(String cate){
		return dao.selectTalkRanks(cate);
	}
	
	// talk 글목록 갯수
	public int selectTalkCount(String cate) {
		return dao.selectTalkCount(cate);
	}
	
	// talk 글보기
	public CommunityVO selectTalkArticle(int no) {
		return dao.selectTalkArticle(no);
	}
	
	// talk 글수정
	public int updateTalkArticle(CommunityVO vo) {
		return dao.updateTalkArticle(vo);
	}
	
	
	
	
	// 페이징(tip)
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
	
	// 페이징(talk)
		public int getLastPageNum2(int total) {
			
			int lastPageNum = 0;
			
			if(total % 12 == 0){
				lastPageNum = total / 12;
			}else{
				lastPageNum = total / 12 + 1;
			}
			
			return lastPageNum;
		}
		
		public int[] getPageGroupNum2(int currentPage, int lastPageNum) {
			int currentPageGroup = (int)Math.ceil(currentPage / 12.0);
			int pageGroupStart = (currentPageGroup - 1) * 12 + 1;
			int pageGroupEnd = currentPageGroup * 12;
			
			if(pageGroupEnd > lastPageNum){
				pageGroupEnd = lastPageNum;
			}
			
			int[] result = {pageGroupStart, pageGroupEnd};
			
			return result;
		}
		
		public int getPageStartNum2(int total, int currentPage) {
			int start = (currentPage - 1) * 12;
			return total - start;
		}
		
		public int getCurrentPage2(String pg) {
			int currentPage = 1;
			
			if(pg != null){
				currentPage = Integer.parseInt(pg);	
			}
			
			return currentPage;
		}
		
		public int getStartNum2(int currentPage) {
			return (currentPage - 1) * 12;
		}
		
	
	
	}

