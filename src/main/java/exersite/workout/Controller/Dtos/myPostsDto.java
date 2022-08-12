package exersite.workout.Controller.Dtos;

import exersite.workout.Domain.Post.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class myPostsDto {

    private Long id;
    private String title;
    private String content;
    private int views;
    private int likes;
    private LocalDateTime postDate;

    public myPostsDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.likes = post.getLikes().size();
        this.postDate = post.getPostDate();
    }
}
