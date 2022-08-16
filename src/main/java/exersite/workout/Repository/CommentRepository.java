package exersite.workout.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Member.QMember;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.Post.QPost;
import exersite.workout.Domain.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static exersite.workout.Domain.Member.QMember.*;
import static exersite.workout.Domain.Post.QPost.*;
import static exersite.workout.Domain.QComment.*;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 댓글 저장
    public void save(Comment comment) {
        em.persist(comment);
    }

    // 댓글 삭제 - post의 comments에서도 삭제 + memeber의 comments에서도 삭제
    public void remove(Comment comment) {
        Member member = comment.getMember();
        Post post = comment.getPost();
        comment.deleteComment(member, post);
        em.remove(comment);
    }

    // 댓글 id로 조회
    public Comment findOne(Long id) {
        Comment comment = em.find(Comment.class, id);
        if(comment == null) {
            throw new InvalidDataAccessApiUsageException("삭제된 댓글입니다");
        }
        return comment;
    }

    // 특정 회원이 작성한 모든 댓글 조회(jpql)
    public List<Comment> findAllByMember(Long memberId) {
//        return em.createQuery("select c from Comment c join c.member m " +
//                        "on m.id = :Id order by c.commentDate desc ", Comment.class)
//                .setParameter("Id", memberId)
//                .getResultList();

        return queryFactory
                .selectFrom(comment)
                .join(comment.member, member)
                .on(member.id.eq(memberId))
                .orderBy(comment.commentDate.desc())
                .fetch();
    }

    // 특정 게시물에 작성된 모든 댓글 조회(jpql)
    public List<Comment> findAllByPost(Long postId) {
//        return em.createQuery("select c from Comment c join c.post p " +
//                        "on p.id = :Id order by c.commentDate asc ", Comment.class)
//                .setParameter("Id", postId)
//                .getResultList();

        return queryFactory
                .selectFrom(comment)
                .join(comment.post, post)
                .on(post.id.eq(postId))
                .orderBy(comment.commentDate.asc())
                .fetch();
    }
}
