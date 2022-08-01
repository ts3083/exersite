package exersite.workout.Controller;

import exersite.workout.Domain.Member;
import exersite.workout.Domain.Post;
import exersite.workout.Domain.PostCategory;
import exersite.workout.Domain.PostSearch;
import exersite.workout.Repository.PostCategoryRepository;
import exersite.workout.Repository.PostRepository;
import exersite.workout.Service.MemberService;
import exersite.workout.Service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostCategoryRepository postCategoryRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/posts/freeCategory")
    public String freeCategoryPosts(Model model) {
        List<Post> posts = postRepository.findAllPostsAndMemberNameWithFetch();
        List<PostDto> freePosts = new ArrayList<>();
        for (Post p : posts) {
            if(p.getPostCategory().getName().compareTo("자유게시판") == 0){ // 자유게시판 글 필터링
                freePosts.add(new PostDto(p));
            }
        }
        model.addAttribute("freePosts", freePosts);
        return "posts/freeBoard";
    }

    @GetMapping("/posts/secretCategory")
    public String secretCategoryPosts(Model model) {
        List<Post> posts = postRepository.findAllPostsAndMemberNameWithFetch();
        List<PostDto> secretPosts = new ArrayList<>();
        for (Post p : posts) {
            if(p.getPostCategory().getName().compareTo("비밀게시판") == 0){ // 자유게시판 글 필터링
                secretPosts.add(new PostDto(p));
            }
        }
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
        List<Post> posts = postService.findPosts(postSearch);
        model.addAttribute("posts", posts);
        return "posts/postList";
    }

    @GetMapping("/posts/{postId}/detail")
    public String detailPost(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        PostDto postDto = new PostDto(post);
        model.addAttribute("postDto", postDto);
        return "posts/detailInformation";
    }
    @Data
    static class PostDto {
        private Long id;
        private String title;
        private String content;
        private String nickname;
        private int views;
        private int likes;
        private LocalDateTime postDate;

        public PostDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.nickname = post.getMember().getNickname();
            this.views = post.getViews();
            this.likes = post.getLikes();
            this.postDate = post.getPostDate();
        }
    }
}
