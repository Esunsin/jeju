package capstone.jejuTourrecommend.web.pageDto.mainPage;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CategoryDto {


    private List list = new ArrayList();

    public CategoryDto(List list) {
        this.list = list;
    }
}






















