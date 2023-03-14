package kr.co.hellopet.controller.cs;

import java.awt.print.Pageable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.hellopet.service.CsService;
import kr.co.hellopet.vo.CsVO;
import lombok.Data;

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
    public String delete(CsVO vo,String pg, HttpServletRequest req){
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
	public String faqModify() {
		return "cs/faq/modify";
	}
	
	/* qna */
	@GetMapping("cs/qna/list")
	public String qnaList() {
		return "cs/qna/list";
	}
	@GetMapping("cs/qna/write")
	public String qnaWrite() {
		return "cs/qna/write";
	}
	@GetMapping("cs/qna/view")
	public String faqView() {
		return "cs/qna/view";
	}

}
