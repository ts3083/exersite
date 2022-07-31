package exersite.workout.Controller;

import exersite.workout.Domain.Post;
import exersite.workout.Repository.PostRepository;
import exersite.workout.Service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

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
        return "posts/freeboard";
    }

    @Data
    static class PostDto { // 게시글 목록을 위해 전달하는 dto
        private Long id;
        private String title;
        private String nickname;
        private int views;
        private int likes;
        private LocalDateTime postDate;

        public PostDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.nickname = post.getMember().getNickname();
            this.views = post.getViews();
            this.likes = post.getLikes();
            this.postDate = post.getPostDate();
        }
    }
}
