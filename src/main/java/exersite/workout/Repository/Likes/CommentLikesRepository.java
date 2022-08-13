package exersite.workout.Repository.Likes;

import exersite.workout.Domain.likes.CommentLikes;
import exersite.workout.Domain.likes.PostLikes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentLikesRepository {

    private final EntityManager em;

    public void save(CommentLikes commentLikes) {
        em.persist(commentLikes);
    }

    public void delete(CommentLikes commentLikes) {
        em.remove(commentLikes);
    }

    public CommentLikes findOne(Long id) {
        return em.find(CommentLikes.class, id);
    }

    public Optional<CommentLikes> findOneByMemberAndComment(Long memberId, Long commentId) {
        return em.createQuery(
                        "select cl from CommentLikes cl " +
                                "where cl.member.id = :memberId " +
                                "and cl.comment.id = :commentId", CommentLikes.class)
                .setParameter("memberId", memberId)
                .setParameter("commentId", commentId)
                .getResultList()
                .stream().findAny();
    }
}
