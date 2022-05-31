package capstone.jejuTourrecommend.web.login.exceptionClass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "error.bad")
public class JwtException extends RuntimeException{

    public JwtException(String message) {
        super(message);
    }

}
