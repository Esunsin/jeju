package capstone.jejuTourrecommend.web.pageDto.spotPage;


import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ReviewListDto {

    private Long status;
    private boolean success;
    private String message;

    private Page<ReviewDto> reviewListDto;

    public ReviewListDto(Long status, boolean success, String message, Page<ReviewDto> reviewListDto) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.reviewListDto = reviewListDto;
    }
}
