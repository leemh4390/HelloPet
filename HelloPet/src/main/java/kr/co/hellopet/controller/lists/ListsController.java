package kr.co.hellopet.controller.lists;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.hellopet.service.ListsService;
import kr.co.hellopet.vo.ListsVO;
import kr.co.hellopet.vo.MedicalVO;
/* 
 *  날짜 : 2023/03/14
 *  이름 : 김채영
 *  설명 : HelloPet Lists 페이지 기능구현
 */
@Controller
public class ListsController {
	
	@Autowired
	private ListsService service;

	@GetMapping("lists/list")
	public String list(Model model, @RequestParam(value ="type", required=false) String type, String pg, Principal principal) {
		
		if(principal != null) {
			String uid = principal.getName();
			int result = service.selectMsg(uid);
			model.addAttribute("result", result);
		}
		
		int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);

        int total = service.selectCountTotal();
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		List<ListsVO> hits = service.selectHit(start);
		List<ListsVO> news = service.selectNew(start);
		List<ListsVO> reserves = service.selectReserve(start);
		
		model.addAttribute("type", type);
		model.addAttribute("hits", hits);
		model.addAttribute("news", news);
		model.addAttribute("reserves", reserves);
		model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
		
        return "lists/list";
	}
}
