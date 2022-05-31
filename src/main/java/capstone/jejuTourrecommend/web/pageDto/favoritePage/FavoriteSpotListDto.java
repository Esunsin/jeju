package capstone.jejuTourrecommend.web.pageDto.favoritePage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FavoriteSpotListDto {

    private Long spotId;
    private String spotName;
    private String spotAddress;
    private String spotDescription;
    private String url;

    //private Location location;




    @QueryProjection
    public FavoriteSpotListDto(Long spotId, String spotName, String spotAddress,
                       String spotDescription, String url) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.spotAddress = spotAddress;
        this.spotDescription = spotDescription;
        this.url = url;
    }

}
