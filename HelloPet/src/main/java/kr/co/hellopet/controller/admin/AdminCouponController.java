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

import kr.co.hellopet.service.AdminCouponService;
import kr.co.hellopet.vo.CouponVO;
import kr.co.hellopet.vo.MedicalVO;

@Controller
public class AdminCouponController {

	@Autowired
	private AdminCouponService service;
	
	@GetMapping("admin/coupon/list")
	public String CouponList(Model model, Principal principal, String pg) {

		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		total = service.selectCouponCount();
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		List<CouponVO> Coupons = service.selectAdminCoupons(start);

		
		model.addAttribute("vo",vo);
		model.addAttribute("Coupons",Coupons);
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
		
		
		
		
		return "admin/coupon/list";
	}
	
	@GetMapping("admin/coupon/register")
	public String CouponRegister(Model model, Principal principal) {
		
		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		model.addAttribute("vo",vo);
		
		return "admin/coupon/register";
	}
	
	@PostMapping("admin/coupon/register")
	public String CouponRegister(HttpServletRequest req, CouponVO vo) {
		
		service.insertAdminCoupon(vo);
		return "redirect:/admin/coupon/list";
	}
	
	@GetMapping("admin/coupon/modify")
	public String CouponModify(Model model, Principal principal, int cpNo) {
		
		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		// 상품 정보
		CouponVO coupon = service.selectAdminCoupon(cpNo);
		
		model.addAttribute("vo",vo);
		model.addAttribute("coupon",coupon);
		
		return "admin/coupon/modify";
	}
	
	@PostMapping("admin/coupon/modify")
	public String CouponModify(HttpServletRequest req, CouponVO vo) {
		
		service.updateAdminCoupon(vo);
		return "redirect:/admin/coupon/list";
	}
	
	@ResponseBody
	@GetMapping("admin/coupon/delete")
	public Map<String, Integer> delete(String cpNo, HttpServletRequest req) {
		List<String> list = Arrays.asList(cpNo.split(","));
		int result = 0;
		for(String no : list) {
			result = service.deleteCheck(no);
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
}
