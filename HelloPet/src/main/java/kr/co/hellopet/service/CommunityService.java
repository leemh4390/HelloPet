package kr.co.hellopet.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
		List<String> names = imgUpload(vo);
		vo.setImg1("/HelloPet/file/"+names.get(0));
		
		
		if(vo.getImg1() != null) {
			result = dao.insertTipArticle(vo);
		}
		return result;
	}
	
	
	// 가상 업로드 경로
    private String uploadPath = "file/";
	
	public List<String> imgUpload(CommunityVO vo) {
		// 첨부파일
		MultipartFile[] imgs = {vo.getFileImg1()};
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
	
	// tip 글목록
	public List<CommunityVO> selectTipArticles(int start){
		return dao.selectTipArticles(start);
	}
	
	// tip 글목록 갯수
	public int selectTipCount(){
		return dao.selectTipCount();
	}
	
	// 페이징
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
	
	}

