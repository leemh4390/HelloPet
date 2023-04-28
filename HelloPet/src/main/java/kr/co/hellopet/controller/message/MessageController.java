package kr.co.hellopet.controller.message;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hellopet.service.MessageService;
import kr.co.hellopet.vo.AdminReserveVO;
import kr.co.hellopet.vo.MessageVO;

@Controller
public class MessageController {

	@Autowired
	private MessageService service;
	
	@GetMapping("message/message")
	public String message(Model model,Principal principal, String msgNo, String pg) {
		
		String uid = principal.getName();
		
		int result = service.selectMsgTotal(uid);
		model.addAttribute("result", result);
		
		int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);

        int total = service.selectCountTotal(uid);
        int lastPageNum = service.getLastPageNum(total);
        int pageStartNum = service.getpageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		List<MessageVO> msgs = service.selectMsgs(start,uid);
		model.addAttribute("msgs", msgs);
		model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
        model.addAttribute("uid", uid);
		
		return "message/message";
	}

	@ResponseBody
	@GetMapping("message/view")
	public Map<String, MessageVO> view(@RequestParam(value="msgNo", required = false) String msgNo) {
		MessageVO message = service.selectMsg(msgNo);
		Map<String, MessageVO> map = new HashMap<>();
		map.put("result", message);
		
		if(message != null) {
			service.updateMsg(msgNo);
		}
		
		return map;
	}
}
