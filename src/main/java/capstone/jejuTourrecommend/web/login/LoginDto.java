package capstone.jejuTourrecommend.web.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoginDto {

//    status: number,
//    success : boolean,
//    message: string,
//    data: {
//        user: {
//            id: number,  // 유저 아이디
//                    createdAt: string,  // 유저 생성 날짜
//                    updatedAt: string,  // 유저 업데이트 날
//        }
//        accesstoken: string  // 유저 토큰
//    }

    private int status;
    private Boolean success;
    private String message;
    private UserDto userDto;
    private String accesstoken;


}
