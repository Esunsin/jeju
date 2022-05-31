package capstone.jejuTourrecommend.web.controller;

import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.domain.Service.LoginService;
import capstone.jejuTourrecommend.web.login.JoinDto;
import capstone.jejuTourrecommend.web.login.LoginDto;
import capstone.jejuTourrecommend.web.login.UserDto;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import capstone.jejuTourrecommend.web.login.exhandler.ErrorResult;
import capstone.jejuTourrecommend.web.login.jwt.JwtTokenProvider;
import capstone.jejuTourrecommend.web.login.form.JoinForm;
import capstone.jejuTourrecommend.web.login.form.LoginForm;
import capstone.jejuTourrecommend.web.login.jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Controller 에서 회원 가입과 로그인을 통한 인증 과정
@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;

    private final LoginService loginService;

    /**
     * RequestBody 로
     */
    //회원가입 (참고)회원가입할때는 accesstoken 반환할 필요 없음
    @PostMapping("/join")
    public JoinDto join(@Valid @RequestBody JoinForm form) {


        log.info("username={} ,email={}, password={}",form.getUsername(),
                form.getEmail(),form.getPassword());

        UserDto userDto = loginService.join(form);


        return new JoinDto(200,true,"회원가입 성공",userDto);

    }

    //지금 로그인 정보를 http body 로 줘서 @RequestBody로 한거고 요청파라미터로 주면 @ModelAttribute로 객체에 담으면 됨
    // 로그인
    @PostMapping("/login")
    public LoginDto login(@Valid @RequestBody LoginForm form) throws IOException {

        log.info("email={}, password={}",form.getEmail(),form.getPassword());

        UserDto userDto = loginService.login(form.getEmail(), form.getPassword());

        //로그인할때 이제서야 accestoken를 넘겨줌
        String accesstoken = jwtTokenProvider.createToken(userDto.getEmail(), userDto.getRole());

        return new LoginDto(200,true,"로그인 성공",userDto,accesstoken);

    }



    /**
     * 파라미터 버전
     */
    //회원가입하기
    //@PostMapping("/join")//@valid 가 오류를 잡아줘서  bindingResult에 들어감
    public JoinDto join1(@Valid @ModelAttribute JoinForm form, BindingResult bindingResult,
                         HttpServletResponse response) throws IOException {

        //여기서 실패하면 이렇게가 실으면 따로 로그인만의 오류 클래스 따로 만들어도됨
        if(bindingResult.hasErrors()){//공백 예외 처리
            log.info("errors = {}",bindingResult);
            //오류 상태 따로 클래스로 만들어도 좋음
            //bindingResult.reject("오류오류오륭ㅇ로", new ErrorResult(200,false,"sss"),"sss");
            String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new UserException(defaultMessage);
        }

        log.info("username={} ,email={}, password={}",form.getUsername(),
                form.getEmail(),form.getPassword());

        UserDto userDto = loginService.join(form);

        //회원가입할때는 accesstoken반환할 필요 없지
        //String accesstoken = jwtTokenProvider.createToken(userDto.getEmail(), userDto.getRole());

        return new JoinDto(200,true,"회원가입 성공",userDto);
    }

    //로그인하기
    //@PostMapping("/login")
    public LoginDto login1(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                           HttpServletResponse response) throws IOException {

        log.info("email={}, password={}",form.getEmail(),form.getPassword());


        //여기서 실패하면 이렇게가 실으면 따로 로그인만의 오류 클래스 따로 만들어도됨
        if(bindingResult.hasErrors()){//공백 예외처리
            log.info("errors={}",bindingResult);
            String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new UserException(defaultMessage);
        }

        UserDto userDto = loginService.login(form.getEmail(), form.getPassword());

        //로그인할때 이제서야 accestoken를 넘겨줌
        String accesstoken = jwtTokenProvider.createToken(userDto.getEmail(), userDto.getRole());

        return new LoginDto(200,true,"로그인 성공",userDto,accesstoken);

    }

    @GetMapping("/token/refresh")
    public TokenResponse refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        return loginService.issueAccessToken(request);


    }

    //Access Token을 재발급 위한 api
//    @PostMapping("/issue")
//    public ResponseEntity issueAccessToken(HttpServletRequest request) throws Exception {
//        return ResponseEntity
//                .ok()
//                .body(userService.issueAccessToken(request));

//    }


    @GetMapping("/user/test")
    public Map userResponseTest() {

        //생각해보니깐 알아서 검증이 되네 그 로직은 jwt 패키지에 있음

        Map<String, String> result = new HashMap<>();

        result.put("result", "user ok");
        return result;
    }
    //회원가입시 기본 권한은 user 다

    @PostMapping("/admin/test")
    public Map adminResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result", "admin ok");
        return result;
    }




}










