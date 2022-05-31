package capstone.jejuTourrecommend.domain.Service;


import capstone.jejuTourrecommend.domain.*;
import capstone.jejuTourrecommend.repository.*;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteDto;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteForm;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteListDto;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.GetFavoriteListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final SpotRepository spotRepository;
    private final FavoriteSpotRepository favoriteSpotRepository;
    private final FavoriteSpotQueryRepository favoriteSpotQueryRepository;
    private final PictureRepository pictureRepository;

    private final EntityManager em;

    //사용자의 위시리스트 목록 "폼" 보여주기
    public Page<FavoriteListDto> getFavoriteList(String memberEmail, Pageable pageable){


        Member member = memberRepository.findOptionByEmail(memberEmail)
                .orElseThrow(() -> new UserException("가입되지 않은 E-MAIL 입니다."));

        //이거 실험용 데이터임 TODO: 실험용 데이터임
        //PageRequest pageRequest = PageRequest.of(0,100);

        Page<FavoriteListDto> favoriteListDtos = favoriteSpotQueryRepository.favoriteList(member.getId(), pageable);


        return favoriteListDtos;


    }

    // 선택한 관광지를 선태한 위시리스트에 추가
    // 선택한 관광지 정보, 사용자 정보, 위시리스트 정보 필요
    public void postFavoriteForm(String memberEmail, FavoriteForm favoriteForm){

        //솔직히 멤버 정보는 필요 없음 어차피 해당 favorite 은 member 와 연결되어 있음
        //Optional<Member> member = memberRepository.findOptionByEmail(memberEmail);

        //Todo: 여기서 관광지 넣을때 중복 체크해줘야함

        Spot spot = spotRepository.findOptionById(favoriteForm.getSpotId())
                .orElseThrow(() -> new UserException("관광지 id가 맞지 않습니다"));

        Favorite favorite = favoriteRepository.findOptionById(favoriteForm.getFavoriteId())
                .orElseThrow(() -> new UserException("위시리스트 id가 맞지 않습니다"));

        Optional<FavoriteSpot> result = favoriteSpotRepository.findOptionBySpotIdAndFavoriteId(spot.getId(), favorite.getId());
        if(result.isPresent()){
            throw new UserException("위시리스트에 중복된 관광지가 들어 있습니다.");
        }


        FavoriteSpot favoriteSpot = new FavoriteSpot(favorite,spot);
        favoriteSpotRepository.save(favoriteSpot);

    }


    //새로운 위시 리스트를 만들고 해당 관광지 넣기
    // 선택한 관광지 정보, 사용자 정보, 위시리스트 이름 필요
    public FavoriteDto newFavoriteListO(String memberEmail, Long spotId, String favoriteName){

        Member member = memberRepository.findOptionByEmail(memberEmail)
                .orElseThrow(() -> new UserException("가입되지 않은 E-MAIL 입니다."));

        Optional<Favorite> optionByName = favoriteRepository.findOptionByName(favoriteName);

        if(optionByName.isPresent()){
            throw new UserException("동일한 위시리스트 이름이 존재합니다");
        }

        Optional<Spot> spot = spotRepository.findOptionById(spotId);


        // 회원객체와 장소 객체 가져오고
        // favorite하고 favoritespot을 생성하고 넣기

        Favorite favorite = new Favorite(favoriteName,member);

        favoriteRepository.save(favorite);

        FavoriteSpot favoriteSpot = new FavoriteSpot(favorite,spot.get());

        favoriteSpotRepository.save(favoriteSpot);

        FavoriteDto favoriteDto = favoriteRepository.findOptionByName(favoriteName)
                .map(favorite1 -> new FavoriteDto(favorite1.getId(), favorite1.getName()))
                .orElseThrow(() -> new UserException("갱신 성공을 못하였습니다"));

        return favoriteDto;

    }

    //관광지가 없기에 새로운 위시리스트 추가만 함
    public FavoriteDto newFavoriteListX(String memberEmail, String favoriteName){

        Member member = memberRepository.findOptionByEmail(memberEmail)
                .orElseThrow(() -> new UserException("가입되지 않은 E-MAIL 입니다."));

        Optional<Favorite> optionByName = favoriteRepository.findOptionByName(favoriteName);

        if(optionByName.isPresent()){
            throw new UserException("동일한 위시리스트 이름이 존재합니다");
        }

        Favorite favorite = new Favorite(favoriteName,member);

        favoriteRepository.save(favorite);

        FavoriteDto favoriteDto = favoriteRepository.findOptionByName(favoriteName)
                .map(favorite1 -> new FavoriteDto(favorite1.getId(), favorite1.getName()))
                .orElseThrow(() -> new UserException("갱신 성공을 못하였습니다"));

        return favoriteDto;


    }


    //위시 리스트 삭제하기
    //해당 위시리스트 정보 필요
    public void deleteFavoriteList(Long favoriteId){

        //먼저 favoriteSpot들 찾아서 삭제해주고 (여러개임), 이때 favorite,spot필요
        //그다음 favorite을 삭제해줘야함 (한개), favorteId만 필요

        //favoriteSpotRepository.deleteAllById();

        favoriteRepository.findOptionById(favoriteId)
                        .orElseThrow(() -> new UserException("올바르지 않는 favoriteId 입니다"));

        favoriteSpotQueryRepository.deleteFavoriteSpotByFavoriteId(favoriteId);

        favoriteRepository.deleteById(favoriteId);
        em.flush();
        em.clear();

    }

    public void deleteSpotInFavoriteList(Long favoriteId, Long spotId){

        favoriteRepository.findOptionById(favoriteId)
                .orElseThrow(() -> new UserException("올바르지 않는 favoriteId 입니다"));

        spotRepository.findOptionById(spotId)
                .orElseThrow(() -> new UserException("올바르지 않는 spotId 입니다"));

        favoriteSpotRepository.deleteByFavoriteIdAndSpotId(favoriteId,spotId);
    }




}


























