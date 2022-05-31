package capstone.jejuTourrecommend.domain.Service;

import capstone.jejuTourrecommend.domain.*;
import capstone.jejuTourrecommend.repository.*;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteDto;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteForm;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteListDto;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.GetFavoriteListDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class FavoriteServiceTest {


    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    FavoriteSpotRepository favoriteSpotRepository;
    @Autowired
    FavoriteSpotQueryRepository favoriteSpotQueryRepository;
    @Autowired
    FavoriteService favoriteService;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    public void testData(){

        Member member = new Member("leoJoo","em@naver.com","1234");
        em.persist(member);

        Favorite favorite1 = new Favorite("1일차",member);
        Favorite favorite2 = new Favorite("2일차",member);
        Favorite favorite3 = new Favorite("3일차",member);
        Favorite favorite4 = new Favorite("4일차",member);
        Favorite favorite5 = new Favorite("5일차",member);
        em.persist(favorite1);
        em.persist(favorite2);
        em.persist(favorite3);
        em.persist(favorite4);
        em.persist(favorite5);
        em.persist(favorite5);
        log.info("favorite1 = {}",favorite1);//2
        log.info("favorite5 = {}",favorite5);//6

        Spot[] spots = new Spot[15];
        for(int i=0;i<15;i++){
            spots[i] = new Spot("관광지 " + Integer.toString(i));

            em.persist(spots[i]);
        }
        log.info("spot0 = {}",spots[0]);  //7번임
        log.info("spot14 = {}",spots[14]);  //21번임

        FavoriteSpot[] favoriteSpots = new FavoriteSpot[15];

        Picture[] pictures = new Picture[15];

        for(int i=0;i<5;i++){
            favoriteSpots[i] = new FavoriteSpot(favorite1,spots[i]);
            em.persist(favoriteSpots[i]);
            pictures[i] = new Picture("숫자"+i ,spots[i]);
            em.persist(pictures[i]);
        }
        for (int i=5;i<10;i++){
            favoriteSpots[i] = new FavoriteSpot(favorite2,spots[i]);
            em.persist(favoriteSpots[i]);
            pictures[i] = new Picture("숫자"+i,spots[i]);
            em.persist(pictures[i]);
        }
        for (int i=10;i<15;i++){
            favoriteSpots[i] = new FavoriteSpot(favorite3,spots[i]);
            em.persist(favoriteSpots[i]);
            pictures[i] = new Picture("숫자"+i,spots[i]);
            em.persist(pictures[i]);
        }


    }

    //사용자의 위시리스트 목록 "폼" 보여주기
    @Test
    public void getFavoriteListTest() throws Exception{
        //given

        //when
        String memberEmail = "em@naver.com";
        //Favorite favorite2 = new Favorite("2일차",m)

        //Spot spot = new Spot("테스트")


        PageRequest pageRequest = PageRequest.of(0,10);

        Page<FavoriteListDto> favoriteList = favoriteService.getFavoriteList(memberEmail, pageRequest);


        List<FavoriteListDto> content = favoriteList.getContent();
        log.info("favoriteListContent = {}",content);

        assertThat(content.size()).isEqualTo(5);
        assertThat(favoriteList.getTotalElements()).isEqualTo(5);

    }

    // 선택한 관광지를 선태한 위시리스트에 추가
    //String memberEmail, Long spotId, Long favoriteId
    @Test
    public void postFavoriteFormTest() throws Exception{
        //given
        String memberEmail = "em@naver.com";
        //7,2 중복된 관관지 존재, 새로운 추가 21,6
        FavoriteForm favoriteForm = new FavoriteForm();
        favoriteForm.setSpotId(21l);
        favoriteForm.setFavoriteId(6l);
        Long spotId = 21l;
        Long favoriteId =6l;


        //when
        favoriteService.postFavoriteForm(memberEmail,favoriteForm);

        Optional<FavoriteSpot> result = favoriteSpotRepository.findOptionBySpotIdAndFavoriteId(spotId, favoriteId);

        assertThat(result).isNotEmpty();


        //then
    }


    //새로운 위시 리스트를 만들고 해당 관광지 넣기
    //String memberEmail, Long spotId, String favoriteName
    @Test
    public void newFavoriteListOTest() throws Exception{
        //given
        String memberEmail = "em@naver.com";
        Long spotId = 21l;
        String favoriteName = "새로운 위시리스트1";
        //String favoriteName = "1일차";


        //when

        FavoriteDto favoriteDto = favoriteService.newFavoriteListO(memberEmail, spotId, favoriteName);

        Optional<Favorite> favorite = favoriteRepository.findOptionByName(favoriteName);

        Optional<FavoriteSpot> result = favoriteSpotRepository.findOptionBySpotIdAndFavoriteId(spotId, favorite.get().getId());


        //FavoriteDto favoriteDto = favoriteService.newFavoriteListO(memberEmail,spotId,favoriteName);


        //then
        assertThat(favorite).isNotEmpty();
        assertThat(result).isNotEmpty();
        log.info("favoriteDto = {}",favoriteDto);
        assertThat(favoriteDto).isNotNull();
    }

    //관광지가 없기에 새로운 위시리스트 추가만 함
    //String memberEmail, String favoriteName
    @Test
    public void newFavoriteListXTest() throws Exception{
        //given
        String memberEmail = "em@naver.com";
        String favoriteName = "새로운 위시리스트1";
        //String favoriteName = "1일차";

        //when
        FavoriteDto favoriteDto = favoriteService.newFavoriteListX(memberEmail, favoriteName);
        Optional<Favorite> favorite = favoriteRepository.findOptionByName(favoriteName);

        //then
        assertThat(favorite).isNotEmpty();
        log.info("favoriteDto = {}",favoriteDto);
        assertThat(favoriteDto).isNotNull();
    }

    //위시 리스트 삭제하기
    //Long favoriteId
    @Test
    public void deleteFavoriteListTest() throws Exception{
        //given
        Long favoriteId =6l;

        //when
        favoriteService.deleteFavoriteList(favoriteId);
        Optional<Favorite> result = favoriteRepository.findOptionById(favoriteId);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void deleteFavoriteListTest1() throws Exception{
        //given
        Long favoriteId =6l;

        //when
        favoriteService.deleteFavoriteList(favoriteId);
        Optional<Favorite> result = favoriteRepository.findOptionById(favoriteId);

        //then
        assertThat(result).isEmpty();
    }





}






















