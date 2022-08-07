package exersite.workout.Repository;

import exersite.workout.Domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) { // 회원 저장
        em.persist(member);
    }

    public Member findOne(Long id) { // 회원 id로 회원 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() { // 모든 회원 조회
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) { // 같은 이름의 회원리스트 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Member findByLoginId(String loginId) { // 특정 loginId를 같은 회원리스트 조회
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
    }
}
