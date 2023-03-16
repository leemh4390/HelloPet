package kr.co.hellopet.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.hellopet.service.AdminService;
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
	public String confirmList(Model model) {
		
		
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
