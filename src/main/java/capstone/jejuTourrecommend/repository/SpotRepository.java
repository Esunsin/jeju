package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Category;
import capstone.jejuTourrecommend.domain.Location;
import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.domain.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> ,SpotRepositoryCustom{

    Optional<Spot> findOptionByName(String spotName);

    Optional<Spot> findOptionById(Long spotId);


}
