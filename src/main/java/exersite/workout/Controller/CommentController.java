package exersite.workout.Controller;

import exersite.workout.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/detail")
    public RedirectView createComment(@PathVariable("postId") Long postId,
                                      @RequestParam("memberStringId") String memberStringId,
                                      @Valid CommentForm commentForm) {
        Long memberId = Long.parseLong(memberStringId);

        commentService.saveComment(memberId, postId, commentForm.getContent());

        return new RedirectView("/posts/{postId}/detail");
    }
}
