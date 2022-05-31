package capstone.jejuTourrecommend.web.pageDto.mainPage;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ResultSpotListDto {

    private Long status;
    private boolean success;
    private String message;
    private Page<SpotListDto> data;

    public ResultSpotListDto(Long status, boolean success, String message, Page<SpotListDto> data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
