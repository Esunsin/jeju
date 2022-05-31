package capstone.jejuTourrecommend.web.pageDto.routePage;

import capstone.jejuTourrecommend.domain.Location;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RouteSpotListDto {


    private Long spotId;
    private String spotName;
    private String spotAddress;
    private String spotDescription;
    private String url;

    private String location;


    @QueryProjection
    public RouteSpotListDto(Long spotId, String spotName, String spotAddress, String spotDescription, String url, Location location) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.spotAddress = spotAddress;
        this.spotDescription = spotDescription;
        this.url = url;
        this.location = location.getKrName();
    }



}
