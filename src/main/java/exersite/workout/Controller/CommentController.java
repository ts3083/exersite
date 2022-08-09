package exersite.workout.Controller;

import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Controller.Forms.CommentForm;
import exersite.workout.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/createComment")
    public RedirectView createComment(@AuthenticationPrincipal PrincipalDetails details,
                                      @PathVariable Long postId,
                                      @Valid CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/posts/{postId}/detail");
        }

        commentService.saveComment(details.getId(), postId, commentForm.getContent());

        return new RedirectView("/posts/{postId}/detail");
    }
}
