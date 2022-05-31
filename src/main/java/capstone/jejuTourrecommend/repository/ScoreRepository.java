package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score,Long> {


    Optional<Score> findOptionalById(Long scoreId);
}
