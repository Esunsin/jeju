package capstone.jejuTourrecommend.web.pageDto.mainPage;

import lombok.Data;

import java.util.List;

@Data
public class SpotListMetaDto {

    private Long status;
    private boolean success;
    //private CategoryDto categoryDto;
    //private RegionDto regionDto;
    private List categoryDummy;
    private List regionDummy;

    public SpotListMetaDto(Long status, boolean success, List categoryDummy, List regionDummy) {
        this.status = status;
        this.success = success;
        this.categoryDummy = categoryDummy;
        this.regionDummy = regionDummy;
    }
}
