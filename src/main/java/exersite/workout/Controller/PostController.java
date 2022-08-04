package exersite.workout.Controller;

import exersite.workout.Domain.Member;
import exersite.workout.Domain.Post;
import exersite.workout.Domain.PostCategory;
import exersite.workout.Domain.PostSearch;
import exersite.workout.Repository.PostCategoryRepository;
import exersite.workout.Repository.PostRepository;
import exersite.workout.Repository.post.simplequery.PostDto;
import exersite.workout.Repository.post.simplequery.PostSearchQueryRepository;
import exersite.workout.Service.MemberService;
import exersite.workout.Service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostCategoryRepository postCategoryRepository;
    private final PostService postService;
    private final PostSearchQueryRepository postSearchQueryRepository;

    @GetMapping("/posts/freeCategory")
    public String freeCategoryPosts(Model model) {
//        List<PostDto> freePosts = postSearchQueryRepository // dto로 조회하는 방법
//                .findPostDtos("자유게시판");
        List<Post> posts = postService.findAllDesc("자유게시판");
        List<PostDto> freePosts = posts.stream()
                .map(post -> new PostDto(post)).collect(Collectors.toList());

        model.addAttribute("freePosts", freePosts);
        return "posts/freeBoard";
    }

    @GetMapping("/posts/secretCategory")
    public String secretCategoryPosts(Model model) {
//        List<PostDto> secretPosts = postSearchQueryRepository // dto로 조회하는 방법
//                .findPostDtos("비밀게시판");
        List<Post> posts = postService.findAllDesc("비밀게시판");
        List<PostDto> secretPosts = posts.stream()
                .map(post -> new PostDto(post)).collect(Collectors.toList());

        model.addAttribute("secretPosts", secretPosts);
        return "posts/secretBoard";
    }

    @GetMapping("/posts/createPost")
    public String creatPost(Model model) {
        List<Member> members = memberService.findMembers();
        List<PostCategory> postCategories = postCategoryRepository.findAll();

        model.addAttribute("members", members);
        model.addAttribute("postCategories", postCategories);
        model.addAttribute("postForm", new PostForm());
        return "posts/createPostForm";
    }

    @PostMapping("/posts/createPost")
    public String post(@RequestParam("memberId") Long memberId,
                       @RequestParam("postCategoryName") String postCategoryName,
                       @Valid PostForm postForm, BindingResult result) {
        if (memberId == null || postCategoryName.equals("") || result.hasErrors()) {
            // 에러가 있으면 다시 게시글 작성 창으로
            return "posts/createPostForm";
        }
        postService.savePost(memberId, postCategoryName,
                postForm.getTitle(), postForm.getContent());
        return "redirect:/";
    }

    @GetMapping("/posts/search") // 검색할 때는 어떤 경우든지 get매핑
    public String postList(@ModelAttribute("postSearch")PostSearch postSearch
            , Model model) {
        // 주의 : 처음 넘겼을 때, postSearch의 필드들은 null로 설정됨
        // 검색 버튼을 누르면 : 아무것도 입력하지 않으면 ""으로 넘어옴(구분 필수)
        List<Post> posts = postService.findPosts(postSearch); // 작성자 정보가 리스트에 없어도 된다면 fetch join만을 이용
        model.addAttribute("posts", posts);
        return "posts/postList";
    }

    @GetMapping("/posts/{postId}/detail")
    public String detailPost(@PathVariable("postId") Long postId, Model model) {
        postService.updateViewsByClickPost(postId); // 조회수 증가
        Post post = postService.findOne(postId);
        PostDto postDto = new PostDto(post);
        model.addAttribute("postDto", postDto);
        return "posts/detailInformation";
    }

    // 게시글 수정
    @GetMapping("/posts/{id}/edit")
    public String updatePostForm(@PathVariable("id") Long postId, Model model) {
        Post post = postService.findOne(postId);
        PostUpdateDto postUpdateDto = new PostUpdateDto(postId);
        model.addAttribute("postDto", new PostDto(post));
        model.addAttribute("postUpdateForm", postUpdateDto);
        return "posts/updatePostForm";
    }

    @PostMapping("/posts/updatePostForm")
    public String updatePost(@Valid PostUpdateDto postUpdateDto) {
        postService.updateTitleContent(postUpdateDto.getId(),
                postUpdateDto.getTitle(), postUpdateDto.getContent());
        return "redirect:/";
    }

    @Data
    static class PostUpdateDto {
        private Long id;
        private String title;
        private String content;

        public PostUpdateDto(Long id) {
            this.id = id;
        }
    }

    // 게시글 삭제
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }
}
