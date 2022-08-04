package exersite.workout.Controller;

import exersite.workout.Domain.Comment;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String nickname;
    private String content;
    private int likes;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
    }
}
