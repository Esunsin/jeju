package capstone.jejuTourrecommend.web.login.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Slf4j
//@RequiredArgsConstructor
public class CustomAuthorizationFilter{ /* extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    //여기서는 access토큰 인증 처리 안하는 경로
    private static final String[] whiteList = {"/login","/token/refresh","/join"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        //"/token/refresh"이 경로는 잘못된 access토큰을 대체하기 위한거니깐 당연히 제외되어야 하는 경로임

        if(isNoAccessTokenCheckPath(requestURI)){
        //if(request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh")) {
            filterChain.doFilter(request, response);
        } else {

            //우선 받은 accesstoken부터 가져와서 검사할것임
            String accessToken = jwtTokenProvider.resolveToken(request);
            if(accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                try{
                    // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
                    // 이 함수 안에 loadUserByUsername 있음(즉 검증작업을 하겠다는 것임)
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

                    //인증이 성공하면 스프링이 관리하는 SecurityContext 에 Authentication 인증 객체를 저장합니다.
                    //이객체는 반드시 Authentication 의 구현체만 가능하다
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    //Token에 대한 유효성을 검사하고, 만약 유효성 검증이 성공한다면, SecurityContextHolder에 해당 Authentication을 잡는다
                    log.info("토큰이 유효하다");

                    filterChain.doFilter(request, response);
                }catch (Exception e) {//유하지 않은 access토큰일 때 에러 반환
                    //유효하지 않은 accesstoken임, 그래서 에러는 반환할 것임
                    log.error("Error logging in: {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(401);

                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);

                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
    /**
     * 화이트 리스트의 경우 인증체크 안함
     */
//    private boolean isNoAccessTokenCheckPath(String requestURI){
//        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
//    }
}
