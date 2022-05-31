package capstone.jejuTourrecommend.web.pageDto.routePage;


import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteSpotListDto;
import capstone.jejuTourrecommend.web.pageDto.mainPage.SpotListDto;
import lombok.Data;

import java.util.List;

@Data
public class ResultFavoriteSpotList {

    private Long status;
    private boolean success;
    private String message;
    private List<FavoriteSpotListDto> favoriteSpotListDtos;

    public ResultFavoriteSpotList(Long status, boolean success, String message, List<FavoriteSpotListDto> favoriteSpotListDtos) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.favoriteSpotListDtos = favoriteSpotListDtos;
    }
}





