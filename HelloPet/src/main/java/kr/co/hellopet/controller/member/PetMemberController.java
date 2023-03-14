package kr.co.hellopet.controller.member;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.classic.Logger;
import kr.co.hellopet.mail.MailSendService;
import kr.co.hellopet.mail.PasswordMailSendService;
import kr.co.hellopet.service.MemberService;
import kr.co.hellopet.vo.Api_HospitalVO;
import kr.co.hellopet.vo.Api_PharmacyVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;

/* 
 *  HelloPet Project 
 *  날짜 : 2023/03/08
 *  담당 : 이민혁
 *  설명 : HelloPet Member 페이지 기능구현
 * 
 */

//푸시되어라
@Controller
public class PetMemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private MailSendService mailService;
	
	@Autowired
	private PasswordMailSendService passwordMail;
	
	// 로그인
	@GetMapping("member/login")
	public String login() {
		return "member/login";
	}
	
	// 조인 (회원가입 구분)
	@GetMapping("member/join")
	public String join() {
		return "member/join";
	}
	
	// 약관
	@GetMapping("member/terms")
	public String terms(@RequestParam(value="type") String type, Model model) {
		
		model.addAttribute("type",type);
		
		return "member/terms";
	}
	
	//약관 인증 처리
	@ResponseBody
	@PostMapping("member/authority")
	public void authority(@RequestParam("check") boolean check, HttpSession session) {
		
		session.setAttribute("termAgreed", check);		
	}
	
	// 가입 (일반회원)
	@GetMapping("member/register")
	public String register(HttpSession session) {
		
		boolean termAgreed = Boolean.TRUE.equals(session.getAttribute("termAgreed"));
		
		  if (!termAgreed) {
		    return "redirect:/member/terms?type=owner";
		    
		  }else {
			  session.removeAttribute("termAgreed");
			  return "/member/register";
		  }
		}
	
	@PostMapping("member/register")
	public String register(MemberVO vo, HttpServletRequest req) {
		
		// ip 설정
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertMember(vo);
		return "redirect:/member/login";
	}
	
	// 가입 (병원 약국)
	@GetMapping("member/registerMedical")
	public String registerMedical(HttpSession session) {
		
		boolean termAgreed = Boolean.TRUE.equals(session.getAttribute("termAgreed"));
		
		  if (!termAgreed) {
		    return "redirect:/member/terms?type=medical";
		    
		  }else {
			  session.removeAttribute("termAgreed");
			  return "/member/registerMedical";
		  }
	}
	
	@PostMapping("member/registerMedical")
	public String registerMedical(MedicalVO vo, HttpServletRequest req) {
		
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertMedical(vo);
		return "redirect:/member/login";
	}
	
	@ResponseBody
	@GetMapping("member/SearchHospital")
	public Map<String, List<Api_HospitalVO>> searchHospital(@RequestParam("trial") String trial, @RequestParam("county") String county, @RequestParam("name") String name) {
		
		/*System.out.println("trial : " + trial);
		System.out.println("county : " + county);
		System.out.println("name : " + name);*/
		
		List<Api_HospitalVO> vo = service.selectMedical(trial, county, name);
		
		Map<String, List<Api_HospitalVO>> map = new HashMap<>();
		
		map.put("result", vo);
		System.out.println("size : " + vo.size());
		
		return map;
	}
	
	@ResponseBody
	@GetMapping("member/SearchPharmacy")
	public Map<String, List<Api_PharmacyVO>> searchPharmacy(@RequestParam("trial") String trial, @RequestParam("county") String county, @RequestParam("name") String name) {
		
		/*System.out.println("trial : " + trial);
		System.out.println("county : " + county);
		System.out.println("name : " + name);*/
		
		List<Api_PharmacyVO> vo = service.selectPharmacy(trial, county, name);
		
		Map<String, List<Api_PharmacyVO>> map = new HashMap<>();
		
		map.put("result", vo);
		System.out.println("size : " + vo.size());
		
		return map;
	}
	
	// 아이디 비밀번호 찾기
	@GetMapping("member/find")
	public String find() {
		return "member/find";
	}
	
	@ResponseBody
	@PostMapping("member/findId")
	public Map<String, ? extends Object> findId(@RequestParam("name") String name, @RequestParam("hp") String hp) {

	    MemberVO owner = service.selectFindId(name, hp);
	    MedicalVO medical = service.selectFindMedicalId(name, hp);

	    if (owner != null) {
	        Map<String, MemberVO> map = new HashMap<>();
	        map.put("owner", owner);
	        return map;
	    } else if (medical != null) {
	        Map<String, MedicalVO> map = new HashMap<>();
	        map.put("medical", medical);
	        return map;
	    } else {
	        return null;
	    }
	}
	
	
	@ResponseBody
	@PostMapping("member/changePass")
	public Map<String, Integer> changePass(@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("hp") String hp) {
		
		return null;
	}

	// uid 중복체크
	@ResponseBody
	@GetMapping("member/countUid")
	public Map<String, Integer> countUid(@RequestParam("uid") String uid) {
		int owner = service.countUid(uid);
		int medical = service.countMedicalUid(uid);
		int result = owner + medical;
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	// hp 중복체크
	@ResponseBody
	@GetMapping("member/countHp")
	public Map<String, Integer> countHp(@RequestParam("hp") String hp) {
		int owner = service.countHp(hp);
		int medical = service.countMedicalHp(hp);
		int result = owner + medical;
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	// email 중복체크
	@ResponseBody
	@GetMapping("member/countEmail")
	public Map<String, Integer> countEmail(@RequestParam("email") String email) {
		int owner = service.countEmail(email);
		int medical = service.countMedicalEmail(email);
		int result = owner + medical;
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	// nick 중복체크
	@ResponseBody
	@GetMapping("member/countNick")
	public Map<String, Integer> countNick(@RequestParam("nick") String nick) {
		int result = service.countNick(nick);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	//회원가입 이메일 인증
	@ResponseBody
	@GetMapping("member/registerAuth")
	public String test(@RequestParam("email") String email) {
		
		System.out.println("이메일 들어오기 확인!!");
		System.err.println("이메일 확인하기 : " + email);
		
		return mailService.joinEmail(email);
	}
}