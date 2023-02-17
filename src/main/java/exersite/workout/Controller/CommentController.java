package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Controller.Dtos.CommentUpdateDto;
import exersite.workout.Controller.Forms.CommentForm;
import exersite.workout.Domain.Comment.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.CommentService;
import exersite.workout.Service.Likes.CommentLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentLikesService commentLikesService;

    // 댓글 생성
    @PostMapping("/posts/{postId}/createComment")
    public RedirectView createComment(@CurrentUser Member member,
                                      @PathVariable Long postId,
                                      @Valid CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/posts/{postId}/detail");
        }

        commentService.saveComment(member.getId(), postId, commentForm.getContent());

        return new RedirectView("/posts/{postId}/detail");
    }

    // 댓글 수정
    @GetMapping("/comments/{id}/update")
    public String updateCommentForm(@PathVariable("id") Long commentId, Model model) {
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto();
        model.addAttribute("updateCommentDto", commentUpdateDto);
        model.addAttribute("commentId", commentId);

        return "comments/updateComment";
    }

    @PostMapping("/comments/{id}/update")
    public RedirectView updateComment(@PathVariable("id") Long commentId,
                                @Valid CommentUpdateDto commentUpdateDto) {
        commentService.updateComment(commentId, commentUpdateDto.getContent());
        Long postId = commentService.findOne(commentId).getPost().getId();

        String url = "/posts/" + postId + "/detail";
        return new RedirectView(url);
    }

    // 댓글 삭제
    @PostMapping("/comments/{id}/delete")
    public RedirectView deleteComment(@PathVariable("id") Long commentId) {
        Long postId = commentService.findOne(commentId).getPost().getId();

        commentService.deleteComment(commentId);

        String url = "/posts/" + postId + "/detail";
        return new RedirectView(url);
    }

    // 댓글 좋아요
    @PostMapping("/posts/{commentId}/clickCommentLikes")
    public RedirectView clickCommentLikes(@CurrentUser Member member,
                                          @PathVariable("commentId") Long commentId) {

        commentLikesService.clickCommentLikes(member.getId(), commentId);
        Long postId = commentService.findOne(commentId).getPost().getId();

        String url = "/posts/" + postId + "/detail";
        return new RedirectView(url);
    }
}
