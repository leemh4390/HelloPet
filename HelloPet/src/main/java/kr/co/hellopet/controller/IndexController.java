package kr.co.hellopet.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.hellopet.service.IndexService;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.ListsVO;
/* 
 *  날짜 : 2023/03/13
 *  이름 : 김채영
 *  설명 : HelloPet Index 페이지 기능구현
 */
@Controller
public class IndexController {
	
	@Autowired
	private IndexService service;

	@GetMapping(value = {"", "index"})
	public String index(Model model, CsVO vo, String pg, Principal principal) {
		String uid = null;
		
		if(principal != null) {
			uid = principal.getName();
		}
		int currentPage = service.getCurrentPage(pg);
		
		/* 최다방문 최근등록 최다예약 5개씩 */
		List<ListsVO> hits = service.selectHit();
		List<ListsVO> news = service.selectNew();
		List<ListsVO> reserves = service.selectReserve();
		
		/* 공지사항 FAQ 5개씩 */
		List<CsVO> notices = service.selectNotice();
		List<CsVO> faqs = service.selectFaq();
		
		model.addAttribute("hits", hits);
		model.addAttribute("news", news);
		model.addAttribute("reserves", reserves);
		model.addAttribute("notices", notices);
		model.addAttribute("faqs", faqs);
		model.addAttribute("currentPage", currentPage);
		
		int result = service.selectMsg(uid);
		model.addAttribute("result", result);
		
		return "index";
	}
}
