package exersite.workout.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 댓글 add하고 게시글 객체 persist하면 댓글을 따로 persist하지 않아도 DB에 저장
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    private LocalDateTime postDate;
    private int views;
    private int likes;
    private String title;
    private String content;

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void setPostCategory(PostCategory postCategory) {
        this.postCategory = postCategory;
        postCategory.getPosts().add(this);
    }

    // 비즈니스 로직
    public void clickLike() { // 좋아요 증가
        this.likes++;
    }

    public void cancelLike() { // 좋아요 취소
        this.likes--;
    }

    public void clickPost() { // 조회수 증가
        this.views++;
    }
}
