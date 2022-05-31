package capstone.jejuTourrecommend.web.pageDto.favoritePage;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class GetFavoriteListDto {

    private Page<FavoriteDto> favoriteDtos;
    private String url;

    public GetFavoriteListDto(Page<FavoriteDto> favoriteDtos, String url) {
        this.favoriteDtos = favoriteDtos;
        this.url = url;
    }
}
