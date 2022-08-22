package exersite.workout.Service;

import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.Post.PostCategory;
import exersite.workout.Domain.Post.PostSearch;
import exersite.workout.Repository.MemberRepository;
import exersite.workout.Repository.PostCategoryRepository;
import exersite.workout.Repository.PostRepository;
import exersite.workout.Repository.post.simplequery.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        post.setPostDate(LocalDateTime.now());
    }

    // 조회수 증가
    public void updateViewsByClickPost(Long postId) {
        Post post = postRepository.findOne(postId);
        //post.setViews(post.getViews() + 1);
        post.clickPost();
    }

    // 게시글 조회
    @Transactional(readOnly = true)
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllDesc() {
        return postRepository.findAllDescPostdate();
    }

    @Transactional(readOnly = true)
    public List<Post> findAllDesc(String CategoryName) {
        return postRepository.findAllDescPostdate(CategoryName);
    }

    // 특정 회원이 작성한 모든 게시글 조회 메서드
    @Transactional(readOnly = true)
    public List<Post> findAllByUser(Long memberId) {
        return postRepository.findAllByMember(memberId);
    }

    // 쿼리dsl을 활용한 동적쿼리
    @Transactional(readOnly = true)
    public List<PostDto> findPostsByDsl(PostSearch postSearch) {
        // postSearch에는 <제목+내용>, <작성자 이름>
        List<Post> posts = postRepository.findPostsBySearchCond(
                postSearch.getTitleOrContent(), postSearch.getNickname());
        return posts.stream()
                .map(post -> new PostDto(post)).collect(Collectors.toList());
    }

//    @Transactional(readOnly = true)
//    public List<Post> findPosts(PostSearch postSearch) {
//        List<Post> posts = postRepository.findAllPostsAndMemberNameWithFetch();
//        if (postSearch.getTitleOrContent() == null
//                && postSearch.getNickname() == null) {
//            // 바로 posts 반환
//            return posts;
//        }
//        // 필터링
//        List<Post> result = new ArrayList<>();
//        for (Post p : posts) {
//            if (isPostSearchContain(postSearch, p)) {
//                result.add(p);
//            }
//        }
//        return result;
//    }

    private boolean isPostSearchContain(PostSearch postSearch, Post p) {
        if (!postSearch.getTitleOrContent().equals("")) {
            if (p.getTitle().contains(postSearch.getTitleOrContent())) {
                return true;
            }
            else if (p.getContent().contains(postSearch.getTitleOrContent())) {
                return true;
            }
        }
        else if (!postSearch.getNickname().equals("")
                && p.getMember().getNickname().contains(postSearch.getNickname())) {
            return true;

        }
        return false;
    }

}
