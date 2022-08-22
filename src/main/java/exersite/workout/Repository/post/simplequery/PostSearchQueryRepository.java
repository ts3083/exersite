package exersite.workout.Repository.post.simplequery;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.Member.QMember;
import exersite.workout.Domain.Post.QPost;
import exersite.workout.Domain.Post.QPostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static exersite.workout.Domain.Member.QMember.*;
import static exersite.workout.Domain.Post.QPost.*;
import static exersite.workout.Domain.Post.QPostCategory.*;

@Repository
@RequiredArgsConstructor
public class PostSearchQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<PostDto> findPostDtos(String postCategoryName) {
        return em.createQuery("select new" +
                " exersite.workout.Repository.post.simplequery" +
                ".PostDto(p.id, p.title, p.content, m.nickname, p.views, p.likes.size, p.postDate)" +
                        " from Post p join p.member m join p.postCategory pc on pc.name = :name " +
                "order by p.postDate desc", PostDto.class)
                .setParameter("name", postCategoryName)
                .getResultList();
    }

    // querydsl + 생성자 사용 프로젝션 결과 반환
    public List<PostDto> findPostDtosByQuerydsl(String postCategoryName) {
        return queryFactory
                .select(Projections.constructor(PostDto.class,
                        post.id, post.title, post.content, member.nickname, post.views,
                        post.likes.size(), post.postDate))
                .from(post)
                .join(post.member, member)
                .join(post.postCategory, postCategory)
                .on(postCategory.name.eq(postCategoryName))
                .orderBy(post.postDate.desc())
                .fetch();
    }
}


