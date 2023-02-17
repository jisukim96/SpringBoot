package com.mysite.sbb.question;

import java.time.LocalDateTime;		//자신의 시스템 로컬시간 설정
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
//persistance : JPA에서 사용된 어노테이션
import jakarta.persistence.Entity;	//JPA에서 적용된 어노테이션
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity			//자바 클래스를 DB의 테이블과 매핑하는 클래스-> 테이블명 : question 
public class Question {

	@Id //primary key
	@GeneratedValue (strategy = GenerationType.IDENTITY)	//시퀀스할당
	private Integer id;	//Primary Key , 시퀀스 (1,1)
	
	@Column(length = 200)	//값에 200자 까지 넣을 수 있다.
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;	//(카멜케이스 이름)->create_date : 실제 테이블 컬럼명
	
//	@Column(length = 300)
//	private String addr;
	
	//question 테이블에서 Answer 테이블을 참조하는 컬럼 생성 @OnetoMAny
	@OneToMany (mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	//question.getAnswerList();
	
	@ManyToOne	//Forieng key : site_user 테이블의 Primary key 참조 
	private SiteUser author; //여러개의 질문이 한 명의 사용자에게 작성될 수 있도록
	
	
	private LocalDateTime modifyDate; //수정일시
	
	@ManyToMany //질문과 추천인은 대등한 관계임으로
	Set<SiteUser> voter;	//추천인은 중복이 안되기 때문에 중복으르 허용하지 않는 자료형 Set으로 설정
}
