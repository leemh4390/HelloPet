package kr.co.hellopet.controller.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public String reserve() {
		return "search/reserve";
	}
	
	@GetMapping("search/complete")
	public String complete() {
		return "search/complete";
	}
	
	@GetMapping("search/view")
	public String view() {
		return "search/view";
	}
	
	@GetMapping("search/SearchHs")
	public String SearchHs(Model model, HttpSession sess, String search) {
		
		return "search/SearchHs";
	}
	
	@ResponseBody
	@PostMapping("search/SearchHs")
	public Map<String, List<SearchVO>> SearchHs(Model model, @RequestParam("search") String search, HttpSession sess, HttpServletResponse resp, String pg) throws IOException {
		Map<String, List<SearchVO>> map = new HashMap<>();
		List<SearchVO> hss = service.selectSearchHs(search);
		map.put("hss", hss);
		
		if(!hss.isEmpty()) {
			sess.setAttribute("hss", hss);
		}
		
		int result = service.selectSearchHsTotal(search);
		
		model.addAttribute("search", search);
		model.addAttribute("hss", hss);
		model.addAttribute("result", result);
		
		// 페이징 처리
		int currentPage = 1;
		int start = (currentPage - 1) * 10;

        int total = result;
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int[] groups = service.getPageGroup(currentPage, lastPageNum);

        model.addAttribute("groups", groups);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("start", start);
        model.addAttribute("total", total);
        model.addAttribute("lastPageNum", lastPageNum);
        
		
		return map;
	}
	
	
	@GetMapping("search/SearchPm")
	public String SearchPm() {
		return "search/SearchPm";
	}
	
	
	
}
