package kr.co.hellopet.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hellopet.dao.AdminProductDAO;
import kr.co.hellopet.vo.AdminProductVO;
import kr.co.hellopet.vo.AdminReserveVO;
import kr.co.hellopet.vo.CommunityVO;
import kr.co.hellopet.vo.MedicalVO;

/* 
 *  날짜 : 2023/03/21
 *  이름 : 김경준
 *  설명 : HelloPet admin/product 페이지 기능구현
 */
@Service
public class AdminProductService {
	
	@Autowired
	private AdminProductDAO dao;

	/* 현재 로그인한 관리자 가져오기*/
	public MedicalVO selectAdmin(String uid) {
		return dao.selectAdmin(uid);
	}
	
	// 관리자 상품 등록하기
	public int insertAdminProduct(AdminProductVO vo) {
		
		int result = 0;
		
		// DB에 저장할 이미지 경로 지정
		List<String> names = imgsUpload(vo);
		
		vo.setThumb1("/HelloPet/file/"+names.get(0));
		vo.setDetail("/HelloPet/file/"+names.get(1));
		
			result = dao.insertAdminProduct(vo);
		
		return result;
	}
	
	// 관리자 상품 수정하기
	public int updateAdminProduct(AdminProductVO vo) {
		
		return dao.updateAdminProduct(vo);
	}
	public AdminProductVO selectAdminProduct(int prodNo) {
		return dao.selectAdminProduct(prodNo);
	}
	
	// 가상 업로드 경로
    private String uploadsPath = "file/";
	
	public List<String> imgsUpload(AdminProductVO vo) {
		// 첨부파일
		MultipartFile[] imgs = {vo.getFileImg1(), vo.getFileImg2()};
        List<String> names = new ArrayList<>();

        	for(MultipartFile img : imgs) {
        		if(!img.isEmpty()) {
        			
        			// 시스템 경로
        			String path = new File(uploadsPath).getAbsolutePath();
                    
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
	
	
	// 관리자 상품 목록
	public List<AdminProductVO> selectAdminProducts(int start){
		return dao.selectAdminProducts(start);
	}
	
	// 관리자 상품 목록갯수
	public int selectProductCount() {
		return dao.selectProductCount();
	}
	
	// 체크목록 삭제
	public int deleteCheck(String prodNo) {
		return dao.deleteCheck(prodNo);
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

	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}


}
	
	
	

