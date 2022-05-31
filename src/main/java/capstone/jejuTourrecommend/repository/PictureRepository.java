package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.domain.Picture;
import capstone.jejuTourrecommend.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> findBySpot(Spot spot);

    List<Picture>  findAll();


}
