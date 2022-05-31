package capstone.jejuTourrecommend.web.login.jwt;

import capstone.jejuTourrecommend.web.login.exceptionClass.JwtException;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//권한 확인하는 필터
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    //이 Filter를 조금 더 확장하여 스프링에서 제공하는 필터가 있는데 그것이 바로 GenericFilterBean이다

    private final JwtTokenProvider jwtTokenProvider;

    //Todo: 여기 지금 테스트하느라 넣은 화이트리스트 있음 마지막 배포때는 없애줄건 없애 줘야함
    private static final String[] whiteList = {"/login", "/token/refresh",
            "/join", "/test/*","/spotList/*","/error","/spot/*","/route/*","/favoriteList/*","/favoriteList","/favorite/*"};

    //이 필터가 요청을 가로채서 jwt토근이 유효한지 판단한다, 유효하면 다시 요청을 진행한다
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = httpRequest.getHeader("ACCESS-TOKEN");


        // 헤더에서 JWT 를 받아옵니다.
        //String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        try {
            log.info("인증 체크 필터 시작: 해당 url = {}", requestURI);
            // 유효한 토큰인지 확인합니다.
            if (isNoAccessTokenCheckPath(requestURI)) {//검증해야 하는 거면 아래 if 문 들어가기
                log.info("whitelist 에 포함되지 않은 url");

                if (accessToken != null) { //토크이 없을 경우
                    if (jwtTokenProvider.validateToken(accessToken)) {
                        // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                        // 이 함수 안에 loadUserByUsername 있음
                        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

                        //인증이 성공하면 스프링이 관리하는 SecurityContext 에 Authentication 인증 객체를 저장합니다.
                        //이객체는 반드시 Authentication 의 구현체만 가능하다
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        //Token에 대한 유효성을 검사하고, 만약 유효성 검증이 성공한다면, SecurityContextHolder에 해당 Authentication을 잡아주시면 됩니다.
                        log.info("토큰이 유효하다");
                    } else {
                        throw new JwtException("유효하지 않은 토큰입니다"); //여기서 예외를 던지면 아래의 catch 문으로 감
                    }
                } else {
                    throw new JwtException("토큰이 있어야 접근이 가능합니다");
                }
            }

            chain.doFilter(request, response);

        }
        catch (Exception e) {
            log.info("예외발생");
            httpResponse.setStatus(400);
            httpResponse.setHeader("ACCESS-TOKEN", accessToken);
            Map<String, Object> errors = new LinkedHashMap<>();

            errors.put("status",400);
            errors.put("success",false);


            errors.put("message", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            //throw new UserException(e.getMessage());
            new ObjectMapper().writeValue(response.getOutputStream(), errors);
            //throw e;
        } finally {
            log.info("필터 종료!");
        }

    }

    /**
     * 화이트 리스트의 경우 인증체크 안함
     */
    private boolean isNoAccessTokenCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

}

















