package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return "Test 코드 블락입니다. - MyWebSite - 추가된 내용입니다. 추추가";
	}
}
