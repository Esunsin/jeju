package capstone.jejuTourrecommend.web.pageDto.mainPage;

import lombok.Data;

@Data
public class UserWeightDto {

    private Double viewWeight;
    private Double priceWeight;
    private Double facilityWeight;
    private Double surroundWeight;

    public UserWeightDto(Double viewWeight, Double priceWeight, Double facilityWeight, Double surroundWeight) {
        this.viewWeight = viewWeight;
        this.priceWeight = priceWeight;
        this.facilityWeight = facilityWeight;
        this.surroundWeight = surroundWeight;
    }
}
