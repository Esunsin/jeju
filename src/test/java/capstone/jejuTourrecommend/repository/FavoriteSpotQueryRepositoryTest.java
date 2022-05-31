package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.*;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import capstone.jejuTourrecommend.web.pageDto.favoritePage.FavoriteSpotListDto;
import capstone.jejuTourrecommend.web.pageDto.mainPage.SpotListDto;
import capstone.jejuTourrecommend.web.pageDto.routePage.RouteForm;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//근데 이거 대로 해도 보인는 것은 순서대로 작동해서 삭제가 먼저 되면 다음테스트에 영향 있음
@SpringBootTest
@Transactional
@Slf4j
class FavoriteSpotQueryRepositoryTest {

    @Autowired
    FavoriteSpotQueryRepository favoriteSpotQueryRepository;

    @Autowired
    FavoriteSpotRepository favoriteSpotRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @PersistenceContext
    EntityManager em;



    @BeforeEach
    public void init(){

        //String encodedPassword = passwordEncoder.encode("1234");
        //log.info("password = {}",encodedPassword);

        Member member1 = new Member("member1","member1@gmail.com","1234");
        Member member2 = new Member("member2","member2@naver.com","2345");
        em.persist(member1);
        em.persist(member2);
        log.info("member1 ={}",member1);

        MemberSpot[] memberSpots = new MemberSpot[100];
        Score[] scores = new Score[100];
        Spot[] spots = new Spot[100];
        Picture[][] pictures = new Picture[100][3];
        Review[] reviews = new Review[100];

        Favorite favorite = new Favorite("1일차",member1);
        em.persist(favorite);

        FavoriteSpot[] favoriteSpots = new FavoriteSpot[50];

        for(int i=0;i<100;i++){ //지역하고 score만

            if(0<=i&&i<25) {
                spots[i] = new Spot(Location.Aewol_eup,createScore(scores,i));
                memberSpots[i] = new MemberSpot(0d,member2,spots[i]);
            }if(25<=i&&i<50){
                spots[i] = new Spot(Location.Aewol_eup,createScore(scores,i));
                memberSpots[i] = new MemberSpot(0d,member2,spots[i]);
            }if(50<=i&&i<75){
                spots[i] = new Spot(Location.Andeok_myeon,createScore(scores,i));
                memberSpots[i] = new MemberSpot(0d,member1,spots[i]);
            }if(75<=i&&i<100){
                spots[i] = new Spot(Location.Andeok_myeon,createScore(scores,i));
                memberSpots[i] = new MemberSpot(0d,member1,spots[i]);
            }
            for(int j=0; j<3;j++) {
                pictures[i][j] = new Picture("asdf1",spots[i]);
                em.persist(pictures[i][j]);
            }
            em.persist(spots[i]);
            em.persist(memberSpots[i]);

            reviews[i] = new Review("content",spots[0]);
            em.persist(reviews[i]);

            if(i%2==0) {
                favoriteSpots[i/2] = new FavoriteSpot(favorite, spots[i]);
                em.persist(favoriteSpots[i/2]);
                log.info("favoriteSpot = {}", favoriteSpots[i/2]);
                log.info("Spot = {}", spots[i]);
            }

        }

        em.flush();
        em.clear();

        //member1 = Member(id=1, username=member1, email=member1@gmail.com, password=1234)
        log.info("member1 = {}",member1);

        log.info("favoriteMain = {}",favorite);   //favorite = Favorite(id=1509, name=1일차)

        //spots[0] = Spot(id=8, address=null, description=null, location=Aewol_eup..
        log.info("spots[0] = {}",spots[0]);

        log.info("favoriteSpots[0] = {}",favoriteSpots[0]);//favoriteSpots[0] = FavoriteSpot(id=11, count=0)

    }

    public Score createScore(Score[] scores,int i){
        Random random = new Random();
        scores[i]= new Score(
                random.nextDouble()*10,random.nextDouble()*10,
                random.nextDouble()*10,random.nextDouble()*10,
                random.nextDouble()*10,random.nextDouble()*10,
                random.nextDouble()*10,random.nextDouble()*10,
                random.nextDouble()*10);
        em.persist(scores[i]);//여기서 이거 한해도 되는 이유는
        // cascade = CascadeType.ALL를 해놓아서 그런거임, 원래는 넣어줘야 함
        return scores[i];
    }

    @Test
    public void favoriteListTest() throws Exception{
        //given

        PageRequest pageRequest = PageRequest.of(0,3);

        //when
        //favoriteSpotQueryRepository.favoriteList()

        //then
    }

    @Test
    public void favoriteSpotListTest(){

        Long favoriteId = 3l;

        List<FavoriteSpotListDto> favoriteSpotListDtos = favoriteSpotQueryRepository.favoriteSpotList(favoriteId);

        log.info("spotListDtos = {} ",favoriteSpotListDtos);


    }

    @Order(1)
    @Test
    public void exitSpotTest() throws Exception{
        //given
        Long favoriteId = 3l;
        List<Long> spotIdList = Arrays.asList(10000l);
        RouteForm routeForm = new RouteForm();
        routeForm.setSpotIdList(spotIdList);

        //when
        FavoriteSpot favoriteSpot = favoriteSpotQueryRepository.existSpot(favoriteId, routeForm);

        Assertions.assertThat(favoriteSpot).isNull();

        //then
    }

    @Order(2)
    @Test
    public void recommendSpotListTest() throws Exception{
        //given
        Long favoriteId = 3l;
        //8l, 23l, 38l
        //72, 168, 800
        List<Long> spotIdList = Arrays.asList(8l, 23l, 38l);
        log.info("spotIdList = {}",spotIdList);
        RouteForm routeForm = new RouteForm();
        routeForm.setSpotIdList(spotIdList);

        //favoriteRepository.findOptionById()

        favoriteRepository.findOptionById(favoriteId).orElseThrow(() -> new UserException("위시리스트 아이디가 틀립니다"));

        List list = favoriteSpotQueryRepository.recommendSpotList(favoriteId,routeForm);

        log.info("list.toString() = {}",list.toString());

        //when

        //then
    }


    @Order(3)
    @Test
    public void deleteFavoriteSpot() throws Exception{
        //given
        Favorite favorite = new Favorite("favorite");
        em.persist(favorite);

        log.info("favoriteCheck = {}",favorite);

        FavoriteSpot favoriteSpot1 = new FavoriteSpot(favorite);
        FavoriteSpot favoriteSpot2 = new FavoriteSpot(favorite);
        FavoriteSpot favoriteSpot3 = new FavoriteSpot(favorite);
        FavoriteSpot favoriteSpot4 = new FavoriteSpot(favorite);
        FavoriteSpot favoriteSpot5 = new FavoriteSpot(favorite);
        FavoriteSpot favoriteSpot6 = new FavoriteSpot(favorite);

        em.persist(favoriteSpot1);
        em.persist(favoriteSpot2);
        em.persist(favoriteSpot3);
        em.persist(favoriteSpot4);
        em.persist(favoriteSpot5);
        em.persist(favoriteSpot6);

        em.flush();
        em.clear();

        List<FavoriteSpot> favoriteSpotList1 =
                favoriteSpotRepository.findByFavoriteId(favorite.getId());

        assertThat(favoriteSpotList1.size()).isEqualTo(6);

        log.info("favoriteId = {}",favorite.getId());
        //when
        favoriteSpotQueryRepository.deleteFavoriteSpotByFavoriteId(favorite.getId());

        List<FavoriteSpot> favoriteSpotList2 =
                favoriteSpotRepository.findByFavoriteId(favorite.getId());
        log.info("favoriteSpotList2 = {}",favoriteSpotList2);



        //then
        assertThat(favoriteSpotList2.size()).isEqualTo(0);


    }

    @Test
    public void deleteTest() throws Exception{
        //given
        Spot spot1 = new Spot("spot1");
        Spot spot2 = new Spot("spot2");
        em.persist(spot1);
        em.persist(spot2);

        Favorite favorite1 = new Favorite("1일차");
        em.persist(favorite1);

        FavoriteSpot favoriteSpot1 = new FavoriteSpot(favorite1,spot1);
        FavoriteSpot favoriteSpot2 = new FavoriteSpot(favorite1,spot2);
        em.persist(favoriteSpot1);
        em.persist(favoriteSpot2);

        em.flush();
        em.clear();

        log.info("22222");
        //when
        favoriteSpotRepository.deleteByFavoriteIdAndSpotId(favorite1.getId(),spot1.getId());

        log.info("11111");

        Optional<FavoriteSpot> result = favoriteSpotRepository.findOptionBySpotIdAndFavoriteId(spot1.getId(), favorite1.getId());

        assertThat(result).isEmpty();

        //then
    }



}










