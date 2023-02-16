package com.mysite.sbb.answer;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	// http://localhost:9696/answer/create/1 요청에 대한 답변글 등록 처리 - 나중에 서비스로 변경
	private final UserService userService;

	@PreAuthorize("isAuthenticated()") //로그인이 필요한 메소드
	@PostMapping("/create/{id}")
	public String createAnswer(
		//<<Validation 전 구성>>	
		//	Model model, @PathVariable("id") Integer id, @RequestParam String content
			
		//content의 유효성 검사	
			Model model, @PathVariable("id") Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult , Principal principal) {
			
		//답변 내용을 저장하는 메소드 호출 (Service에서 호출)
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		//content의 값이 비어있을 때
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}
		
		this.answerService.create(question, answerForm.getContent(),siteUser);
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	
}
