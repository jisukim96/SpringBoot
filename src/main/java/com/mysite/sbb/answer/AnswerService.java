package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	//생성자를 통한 객체 주입 : DI
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	//답변글을 저장하는 메소드 , Controller 에서 Question 생성해서 arguments로 인풋 
	public Answer create(Question question, String content,SiteUser author) {
		
		//Answer 객체를 생성 후 아규먼트로 넘어오는 값을 세터주입
		Answer answer = new Answer();
		
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		
		this.answerRepository.save(answer);
		return answer;
	}
	
	//기존 답변 작성자와 수정자가 다른 경우 예외발생
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DataNotFoundException("answer not found");
		}
	}
	//답변내용 수정, 수정일자로 날짜 수정
	public void modify(Answer answer,String content) { 
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
}
