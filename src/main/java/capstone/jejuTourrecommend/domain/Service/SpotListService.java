package capstone.jejuTourrecommend.domain.Service;

import capstone.jejuTourrecommend.domain.Category;
import capstone.jejuTourrecommend.domain.Location;
import capstone.jejuTourrecommend.domain.Member;
import capstone.jejuTourrecommend.repository.MemberRepository;
import capstone.jejuTourrecommend.repository.SpotRepository;
import capstone.jejuTourrecommend.web.login.exceptionClass.UserException;
import capstone.jejuTourrecommend.web.pageDto.mainPage.MainPageForm;
import capstone.jejuTourrecommend.web.pageDto.mainPage.ResultSpotListDto;
import capstone.jejuTourrecommend.web.pageDto.mainPage.SpotListDto;
import capstone.jejuTourrecommend.web.pageDto.mainPage.UserWeightDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SpotListService {


    private final SpotRepository spotRepository;
    private final MemberRepository memberRepository;

    private final EntityManager em;



    public ResultSpotListDto postSpotList(MainPageForm mainPageForm,String memberEmail, Pageable pageable){

        //Location location = findLocation(mainPageForm);

        List locationList = findLocationList(mainPageForm);

        Category category = findCategory(mainPageForm);

        //mainPageForm.setLocation(Location.Andeok_myeon);
        //log.info("location = {} ",location);
        log.info("location = {}", locationList);
        //mainPageForm.setCategory(Category.VIEW);
        log.info("category = {} ",category);
        log.info("mainPageForm.getUserWeightDto() = {}",mainPageForm.getUserWeightDto());


        Member member = memberRepository.findOptionByEmail(memberEmail)
                .orElseThrow(() -> new UserException("???????????? ?????? E-MAIL ?????????."));

        //?????? ????????? ???????????? TODO: ????????? ????????????
//        mainPageForm.setPage(0);
//        mainPageForm.setPage(10);
//        PageRequest pageRequest = PageRequest.of(mainPageForm.getPage(), mainPageForm.getSize());

        //???????????? ???????????? ?????? ?????? ??????
        if(mainPageForm.getUserWeightDto()==null) {
            Page<SpotListDto> result = spotRepository.searchSpotByLocationAndCategory(
                    locationList, category, pageable);

            em.flush();
            em.clear();

            return new ResultSpotListDto(200l,true,"??????",result);
        }
        else {//???????????? ???????????? ?????? ??????
            UserWeightDto userWeightDto = new UserWeightDto(
                    mainPageForm.getUserWeightDto().getViewWeight(),
                    mainPageForm.getUserWeightDto().getPriceWeight(),
                    mainPageForm.getUserWeightDto().getFacilityWeight(),
                    mainPageForm.getUserWeightDto().getSurroundWeight()
            );
            log.info("userWeightDto = {}",userWeightDto);



            Page<SpotListDto> resultPriority = spotRepository.searchSpotByUserPriority(
                    member.getId(), locationList, userWeightDto, pageable);

            em.flush();
            em.clear();

            return new ResultSpotListDto(200l,true,"??????",resultPriority);

        }
    }

    public Category findCategory(MainPageForm mainPageForm) {
        log.info("mainPageCategory = {} ",mainPageForm.getCategory());
        Category category = null;

        //log.info("isTrue? = {}",StringUtils.hasText(mainPageForm.getCategory()));
        if(!StringUtils.hasText(mainPageForm.getCategory())){
            log.info("???????????? null??? ?????????");
            //category = Category.VIEW;   //???????????? ?????? ??????
            return category;
        }

        if(mainPageForm.getCategory().equals("view")) {
            category = Category.VIEW;
        }
        else if(mainPageForm.getCategory().equals("price"))
            category = Category.PRICE;
        else if(mainPageForm.getCategory().equals("facility"))
            category = Category.FACILITY;
        else if(mainPageForm.getCategory().equals("surround"))
            category = Category.SURROUND;
        else {
            category = Category.VIEW;   //???????????? ?????? ??????
        }

        return category;
    }

    public Location findLocation(MainPageForm mainPageForm) {
        log.info("mainPageLocation = {}",mainPageForm.getLocation());
        Location location=null;
        //log.info("location = {}",location);

        if(!StringUtils.hasText(mainPageForm.getLocation())){
            log.info("???????????? null??? ?????????");
            //category = Category.VIEW;   //???????????? ?????? ??????
            return location;
        }

        if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Jeju_si;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Aewol_eup;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Hallim_eup;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Hangyeong_myeon;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Jocheon_eup;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Gujwa_eup;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Daejeong_eup;
        else if(mainPageForm.getLocation().equals("?????????")) {
            location = Location.Andeok_myeon;
            //log.info("location = {} ",location);
        }
        else if(mainPageForm.getLocation().equals("????????????"))
            location = Location.Seogwipo_si;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Namwon_eup;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Pyoseon_myeon;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Seongsan_eup;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Udo_myeon;
        else if(mainPageForm.getLocation().equals("?????????"))
            location = Location.Chuja_myeon;
        else {
            location=Location.Jeju_si;
        }

        return location;
    }

    public List findLocationList(MainPageForm mainPageForm) {
        log.info("mainPageLocation = {}",mainPageForm.getLocation());
        List list = null;
        if(!StringUtils.hasText(mainPageForm.getLocation())){
            log.info("??????????????? null ?????? ??????????????????");
            throw new UserException("??????????????? null ?????? ??????????????????");
        }
        /**
         * ??? : ?????????,?????????,?????????,?????????,?????????
         * ??? : ?????????, ?????????, ?????????
         * ??? : ?????????, ?????????, ?????????, ?????????
         * ??? : ????????????
         */



        if(mainPageForm.getLocation().equals("??????")){
            List northList = Arrays.asList(Location.Aewol_eup,Location.Jeju_si,Location.Jocheon_eup,Location.Gujwa_eup,Location.Udo_myeon);
            return northList;
        }
        else if(mainPageForm.getLocation().equals("??????")){
            List eastList = Arrays.asList(Location.Namwon_eup,Location.Pyoseon_myeon,Location.Seongsan_eup);
            return eastList;
        }
        else if(mainPageForm.getLocation().equals("??????")){
            List westList = Arrays.asList(Location.Hallim_eup,Location.Hangyeong_myeon,Location.Daejeong_eup,Location.Andeok_myeon);
            return westList;
        }
        else if(mainPageForm.getLocation().equals("??????")){
            List southList = Arrays.asList(Location.Seogwipo_si);
            return southList;
        }else if(mainPageForm.getLocation().equals("??????")){
            List allList = Arrays.asList(Location.Jeju_si,Location.Aewol_eup,Location.Hallim_eup,
                    Location.Hangyeong_myeon,Location.Jocheon_eup,Location.Gujwa_eup,
                    Location.Daejeong_eup,Location.Andeok_myeon,Location.Seogwipo_si,
                    Location.Namwon_eup,Location.Pyoseon_myeon,Location.Seongsan_eup,Location.Udo_myeon
            );
            return allList;
        }else {
            throw new UserException("??????????????? ???????????? ???????????? ????????? ?????????");
        }


    }


}




