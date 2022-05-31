package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Category;
import capstone.jejuTourrecommend.domain.Location;
import capstone.jejuTourrecommend.domain.Spot;
import capstone.jejuTourrecommend.web.pageDto.mainPage.SpotListDto;
import capstone.jejuTourrecommend.web.pageDto.mainPage.UserWeightDto;
import capstone.jejuTourrecommend.web.pageDto.spotPage.ScoreDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SpotRepositoryCustom {

    Page<SpotListDto> searchSpotByLocationAndCategory(List locationList, Category category, Pageable pageable);

    Page<SpotListDto> searchSpotByUserPriority(Long memberId, List locationList, UserWeightDto userWeightDto, Pageable pageable);

    //Page<SpotDetailDto> searchSpotDetail(String spotName);

    ScoreDto searchScore(Spot spot);

    Boolean isFavoriteSpot(Long memberId, Long spotId);

}
