package kr.co.hellopet.controller.message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

	@GetMapping("message/message")
	public String message() {
		return "message/message";
	}
}
