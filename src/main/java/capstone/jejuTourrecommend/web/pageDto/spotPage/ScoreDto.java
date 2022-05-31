package capstone.jejuTourrecommend.web.pageDto.spotPage;

import capstone.jejuTourrecommend.domain.Score;
import lombok.Data;

@Data
public class ScoreDto {

    //        "score": {
//            "viewScore": 11.2, //뷰 점수
//                    "priceScore": 14.2, //가격 점수
//                    "facilityScore": 13.3, //시설 점수
//                    "surroundScore": 12.3, //주변 점수
//
//                    "viewRank": 43.2, //뷰 순위
//                    "priceRank": 12.2, //가격 순위
//                    "facilityRank": 1.1, //시설 순위
//                    "surroundRank": 14.2, //주변 순위
//        },

    private Long id;

    private Double viewScore;
    private Double priceScore;
    private Double facilityScore;
    private Double surroundScore;

    private Double viewRank;
    private Double priceRank;
    private Double facilityRank;
    private Double surroundRank;

    public ScoreDto(Long id, Double viewScore, Double priceScore,
                    Double facilityScore, Double surroundScore,
                    Double viewRank, Double priceRank,
                    Double facilityRank, Double surroundRank) {
        this.id = id;
        this.viewScore = viewScore;
        this.priceScore = priceScore;
        this.facilityScore = facilityScore;
        this.surroundScore = surroundScore;
        this.viewRank = viewRank;
        this.priceRank = priceRank;
        this.facilityRank = facilityRank;
        this.surroundRank = surroundRank;
    }

    public ScoreDto(Score score){

        this.id = score.getId();
        this.viewScore = score.getViewScore();
        this.priceScore = score.getPriceScore();
        this.facilityScore = score.getFacilityScore();
        this.surroundScore = score.getSurroundScore();
        this.viewRank = score.getViewRank();
        this.priceRank = score.getPriceRank();
        this.facilityRank = score.getFacilityRank();
        this.surroundRank = score.getSurroundScore();


    }




}
