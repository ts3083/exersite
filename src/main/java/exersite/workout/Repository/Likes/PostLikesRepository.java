package exersite.workout.Repository.Likes;

import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.likes.PostLikes;
import exersite.workout.Domain.likes.QPostLikes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static exersite.workout.Domain.likes.QPostLikes.*;

@Repository
@RequiredArgsConstructor
public class PostLikesRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(PostLikes postLikes) {
        em.persist(postLikes);
    }

    public void delete(PostLikes postLikes) {
        em.remove(postLikes);
    }

    public PostLikes findOne(Long id) {
        return em.find(PostLikes.class, id);
    }

//    public Optional<PostLikes> findOneByMemberAndPost(Long memberId, Long postId) {
//        return em.createQuery(
//                "select pl from PostLikes pl " +
//                        "where pl.member.id = :memberId " +
//                        "and pl.post.id = :postId", PostLikes.class)
//                .setParameter("memberId", memberId)
//                .setParameter("postId", postId)
//                .getResultList()
//                .stream().findAny();
//    }

    public PostLikes findOneByMemberAndPost(Long memberId, Long postId) {
        return queryFactory
                .selectFrom(postLikes)
                .where(
                        postLikes.member.id.eq(memberId),
                        postLikes.post.id.eq(postId))
                .fetchOne();
    }
}
