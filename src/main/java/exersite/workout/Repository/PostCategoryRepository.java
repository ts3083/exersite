package exersite.workout.Repository;

import exersite.workout.Domain.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCategoryRepository {

    private final EntityManager em;

    // 카테고리 id로 조회
    public PostCategory findOne(Long postCategoryId) {
        return em.find(PostCategory.class, postCategoryId);
    }

    // 카테고리 이름으로 조회
    public PostCategory findOneByName(String name) {
        List<PostCategory> list = em.createQuery(
                "select pc from PostCategory pc " +
                        "where pc.name = :name", PostCategory.class)
                .setParameter("name", name)
                .getResultList();

        PostCategory result = null;
        for (PostCategory pc : list) {
            if (pc.getName().equals(name)) {
                result = pc;
                break;
            }
        }
        return result;
    }
}
