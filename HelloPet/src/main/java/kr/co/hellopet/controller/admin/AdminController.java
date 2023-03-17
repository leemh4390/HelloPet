package kr.co.hellopet.controller.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hellopet.service.AdminService;
import kr.co.hellopet.vo.AdminReserveVO;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MedicalVO;

@Controller
public class AdminController {

	@Autowired
	private AdminService service;
	
	@GetMapping("admin/info")
	public String info(Model model, Principal principal) {

		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		
		model.addAttribute("vo",vo);
		return "admin/info";
	}
	@GetMapping("admin/confirm/list")
	public String confirmList(Model model, String pg, String medNo, Principal principal, Integer revNo) {
		
		String uid = principal.getName();
		MedicalVO vo = service.selectAdmin(uid);
		
		model.addAttribute("vo",vo);
		
		// 페이징 처리
		int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);

        int total = service.selectCountTotal(medNo);
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
        // 예약 목록 출력
		List<AdminReserveVO> reserves = service.selectReserves(start, medNo);
		
		// 팝업창 예약 정보 출력
		AdminReserveVO reserve = service.selectReserve(revNo);
		model.addAttribute("reserve",reserve);
		
		
		model.addAttribute("reserves", reserves);
		model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
		
        return "admin/confirm/list";
	}
	
	@PostMapping("admin/confirm/list")
	public String view(Model model, @RequestParam(value="revNo", required = false) Integer revNo) {
		AdminReserveVO reserve = service.selectReserve(revNo);
		model.addAttribute("reserve",reserve);
		
		return "admin/confirm/list";
	}
	
	@GetMapping("admin/infoModify")
	public String infoModify(Model model, Principal principal) {
		
		String uid = principal.getName();
		MedicalVO vo = service.selectAdmin(uid);
		
		model.addAttribute("vo",vo);
		return "admin/infoModify";
	}
	
	@PostMapping("admin/infoModify")
	public String infoModify(MedicalVO vo) {
		service.updateAdmin(vo);
		return "redirect:/admin/info";
	}
}
