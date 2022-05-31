package capstone.jejuTourrecommend.domain.Service;


import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.domain.MemberSpot;
import capstone.jejuTourrecommend.domain.Spot;
import capstone.jejuTourrecommend.repository.*;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import capstone.jejuTourrecommend.web.pageDto.spotPage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SpotService {

    private final SpotRepository spotRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final PictureRepository pictureRepository;
    private final MemberSpotRepository memberSpotRepository;

    public Page<ReviewDto> reviewPage(Long spotId, Pageable pageable){

        Spot spot = spotRepository.findOptionById(spotId)
                .orElseThrow(() -> new UserException("spotId가 올바르지 않습니다."));

        //리뷰 데이터 받아오기
        Page<ReviewDto> reviewDtoList = reviewRepository.searchSpotReview(spot, pageable);

        return reviewDtoList;

    }

    public SpotDetailDto spotPage(Long spotId, String memberEmail){

        Spot spot = spotRepository.findOptionById(spotId)
                .orElseThrow(() -> new UserException("spotId가 올바르지 않습니다."));
        SpotDto spotDto = new SpotDto(spot);

        Member member = memberRepository.findOptionByEmail(memberEmail)
                .orElseThrow(() -> new UserException("가입되지 않은 E-MAIL 입니다."));
        log.info("member = {}",member);

        //이거 실험용 데이터임 TODO: 실험용 데이터임
        //PageRequest pageRequest = PageRequest.of(1,3);


        List<PictureDto> pictureDtoList = pictureRepository.findBySpot(
                        spot).stream().map(picture -> new PictureDto(picture))
                .collect(Collectors.toList());

        ScoreDto scoreDto = spotRepository.searchScore(spot);

        Boolean isFavoriteSpot = spotRepository.isFavoriteSpot(member.getId(), spotId);


        return new SpotDetailDto(spotDto,scoreDto,pictureDtoList, isFavoriteSpot);

    }






}














