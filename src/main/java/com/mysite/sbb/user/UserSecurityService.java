package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

//사용자 정보를 폼에서 넘겨 받아서 인증을 처리함.

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {//스프링 시큐리티의 UserDetailsService는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스

    private final UserRepository userRepository;

    @Override	//DB에 있는 Username , password와 비교
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        
        //폼에서 넘어오는 username을 DB에서 select해서 siteUser객체에 담은 값을 Optional에서 뽑아옴
        //siteUser : DB에서 select한 레코드
        SiteUser siteUser = _siteUser.get();
        
        //GrantedAuthority 인증된 사용자를 담는 framework 내부 ????
        //Authentication (인증) : Idnetity(ID) + Password를 확인하는 것
        //Authorization (허가) : 인증된 사용자에게 사이트의 서비스를 쓸 수 있도록 권한을 부여하는 것
        	//계정이 admin이라면 ADMIN Role을 적용
        	//계정이 admin이 아니라면 USER Role을 적용
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
