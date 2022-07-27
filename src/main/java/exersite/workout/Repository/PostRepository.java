package exersite.workout.Repository;

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

    // 댓글 삭제
    public void remove(Post post) {
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
                        "on m.id = :Id", Post.class)
                .setParameter("Id", memberId)
                .getResultList();
    }

    // 특정 카테고리 게시글 리스트 조회
    public List<Post> findAllByPostCategory(String postCategoryName) {
        return em.createQuery("select p from Post p join p.postCategory pc " +
                        "on pc.name = :name", Post.class)
                .setParameter("name", postCategoryName)
                .getResultList();
    }
}
