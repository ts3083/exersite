package exersite.workout.Service;

import exersite.workout.Controller.Dtos.CommentDto;
import exersite.workout.Controller.Dtos.PostDetailDto;
import exersite.workout.Controller.Dtos.myPostsDto;
import exersite.workout.Controller.Forms.PostForm;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.Post.PostSearch;
import exersite.workout.Repository.CommentRepository;
import exersite.workout.Repository.MemberRepository;
import exersite.workout.Repository.PostRepository;
import exersite.workout.Repository.post.simplequery.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 저장
    @Transactional
    public Long savePost(Long memberId, String postCategoryName, PostForm postForm) {
        // 회원 찾아오기
        Member member = memberRepository.findOne(memberId);
        // 게시글 생성 - 회원, 카테고리, 제목, 본문
        Post post = Post.createPost(member, postCategoryName, postForm);
        // 게시글 저장
        postRepository.save(post);
        // 게시글 저장되면 알림 이벤트 처리

        return post.getId();
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findOne(postId);
        postRepository.remove(post);
    }

    // 게시글 수정
    @Transactional
    public void updateTitleContent(Long postId, String title, String content) {
        Post post = postRepository.findOne(postId);
        post.setTitle(title);
        post.setContent(content);
        post.setPostDate(LocalDateTime.now());
    }

    // 조회수 증가
    @Transactional
    public void updateViewsByClickPost(Long postId) {
        Post post = postRepository.findOne(postId);
        //post.setViews(post.getViews() + 1);
        post.clickPost();
    }

    // 게시글 조회
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    public List<PostDto> findAllPostDtosDesc(String categoryName) {
        return postRepository.findAllDescPostdate(categoryName).stream()
                .map(post -> new PostDto(post)).collect(Collectors.toList());
    }

    public PostDetailDto findOneByPostDetailDto(Long postId) {
        return new PostDetailDto(postRepository.findOne(postId));
    }

    // 특정 회원이 작성한 모든 게시글 조회 메서드
    public List<myPostsDto> findAllmyPostDtosByUser(Long memberId) {
        return postRepository.findAllByMember(memberId).stream()
                .map(post -> new myPostsDto(post)).collect(Collectors.toList());
    }

    // 쿼리dsl을 활용한 동적쿼리
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
