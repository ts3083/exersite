package exersite.workout;

import exersite.workout.Domain.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class InitDb {

    private final InitPostCategory initPostCategory;

    @PostConstruct
    public void init() {
        initPostCategory.dbInit(); // 초기 게시판 카테고리 설정
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitPostCategory {

        private final EntityManager em;

        public void dbInit() {
            PostCategory postCategory1 = new PostCategory();
            postCategory1.setName("자유게시판");
            em.persist(postCategory1);
            PostCategory postCategory2 = new PostCategory();
            postCategory2.setName("비밀게시판");
            em.persist(postCategory2);
        }
    }
}
