package capstone.jejuTourrecommend.web.login.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @Email(message = "이메일 형식이어야 합니")
    private String email;

    @NotEmpty(message = "공백을 입력할수 없습니다")
    private String password;

}







