package capstone.jejuTourrecommend.web.pageDto.routePage;


import lombok.Data;

import java.util.List;

@Data
public class ResultTopSpot {

    private Long status;
    private boolean success;
    private String message;
    private List spotList;

    public ResultTopSpot(Long status, boolean success, String message, List spotList) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.spotList = spotList;
    }
}










