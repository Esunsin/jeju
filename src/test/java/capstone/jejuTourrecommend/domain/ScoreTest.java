package capstone.jejuTourrecommend.domain;

import capstone.jejuTourrecommend.web.pageDto.mainPage.CategoryDto;
import capstone.jejuTourrecommend.web.pageDto.mainPage.RegionDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
//@Commit
class ScoreTest {


    @PersistenceContext
    EntityManager em;


    @Test
    public void score_spotTest() throws Exception{

        Score score1 = new Score();
        Score score2 = new Score();

        em.persist(score1);
        em.persist(score2);

        //spot이 주인임
        Spot spot1 = new Spot("spot1",score1);
        Spot spot2 = new Spot("spot2",score2);

        em.persist(spot1);
        em.persist(spot2);

        em.flush();
        em.clear();

        List<Spot> resultList = em.createQuery("select s from Spot s", Spot.class)
                .getResultList();

        for (Spot spot : resultList) {
            System.out.println("spot = " + spot);
            System.out.println("spot.getScore() = " + spot.getScore());
        }

    }

    @Test
    public void abstract_test() throws Exception{



        //given

        //when

        //then
    }

 

    @Test
    public void tag_Test() throws Exception{
        //given
        Map map;
        List list = new ArrayList();

        map = new LinkedHashMap();
        map.put("id",1);
        map.put("name","view");
        list.add(map);

        map = new LinkedHashMap();
        map.put("id",2);
        map.put("name","price");
        list.add(map);

        map = new LinkedHashMap();
        map.put("id",3);
        map.put("name","facility");
        list.add(map);

        map = new LinkedHashMap();
        map.put("id",4);
        map.put("name","surround");
        list.add(map);

        CategoryDto categoryDto = new CategoryDto(list);

        System.out.println("categoryDto = " + categoryDto.toString());

//        5 제주시
//        6 애월읍
//        7 한림읍
//        8 한경면
//        9 조천읍
//        10 구좌읍
//        11 대정읍
//        12 안덕면
//        13 서귀포
//        14 남원읍
//        15 표선면
//        16 성산읍
//        17 우도면
//        18 추자면

        Map map1;
        List list1 = new ArrayList();

        map1 = new LinkedHashMap();
        map1.put("id",5);
        map1.put("name","제주시");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",6);
        map1.put("name","애월읍");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",7);
        map1.put("name","한림읍");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",8);
        map1.put("name","한경면");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",9);
        map1.put("name","조천읍");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",10);
        map1.put("name","구좌읍");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",11);
        map1.put("name","대정읍");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",12);
        map1.put("name","안덕면");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",13);
        map1.put("name","서귀포");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",14);
        map1.put("name","남원읍");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",15);
        map1.put("name","표선면");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",16);
        map1.put("name","성산읍");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",17);
        map1.put("name","우도면");
        list1.add(map1);

        map1 = new LinkedHashMap();
        map1.put("id",18);
        map1.put("name","추자면");
        list1.add(map1);

        RegionDto regionDto = new RegionDto(list1);

        System.out.println("regionDto.toString() = " + regionDto.toString());

        for (Object o : list1) {
            System.out.println("o.toString() = " + o.toString());
        }




        //when

        //then
    }



}















