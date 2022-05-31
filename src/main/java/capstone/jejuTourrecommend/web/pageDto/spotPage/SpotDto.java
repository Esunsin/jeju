package capstone.jejuTourrecommend.web.pageDto.spotPage;

import capstone.jejuTourrecommend.domain.Location;
import capstone.jejuTourrecommend.domain.Spot;
import lombok.Data;

@Data
public class SpotDto {
//        "spot": {
//            "id": 1,
//                    "name": 해수욕장,
//                    "address": 언주로12 34,
//                    "description": "이러이러한 관광지이다",
//        },

    private Long id;
    private String name;
    private String address;
    private String description;

    public SpotDto(Spot spot) {
        this.id = spot.getId();
        this.name = spot.getName();
        this.address = spot.getAddress();
        this.description = spot.getDescription();
    }
}
