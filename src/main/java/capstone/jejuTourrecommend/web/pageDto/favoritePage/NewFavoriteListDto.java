package capstone.jejuTourrecommend.web.pageDto.favoritePage;

import lombok.Data;

@Data
public class NewFavoriteListDto {


    private Long status;
    private boolean success;
    private String message;
    private FavoriteDto favoriteDto;

    public NewFavoriteListDto(Long status, boolean success, String message, FavoriteDto favoriteDto) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.favoriteDto = favoriteDto;
    }
}
