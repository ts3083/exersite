package exersite.workout.api;

import exersite.workout.Controller.Forms.PostForm;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.PostService;
import exersite.workout.api.Response.BasicResponse;
import exersite.workout.api.Response.CommonResponse;
import exersite.workout.api.Response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/freeCategory")
    public ResponseEntity<? extends CommonResponse> freeCategoryPostsApi() {
        return ResponseEntity.ok()
                .body(new BasicResponse<>(postService.findAllPostDtosDesc("free")));
    }

    @GetMapping("/secretCategory")
    public ResponseEntity<? extends CommonResponse> secretCategoryPostsApi() {
        return ResponseEntity.ok()
                .body(new BasicResponse<>(postService.findAllPostDtosDesc("secret")));
    }

    @PostMapping("/createPost/{postCategoryName}")
    public ResponseEntity<? extends CommonResponse> createPost(Member member, @PathVariable("postCategoryName") String postCategoryName,
                             @Valid @RequestBody PostForm postForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("잘못된 요청입니다."));
        }
        postService.savePost(member.getId(), postCategoryName,
                postForm.getTitle(), postForm.getContent());
        return ResponseEntity.ok().build();
    }
}
