package kr.co.hellopet.controller.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hellopet.service.CommunityService;
import kr.co.hellopet.vo.Api_HospitalVO;
import kr.co.hellopet.vo.CommunityVO;

/*
 * 날짜 : 2023/03/09
 * 이름 : 김경준
 * 설명 : HelloPet 커뮤니티 페이지 기능구현
 */

@Controller
public class CommunityController {
	
	@Autowired
	private CommunityService service;
	
	// tip 목록
	@GetMapping("community/tip/list")
	public String tipList(String pg, Model model) {
		
		//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		total = service.selectTipCount();
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
    			
		//전체 목록 가져오기
		List<CommunityVO> articles = service.selectTipArticles(start);
		
		model.addAttribute("articles", articles);
		
		return "community/tip/list";
	}
	
	// tip 글보기
	@GetMapping("community/tip/view")
	public String tipView(int no, Model model) {
		
		CommunityVO article = service.selectTipView(no);
		
		

		
		model.addAttribute("article", article);
		
		return "community/tip/view";
	}
	
	// tip 글쓰기
	@GetMapping("community/tip/write")
	public String tipWrite() {
		
		
		return "community/tip/write";
	}
	
	// tip 글쓰기 폼
	@PostMapping("community/tip/write")
	public String tipWrite(CommunityVO vo, HttpServletRequest req) {
		
		
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertTipArticle(vo);
		
		return "redirect:/community/tip/list";
	}
	
	// tip 글수정
	@GetMapping("community/tip/modify")
	public String tipModify(Model model, CommunityVO vo, int no) {
		
		CommunityVO article = service.selectTipModify(no);
		model.addAttribute("article", article);
		
		return "community/tip/modify";
	}
	
	// tip 글수정 폼
	@PostMapping("community/tip/modify")
	public String tipModify(CommunityVO vo) {
		
		service.updateTipArticle(vo);
		
		
		return "redirect:/community/tip/view?no="+vo.getNo();
	}
	
	// tip 글삭제
	@GetMapping("community/tip/delete")
	public String tipDelete(int no, String group){
		service.deleteArticle(no);
		
		return "redirect:/community/tip/list?group="+group;
	}
	
	// talktalk 목록
		@GetMapping("community/talktalk/list")
		public String talkList(String pg, Model model, String cate, String sort) {
			
			//페이징 
	    	int currentPage = service.getCurrentPage2(pg); // 현재 페이지 번호
			int total = 0;
			
			total = service.selectTalkCount(cate);
			
			int lastPageNum = service.getLastPageNum2(total);// 마지막 페이지 번호
			int[] result = service.getPageGroupNum2(currentPage, lastPageNum); // 페이지 그룹번호
			int pageStartNum = service.getPageStartNum2(total, currentPage); // 페이지 시작번호
			int start = service.getStartNum2(currentPage); // 시작 인덱스
			
			model.addAttribute("lastPageNum", lastPageNum);		
			model.addAttribute("currentPage", currentPage);		
			model.addAttribute("pageGroupStart", result[0]);
			model.addAttribute("pageGroupEnd", result[1]);
			model.addAttribute("pageStartNum", pageStartNum+1);
	    			
			//전체 목록 가져오기
			List<CommunityVO> articles = service.selectTalkArticles(start, cate, sort);
			//좋아요 랭킹 3위 목록 가져오기
			List<CommunityVO> ranks = service.selectTalkRanks(cate);
			
			
			
			
			
			model.addAttribute("articles", articles);
			model.addAttribute("ranks",ranks);
			model.addAttribute("sort", sort);
			model.addAttribute("cate", cate);
			
			return "community/talktalk/list";
		}
	
	// talktalk 모달 뷰
		@ResponseBody
		@GetMapping("community/talktalk/view")
		public Map<String, CommunityVO> talkView(Model model, @RequestParam("no") int no) {
			
			//글 가져오기
			CommunityVO article = service.selectTalkArticle(no);
			
			Map<String, CommunityVO> map = new HashMap<>();
			
			map.put("result", article);
			return map;
		}
		
	// talktalk 글쓰기
	@GetMapping("community/talktalk/write")
	public String talkWrite() {
		return "community/talktalk/write";
	}
	
	// talktalk 글쓰기 폼
	@PostMapping("community/talktalk/write")
	public String talkWrite(CommunityVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertTalkArticle(vo);
		
		return "redirect:/community/talktalk/list";
	}
	
	// talktalk 글수정
	@GetMapping("community/talktalk/modify")
	public String talkModify(Model model, CommunityVO vo, int no) {
		
		CommunityVO article = service.selectTalkArticle(no);
		model.addAttribute("article", article);
		
		return "community/talktalk/modify";
	}
	
	// talktalk 글수정 폼
	@PostMapping("community/talktalk/modify")
	public String talkModify(CommunityVO vo,String pg, String cate, String sort) {
		
		
		service.updateTalkArticle(vo);
		return "redirect:/community/talktalk/list";
	}
	
	
	
}
