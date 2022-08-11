package exersite.workout.Controller.Forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class CommentForm {

    private String nickname;
    @NotEmpty(message = "필수 사항")
    private String content;
}
