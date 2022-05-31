package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Review;
import capstone.jejuTourrecommend.domain.Spot;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    SpotRepository spotRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void findReview() throws Exception{
        //given
        Spot spot1 = new Spot("spot1");
        em.persist(spot1);

        Review review1 = new Review("fun1",spot1);
        Review review2 = new Review("fun2",spot1);
        Review review3 = new Review("fun3",spot1);
        Review review4 = new Review("fun4",spot1);
        em.persist(review1);
        em.persist(review2);
        em.persist(review3);
        em.persist(review4);

        em.flush();
        em.clear();

        log.info("spot1 = {}",spot1);

        //Optional<Spot> spot = spotRepository.findOptionById(spot1.getId());

        //when
        //List<Review> result1 = reviewRepository.findBySpot(spot1.getId());
        //List<Review> result2 = reviewRepository.findBySpot(spot.get());
        List<Review> result2 = reviewRepository.findBySpotId(1l);

        //then
        assertThat(result2.size()).isEqualTo(4);

    }

}