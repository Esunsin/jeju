package capstone.jejuTourrecommend.domain;

import lombok.Getter;

@Getter
public enum Location {

    Default("기본값"),

    Jeju_si("제주시"),
    Aewol_eup("애월읍"),
    Hallim_eup("한림읍"),
    Hangyeong_myeon("한경면"),
    Jocheon_eup("조천읍"),
    Gujwa_eup("구좌읍"),
    Daejeong_eup("대정읍"),
    Andeok_myeon("안덕면"),
    Seogwipo_si("서귀포시"),
    Namwon_eup("남원읍"),
    Pyoseon_myeon("표선면"),
    Seongsan_eup("성산읍"),
    Udo_myeon("우도면"),
    Chuja_myeon("추자면");

    private String krName;

    Location(String krName) {
        this.krName = krName;
    }


}











