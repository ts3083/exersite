package exersite.workout.Domain.likes;

import exersite.workout.Domain.Comment.Comment;
import exersite.workout.Domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CommentLikes {

    @Id @GeneratedValue
    @Column(name = "comment_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    // 연관관계 메서드
    public void setComment(Comment comment) {
        this.comment = comment;
        comment.getLikes().add(this);
    }

    // 생성 메서드 - 생성 시 누가, 어떤 댓글에 좋아요했는지 반드시 필요
    public static CommentLikes createPostLikes(Member member, Comment comment) {
        CommentLikes commentLikes = new CommentLikes();
        commentLikes.setMember(member);
        commentLikes.setComment(comment);
        return commentLikes;
    }
}
