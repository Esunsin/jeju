package capstone.jejuTourrecommend.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Commit
class ReviewTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void review_spotTest() throws Exception{
        //given
        Spot spot1 = new Spot("spot1");
        Spot spot2 = new Spot("spot2");

        em.persist(spot1);
        em.persist(spot2);

        Review review1 = new Review("123123", spot1);
        Review review2 = new Review("234234", spot1);
        Review review3 = new Review("3453453", spot2);
        Review review4 = new Review("456456", spot2);

        em.persist(review1);
        em.persist(review2);
        em.persist(review3);
        em.persist(review4);

        em.flush();
        em.clear();

        List<Spot> resultList = em.createQuery("select s from Spot s", Spot.class)
                .getResultList();

        for (Spot spot : resultList) {
            System.out.println("spot = " + spot);
            System.out.println("spot.getReviews() = " + spot.getReviews());
        }


        //when

        //then
    }
}