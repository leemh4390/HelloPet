package kr.co.hellopet.controller.admin;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import kr.co.hellopet.vo.CommunityVO;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.MessageVO;

@Controller
public class AdminController {

	@Autowired
	private AdminService service;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("admin/info")
	public String info(Model model, Principal principal) {

		String uid = principal.getName();
		
		MedicalVO vo = service.selectAdmin(uid);
		MemberVO user = service.selectUser(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		model.addAttribute("vo",vo);
		model.addAttribute("user",user);
		return "admin/info";
	}
	
	@GetMapping("admin/pwChange")
	public String pwchange(Model model, Principal principal){
		
		String uid = principal.getName();
		MedicalVO vo = service.selectAdmin(uid);
		MemberVO user = service.selectUser(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		model.addAttribute("vo",vo);
		model.addAttribute("user",user);
		
		return "admin/pwChange";
	}
	

	@ResponseBody
	@PostMapping("admin/pwChange")
	public Map<String, Integer> findPwChange(@RequestParam("uid") String uid, @RequestParam("pass") String pass, HttpServletRequest request, HttpServletResponse response) {
		pass = passwordEncoder.encode(pass);
		int result = service.findPwChange(uid, pass);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		// 캐시 비우기
	    new SecurityContextLogoutHandler().logout(request, response, null);
		return map;
	}
	@GetMapping("admin/pwChangeUser")
	public String pwchangeUser(Model model, Principal principal){
		
		String uid = principal.getName();
		MedicalVO vo = service.selectAdmin(uid);
		MemberVO user = service.selectUser(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		model.addAttribute("vo",vo);
		model.addAttribute("user",user);
		
		return "admin/pwChangeUser";
	}
	
	
	@ResponseBody
	@PostMapping("admin/pwChangeUser")
	public Map<String, Integer> findPwChangeUser(@RequestParam("uid") String uid, @RequestParam("pass") String pass, HttpServletRequest request, HttpServletResponse response) {
		pass = passwordEncoder.encode(pass);
		int result = service.findPwChangeUser(uid, pass);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		// 캐시 비우기
	    new SecurityContextLogoutHandler().logout(request, response, null);
		return map;
	}
	
	@ResponseBody
	@GetMapping("admin/withdrawAdmin")
	public int withdrawAdmin(@RequestParam("uid") String uid) {
		
		int result = service.deleteWithdrawAdmin(uid);
		
		System.out.println("uid : " + uid);
		
		System.out.println(result);
		
		return result;
	}
	@ResponseBody
	@GetMapping("admin/withdrawOwner")
	public int withdrawOwner(@RequestParam("uid") String uid) {
		
		int result = service.deleteWithdrawOwner(uid);
		
		System.out.println("uid : " + uid);
		
		System.out.println(result);
		
		return result;
	}
	/*
	@Transactional
	@GetMapping("admin/delete")
	public String delete(Principal principal, HttpServletRequest request, HttpServletResponse response) {
		String uid = principal.getName();
		
		service.deleteWithdrawMember(uid);
		service.deleteWithdrawUser(uid);
		
		// 캐시 비우기
	    new SecurityContextLogoutHandler().logout(request, response, null);
		
		return "redirect:/index";
	}
	*/
	
	@GetMapping("admin/confirm/list")
	public String confirmList(Model model, String pg, String medNo, Principal principal, @RequestParam(value="revNo", required=false) Integer revNo) {
		
		String uid = principal.getName();
		MedicalVO vo = service.selectAdmin(uid);
		int msg2 = service.selectMsg(uid);
		model.addAttribute("msg2", msg2);
		
		model.addAttribute("vo",vo);
		
		// 페이징 처리
		int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);
        int pageSize = 10;
        
        int total = service.selectCountTotal(medNo);
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
        // 예약 목록 출력
		List<AdminReserveVO> reserves = service.selectReserves(start, medNo, pageSize);
		int size = reserves.size();
		int idx = 1;
		for(AdminReserveVO res2 : reserves) {
			res2.setId(1+ size - idx++);
		}
		// 팝업창 예약 정보 출력
		AdminReserveVO reserve = service.selectReserve(revNo);
		
		model.addAttribute("res",reserve);
		
		
		model.addAttribute("reserves", reserves);
		model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
        model.addAttribute("medNo", medNo);
		
        return "admin/confirm/list";
	}
	
	@ResponseBody
	@GetMapping("admin/confirm/ok")
	public Map<String, AdminReserveVO> list(Model model,@RequestParam(value="revNo", required = false) Integer revNo,@RequestParam(value="medNo", required = false) Integer medNo, Principal principal) {
		AdminReserveVO reserve = service.selectReserve(revNo);
		Map<String, AdminReserveVO> map = new HashMap<>();
		map.put("result", reserve);
		
		String uid = principal.getName();		
		int res = service.selectMsg(uid);
		model.addAttribute("res", res);
		
		return map;
	}
	
	@ResponseBody
	@PostMapping("admin/confirm/ok")
	public Map<String, Boolean> ok(@RequestParam(value="coupon", required = false) Integer coupon, @RequestParam(value="revNo", required = false) Integer revNo, MessageVO vo, String medical, String uid, String medNo) {
	    boolean success = service.updateConfirm(revNo);
	    Map<String, Boolean> map = new HashMap<>();
	    map.put("result", success);
	    
	    vo.setUid(uid);
	    vo.setMedical(medical);
	    
	    if(success == true) {
	    	vo.setTitle(vo.getMedical()+ "에서 예약을 수락하였습니다.");
	    	vo.setContent(vo.getMedical()+ "에서 예약을 수락하였습니다. <a href='/HelloPet/my/myReserve'>마이페이지 예약관리 바로가기</a>");
	    	service.insertMsg(vo);
	    	service.updateReserve(medNo);
	    	service.deleteCoupon(coupon, uid);
	    	service.updateCouponOwner(uid);
	    }
	    
	    return map;
	}
	
	@ResponseBody
	@GetMapping("admin/confirm/view")
	public Map<String, AdminReserveVO> view(Model model, @RequestParam(value="revNo", required = false) Integer revNo, Principal principal) {
		AdminReserveVO reserve = service.selectReserve(revNo);
		Map<String, AdminReserveVO> map = new HashMap<>();
		map.put("result", reserve);
		
		String uid = principal.getName();		
		int res = service.selectMsg(uid);
		model.addAttribute("res", res);
		
		return map;
	}
	
	@ResponseBody
	@PostMapping("admin/confirm/view")
	public Map<String, Boolean> view(@RequestParam(value="revNo", required = false) Integer revNo, MessageVO vo, String medical, String uid) {
	    boolean success = service.updateReject(revNo);
	    Map<String, Boolean> map = new HashMap<>();
	    map.put("result", success);
	    
	    vo.setUid(uid);
	    vo.setMedical(medical);
	    
	    if(success == true) {
	    	vo.setTitle(vo.getMedical()+ "에서 예약을 거절하였습니다.");
	    	vo.setContent("거절사유 : "+vo.getContent() + " <a href='/HelloPet/my/myReserve'>마이페이지 예약관리 바로가기</a>");
	    	service.insertMsg(vo);
	    }
	    
	    return map;
	}
	
	@GetMapping("admin/infoModify")
	public String infoModify(Model model, Principal principal) {
		
		String uid = principal.getName();
		MedicalVO vo = service.selectAdmin(uid);
		MemberVO user = service.selectUser(uid);

		int res = service.selectMsg(uid);
		model.addAttribute("res", res);
		
		model.addAttribute("vo",vo);
		model.addAttribute("user",user);
		return "admin/infoModify";
	}
	
	@PostMapping("admin/infoModify")
	public String infoModify(MedicalVO vo, MemberVO mem) {
		service.updateAdmin(vo);
		service.updateUser(mem);
		return "redirect:/admin/info";
	}
}
