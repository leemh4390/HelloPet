package kr.co.hellopet.controller.cs;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.hellopet.service.CsService;
import kr.co.hellopet.vo.CsVO;

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
	public String noticeList(Model model, String pg) {
		
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
		
		return "cs/notice/list";
	}

	@GetMapping("cs/notice/write")
	public String noticeWrite() {
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
	public String noticeView(Model model,int no, String pg, String rdate) {
		int currentPage = service.getCurrentPage(pg);
		service.updateArticleHit(no);
		CsVO prev = service.getPrev(rdate);
		CsVO next =service.getNext(rdate);
		
		CsVO vo = service.selectArticle(no);
		model.addAttribute("vo", vo);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		return "cs/notice/view";
	}
	
	@GetMapping("cs/notice/modify")
	public String noticeModify(Model model,int no, String pg) {
		int currentPage = service.getCurrentPage(pg);
		CsVO vo = service.selectArticle(no);
		
		model.addAttribute("vo", vo);
		model.addAttribute("currentPage", currentPage);
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
	public String faqList(Model model) {
		List<CsVO> faqs = service.selectFaqs();
		model.addAttribute("faqs",faqs);
		return "cs/faq/list";
	}
	
	@GetMapping("cs/faq/write")
	public String faqWrite() {
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
	public String faqModify(Model model,int no) {
		CsVO vo = service.selectArticle(no);
		
		model.addAttribute("vo", vo);
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
	public String qnaList(Model model, String pg) {
		
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
		
		return "cs/qna/list";
	}
	@GetMapping("cs/qna/write")
	public String qnaWrite() {
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
	public String qnaView(Model model,int no, String pg) {
		/* 문의하기 글 정보 */
		int currentPage = service.getCurrentPage(pg);
		service.updateArticleHit(no);
		
		CsVO vo = service.selectArticle(no);
		model.addAttribute("vo", vo);
		model.addAttribute("currentPage", currentPage);
		
		
		
		List<CsVO> replys = service.selectReply(no);
		model.addAttribute("replys", replys);
		
		
		return "cs/qna/view";
	}
	
	@PostMapping("cs/qna/view")
	public String qnaView(CsVO vo, String pg, HttpServletRequest req) {
		int currentPage = service.getCurrentPage(pg);
		
		/* 답변 */
		String regip = req.getRemoteAddr();
        vo.setRegip(regip);
		int result = service.insertReply(vo);
		
		if(result > 0) {
			service.updateReply(vo.getNo());
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
