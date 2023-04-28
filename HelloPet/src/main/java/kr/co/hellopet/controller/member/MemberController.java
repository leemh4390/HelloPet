package kr.co.hellopet.controller.member;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hellopet.mail.MailSendService;
import kr.co.hellopet.mail.PasswordMailSendService;
import kr.co.hellopet.service.MemberService;
import kr.co.hellopet.vo.Api_HospitalVO;
import kr.co.hellopet.vo.Api_PharmacyVO;
import kr.co.hellopet.vo.CouponVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.TermsVO;
import lombok.extern.log4j.Log4j2;

/* 
 *  HelloPet Project 
 *  날짜 : 2023/03/08
 *  담당 : 이민혁
 *  설명 : HelloPet Member 페이지 기능구현
 * 
 */

//푸시되어라
@Log4j2
@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private MailSendService mailService;
	
	@Autowired
	private PasswordMailSendService passwordMail;
	
	@ResponseBody
	@GetMapping("member/naverEmail")
	public String loginTest(String name, String email) {
		
		System.out.println("name : " + name);
		System.out.println("email : " + email);
		
		return "member/test";
	}
	
	// 로그인
	@GetMapping("member/login")
	public String login(Model model) {		
		log.debug("member/login...");
		return "member/login";
	}
	
	// 조인 (회원가입 구분)
	@GetMapping("member/join")
	public String join() {
		log.debug("member/join...");
		return "member/join";
	}
	
	// 약관
	@GetMapping("member/terms")
	public String terms(@RequestParam(value="type") String type, Model model) {
		log.debug("member/terms...");
		model.addAttribute("type",type);
		
		List<TermsVO> vo = service.selectTerms();
		
		model.addAttribute("vo", vo);
		
		return "member/terms";
	}
	
	//약관 인증 처리
	@ResponseBody
	@PostMapping("member/authority")
	public void authority(@RequestParam("check") boolean check, HttpSession session) {
		log.debug("member/authority...");
	    session.setAttribute("termAgreed", check);	
	    
	    System.out.println("termAgreed: " + session.getAttribute("termAgreed"));
	}
	
	// 가입 (일반회원)
	@GetMapping("member/register")
	public String register(HttpSession session) {
		log.debug("member/register...GET");
		boolean termAgreed = Boolean.TRUE.equals(session.getAttribute("termAgreed"));
		
		  if (!termAgreed) {
		    return "redirect:/member/terms?type=owner";
		    
		  }else {
			  session.removeAttribute("termAgreed");
			  return "member/register";
		  }
		}
	
	@ResponseBody
	@GetMapping("member/countCoupon")
	public void countCoupon(Authentication authentication) {
		
		if(authentication != null) {
			String uid = authentication.getName();
			
			int count = service.selectCountOwnerCoupon(uid);
			
			System.out.println("uid : " + uid + " count : " + count);
			
			service.updateCouponPetOwner(count, uid);
		}
		
	}
	
	
	@PostMapping("member/register")
	public String register(MemberVO vo, HttpServletRequest req, @RequestParam("uid") String uid) {
		log.debug("member/register...POST");
		// ip 설정
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertMember(vo);
		
		List<CouponVO> coupons = service.selectCouponGrade();
		
		for(CouponVO coupon : coupons) {
			
			List<Integer> cpNos = Arrays.asList(coupon.getCpNo());
			
			for(Integer cpNo : cpNos) {
				
				System.out.println("cpNo : " + cpNo);
				
				service.updateCouponDownload(cpNo);
				
				service.insertMemberCoupon(cpNo, uid);
			}
		}
		
		System.out.println("uid : " + uid);
		
		return "redirect:/member/login";
	}
	
	// 가입 (병원 약국)
	@GetMapping("member/registerMedical")
	public String registerMedical(HttpSession session) {
		log.debug("member/registerMedical...GET");
		boolean termAgreed = Boolean.TRUE.equals(session.getAttribute("termAgreed"));
		
		System.out.println("termsAgreed : " + termAgreed);
		
		  if (!termAgreed) {
		    return "redirect:/member/terms?type=medical";
		    
		  }else {
			  session.removeAttribute("termAgreed");
			  return "member/registerMedical";
		  }
	}
	
	@PostMapping("member/registerMedical")
	public String registerMedical(MedicalVO vo, HttpServletRequest req) {
		log.debug("member/registerMedical...POST");
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
		
		// ajax 데이터 수신 확인
		System.out.println("email : " +  email + " name : " + name + " hp : "+  hp);

		// 임시비밀번호 생성하기
		String code = service.makeRandomPass();
		System.out.println("인증번호는 " + code + " 입니다.");

		// 데이터를 map 에 담기
		Map<String, Integer> resultMap = new HashMap<>();

		// 수신한 데이터를 바탕으로 데이터 조회 (owner table)
		int countMember = service.selectCountMemberForChangePass(email, name, hp);

		// 수신한 데이터를 바탕으로 데이터 조회 (medical table)
		int countMedical = service.selectCountMedicalForChangePass(email, name, hp);

		if(countMember == 1) {
			service.updatePetOwnerPasswordByCodeAndInfo(code, email, name, hp);
			resultMap.put("result", 1);
			passwordMail.SendPasswordEmail(email, code);
			System.out.println("일반 회원입니다.");

		}else if(countMedical == 1) {
			service.updateMedicalPasswordByCodeAndInfo(code, email, name, hp);
			resultMap.put("result", 1);
			passwordMail.SendPasswordEmail(email, code);
			System.out.println("병원 회원입니다.");
		}else {
			resultMap.put("result", 0);
		}

		return resultMap;
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