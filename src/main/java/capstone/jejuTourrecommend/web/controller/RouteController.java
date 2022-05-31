package capstone.jejuTourrecommend.web.controller;


import capstone.jejuTourrecommend.domain.Service.RouteService;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteSpotListDto;
import capstone.jejuTourrecommend.web.pageDto.mainPage.SpotListDto;
import capstone.jejuTourrecommend.web.pageDto.routePage.ResultFavoriteSpotList;
import capstone.jejuTourrecommend.web.pageDto.routePage.ResultTopSpot;
import capstone.jejuTourrecommend.web.pageDto.routePage.RouteForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/user/route/spot/{favoriteId}")
    public ResultFavoriteSpotList favoriteRoute(@PathVariable Long favoriteId){

        //Todo: 테스트 데이터
        //Long favoriteIdTest = 3l;

        List<FavoriteSpotListDto> favoriteSpotListDtos = routeService.favoriteSpotList(favoriteId);

        return new ResultFavoriteSpotList(200l,true,"성공",favoriteSpotListDtos);
    }

    @PostMapping("/user/route/topList/{favoriteId}")
    public ResultTopSpot topList(@PathVariable Long favoriteId, @RequestBody RouteForm routeForm){

        //Todo: 테스트 데이터
        //Long favoriteIdTest = 3l;
        log.info("routeForm.getSpotIdList() = {}", routeForm.getSpotIdList());

        List list = routeService.recommentSpotList(favoriteId, routeForm);

        return new ResultTopSpot(200l, true, "성공",list);


    }


}



















