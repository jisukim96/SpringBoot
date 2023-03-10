package com.mysite.sbb.answer;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
		//2월 20일 답변을 등록후 해당 답변으로 이동하도록 앵커 기능 추가
		Answer answer = this.answerService.create(question,
						answerForm.getContent(), siteUser);
						
		return String.format("redirect:/question/detail/%s#answer_%s",
				answer.getQuestion().getId(), answer.getId());	
	}
	//url의 답변 아이디를 통해 조회한 답변 내용을 가져오기위함
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm,@PathVariable("id") Integer id,Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());		
		return "answer_form";
	}
	//수정-post방식의 url처리를 위함
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
            @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        //2월 20일 답변을 수정후 해당 답변으로 이동하도록 앵커 기능 추가
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal,@PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
		}
		this.answerService.delete(answer);
		//2월 20일 답변을 삭제 후 해당 답변으로 이동하도록 앵커 기능 추가
		return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
	}
	//답변-추천버튼 호출되는 URL
	@PreAuthorize("isAuthenticated()")	//로그인 한 사람만 추천가능
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUser);		//vote메소드 호출하여 추천인 저장
		return String.format("redirect:/question/detail/%s",answer.getQuestion().getId());
	}
	
}
