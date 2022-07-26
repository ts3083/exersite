package exersite.workout.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime commentDate;
    private String content;
    private int likes;

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    // 비즈니스 로직
    public void clickLike() { // 좋아요 증가
        this.likes++;
    }

    public void cancelLike() { // 좋아요 취소
        this.likes--;
    }
}
