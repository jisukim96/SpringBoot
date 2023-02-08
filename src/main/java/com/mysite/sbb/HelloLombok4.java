package com.mysite.sbb;

//import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@NoArgsConstructor
@ToString
@RequiredArgsConstructor	//생성자 생성시 필드 이름앞에 final이 들어있는 필드만 아규먼트로 생성
//@AllArgsConstructor
public class HelloLombok4 {

	private final String hello;		//필드 : private
	private  int lombok;			//필드 : private
	
	/*	@RequiredArgsConstructor : 필드 이름앞에 final 키가 할당된 필드만 아규먼트로 등록
	 	public HelloLombok4(String hello){
	 		this.hello = hello;
	 	}
	 */
	public static void main(String[] args) {
		// @RequiredArgsConstructor 테스트
		HelloLombok4 lombok4 = new HelloLombok4("안녕"); 
		
		//toString 
		System.out.println(lombok4);
	}

}
