package capstone.jejuTourrecommend.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class FavoriteSpotTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void fs_favoriteTest() throws Exception{
        Favorite favoriteA = new Favorite("favoriteA");
        Favorite favoriteB = new Favorite("favoriteB");

        em.persist(favoriteA);
        em.persist(favoriteB);
        
        FavoriteSpot favoriteSpot1 = new FavoriteSpot(favoriteA);
        FavoriteSpot favoriteSpot2 = new FavoriteSpot(favoriteA);
        FavoriteSpot favoriteSpot3 = new FavoriteSpot(favoriteB);
        FavoriteSpot favoriteSpot4 = new FavoriteSpot(favoriteB);

        em.persist(favoriteSpot1);
        em.persist(favoriteSpot2);
        em.persist(favoriteSpot3);
        em.persist(favoriteSpot4);

        em.flush();
        em.clear();

        List<FavoriteSpot> favoriteSpotList = em.createQuery("select fs from FavoriteSpot fs",FavoriteSpot.class)
                .getResultList();

        for (FavoriteSpot favoriteSpot : favoriteSpotList) {
            System.out.println("favoriteSpot = " + favoriteSpot);
            System.out.println("favoriteSpot.getFavorite() = " + favoriteSpot.getFavorite());
        }
        
    }

    @Test
    public void fs_SpotTest() throws Exception{
        //given
        Spot spot1 = new Spot("spot1");
        Spot spot2 = new Spot("spot2");

        em.persist(spot1);
        em.persist(spot2);

        FavoriteSpot favoriteSpot1 = new FavoriteSpot(spot1);
        FavoriteSpot favoriteSpot2 = new FavoriteSpot(spot1);
        FavoriteSpot favoriteSpot3 = new FavoriteSpot(spot2);
        FavoriteSpot favoriteSpot4 = new FavoriteSpot(spot2);

        em.persist(favoriteSpot1);
        em.persist(favoriteSpot2);
        em.persist(favoriteSpot3);
        em.persist(favoriteSpot4);

        em.flush();
        em.clear();

        List<FavoriteSpot> favoriteSpotList = em.createQuery("select fs from FavoriteSpot fs", FavoriteSpot.class)
                .getResultList();

        for (FavoriteSpot favoriteSpot : favoriteSpotList) {
            System.out.println("favoriteSpot = " + favoriteSpot);
            System.out.println("favoriteSpot.getSpot() = " + favoriteSpot.getSpot());
        }

    }

}






























