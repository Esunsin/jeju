package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.FavoriteSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteSpotRepository extends JpaRepository<FavoriteSpot, Long> {

    List<FavoriteSpot> findByFavoriteId(Long favoriteId);

    Optional<FavoriteSpot> findOptionBySpotIdAndFavoriteId(Long spotId, Long favoriteId);

    @Modifying(clearAutomatically = true)
    @Query("delete from FavoriteSpot fs where fs.favorite.id= :favoriteId and fs.spot.id= :spotId")
    void deleteByFavoriteIdAndSpotId(@Param("favoriteId") Long favoriteId, @Param("spotId") Long spotId);

    //void deleteAllById(Long favoriteId, Long spotId);//이거 그냥 아예 jpa에서 이거 빈 생성이 안됨

}













