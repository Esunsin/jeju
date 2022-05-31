package capstone.jejuTourrecommend.web.login.form;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class JoinForm {

    @NotEmpty(message = "공백을 입력할수 없습니다")
    private String username;

    @Email(message = "이메일 형식이어야 합니")
    private String email;

    @NotEmpty(message = "공백을 입력할수 없습니다")
    private String password;

}
