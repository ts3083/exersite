package exersite.workout.Controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class PostForm {

    @NotEmpty(message = "필수 사항")
    private String memberName;
    @NotEmpty(message = "필수 사항")
    private String postCategory;
    @NotEmpty(message = "필수 사항")
    private String title;
    @NotEmpty(message = "필수 사항")
    private String content;
}
