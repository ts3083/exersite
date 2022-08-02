package exersite.workout.Repository.post.simplequery;

import exersite.workout.Domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private int views;
    private int likes;
    private LocalDateTime postDate;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getMember().getNickname();
        this.views = post.getViews();
        this.likes = post.getLikes();
        this.postDate = post.getPostDate();
    }

    public PostDto(Long id, String title, String content, String nickname,
                   int views, int likes, LocalDateTime postDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.views = views;
        this.likes = likes;
        this.postDate = postDate;
    }
}
