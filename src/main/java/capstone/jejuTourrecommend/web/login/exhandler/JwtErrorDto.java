package capstone.jejuTourrecommend.web.login.exhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtErrorDto {

    private int status;
    private Boolean success;
    private String message;
    private String accesstoken;

}
