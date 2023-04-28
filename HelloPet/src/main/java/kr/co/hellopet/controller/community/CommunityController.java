package kr.co.hellopet.controller.community;

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

import kr.co.hellopet.service.CommunityService;
import kr.co.hellopet.vo.CommunityVO;
import kr.co.hellopet.vo.MessageVO;
import lombok.extern.log4j.Log4j2;

/*
 * 날짜 : 2023/03/09
 * 이름 : 김경준
 * 설명 : HelloPet 커뮤니티 페이지 기능구현
 */

@Log4j2
@Controller
public class CommunityController {
	
	@Autowired
	private CommunityService service;
	
	// tip 목록
	@GetMapping("community/tip/list")
	public String tipList(String pg, Model model, Principal principal) {
		
		//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		total = service.selectTipCount();
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
    			
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		//전체 목록 가져오기
		List<CommunityVO> articles = service.selectTipArticles(start);
		
		
		model.addAttribute("articles", articles);
		
		
		return "community/tip/list";
	}
	
	// tip 글보기
	@GetMapping("community/tip/view")
	public String tipView(@RequestParam("no") int no, Model model,Principal principal) {
		
		String uid = "";
		
		if(principal != null) {
			uid = principal.getName();
		}else {
			uid = "";
		}
		
		
		if(principal != null) {
			String uid2 = principal.getName();
			int msg2 = service.selectMsg(uid2);
			model.addAttribute("msg2", msg2);
		}
		CommunityVO article = service.selectTipView(no);
		
		int find = service.findHeart(no, uid);

		model.addAttribute("uid", uid);
		
		model.addAttribute("article", article);
		model.addAttribute("find",find);
		
		return "community/tip/view";
	}
	
	// tip 글쓰기
	@GetMapping("community/tip/write")
	public String tipWrite(Model model, Principal principal) {
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "community/tip/write";
	}
	
	// tip 글쓰기 폼
	@PostMapping("community/tip/write")
	public String tipWrite(CommunityVO vo, HttpServletRequest req) {
		
		
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertTipArticle(vo);
		
		return "redirect:/community/tip/list";
	}
	
	// tip 글수정
	@GetMapping("community/tip/modify")
	public String tipModify(Model model, CommunityVO vo, int no, Principal principal) {
		
		CommunityVO article = service.selectTipModify(no);
		model.addAttribute("article", article);
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		return "community/tip/modify";
	}
	
	// tip 글수정 폼
	@PostMapping("community/tip/modify")
	public String tipModify(CommunityVO vo) {
		
		service.updateTipArticle(vo);
		
		
		return "redirect:/community/tip/view?no="+vo.getNo();
	}
	
	// tip 글삭제
	@GetMapping("community/tip/delete")
	public String tipDelete(int no, String group){
		service.deleteArticle(no);
		
		return "redirect:/community/tip/list?group="+group;
	}
	
	// talktalk 목록
		@GetMapping("community/talktalk/list")
		public String talkList(String pg, Model model, String cate, String sort,Principal principal) {
			
			String uid = "";
			
			if(principal != null) {
				uid = principal.getName();
			}else {
				uid = "";
			}
			
			if(principal != null) {
				String uid2 = principal.getName();
				int msg2 = service.selectMsg(uid2);
				model.addAttribute("msg2", msg2);
			}
			//페이징 
	    	int currentPage = service.getCurrentPage2(pg); // 현재 페이지 번호
			int total = 0;
			
			total = service.selectTalkCount(cate);
			
			int lastPageNum = service.getLastPageNum2(total);// 마지막 페이지 번호
			int[] result = service.getPageGroupNum2(currentPage, lastPageNum); // 페이지 그룹번호
			int pageStartNum = service.getPageStartNum2(total, currentPage); // 페이지 시작번호
			int start = service.getStartNum2(currentPage); // 시작 인덱스
			
			model.addAttribute("lastPageNum", lastPageNum);		
			model.addAttribute("currentPage", currentPage);		
			model.addAttribute("pageGroupStart", result[0]);
			model.addAttribute("pageGroupEnd", result[1]);
			model.addAttribute("pageStartNum", pageStartNum+1);
	    			
			//전체 목록 가져오기
			List<CommunityVO> articles = service.selectTalkArticles(start, cate, sort);
			//좋아요 랭킹 3위 목록 가져오기
			List<CommunityVO> ranks = service.selectTalkRanks(cate);
			
			
			
			int no = 0;
			
			int find = service.findHeart(no, uid);
			model.addAttribute("find",find);
			
			
			model.addAttribute("articles", articles);
			model.addAttribute("ranks",ranks);
			model.addAttribute("sort", sort);
			model.addAttribute("cate", cate);
			return "community/talktalk/list";
		}
	
	// talktalk 모달 뷰
		@ResponseBody
		@GetMapping("community/talktalk/view")
		public Map<String, CommunityVO> talkView(@RequestParam("no") int no, Principal principal, Model model) {
			
			
			//글 가져오기
			CommunityVO article = service.selectTalkArticle(no);
			
			
			
			Map<String, CommunityVO> map = new HashMap<>();
			
			if(principal != null) {
				String uid = principal.getName();
				int msg2 = service.selectMsg(uid);
				model.addAttribute("msg2", msg2);
			}
			
			map.put("result", article);
			
			return map;
		}
		
		
		
	// talktalk 글쓰기
	@GetMapping("community/talktalk/write")
	public String talkWrite(Model model, Principal principal) {
		
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		return "community/talktalk/write";
	}
	
	// talktalk 글쓰기 폼
	@PostMapping("community/talktalk/write")
	public String talkWrite(CommunityVO vo, HttpServletRequest req) {
		
		log.info("POST talkWrite : " + vo);
		
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		service.insertTalkArticle(vo);
		
		return "redirect:/community/talktalk/list";
	}
	
	// talktalk 글수정
	@GetMapping("community/talktalk/modify")
	public String talkModify(Model model, CommunityVO vo, int no, Principal principal) {
		
		CommunityVO article = service.selectTalkArticle(no);
		model.addAttribute("article", article);
		if(principal != null) {
			String uid = principal.getName();
			int msg2 = service.selectMsg(uid);
			model.addAttribute("msg2", msg2);
		}
		
		return "community/talktalk/modify";
	}
	
	// talktalk 글수정 폼
	@PostMapping("community/talktalk/modify")
	public String talkModify(CommunityVO vo,String pg, String cate, String sort) {
		
		
		service.updateTalkArticle(vo);
		return "redirect:/community/talktalk/list";
	}
	
	// talktalk 글삭제
	@GetMapping("community/talktalk/delete")
	public String talkDelete(int no){
		service.deleteArticle(no);
		
		return "redirect:/community/talktalk/list";
	}
	
	// 글 좋아요 여부
	@ResponseBody
	@GetMapping("community/findHeart")
	public Map<String, Integer> findHeart(@RequestParam("no") int no, @RequestParam("uid") String uid){
		
		int result = service.findHeart(no, uid);
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("result", result);
		
		
		
		return map;
	}
	
	// 글 좋아요 안눌렀을때 +1
	@ResponseBody
	@GetMapping("community/HeartUp")
	public int HeartUp(@RequestParam("no") int no, @RequestParam("uid") String uid, MessageVO vo, String writerUid, String nick){
		
		int up = service.insertHeart(no, uid);
		
		if(up > 0) {
			vo.setUid(writerUid);
			vo.setTitle(nick + "님이 회원님의 게시물에 좋아요를 눌렀습니다.");
			vo.setContent(nick + "님이 회원님의 게시물에 좋아요를 눌렀습니다.");
			service.insertMsg(vo);
		}
		return up;
	}
	
	// 글 좋아요 눌렀을때 -1
	@ResponseBody
	@GetMapping("community/HeartDown")
	public int HeartDown(@RequestParam("no") int no, @RequestParam("uid") String uid){
		
		int down = service.deleteHeart(no, uid);
		
		
		
		return down;
		
	}
	
	// 댓글출력
	@ResponseBody
	@GetMapping("community/selectReplys")
	public Map<String, List<CommunityVO>> selectReplys(@RequestParam("no") int no){
		
		// 댓글가져오기
		List<CommunityVO> replys = service.selectReplys(no);
		
		 Map<String, List<CommunityVO>> map = new HashMap<>();
		
		map.put("result", replys);
		
		return map;
		
	}
	
	// 댓글달기
	@ResponseBody
	@PostMapping("community/insertReplys")
	public Map<String, CommunityVO> insertReply(CommunityVO vo, HttpServletRequest req, MessageVO msg, String nick, String writerUid){
		
		
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		
		int reply = service.insertReply(vo);
		
		Map<String, CommunityVO> map = new HashMap<>();
		
		map.put("result", vo);
		
		if(reply > 0) {
			msg.setUid(writerUid);
			msg.setTitle(nick +"님이 회원님의 게시물에 댓글을 달았습니다.");
			msg.setContent("댓글내용 : "+ msg.getContent());
			service.insertMsg(msg);
		}
		
		return map;
		
	}
	
	// 댓글삭제
	@ResponseBody
	@GetMapping("community/deleteReplys")
	public Map<String, Integer> deleteReply(@RequestParam("no") int no, @RequestParam("uid") String uid, @RequestParam("reply_no") int reply_no){
		
		
		
		
		int result = service.deleteReply(no, uid, reply_no);
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("result", result);
		
		return map;
		
	}
	
	// 모달창 최근 좋아요누른 별명 출력
	@ResponseBody
	@GetMapping("community/selectHeartUser")
	public Map<String, CommunityVO> selectHeartUser(@RequestParam("no") int no){
		
		// 댓글가져오기
		CommunityVO user = service.selectHeartUser(no);
		
		 Map<String, CommunityVO> map = new HashMap<>();
		
		map.put("result", user);
		
		return map;
		
	}
	
	
	
}
