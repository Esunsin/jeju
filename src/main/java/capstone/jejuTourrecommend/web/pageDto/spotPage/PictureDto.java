package capstone.jejuTourrecommend.web.pageDto.spotPage;

import capstone.jejuTourrecommend.domain.Picture;
import lombok.Data;

import java.util.List;

@Data
public class PictureDto {

    //        "picture": {
//            "id": 1,
//                    "urls":
//						[
//            "url": "http//~~",
//								...
//						]
//        },

    private Long id;
    private String url;

    public PictureDto(Picture picture) {
        this.id = picture.getId();
        this.url = picture.getUrl();
    }
}












