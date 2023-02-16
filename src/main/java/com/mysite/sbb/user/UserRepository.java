package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long>{

	
	
	//JPA는 SQL 쿼리를 사용하지 않고 JPA메소드가 SQL쿼리로 변환해서 처리
	
	//findAll()
	//findById()
	//save() 	<== insert, Update
	//delete() 	<==delete
	
	//로그인 처리하기 위해서 사용자 정보를 입력받아서 DataBase에서 select해서 SiteUser객체에 저장함.
	//Select * from site_user where username =?
	Optional<SiteUser> findByusername(String username);
}
