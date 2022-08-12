package exersite.workout.Controller.Dtos;

import exersite.workout.Domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class myCommentsDto {

    private Long commentId;
    private Long postId;
    private String postTitle;
    private String content;
    private int likes;
    private LocalDateTime commentDate;

    public myCommentsDto(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.postTitle = comment.getPost().getTitle();
        this.content = comment.getContent();
        this.likes = comment.getLikes().size();
        this.commentDate = comment.getCommentDate();
    }
}
