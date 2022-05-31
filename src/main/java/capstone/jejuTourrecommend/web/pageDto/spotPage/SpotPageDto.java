package capstone.jejuTourrecommend.web.pageDto.spotPage;

import lombok.Data;

@Data
public class SpotPageDto {

//    "status": 200,
//            "success": true,
//            "message": "초기 메인 페이지 보여주기 성공",

    private Long status;
    private boolean success;
    private String message;

    private SpotDetailDto data;

    public SpotPageDto(Long status, boolean success, String message, SpotDetailDto data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
