package exersite.workout.Controller.Dtos;

import exersite.workout.Domain.Post.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailDto {
    private Long postId;
    private String memberLoginId;
    private String title;
    private String content;
    private String nickname;
    private int views;
    private int likes;
    private LocalDateTime postDate;

    public PostDetailDto(Post post) {
        this.postId = post.getId();
        this.memberLoginId = post.getMember().getLoginId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getMember().getNickname();
        this.views = post.getViews();
        this.likes = post.getLikes().size();
        this.postDate = post.getPostDate();
    }
}
