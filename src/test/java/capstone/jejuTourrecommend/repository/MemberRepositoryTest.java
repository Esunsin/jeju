package capstone.jejuTourrecommend.repository;

import capstone.jejuTourrecommend.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberRepositoryTest {


    @Autowired MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    public void testMember() throws Exception{

        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());

        Member member = new Member("123@naver.com");

        memberRepository.save(member);

        //optional 사용예제
        Member result1 = memberRepository.findOptionByEmail(
                member.getEmail()).orElseGet(() -> new Member("no name"));

        assertThat(result1).isEqualTo(member);

        List<Member> all = memberRepository.findAll();
        assertThat(all).containsExactly(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);


    }

}