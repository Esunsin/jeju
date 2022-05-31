package capstone.jejuTourrecommend.web.login.jwt;


import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.repository.MemberRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

//토큰에 저장된 유저 정보를 활용해야 하기 때문에 CustomUserDetatilService 라는 이름의 클래스를 만들고
//UserDetailsService를 상속받아 재정의 하는 과정을 진행한다
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    //private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    //loadUserByUsername 메서드에서 실제 db의 회원정보를 가져온다
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //그리고 여시서 UserDetails라는 인터페이스를 리턴한
        //UserDetails 는 스프링이 제공하는 User라는 구현차 사용//여기도 나름 수정 필요 토큰 저장 장소화 멤버 저장장소 분리 필요

        User user = memberRepository.findOptionByEmail(email)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));

        return user;

        //회원정보 못 찾으면 runtime 예외처리해서 따로 @Exceptional로 처리해주도 됨 지금 우리는 안함

//        return userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(Member member) {

        List<GrantedAuthority> grantedAuthorities = Collections
                .singletonList(new SimpleGrantedAuthority(member.getRole()));

        //TODO: 이쁘

        //TODO: username 에 email을 넣는 방법이 적합한지?
        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }

}















