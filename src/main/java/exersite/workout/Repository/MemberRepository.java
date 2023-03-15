package exersite.workout.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static exersite.workout.Domain.Member.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Member member) { // 회원 저장
        if (member.getId() == null) {
            em.persist(member);
        } else {
            em.merge(member);
        }
    }

    public Member findOne(Long id) { // 회원 id로 회원 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() { // 모든 회원 조회
//        return em.createQuery("select m from Member m", Member.class)
//                .getResultList();

        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public Member findByNickname(String nickname) { // 같은 이름의 회원리스트 조회
//        return em.createQuery("select m from Member m where m.name = :name", Member.class)
//                .setParameter("name", name)
//                .getResultList();

        return queryFactory
                .selectFrom(member)
                .where(member.nickname.eq(nickname))
                .fetchOne();
    }

    public Member findByLoginId(String loginId) { // 특정 loginId를 같은 회원리스트 조회
//        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
//                .setParameter("loginId", loginId)
//                .getSingleResult();

        return queryFactory
                .selectFrom(member)
                .where(member.loginId.eq(loginId))
                .fetchOne();
    }

    public List<Member> findMembersByLoginId(String loginId) { // 특정 loginId를 같은 회원리스트 조회
//        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
//                .setParameter("loginId", loginId)
//                .getResultList();

        return queryFactory
                .selectFrom(member)
                .where(member.loginId.eq(loginId))
                .fetch();
    }

    public boolean checkEmptyByemail(String loginId) {
        return queryFactory
                .selectFrom(member)
                .where(member.loginId.eq(loginId))
                .fetch().isEmpty();
    }

    public boolean checkEmptyByNickname(String nickname) {
        return queryFactory
                .selectFrom(member)
                .where(member.nickname.eq(nickname))
                .fetch().isEmpty();
    }
}
