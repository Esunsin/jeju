package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.domain.MemberSpot;
import capstone.jejuTourrecommend.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberSpotRepository extends JpaRepository<MemberSpot,Long> {
     Optional<MemberSpot> findBySpotAndMember(Spot spot, Member member);

}





