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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberCouponVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.ReserveVO;

@Controller
public class MyController {
	
	@Autowired
	private MyService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		return "my/info";
	}
	
	@PostMapping("my/info")
	public String info(String name, String email, String nick, String hp, String uid, String zip, String addr1, String addr2) {
		
		// info update
		service.updateInfoModify(name, email, nick, hp, uid);
			
		return "redirect:/my/info";
	}
	

	@GetMapping("my/myArticle")
	public String myArticle(Authentication authentication, Model model, String pg) {
		
		String uid = authentication.getName();
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
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
		System.out.println("total : " + total);
		System.out.println("groups[0] : " + groups[0] + " groups[1] : " + groups[1]);
		
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
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
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
		System.out.println("groups[0] : " + groups[0] + " groups[1] : " + groups[1]);
		
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
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
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
	@GetMapping("my/withdrawMember")
	public int withdrawMember(@RequestParam("uid") String uid) {
		
		int result = service.deleteWithdrawMember(uid);
		
		System.out.println("uid : " + uid);
		
		System.out.println(result);
		
		return result;
	}
	/*
	@Transactional
	@GetMapping("my/delete")
	public String delete(Principal principal, HttpServletRequest request, HttpServletResponse response) {
		String uid = principal.getName();
		
		service.deleteWithdrawUser(uid);
		
		// 캐시 비우기
	    new SecurityContextLogoutHandler().logout(request, response, null);
		
		return "redirect:/index";
	}
	*/
	
	
	@ResponseBody
	@PostMapping("my/pwChange")
	public Map<String, Integer> pwChange(String uid, String pass) {
		
		if(uid != null && pass != null) {
			System.out.println(uid);
		}
		if(pass != null) {
			System.out.println(pass);
		}
		String encodedPassword = passwordEncoder.encode(pass);
		int result = service.updatePw(uid, encodedPassword);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		System.out.println("result :" +result);
		return map;
	}
	
	@ResponseBody
	@GetMapping("my/myReserve_List")
	public Map<String, Object> myReserveList(Authentication authentication, String pg, Model model) {
		
		String uid = authentication.getName();
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
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
		
		Map<String, Object> map = new HashMap<>();
		map.put("articles", articles);
		map.put("total", total);
		map.put("currentPage", currentPage);
		map.put("lastPageNum", lastPageNum);
		map.put("pageStartNum", pageStartNum);
		map.put("groups", groups);
		
		return map;
	}
	
	@GetMapping("my/coupon")
	public String coupon(Authentication authentication, Model model, String pg) {
		
		String uid = authentication.getName();
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);

		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		int total = service.selectCountMyArticle(uid);
		int lastPageNum = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		// article list 정보
		List<MemberCouponVO> coupons = service.selectMyCoupon(uid, start);
		
		// model 에 담기
		model.addAttribute("coupons", coupons);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		model.addAttribute("total",total);
		
		return "my/coupon";
	}
}
