package exersite.workout.api;

import exersite.workout.Repository.post.simplequery.PostDto;
import exersite.workout.Service.PostService;
import exersite.workout.api.Response.BasicResponse;
import exersite.workout.api.Response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/posts/freeCategory")
    public ResponseEntity<? extends CommonResponse> freeCategoryPostsApi() {
        return ResponseEntity.ok()
                .body(new BasicResponse<>(postService.findAllPostDtosDesc("free")));
    }
}
