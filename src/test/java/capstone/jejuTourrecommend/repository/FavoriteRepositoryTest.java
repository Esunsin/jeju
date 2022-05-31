package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Favorite;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@SpringBootTest
@Transactional
class FavoriteRepositoryTest {

    @Autowired
    FavoriteRepository favoriteRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void deleteFavorite() throws Exception{
        //given
        Favorite favorite = new Favorite("favoriteName");
        em.persist(favorite);

        em.flush();
        em.clear();

        System.out.println("favorite.getId() = " + favorite.getId());

        Optional<Favorite> favoriteOptional1 = favoriteRepository.findOptionById(favorite.getId());
        assertThat(favoriteOptional1.isEmpty()).isEqualTo(false);

        //when
        favoriteRepository.deleteById(favorite.getId());

        em.flush();
        em.clear();

        Optional<Favorite> favoriteOptional = favoriteRepository.findOptionById(favorite.getId());

        //then
        assertThat(favoriteOptional.isEmpty()).isEqualTo(true);

        //assertThat(favorite.getId()).isEqualTo(1l);

    }

}