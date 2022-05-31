package capstone.jejuTourrecommend.repository;


import capstone.jejuTourrecommend.domain.Spot;
import capstone.jejuTourrecommend.web.pageDto.spotPage.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<ReviewDto> searchSpotReview(Spot spot, Pageable pageable);

}
