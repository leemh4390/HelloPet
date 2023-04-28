package kr.co.hellopet.controller.cs;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hellopet.service.CsService;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MemberCouponVO;
import kr.co.hellopet.vo.MessageVO;

/* 
 *  날짜 : 2023/03/09
 *  이름 : 김채영
 *  설명 : HelloPet CS 페이지 기능구현
 */
@Controller
public class CsController {
	
	@Autowired
	private CsService service;

	/* notice */
	@GetMapping("cs/notice/list")
	public String noticeList(Model model, String pg, Principal principal) {
		
		int currentPage = service.getCurrentPage(pg);
		int pageSize = 10;
        int start = service.getLimitStart(currentPage);

        int total = service.selectCountTotalNotice();
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		List<CsVO> articles = service.selectNotices(start, pageSize);
		
		int idx = 1;
		for(CsVO vo : articles) {
			vo.setId(start + idx++);
		}
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
		
        if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
        
		return "cs/notice/list";
	}

	@GetMapping("cs/notice/write")
	public String noticeWrite(Model model, Principal principal) {
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "cs/notice/write";
	}
	
	@PostMapping("cs/notice/write")
	public String noticeWrite(CsVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
        vo.setRegip(regip);
        
        service.insertNotice(vo);
		
        return "redirect:/cs/notice/list";
	}
	
	@GetMapping("cs/notice/view")
	public String noticeView(CsVO cs, Model model,int no, String pg, String rdate, Principal principal) {
		int currentPage = service.getCurrentPage(pg);
		service.updateArticleHit(no);
		CsVO prev = service.getPrev(rdate);
		CsVO next =service.getNext(rdate);
		
		CsVO vo = null;
		
		if(cs.getCouponNo() > 0) {
			vo = service.selectArticle(no);
		}else {
			vo = service.selectNotice(no);
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		
		return "cs/notice/view";
	}
	
	// 쿠폰 다운로드
	@ResponseBody
	@GetMapping("cs/coupon")
	public int coupon(MemberCouponVO vo ,@RequestParam("cpNo") int cpNo, @RequestParam("uid") String uid){
		
		int coupon = service.insertCoupon(vo);
		if(coupon > 0) {
			service.updateDownload(cpNo);
			service.updateCouponOwner(uid);
		}
		
		return coupon;
	}
	
	// 쿠폰 발급 중복체크
	@ResponseBody
	@GetMapping("cs/countCoupon")
	public Map<String, Integer> countCoupon(@RequestParam("cpNo") String cpNo,@RequestParam("uid") String uid) {
		int count = service.countCoupon(cpNo, uid);
		int result = count;
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	@GetMapping("cs/notice/modify")
	public String noticeModify(Model model,int no, String pg, Principal principal) {
		int currentPage = service.getCurrentPage(pg);
		CsVO vo = service.selectArticle(no);
		
		model.addAttribute("vo", vo);
		model.addAttribute("currentPage", currentPage);
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "cs/notice/modify";
	}
	@PostMapping("cs/notice/modify")
	public String noticeModify(CsVO vo, String pg, int no) {
		int currentPage = service.getCurrentPage(pg);
		service.updateArticle(vo);
		CsVO article = service.selectArticle(no);
		
		return "redirect:/cs/notice/view?no="+vo.getNo()+"&pg="+currentPage+"&rdate="+article.getRdate();
	}
	
	@GetMapping("cs/notice/delete")
    public String noticeDelete(CsVO vo,String pg, HttpServletRequest req){
        int currentPage = service.getCurrentPage(pg);
        service.deleteArticle(vo.getNo());

        return "redirect:/cs/notice/list?pg="+currentPage;
    }

	/* faq */
	@GetMapping("cs/faq/list")
	public String faqList(Model model, Principal principal) {
		List<CsVO> faqs = service.selectFaqs();
		model.addAttribute("faqs",faqs);
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "cs/faq/list";
	}
	
	@GetMapping("cs/faq/write")
	public String faqWrite(Model model, Principal principal) {
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		return "cs/faq/write";
	}
	@PostMapping("cs/faq/write")
	public String faqWrite(CsVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
        vo.setRegip(regip);
		
		service.insertFaq(vo);
		return "redirect:/cs/faq/list";
	}
	
	@GetMapping("cs/faq/modify")
	public String faqModify(Model model,int no, Principal principal) {
		CsVO vo = service.selectArticle(no);
		
		model.addAttribute("vo", vo);
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "cs/faq/modify";
	}
	@PostMapping("cs/faq/modify")
	public String faqModify(CsVO vo) {
		service.updateArticle(vo);
		return "redirect:/cs/faq/list";
	}
	
	@GetMapping("cs/faq/delete")
    public String faqDelete(CsVO vo, HttpServletRequest req){
        service.deleteArticle(vo.getNo());
        return "redirect:/cs/faq/list";
    }
	
	/* qna */
	@GetMapping("cs/qna/list")
	public String qnaList(Model model, String pg, Principal principal) {
		
		int currentPage = service.getCurrentPage(pg);
		int pageSize = 10;
        int start = service.getLimitStart(currentPage);

        int total = service.selectCountTotalQna();
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		List<CsVO> articles = service.selectQnas(start, pageSize);
		
		int idx = 1;
		for(CsVO vo : articles) {
			vo.setId(start + idx++);
		}
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
        
        if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		return "cs/qna/list";
	}
	@GetMapping("cs/qna/write")
	public String qnaWrite(Model model, Principal principal) {
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "cs/qna/write";
	}
	@PostMapping("cs/qna/write")
	public String qnaWrite(CsVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
        vo.setRegip(regip);
        service.insertQna(vo);
        return "redirect:/cs/qna/list";

	}
	
	@GetMapping("cs/qna/view")
	public String qnaView(Model model,int no, String pg, Principal principal) {
		/* 문의하기 글 정보 */
		int currentPage = service.getCurrentPage(pg);
		service.updateArticleHit(no);
		
		CsVO vo = service.selectArticle(no);
		model.addAttribute("vo", vo);
		model.addAttribute("currentPage", currentPage);
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		
		List<CsVO> replys = service.selectReply(no);
		model.addAttribute("replys", replys);
		
		
		return "cs/qna/view";
	}
	
	@PostMapping("cs/qna/view")
	public String qnaView(CsVO vo, String pg, HttpServletRequest req, @RequestParam(value="writerUid", required=false) String writerUid, MessageVO msg) {
		int currentPage = service.getCurrentPage(pg);
		
		/* 답변 */
		String regip = req.getRemoteAddr();
        vo.setRegip(regip);
		int result = service.insertReply(vo);
		
		if(result > 0) {
			int reply = service.updateReply(vo.getNo());
			
			msg.setUid(writerUid);
			msg.setTitle("회원님의 문의글에 답변이 달렸습니다.");
			msg.setContent("마이페이지 > 1:1 문의에서 확인하세요. <a href='/HelloPet/cs/qna/view?no="+vo.getNo()+"&pg="+currentPage+"'>문의글 바로가기</a>");
			service.insertMsg(msg);
		}
		
		return "redirect:/cs/qna/view?no="+vo.getNo()+"&pg="+currentPage;
	}
	@GetMapping("cs/qna/delete")
    public String qnaDelete(CsVO vo,String pg, HttpServletRequest req){
        int currentPage = service.getCurrentPage(pg);
        service.deleteArticle(vo.getNo());

        return "redirect:/cs/qna/list?pg="+currentPage;
    }
	
	@GetMapping("cs/qnaReply/delete")
    public String qnaReplyDelete(CsVO vo,String pg, HttpServletRequest req){
        int currentPage = service.getCurrentPage(pg);
        int result = service.deleteReply(vo.getNo());

        if(result > 0) {
			service.updateDelReply(vo.getNo());
		}
        
        return "redirect:/cs/qna/view?no="+vo.getNo()+"&pg="+currentPage;
    }

}
