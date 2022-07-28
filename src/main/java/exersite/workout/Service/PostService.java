package exersite.workout.Service;

import exersite.workout.Domain.Member;
import exersite.workout.Domain.Post;
import exersite.workout.Domain.PostCategory;
import exersite.workout.Repository.MemberRepository;
import exersite.workout.Repository.PostCategoryRepository;
import exersite.workout.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostCategoryRepository postCategoryRepository;

    // 게시글 저장
    public Long savePost(Long memberId, String postCategoryName,
                         String title, String content) {
        // 회원 찾아오기
        Member member = memberRepository.findOne(memberId);
        PostCategory postCategory = postCategoryRepository
                .findOneByName(postCategoryName);

        // 게시글 생성 - 회원, 카테고리, 제목, 본문
        Post post = Post.createPost(member, postCategory, title, content);

        // 게시글 저장
        postRepository.save(post);
        return post.getId();
    }

    // 게시글 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findOne(postId);
        postRepository.remove(post);
    }

    // 게시글 수정
    public void updateTitleContent(Long postId, String title, String content) {
        Post post = postRepository.findOne(postId);
        post.setTitle(title);
        post.setContent(content);
    }

    // 게시글 조회
    @Transactional(readOnly = true)
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }
}