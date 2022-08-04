package exersite.workout.Controller;

import exersite.workout.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/new")
    public String createComment(@Valid CommentForm commentForm) {
        String s = commentForm.getPostId().toString();


        return "redirect:/posts/s/detail";
    }

}
