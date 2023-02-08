package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	@Autowired		//객체 자동 주입, JPA의 CRUD할 수 있는 메소드가 적용되어있음.
	private QuestionRepository questionRepository;
	
	@Autowired		//객체 자동 주입(DI : Dependency Injection) , JPA의 메소드를 사용, findAll(), findById(),save(),delete()
	private AnswerRepository answerRepository;
	
	@Test
	public void testAnswerjpa() {
		// 1. Question(부모) 테이블의 답변을 처리할 레코드를 먼저 select 한다. findById(1)
		Optional<Question> op =
			this.questionRepository.findById(3);
		Question q = op.get();
		
		// 2. Answer 엔티티 테이블의 객체 생성을 하고, setter를 사용해서 값을 넣는다.
		//		setQuestion(q)
		Answer a = new Answer();
		a.setQuestion(q);		//Question 테이블의 select된 id컬럼
		a.setContent("3 - sbb에 대한 답변3입니다.");
		a.setCreateDate(LocalDateTime.now());
		
		// question필드의 값을 넣을 때는 부모에서 가져온 값을 세터주입함
		// 3. save 메소드를 사용해서 저장
		this.answerRepository.save(a);
	}
	
	/* 데이터 삭제 : JPA 메소드 : delete() 
	@Test
	public void testjpa6() {
		//1. 테이블(DB)에서 삭제할 레코드를 가지고 옴
		Optional<Question> op =
				this.questionRepository.findById(2);
		//2. Optional에 저장된 객체를 끄집어 낸다.
		Question q = op.get();
		
		//3. delete(q)
		this.questionRepository.delete(q);
	}
	*/
	
	/* 데이터 수정 : save() 
	@Test
	public void testjpa5() {
		//1.수정할 객체를 findById() 메소드를 사용해서 가지고 온다.
		Optional<Question> op = 
				this.questionRepository.findById(1);
		//2. 가지고 온 객체를 끄집어내서 setter를 사용해서 수정한다.(하나의 값만 가져오기때문에 번호를 넣을 필요없음)
		Question q = op.get();
		q.setSubject("수정된 제목");
		q.setContent("수정된 내용");
		//3. 수정된 객체를 save(q) 안에 넣어 업데이트 시킨다.
		this.questionRepository.save(q);
	}
	*/
	/* 테이블의 모든 레코드 정렬 : asc, desc
	@Test
	public void testjpa4() {
		List<Question> all =
		this.questionRepository.findAllByOrderByCreateDateAsc();
		
		List<Question> all2 =
		this.questionRepository.findAllByOrderByCreateDateDesc();
		 
		System.out.println("총 리스트에 있는 객체 수 :" + all.size());
		System.out.println("==== AESC 정렬 후 출력 ================");
		
		for( int i = 0; i < all.size(); i++) {
			Question q = all.get(i);
			
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
			System.out.println("====================");
		}
		System.out.println("==== DESC 정렬 후 출력 ================");

		for( int i = 0; i < all2.size(); i++) {
			Question q = all2.get(i);
			
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
			System.out.println("====================");
		}
	}
	*/
	
	
	/* 특정 조건을 사용해서 검색 후 정렬해서 출력
	@Test
	public void testjpa3() {
		List<Question> or = 
		this.questionRepository.findBySubjectLikeOrderByCreateDateAsc("%sbb%");
		
		List<Question> or2 = 
		this.questionRepository.findBySubjectLikeOrderByCreateDateDesc("%sbb%");
		
		System.out.println("검색된 레코드 수 : " + or.size());
		
		System.out.println(" ASC : 오름차순 정렬 ");
		
		for(int i = 0; i<or.size();i++) {
		Question q = or.get(0);
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getCreateDate()); //날짜 기준으로 정렬
		System.out.println("======================");
		
		}
		System.out.println(" DESC : 내림차순 정렬");
		
		for(int i = 0; i<or2.size();i++) {
			Question q = or2.get(0);
			
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getCreateDate()); //날짜 기준으로 정렬
			System.out.println("======================");
			
		}
		
	//	for( Question q : or) {
	//		System.out.println(q); 		//Question에서 toString 객체 재정의함 
	//	}
	}
	*/
	
	/* 두 컬럼을 OR 연산으로 검색 : subject , content
	@Test
	public void testjpa2() {
		List<Question> sq =  
		this.questionRepository.findBySubjectOrContentLike("%sbb%", "%생성%");
	
		Question q4 = sq.get(0);
		System.out.println("총 검색된 갯수 : "+ sq.size());
		System.out.println(q4.getId());
		System.out.println(q4.getSubject());
		System.out.println(q4.getContent());
			
	}
	*/
	
	/* 사용자 정의 select문 : subject 컬럼 , content 컬럼 검색 -> Like 사용
	@Test
	public void testjpa() {
		
		List<Question> all = this.questionRepository.findBySubjectLike("%sbb%");
		
		Question q = all.get(0);
		System.out.println("리스트에 검색된 레코드 수 : " + all.size());
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent());
		System.out.println("======================");
	
		List<Question> all2 = this.questionRepository.findByContentLike("%생성%");
		
		Question q3 = all2.get(0);
		System.out.println("리스트에 검색된 레코드 수 : " + all.size());
		System.out.println(q3.getId());
		System.out.println(q3.getSubject());
		System.out.println(q3.getContent());
		
	}
	*/
	
		
	
	
	
	
	/* 조건에 맞는 레코드 하나만 가져오기 - select + where : PK 컬럼은 findById(1)
	 * Question 테이블의 Primary Key 컬럼은 기본적으로 제공됨 : findById()
	 *  
	@Test
	public void jpaTestget() {
		Optional<Question> oq = this.questionRepository.findById(2);
		if(oq.isPresent()) {
			Question q = oq.get();
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
		}
	}
	*/
	
	/* Select List JUnit Test - findAll()
	@Test
	public void jpaTest() {
		List<Question> all = this.questionRepository.findAll(); //questionrepository 라는 엔티티가 
		//assertEquals(4,all.size()); //assertEquals(기대값, 실제값) , 성공(=두 값이 일치할 때)
		
		Question q = all.get(6);		// List all 변수에 담긴 0번방의 Question객체를 끄집어낸다.
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
