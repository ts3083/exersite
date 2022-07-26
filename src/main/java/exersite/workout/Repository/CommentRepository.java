package exersite.workout.Repository;

import exersite.workout.Domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    // 댓글 저장
    public void save(Comment comment) {
        em.persist(comment);
    }

    // 댓글 삭제
    public void remove(Comment comment) {
        em.remove(comment);
    }

    // 댓글 id로 조회
    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    // 특정 회원이 작성한 모든 댓글 조회(jpql)
    public List<Comment> findAllByMember(Long memberId) {
        return em.createQuery("select c from Comment c join c.member m " +
                "where m.id = :Id", Comment.class)
                .setParameter("Id", memberId)
                .getResultList();
    }

    // 특정 게시물에 작성된 모든 댓글 조회(jpql)
    public List<Comment> findAllByPost(Long postId) {
        return em.createQuery("select c from Comment c join c.post p " +
                        "where p.id = :Id", Comment.class)
                .setParameter("Id", postId)
                .getResultList();
    }
}
