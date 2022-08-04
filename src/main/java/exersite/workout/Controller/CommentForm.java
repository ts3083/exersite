package exersite.workout.Controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class CommentForm {

    private Long postId;
    private String nickname;
    @NotEmpty(message = "필수 사항")
    private String content;

    public CommentForm(Long postId) {
        this.postId = postId;
    }
}
