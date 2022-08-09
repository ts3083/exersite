package exersite.workout.Controller.Dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentUpdateDto {

    private String nickname;
    @NotEmpty(message = "필수 사항")
    private String content;
}
