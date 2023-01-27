package exersite.workout.Controller.Dtos;

import exersite.workout.Domain.Comment.Comment;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String nickname;
    private String commentUserLoginId;
    private String content;
    private int likes;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getMember().getNickname();
        this.commentUserLoginId = comment.getMember().getLoginId();
        this.content = comment.getContent();
        this.likes = comment.getLikes().size();
    }
}
