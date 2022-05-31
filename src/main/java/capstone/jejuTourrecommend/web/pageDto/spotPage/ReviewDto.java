package capstone.jejuTourrecommend.web.pageDto.spotPage;

import capstone.jejuTourrecommend.domain.Review;
import capstone.jejuTourrecommend.domain.Spot;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class ReviewDto {


//        "review": {
//            "id": 2,
//                    "contents": [
//            content: "주변시설이 좋아요",
//							...
//						]
//        }

    private Long id;
    private String content;

    //@QueryProjection
    public ReviewDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
    }
}








