package exersite.workout.Domain.Comment;

import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.likes.CommentLikes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    Set<CommentLikes> likes = new HashSet<>();

    private LocalDateTime commentDate;
    private String content;

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void deleteComment(Member member, Post post) {
        member.getComments().remove(this);
        post.getComments().remove(this);
    }

    public void deleteMemberfromCommmment(Member member) {
        member.getComments().remove(this);
    }

    // 생성 메서드
    public static Comment createComment(Member member, Post post, String content) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(content);
        comment.setCommentDate(LocalDateTime.now());
        return comment;
    }
}
