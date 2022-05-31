package capstone.jejuTourrecommend.web.pageDto.mainPage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SpotScoreDto {

    private Double viewScore;
    private Double priceScore;
    private Double facilityScore;
    private Double surroundScore;

    @QueryProjection
    public SpotScoreDto(Double viewScore, Double priceScore, Double facilityScore, Double surroundScore) {
        this.viewScore = viewScore;
        this.priceScore = priceScore;
        this.facilityScore = facilityScore;
        this.surroundScore = surroundScore;
    }

}








