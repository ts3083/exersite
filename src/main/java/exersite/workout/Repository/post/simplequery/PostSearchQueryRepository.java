package exersite.workout.Repository.post.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostSearchQueryRepository {

    private final EntityManager em;

    public List<PostDto> findPostDtos(String postCategoryName) {
        return em.createQuery("select new" +
                " exersite.workout.Repository.post.simplequery" +
                ".PostDto(p.id, p.title, p.content, m.nickname, p.views, p.likes, p.postDate)" +
                        " from Post p join p.member m join p.postCategory pc on pc.name = :name " +
                "order by p.postDate desc", PostDto.class)
                .setParameter("name", postCategoryName)
                .getResultList();
    }
}


