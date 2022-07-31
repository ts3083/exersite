package exersite.workout.Repository;

import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member;
import exersite.workout.Domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    // 게시글 저장
    public void save(Post post) {
        em.persist(post);
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
        return em.createQuery("select p from Post p join p.member m " +
                        "on m.id = :Id order by p.postDate desc", Post.class)
                .setParameter("Id", memberId)
                .getResultList();
    }

    // 특정 카테고리 게시글 리스트 조회
    public List<Post> findAllByPostCategory(String postCategoryName) {
        return em.createQuery("select p from Post p join p.postCategory pc " +
                        "on pc.name = :name order by p.postDate desc", Post.class)
                .setParameter("name", postCategoryName)
                .getResultList();
    }

    // 게시글과 게시글 작성자 이름을 조회하기 위한 fetch join
    public List<Post> findAllPostsAndMemberNameWithFetch() {
        return em.createQuery("select p from Post p " +
                        " join fetch p.member m", Post.class)
                .getResultList();
    }
}
