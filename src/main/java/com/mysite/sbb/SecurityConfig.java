package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration			//스프링의 환경 설정 파일임을 의미. 스프링 시큐리티의 설정을 위해 사용
@EnableWebSecurity		//모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만듦
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests().requestMatchers(
				
				//Spring Security 에서 모든 URL에서 인증없이 접근할 수 있도록 허용함
                new AntPathRequestMatcher("/**")).permitAll()
			//H!-DataBase는 http로 통신하는 DataVase이므로 csrf 적용되지 않도록 설정
			.and()	//http 객체의 설정을 이어서 할 수 있게 하는 메소드
				.csrf().ignoringRequestMatchers( //
						new AntPathRequestMatcher("/h2-console/**")) ///h2-console/로 시작하는 URL은 CSRF 검증을 하지 않는다는 설정
			.and()
				.headers()
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
						XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
			//Spring Security 로그인 처리부분
			.and()
				.formLogin()
				.loginPage("/user/login")
				.defaultSuccessUrl("/")		//로그인 성공 시 세션에 로그인 정보를 담고 '/'페이지로 이동
			
			//Spring Security 로그아웃 처리부분
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)	//세션에 담긴 모든 값을 삭제
				;
        return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean		//스프링 시큐리티 인증 담당
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
