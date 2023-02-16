package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// DI (생성자를 통한 객체주입)
@Service
public class QuestionService {
	//JPA 메소드를 사용하기 위해 (생성자를 이용한 객체 자동 주입 DI) 
	private final QuestionRepository questionRepository; //questionrepository안에있는dao메소드사용하기위함
	
	//메소드 : question테이블의 List 정보를 가지고 오는 메소드 <2월14일 수정됨 : paging 처리를 위해>
//	public List<Question> getList() {
//		return this.questionRepository.findAll();
//	}
	
	//Controller에서 getList()메소드 호출 시 출력할 page 번호를 매개변수로 입력:0,1,2,3
	public Page<Question> getList(int page){
		//최신 글 먼저 출력하기, 날짜 컬럼(createDate)을 desc해서 출력
		List<Sort.Order> sorts= new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		
		//Pageable 객체에 두 개의 값을 담아서 매개변수로 던짐 , 10<==출력할 레코드 수 
		Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
		
		return this.questionRepository.findAll(pageable);
	}
	
	
	
	//상세 페이지를 처리하는 메소드 : id를 받아서 Question 테이블을 select (findById(1))
//		해서 select한 레코드를 Question 객체에 담아서 리턴
	public Question getQuestion(Integer id) {
		
		//select * from question where id = ?
		Optional<Question> op = this.questionRepository.findById(id);
		if(op.isPresent()) {	//op에 값이 존재하는 경우
			return op.get();	//Question 객체 리턴
		}else {
			//사용자 정의 예외 : 
			//throw : 예외를 강제로 발생
			//throws : 예외를 요청한 곳에서 처리하도록 미루는 것
			throw new DataNotFoundException("요청한 파일을 찾을 수 없습니다.");
		}
	}
	public void create(String subject,String content, SiteUser user) {
		Question q = new Question();
		//Question객체를 생성 후 setter 주입
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		
		//Repository의 save() 에 Question 객체 저장
		this.questionRepository.save(q);		//DB에 insert
	}
}
