package exersite.workout.Domain.Post;

import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.likes.PostLikes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    Set<PostLikes> likes = new HashSet<>();

    private LocalDateTime postDate;
    private int views;
    private String title;
    private String content;

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void setPostCategory(PostCategory postCategory) {
        this.postCategory = postCategory;
        postCategory.getPosts().add(this);
    }

    public void deletePost(Member member) {
        member.getPosts().remove(this);
    }

    // 생성 메서드
    public static Post createPost(Member member, PostCategory postCategory,
                                  String title, String content) {
        Post post = new Post();
        post.setMember(member);
        post.setPostCategory(postCategory);
        post.setTitle(title);
        post.setContent(content);
        post.setViews(0);
        post.setPostDate(LocalDateTime.now());
        return post;
    }

    // 비즈니스 로직

    public void clickPost() { // 조회수 증가
        this.views += 1;
    }
}
