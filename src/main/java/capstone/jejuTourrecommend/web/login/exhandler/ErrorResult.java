package capstone.jejuTourrecommend.web.login.exhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

//    {
//        "status": 400,
//            "success": false,
//            "message": "이미 존재하는 이메일입니다."
//    }

    private int status;
    private Boolean success;
    private String message;

}



