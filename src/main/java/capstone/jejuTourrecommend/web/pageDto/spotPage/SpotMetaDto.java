package capstone.jejuTourrecommend.web.pageDto.spotPage;


import lombok.Data;

import java.util.List;

@Data
public class SpotMetaDto {


    private Long status;
    private boolean success;
    //private CategoryDto categoryDto;
    private List categoryDummy;


    public SpotMetaDto(Long status, boolean success, List categoryDummy) {
        this.status = status;
        this.success = success;
        this.categoryDummy = categoryDummy;
    }
}
