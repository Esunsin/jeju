package capstone.jejuTourrecommend.web.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JoinDto {


//    {
//        "status": 200,
//            "success": true,
//            "message": "회원 가입에 성공하였습니다",
//            "data": {
//        "user": {
//            "id": 0,
//                    "name": "김팀블",
//                    "email": "teamble@gmail.com",
//                    "createdAt": "2021-11-37T01:06:14.2472",  *****
//            "updatedAt": "2021-11-37T01:06:14.2472",*****
//            "isDeleted": false,  *****
//        },
//        "accesstoken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTQsInVzZXJuYW1lIjoi7Jqw7JiBIiwic25zSWQiOiIxIiwic29jaWFsVHlwZSI6Imtha2FvIiwiaWF0IjoxNjEwMDA4NzU1LCJleHAiOjE2MTI2MDA3NTUsImlzcyI6Im1vdGlpdiJ9.1_CTWbQWps8vcpPUzzKuAYFy7Th4fqpIPummhhabEm8"
//    }
//    }

    private int status;
    private Boolean success;
    private String message;
    private UserDto userDto;


}












