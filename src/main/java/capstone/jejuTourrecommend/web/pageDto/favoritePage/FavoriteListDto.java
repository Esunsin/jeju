package capstone.jejuTourrecommend.web.pageDto.favoritePage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FavoriteListDto {

    private Long favoriteId;
    private String favoriteName;
    private String spotURL;

    @QueryProjection
    public FavoriteListDto(Long favoriteId, String favoriteName, String spotURL) {
        this.favoriteId = favoriteId;
        this.favoriteName = favoriteName;
        this.spotURL = spotURL;
    }
}
