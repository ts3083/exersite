package exersite.workout.Repository.Likes;

import exersite.workout.Domain.likes.PostLikes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostLikesRepository {

    private final EntityManager em;

    public void save(PostLikes postLikes) {
        em.persist(postLikes);
    }

    public void delete(PostLikes postLikes) {
        em.remove(postLikes);
    }

    public PostLikes findOne(Long id) {
        return em.find(PostLikes.class, id);
    }

    public PostLikes findOneByMemberAndPost(Long memberId, Long postId) {
        return em.createQuery(
                "select pl from PostLikes pl " +
                        "where pl.member.id = :memberId " +
                        "and pl.post.id = :postId", PostLikes.class)
                .setParameter("memberId", memberId)
                .setParameter("postId", postId)
                .getSingleResult();
    }
}
