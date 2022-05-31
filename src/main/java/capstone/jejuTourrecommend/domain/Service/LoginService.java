package capstone.jejuTourrecommend.domain.Service;

import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.repository.MemberRepository;
import capstone.jejuTourrecommend.web.login.UserDto;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import capstone.jejuTourrecommend.web.login.form.JoinForm;
import capstone.jejuTourrecommend.web.login.jwt.JwtTokenProvider;
import capstone.jejuTourrecommend.web.login.jwt.TokenResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */
    public UserDto join(JoinForm form){

        //유저 중복성 검사(예외처리)
        validateDuplicateMember(form.getEmail());

        //비밀번호 암호화 하여 디비에 저장하기위한 작업//테스트를 위해 일단 사용X
        //String encodedPassword = passwordEncoder.encode(form.getPassword());


        //이게 refresh토큰을 만들고 db에 저장해주기(회원가입때는 accesstoken생성하지 않음, 로그인할때 사용합)
        //String refreshToken = jwtTokenProvider.createRefreshToken(form.getEmail(),"ROLE_USER");

        Member member = new Member(
                form.getUsername(), form.getEmail(), form.getPassword(),"ROLE_USER"
        );
        //유저 저장
        memberRepository.save(member);


        UserDto userDto = memberRepository.findOptionByEmail(form.getEmail())
                .map(member1 -> new UserDto(member1.getId(), member1.getUsername(),
                        member1.getEmail(),member1.getRole(),member1.getCreatedDate(),
                        member1.getLastModifiedDate()))
                .orElseThrow(() -> new UserException("db에 회원 저장인 안됐습니다. "));

        //String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());


        return userDto;
    }

    //로그인 정보가 일치하는지 확인
    public UserDto login(String email, String password){
        log.info("email={}, password={}",email,password);

        Member member = memberRepository.findOptionByEmail(email)
                .orElseThrow(() -> new UserException("가입되지 않은 E-MAIL 입니다."));

        /*//테스트를 위해 비밀번호 인코딩 안해놓음
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new UserException("잘못된 비밀번호입니다.");
        }*/

        log.info("password = {}",password);
        log.info("member.getPassword() = {}",member.getPassword());
        if (!member.getPassword().equals(password)) {
            throw new UserException("잘못된 비밀번호입니다.");
        }


        UserDto userDto = new UserDto(
                member.getId(),member.getUsername(),member.getEmail(),member.getRole()
        ,member.getCreatedDate(),member.getLastModifiedDate());


        return userDto;
    }

    //회원 중복 검사
    private void validateDuplicateMember(String email) {

        //이메일로 회원 조회
        memberRepository.findOptionByEmail(email)
                .ifPresent(member-> {
                    throw new UserException("이미 존재하는 E-MAIL입니다");
                });

    }

    //이부분 없어도 됨
    public TokenResponse issueAccessToken(HttpServletRequest request){
        String accessToken = jwtTokenProvider.resolveToken(request);

        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        log.info("accessToken = {}", accessToken);
        log.info("refreshToken = {}", refreshToken);

//        Member member1 = memberRepository.findOptionByRefreshToken(refreshToken)
//                .orElseThrow(() -> new UserException("db와 일치하지 않음"));
//        log.info("db와 일치");
        //log.info("member1.getRefreshToken() = {}",member1.getRefreshToken());

        //accessToken이 만료됐고 refreshToken이 맞으면 accessToken을 새로 발급(refreshToken의 내용을 통해서)
        if(!jwtTokenProvider.validateToken(accessToken)){  //클라이언트에서 토큰 재발급 api로의 요청을 확정해주면 이 조건문은 필요없다.
            log.info("Access 토큰 만료됨");

            if(jwtTokenProvider.isValidRefreshToken(refreshToken)){     //들어온 Refresh 토큰이 유효한지
                log.info("Refresh 토큰은 유효함");

                //받은 refresh토큰을 가지고 db에서 찾아보기

                String userPkByRefreshToken = jwtTokenProvider.getUserPkByRefreshToken(refreshToken);

                Member member = memberRepository.findOptionByEmail(userPkByRefreshToken)
                        .orElseThrow(() -> new UserException("일치하지 않은 refresh 토큰입니다"));

                if(refreshToken.equals(member.getRefreshToken())) {   //DB의 refresh 토큰과 지금들어온 토큰이 같은지 확인
                    log.info("accesstoken 을 다시 만들어주었습니다");
                    accessToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole());
                }
                else{
                    //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                    throw new UserException("변조된 refresh 토큰이 들어왔습니다");
                    //예외발생
                }
            }else{//Refresh 토큰이 제대로 안들어올 경우
                throw new UserException("들어온 Refresh 토큰은 유효하지 않습니다. ");
            }
        }
        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }




}























