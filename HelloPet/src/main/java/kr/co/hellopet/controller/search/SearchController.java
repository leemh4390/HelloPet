package kr.co.hellopet.controller.search;
/*
 * 날짜 : 2023/03/11 ~
 * 내용 : searchController 작성
 * 이름 : 장인화
 * */
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hellopet.service.SearchService;
import kr.co.hellopet.vo.Api_HospitalVO;
import kr.co.hellopet.vo.ICouponVO;
import kr.co.hellopet.vo.IMember_couponVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.MessageVO;
import kr.co.hellopet.vo.ProductVO;
import kr.co.hellopet.vo.ReserveVO;
import kr.co.hellopet.vo.SearchVO;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService service;
	
	
	@GetMapping(value = {"search/", "search/index"})
	public String index(Model model, Principal principal) {
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "search/index";
	}
	
	@GetMapping("search/reserve")
	public String reserve(Model model, String hosNo, String prodNo, Principal principal) {
		SearchVO hs = service.selectViewHs(hosNo);
		model.addAttribute("hs", hs);
		
		MedicalVO md = service.selectHospital(hosNo);
		model.addAttribute("md", md);
		
		String uid = principal.getName();
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		List<IMember_couponVO> mc = service.selectMemberCoupon(uid);
		model.addAttribute("mc", mc);
		
		List<ICouponVO> cps = service.selectCoupon(uid, hosNo);
		List<ICouponVO> filteredCps = new ArrayList<>();
		for (ICouponVO cp : cps) {
		    if (cp.getMedNo() == 0 || cp.getMedNo() == Integer.parseInt(hosNo)) {
		        filteredCps.add(cp);
		    }
		}
		model.addAttribute("cps", filteredCps);
		
	
		ProductVO product = service.selectProductOne(prodNo);
		model.addAttribute("product", product);
		model.addAttribute("prodNo", prodNo);
		
		return "search/reserve";
	}
	
	@PostMapping("search/reserve")
	public String reserve(Model model, ReserveVO vo, MessageVO msg, String medicalUid, String medicalName) {
		
		int result = service.insertReserve(vo);
		String uid = vo.getUid();
		int medNo = vo.getMedNo();
		
		if(result > 0) {
			msg.setUid(medicalUid);
			msg.setMedical(medicalName);
			msg.setTitle("새로운 예약이 도착했습니다.");
			msg.setContent("내 병원관리 > 예약내역을 확인해주세요. <a href='/HelloPet/admin/confirm/list?medNo="+medNo+"'>예약내역 바로가기</a>");
			service.insertMsg(msg);
		}
		return "redirect:/search/complete?uid="+ uid;
		
	}
	
	@GetMapping("search/complete")
	public String complete(Model model,String uid) {
		
		ReserveVO rv = service.selectComplete(uid);
		model.addAttribute("rv",rv);
		
		model.addAttribute(uid);
		
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		return "search/complete";
	}
	
	@GetMapping("search/view2")
	public String view2(Model model, String uid) {
		
		MemberVO m = service.selectView2(uid);
		model.addAttribute("m", m);
		
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		return "search/view2";
	}
	
	
	
	@GetMapping("search/view")
	public String view(Model model, String hosNo, String pharNo, boolean isMds, Principal principal) {
		
		
		service.updatePhHit(hosNo);
		
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		if (hosNo != null) {
			SearchVO a = service.selectViewHs(hosNo);
			model.addAttribute("a", a);
			model.addAttribute("isMds", isMds);
			
			// 회원 
			List<MedicalVO> mds = service.searchHsJoin();	
			model.addAttribute("mds",mds);
			
			} else if (pharNo != null) {
			SearchVO b = service.selectViewPh(pharNo);
			model.addAttribute("b", b);
			
			}
			return "search/view";
		
	}
	
	
	@GetMapping("search/SearchHs")
	public String SearchHs(Model model, HttpSession sess, String search, Principal principal) {
	
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		return "search/SearchHs";
	}
	
	@ResponseBody
	@PostMapping("search/SearchHs")
	public Map<String, Object> SearchHs(@RequestParam("search") String search, 
										@RequestParam("searchType") String searchType, 
										String pg,
										HttpSession sess) throws IOException {
		
		int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);
		
		Map<String, Object> map = new HashMap<>();
		
		// 회원 
		List<MedicalVO> mds = service.searchHsJoin();
		map.put("mds", mds);
		
		
		 List<SearchVO> hss = null; // hss 변수 선언

	    if ("name".equals(searchType)) {
	        hss = service.SearchHsName(search, start);
	        map.put("hss", hss);
	    } else if ("addr".equals(searchType)) {
	        hss = service.SearchHsAddr(search, start);
	        map.put("hss", hss);
	    } else {
	        hss = service.SearchHs(search, start);
	        map.put("hss", hss);
	        
	    }
	    
	    
	    if (hss != null && !hss.isEmpty()) { // hss 변수가 존재할 경우에 세션에 추가
	        sess.setAttribute("hss", hss);
	    }
	    
	    int result = 0;
	    
	    if ("name".equals(searchType)) {
	    	result = service.selectSearchHsTotalName(search);
	    } else if ("addr".equals(searchType)) {
	    	result = service.selectSearchHsTotalAddr(search);
	    } else {
	    	result = service.selectSearchHsTotal(search);
	    }	
	        
	    
		// 페이징 처리

        int total = result;
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int[] groups = service.getPageGroup(currentPage, lastPageNum);

        map.put("groups", groups);
        map.put("currentPage", currentPage);
        map.put("start", start);
        map.put("total", total);
        map.put("lastPageNum", lastPageNum);
        map.put("pageStartNum", pageStartNum);
        
		return map;
	}
	
	
	@GetMapping("search/SearchPh")
	public String SearchPh(Model model, Principal principal) {
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "search/SearchPh";
	}
	
	
	@ResponseBody
	@PostMapping("search/SearchPh")
	public Map<String, Object> SearchPh(@RequestParam("search") String search, 
										@RequestParam("searchType") String searchType, 
										String pg,
										HttpSession sess) throws IOException {
		
		int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);
		
		Map<String, Object> map = new HashMap<>();
		
		 List<SearchVO> phs = null; // phs 변수 선언

	    if ("name".equals(searchType)) {
	    	phs = service.SearchPhName(search, start);
	        map.put("phs", phs);
	    } else if ("addr".equals(searchType)) {
	    	phs = service.SearchPhAddr(search, start);
	        map.put("phs", phs);
	    } else {
	    	phs = service.SearchPh(search, start);
	        map.put("phs", phs);
	        
	        
	    }

	    if (phs != null && !phs.isEmpty()) { // phs 변수가 존재할 경우에 세션에 추가
	        sess.setAttribute("hss", phs);
	    }
	    
	    int result = 0;
	    
	    if ("name".equals(searchType)) {
	    	result = service.selectSearchPhTotalName(search);
	    } else if ("addr".equals(searchType)) {
	    	result = service.selectSearchPhTotalAddr(search);
	    } else {
	    	result = service.selectSearchPhTotal(search);
	    }	
	        
	    
		// 페이징 처리

        int total = result;
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int[] groups = service.getPageGroup(currentPage, lastPageNum);

        map.put("groups", groups);
        map.put("currentPage", currentPage);
        map.put("start", start);
        map.put("total", total);
        map.put("lastPageNum", lastPageNum);
        map.put("pageStartNum", pageStartNum);
        
		return map;
	}

	
	
}
