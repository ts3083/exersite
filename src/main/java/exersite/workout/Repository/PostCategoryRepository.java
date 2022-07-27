package exersite.workout.Repository;

import exersite.workout.Domain.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostCategoryRepository {

    private final EntityManager em;

    // 카테고리 이름으로 조회
    public PostCategory findOneByName(String name) {
        return em.find(PostCategory.class, name);
    }
}
