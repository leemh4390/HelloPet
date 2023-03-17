package kr.co.hellopet.controller.my;

/*
 * 날짜 : 2023/03/17 
 * 내용 : HelloPet/my 페이지 화면 및 기능구현
 * 담당 : 이민혁
 * 
 */

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hellopet.config.MyUserDetails;
import kr.co.hellopet.service.MyService;
import kr.co.hellopet.vo.CommunityVO;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.ReserveVO;

@Controller
public class MyController {
	
	@Autowired
	private MyService service;

	@GetMapping("my/info")
	public String info(Authentication authentication, Model model) {
		
		MyUserDetails userDetails = null;
		
		// authentication 으로 사용자 객체 가져오기
		String uid = authentication.getName();
		
		// 사용자 객체 유무 확인...
		if(authentication != null) {
			
			// 사용자 객체 조회 후 vo 에 담아서 화면 구현..
			MemberVO  vo = service.selectUser(uid);
			
			model.addAttribute("member", vo);
		}
		
		return "my/info";
	}
	
	@PostMapping("my/info")
	public String info(String name, String email, String nick, String hp, String uid) {
		
		// info update
		service.updateInfoModify(name, email, nick, hp, uid);
			
		return "redirect:/my/info";
	}

	@GetMapping("my/myArticle")
	public String myArticle(Authentication authentication, Model model, String pg) {
		
		String uid = authentication.getName();
		
		// 현재 페이지
		int currentPage = service.getCurrentPage(pg);
		
		// 시작
		int start = service.getLimitStart(currentPage);
		
		// total
		int total = service.selectCountMyArticle(uid);
		
		// 마지막 페이지 번호
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이시 시작번호
		int pageStartNum = service.getPageStartNum(total, start);
		
		// group 값
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		// article list 정보
		List<CommunityVO> articles = service.selectMyArticle(uid, start);
		
		//System.out.println("currentPage : " + currentPage);
		//System.out.println("lastPageNum : " + lastPageNum);
		//System.out.println("pageStartNum : " + pageStartNum);
		//System.out.println("groups[0] : " + groups[0] + " groups[1] : " + groups[1]);
		
		// model 에 담기
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		model.addAttribute("total",total);
		
		return "my/myArticle";
	}
	
	@GetMapping("my/myQna")
	public String myQna(Model model, Authentication authentication, String pg) {
		
		String uid = authentication.getName();
		
		// 현재 페이지
		int currentPage = service.getCurrentPage(pg);
		
		// 시작
		int start = service.getLimitStart(currentPage);
		
		// total
		int total = service.selectCountMyQna(uid);
		
		// 마지막 페이지 번호
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이시 시작번호
		int pageStartNum = service.getPageStartNum(total, start);
		
		// group 값
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		// article list 정보
		List<CsVO> articles = service.selectMyQna(uid, start);
		
		//System.out.println("currentPage : " + currentPage);
		//System.out.println("lastPageNum : " + lastPageNum);
		//System.out.println("pageStartNum : " + pageStartNum);
		//System.out.println("groups[0] : " + groups[0] + " groups[1] : " + groups[1]);
		
		// model 에 담기
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		model.addAttribute("total",total);
		
		return "my/myQna";
	}
	
	@GetMapping("my/myReserve")
	public String myReserve(Authentication authentication, Model model, String pg) {
		
		String uid = authentication.getName();
		
		// 현재 페이지
		int currentPage = service.getCurrentPage(pg);
		
		// 시작
		int start = service.getLimitStart(currentPage);
		
		// total
		int total = service.selectCountMyReserve(uid);
		
		// 마지막 페이지 번호
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이시 시작번호
		int pageStartNum = service.getPageStartNum(total, start);
		
		// group 값
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		// article list 정보
		List<ReserveVO> articles = service.selectMyReserve(uid, start);
		
		//System.out.println("currentPage : " + currentPage);
		//System.out.println("lastPageNum : " + lastPageNum);
		//System.out.println("pageStartNum : " + pageStartNum);
		//System.out.println("groups[0] : " + groups[0] + " groups[1] : " + groups[1]);
		
		// model 에 담기
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		model.addAttribute("total",total);
		
		return "my/myReserve";
	}
	
	@ResponseBody
	@GetMapping("my/myReserveCancel")
	public Map<String, Integer> myReserveCancel(@RequestParam("rev_No") List<Integer> rev_No) {
		
		for(int no : rev_No) {
			service.deleteMyReserve(no);
		}
		
		Map<String, Integer> resultMap = new HashMap<>();
		
		resultMap.put("result", rev_No.size());
		
		return resultMap;
	}
	
	@ResponseBody
	@GetMapping("my/myReserve_List")
	public Map<String, Object> myReserveList(Authentication authentication, Model model) {
		
		String uid = authentication.getName();
		
		List<ReserveVO> article = service.selectMyReserves(uid);
		
		int total = service.selectCountMyReserve(uid);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", article);
		map.put("total", total);
		
		return map;
	}
}
