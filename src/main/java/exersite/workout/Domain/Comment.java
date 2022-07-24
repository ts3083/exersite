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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 댓글 저장하면 post 자동 DB저장
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 댓글 저장하면 member 자동 DB저장
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime commentDate;
    private String content;
    private Integer likes;
}
