package capstone.jejuTourrecommend.web.pageDto.spotPage;


import capstone.jejuTourrecommend.domain.Spot;
import lombok.Data;

@Data
public class TestSpotDto {


    private Long id;
    private String name;
    private String address;
    private String description;

    private String location;

    public TestSpotDto(Spot spot) {
        this.id = spot.getId();
        this.name = spot.getName();
        this.address = spot.getAddress();
        this.description = spot.getDescription();
        this.location = spot.getLocation().getKrName();
    }
}
