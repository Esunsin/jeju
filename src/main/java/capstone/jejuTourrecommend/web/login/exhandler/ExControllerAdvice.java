package capstone.jejuTourrecommend.web.login.exhandler;

import capstone.jejuTourrecommend.web.login.exceptionClass.JwtException;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j//*****************여기 패치지 지정한거 보셈*****이렇게 페키지 별로 오류설정 가능함(로그인이면 로그인 api면 api)
@RestControllerAdvice(basePackages = "capstone.jejuTourrecommend.web")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)//IllegalArgumentException 오류 오면 반응함
    public ErrorResult illegalExhandler(IllegalArgumentException e){
        log.info("[exceptionHandler] ex",e);

        //IllegalArgumentException 오류 발생했을때 e에 메세지도 같이 들어 있음
        // 그것도 ErrorResult 에 넣어서 객체 반환해줌
        return new ErrorResult(400,false,e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalStateExhandler(IllegalStateException e){
        log.info("[exceptionHandler] ex",e);

        return  new ErrorResult(400,false,e.getMessage());
    }

    //ResponseEntity 에 에러를 담아서 보냄
    @ExceptionHandler(UserException.class)
    public ErrorResult userExHandler(UserException e){
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult(400,false,e.getMessage());
    }

    //회원가입, 로그인에서 공백을 입력 못하게
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> userFormExHandler(MethodArgumentNotValidException e){
        log.error("[exceptionHandler] ex",e);//오류가 여러개여도

        Map<String, Object> errors = new LinkedHashMap<>();//순서대로 출력하려고 linkedHashMap 으로 함

        errors.put("status",400);
        errors.put("success",false);

        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));


        return ResponseEntity.badRequest().body(errors);
    }




    @ExceptionHandler(JwtException.class)
    public ErrorResult JwtExceptionExhandler(JwtException e){
        log.info("[exceptionHandler] ex",e);

        return new ErrorResult(400,false,e.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult(400,false,"내부 오류");
    }
}




