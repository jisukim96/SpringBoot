package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	//JPA에서 Question 테이블을 Select,Insert,Update,Delete
	//Question 테이블을 SQL 쿼리를 사용하지 않고 JPA 메소드를 사용해서 CRUD하는 Repository
		//JpaRepository<Question,Integer>
				//Question : JPA메소드에서 쿼리할 엔티티 클래스
				//Integer: 엔티티 클래스의 Primary key 컬럼의 데이터 타입
}
