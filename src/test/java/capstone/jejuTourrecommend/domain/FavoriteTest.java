package capstone.jejuTourrecommend.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class FavoriteTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void favorite_memberTest() throws Exception{
        //given
        Member memberA = new Member("memberA", "123@gmail.com");
        Member memberB = new Member("memberB", "234@naver.com");

        em.persist(memberA);
        em.persist(memberB);

        Favorite favorite1 = new Favorite("favorite1",memberA);
        Favorite favorite2 = new Favorite("favorite2",memberA);
        Favorite favorite3 = new Favorite("favorite3",memberB);
        Favorite favorite4 = new Favorite("favorite4",memberB);

        em.persist(favorite1);
        em.persist(favorite2);
        em.persist(favorite3);
        em.persist(favorite4);

        em.flush();
        em.clear();

        List<Favorite> favorites = em.createQuery("select f from Favorite f",Favorite.class)
                .getResultList();

        for (Favorite favorite : favorites) {
            System.out.println("favorite.getName() = " + favorite.getName());
            System.out.println("favorite.getUser() = " + favorite.getMember());
        }

    }

}





























