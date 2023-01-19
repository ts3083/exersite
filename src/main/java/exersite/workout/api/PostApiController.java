package exersite.workout.api;

import exersite.workout.Controller.Dtos.myPostsDto;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/posts/freeCategory")
    public List<myPostsDto> freeCategoryPostsApi() {
        List<Post> posts = postService.findAllDesc("free");
        List<myPostsDto> freePosts = posts.stream()
                .map(post -> new myPostsDto(post)).collect(Collectors.toList());

        return freePosts;
    }
}
