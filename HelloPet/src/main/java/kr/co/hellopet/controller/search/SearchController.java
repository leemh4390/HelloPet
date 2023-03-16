package kr.co.hellopet.controller.search;
/*
 * 날짜 : 2023/03/11 ~
 * 내용 : searchController 작성
 * 이름 : 장인화
 * */
import java.io.IOException;
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
import kr.co.hellopet.vo.ReserveVO;
import kr.co.hellopet.vo.SearchVO;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService service;
	
	
	@GetMapping(value = {"search/", "search/index"})
	public String index() {
		return "search/index";
	}
	
	@GetMapping("search/reserve")
	public String reserve(Model model, String hosNo) {
		
		SearchVO hs = service.selectViewHs(hosNo);
		model.addAttribute("hs", hs);
		
		return "search/reserve";
	}
	
	@PostMapping("search/reserve")
	public String reserve(Model model, ReserveVO vo) {
		
		
		service.insertReserve(vo);
		String uid = vo.getUid();
		return "redirect:/search/complete?uid="+ uid;
		
	}
	
	@GetMapping("search/complete")
	public String complete(Model model,String uid) {
		
		ReserveVO rv = service.selectComplete(uid);
		model.addAttribute("rv",rv);
		
		model.addAttribute(uid);
		
		return "search/complete";
	}
	
	@GetMapping("search/view")
	public String view(Model model, String hosNo, String pharNo) {
	
		if (hosNo != null) {
			SearchVO a = service.selectViewHs(hosNo);
			model.addAttribute("a", a);
			} else if (pharNo != null) {
			SearchVO b = service.selectViewPh(pharNo);
			model.addAttribute("b", b);
			}
			return "search/view";
		
	}
	
	
	@GetMapping("search/SearchHs")
	public String SearchHs(Model model, HttpSession sess, String search) {
		
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
	public String SearchPh() {
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
