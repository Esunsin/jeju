package capstone.jejuTourrecommend.web;

import lombok.Data;

@Data
public class GlobalDto {

    private Long status;
    private boolean success;
    private String message;


    public GlobalDto(Long status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}






