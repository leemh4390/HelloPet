package kr.co.hellopet.controller.my;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

	@GetMapping("my/info")
	public String info() {
		return "my/info";
	}

	@GetMapping("my/myArticle")
	public String myArticle() {
		return "my/myArticle";
	}
	
	@GetMapping("my/myQna")
	public String myQna() {
		return "my/myQna";
	}
	
	@GetMapping("my/myReserve")
	public String myReserve() {
		return "my/myReserve";
	}
}
