package exersite.workout.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.Comment.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.Post.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static exersite.workout.Domain.Member.QMember.*;
import static exersite.workout.Domain.Post.QPost.*;
import static org.springframework.util.StringUtils.*;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 게시글 저장
    public void save(Post post) {
        if (post.getId() == null) {
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    // 게시글 삭제 - 영속성 전이로 DB의 게시글에 달린 댓글들 사리짐
    // + 객체 관점에서 member의 posts에서 삭제 + 댓글들 member의 comments에서 삭제되어야 함
    public void remove(Post post) {
        Member member = post.getMember();
        post.deletePost(member); // 게시글과 회원의 posts에서 게시글 삭제

        List<Comment> comments = post.getComments();
        for (Comment c : comments) {
            c.deleteMemberfromCommmment(c.getMember());
        }
        em.remove(post);
    }

    // 게시글 id로 조회
    public Post findOne(Long postId) {
        Post post = em.find(Post.class, postId);
        if(post == null) {
            throw new InvalidDataAccessApiUsageException("삭제된 게시글입니다");
        }
        return post;
    }

    // 특정 회원이 작성한 모든 게시글 조회(jpql)
    public List<Post> findAllByMember(Long memberId) {
//        return em.createQuery("select p from Post p join p.member m " +
//                        "on m.id = :Id order by p.postDate desc", Post.class)
//                .setParameter("Id", memberId)
//                .getResultList();

        return queryFactory
                .selectFrom(post)
                .join(post.member, member)
                .on(member.id.eq(memberId))
                .orderBy(post.postDate.desc())
                .fetch();
    }

    // 게시글과 게시글 작성자 이름을 조회하기 위한 fetch join
    public List<Post> findAllPostsAndMemberNameWithFetch() {
//        return em.createQuery("select p from Post p " +
//                        " join fetch p.member m order by p.postDate desc ", Post.class)
//                .getResultList();

        return queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .orderBy(post.postDate.desc())
                .fetch();
    }

    // 쿼리dsl을 활용한 동적쿼리 생성
    public List<Post> findPostsBySearchCond(String titleContent, String memberNickname) {
        return queryFactory
                .selectFrom(post)
                .join(post.member, member)
                .where(getTitleContentContains(titleContent),
                        getMemberNicknameContains(memberNickname))
                .fetch();
    }

    private BooleanExpression getTitleContentContains(String titleContent) {
        if (hasText(titleContent)) {
            return getTitleContains(titleContent).or(getContentContains(titleContent));
        } else {
            return null;
        }
    }

    private BooleanExpression getTitleContains(String title) {
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression getContentContains(String content) {
        return hasText(content) ? post.content.contains(content) : null;
    }

    private BooleanExpression getMemberNicknameContains(String memberNickname) {
        return hasText(memberNickname) ? member.nickname.contains(memberNickname) : null;
    }

    // 전체 게시글 최신순으로 조회
    public List<Post> findAllDescPostdate() {
//        return em.createQuery(
//                "select p from Post p order by p.postDate desc ", Post.class)
//                .getResultList();

        return queryFactory
                .selectFrom(post)
                .orderBy(post.postDate.desc())
                .fetch();
    }

    // 특정 카테고리 게시글 리스트 조회
    public List<Post> findAllDescPostdate(String postCategoryName) {
//        return em.createQuery(
//                        "select p from Post p " +
//                                "join p.postCategory pc on pc.name = :categoryName " +
//                                "order by p.postDate desc ", Post.class)
//                .setParameter("categoryName", postCategoryName)
//                .getResultList();

        return queryFactory
                .selectFrom(post)
                .where(post.postCategory.eq(PostCategory.valueOf(postCategoryName)))
                .orderBy(post.postDate.desc())
                .fetch();
    }
}
