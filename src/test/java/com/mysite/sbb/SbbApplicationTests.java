package com.mysite.sbb;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	@Autowired		//객체 자동 주입, JPA의 CRUD할 수 있는 메소드가 적용되어있음.
	private QuestionRepository questionRepository;
	
	/* 조건에 맞는 레코드 하나만 가져오기*/
	@Test
	public void jpaTestget() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q = oq.get();
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
		}
	}
	
	
	
	
	/* Select List JUnit Test - findAll()
	@Test
	public void jpaTest() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(4,all.size()); //assertEquals(기대값, 실제값) , 성공(=두 값이 일치할 때)
		
		Question q = all.get(3);		// List all 변수에 담긴 0번방의 Question객체를 끄집어낸다.
		//assertEquals("sbb가 무엇인가요",q.getSubject() ); //성공
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent());		
		
	}
	*/
	/* Insert JUnit Test , JPA 인터페이스에 정의된 save() 
	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());	//현재 시간을 setter에 저장
		this.questionRepository.save(q1);		//첫번째 질문 저장
		//자바객체의 메소드를 통해 insert하는 것
		
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다");
		q2.setContent("id 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());	//현재 시간을 setter에 저장
		this.questionRepository.save(q2);		
		
		}
		*/
	

}
