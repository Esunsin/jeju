package capstone.jejuTourrecommend.web.login.jwt;


import capstone.jejuTourrecommend.web.login.exceptionClass.JwtException;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {//jwt토큰 제공자

    private String secretKey = "webfirewood";

    private String refreshKey = "webfirewood1";


    private final long tokenValidTime = 40 * 60 * 1000L; // 토큰 유효시간 40분
    //private long tokenValidTime = 1L;

    private final long refreshTokenValidTime = 60 * 60 * 24 * 7 * 1000L;   // 1주


    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        refreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes());
    }


    /**
     * 나여기서 권한 한개만 갖는걸로 함 list<String> roles 을 string role으로 바꿈
     */
    // JWT 토큰 생성
    //여기서 내가 넣었준 값은 회원 이메일과 역할이다
    public String createToken(String userPk, String role) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        //setSubject 는 unique 값으로 설정한다. 내가 설정한 email 은 unique 함

        claims.put("roles", role); // 정보는 key / value 쌍으로 저장된다.

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                                                                // signature 에 들어갈 secret 값 세팅
                .compact();
    }

    public String createRefreshToken(String userPk, String role) {

        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        //setSubject 는 unique 값으로 설정한다. 내가 설정한 email 은 unique 함

        claims.put("roles", role); // 정보는 key / value 쌍으로 저장된다.

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshKey)
                .compact();
    }


    // JWT 토큰에서 인증 정보 조회
    // loadUserByUsername 으로 회원정보를 가져옴 (참고로 나는 pk를 username 이 아니라 email 로 함)
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //access 토큰에서 회원 정보(이메일) 추출******
    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //refresh 토크에서 "member 이메일" 가져오기
    public String getUserPkByRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(refreshKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    // Request의 Header에서 token 값을 가져옵니다. "ACCESS-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
//        if(StringUtils.hasText(request.getHeader("ACCESS-TOKEN"))){
//            throw new JwtException("ACCESS-TOKEN 값을 넣어주십쇼");
//        }

        return request
                .getHeader("ACCESS-TOKEN");
    }//이거 없어도 됨 @RequestHeader어노테이션이 있음//아님 있어야 함 filter에서 씀

    public String resolveRefreshToken(HttpServletRequest request) {
//        if(StringUtils.hasText(request.getHeader("REFRESH-TOKEN"))){
//            throw new JwtException("REFRESH-TOKEN 값을 넣어주십쇼");
//        }

        return request.getHeader("REFRESH-TOKEN");//REFRESH-TOKEN
    }


    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);

            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());// 만료날짜가 현재날짜(Date)이전이지 않으면 반환

        } catch (Exception e) {         // 이후면 false 반환
            log.info("만료된 토큰입니다");

            return false;
        }
    }


    public boolean isValidRefreshToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(refreshKey)
                    .parseClaimsJws(token);

            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());// 만료날짜가 현재날짜(Date)이전이지 않으면 반환

        } catch (Exception e) {         // 이후면 false 반환
            log.info("만료된 토큰입니다");
            //throw new UserException("만료된 혹은 잘못된 토큰입니다");
            return false;
        }
    }



}






