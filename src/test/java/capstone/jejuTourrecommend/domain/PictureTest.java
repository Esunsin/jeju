package capstone.jejuTourrecommend.domain;

import org.assertj.core.api.Assertions;
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
class PictureTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void picture_spotTest() throws Exception{

        Spot spot1 = new Spot("spot1");
        Spot spot2 = new Spot("spot2");

        em.persist(spot1);
        em.persist(spot2);

        Picture pictureA = new Picture("http123", spot1);
        Picture pictureB = new Picture("http234",spot1);
        Picture pictureC = new Picture("http345",spot2);
        Picture pictureD = new Picture("http456",spot2);

        em.persist(pictureA);
        em.persist(pictureB);
        em.persist(pictureC);
        em.persist(pictureD);

        em.flush();
        em.clear();

        List<Picture> resultList = em.createQuery("select p from Picture p", Picture.class)
                .getResultList();

        Assertions.assertThat(resultList.size()).isEqualTo(4);

    }

}
































