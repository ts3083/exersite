package exersite.workout.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.Post.PostCategory;
import exersite.workout.Domain.Post.QPostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static exersite.workout.Domain.Post.QPostCategory.*;

@Repository
@RequiredArgsConstructor
public class PostCategoryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 카테고리 이름으로 조회
    public PostCategory findOneByName(String name) {
        return em.find(PostCategory.class, name);
    }

    // 모든 카테고리 조회
    public List<PostCategory> findAll() {
//        return em.createQuery("select pc from PostCategory pc", PostCategory.class)
//                .getResultList();

        return queryFactory
                .selectFrom(postCategory)
                .fetch();
    }
}
