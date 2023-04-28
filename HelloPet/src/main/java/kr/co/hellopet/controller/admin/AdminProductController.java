package kr.co.hellopet.controller.admin;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hellopet.service.AdminProductService;
import kr.co.hellopet.vo.AdminProductVO;
import kr.co.hellopet.vo.MedicalVO;

@Controller
public class AdminProductController {

	@Autowired
	private AdminProductService service;
	
	@GetMapping("admin/product/list")
	public String productList(Model model, Principal principal, String pg) {

		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		
		//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		total = service.selectProductCount();
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		List<AdminProductVO> products = service.selectAdminProducts(start);

		
		model.addAttribute("vo",vo);
		model.addAttribute("products",products);
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
		
		
		
		
		return "admin/product/list";
	}
	
	@GetMapping("admin/product/register")
	public String productRegister(Model model, Principal principal) {
		
		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		model.addAttribute("vo",vo);
		
		return "admin/product/register";
	}
	
	@PostMapping("admin/product/register")
	public String productRegister(HttpServletRequest req, AdminProductVO vo) {
		
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertAdminProduct(vo);
		return "redirect:/admin/product/list?medNo="+vo.getMedNo();
	}
	
	@GetMapping("admin/product/modify")
	public String productModify(Model model, Principal principal, int prodNo) {
		
		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		// 상품 정보
		AdminProductVO product = service.selectAdminProduct(prodNo);
		
		model.addAttribute("vo",vo);
		model.addAttribute("product",product);
		
		return "admin/product/modify";
	}
	
	@PostMapping("admin/product/modify")
	public String productModify(HttpServletRequest req, AdminProductVO vo) {
		
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		
		
		
		service.updateAdminProduct(vo);
		return "redirect:/admin/product/list";
	}
	
	@ResponseBody
	@GetMapping("admin/product/delete")
	public Map<String, Integer> delete(String prodNo, HttpServletRequest req) {
		List<String> list = Arrays.asList(prodNo.split(","));
		int result = 0;
		for(String no : list) {
			result = service.deleteCheck(no);
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
}
