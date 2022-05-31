package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Review;
import capstone.jejuTourrecommend.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewRepositoryCustom{

    //List<Review> findBySpot(Long spotId); 이거 안됨

    List<Review> findBySpot(Spot spot);

    List<Review> findBySpotId(Long spotId);//여기서 페이징으로 반환하여 가능하기는 한데 count 셀때 다로 쿼리 날려야함

}








