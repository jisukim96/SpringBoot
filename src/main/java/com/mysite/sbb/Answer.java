package com.mysite.sbb;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity		//JPA에서 자바 객체를 DataBase 테이블에 매핑
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;				//Primary Key , 자동 증가(1,1)
	
	@Column(columnDefinition = "TEXT") 
	private String content;
	
	private LocalDateTime createDate;
	
	//하나의 질문(부모테이블-Question)에 대한 여러가지 답변(자식테이블-Answer)
	@ManyToOne		//Foreign Key : 부모테이블의 PK,UK컬럼의 값을 참조해서 값을 할당.
	private Question question;		//부모 테이블인 Question 테이블의 Primary Key를 참조(id 컬럼)
	
	
}
