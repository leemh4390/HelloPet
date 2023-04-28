package kr.co.hellopet.controller.disease;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.hellopet.service.DiseaseService;
import kr.co.hellopet.vo.DiseaseResultMapVO;
import kr.co.hellopet.vo.DiseaseVO;
import kr.co.hellopet.vo.Disease_cate1VO;
import kr.co.hellopet.vo.Disease_cate2VO;

/*
 * HelloPet Project
 * 날짜 : 2023/03/10
 * 담당 : 임민지
 * 내용 : HelloPet Disease 화면 구현 
 */

@Controller
public class DiseaseController {
	
	
	@Autowired
	private DiseaseService service;
	
	/*dog*/
	
	@GetMapping("disease/index")
	public String index(Model model, String group, Principal principal) {
		
		if(principal != null) {
			String uid = principal.getName();
			int result = service.selectMsg(uid);
			model.addAttribute("result", result);
		}
		
		if(group == null) {
			group = "dog";
		}
		
		List<DiseaseResultMapVO> resultMaps = service.selectDiseases(group);
		model.addAttribute("resultMaps", resultMaps);
		model.addAttribute("group", group);
		
		
		return "disease/index";
		
	}
	
	@GetMapping("disease/view")
	public String view(Model model, int cate1, int cate2, Principal principal) {
		DiseaseVO vo = service.selectDisease(cate1, cate2);
		model.addAttribute("vo", vo);
		if(principal != null) {
			String uid = principal.getName();
			int result = service.selectMsg(uid);
			model.addAttribute("result", result);
		}
		
		return "disease/view";
	}
	
	

	
	
}

