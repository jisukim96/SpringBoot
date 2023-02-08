package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller		//SpringBoot Framework에 HelloController라는 빈(Bean) 등록
public class HelloController {

	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {	//리턴 : String
		
		return "Hello World - My Web Site";
		
		
	}
	
}
