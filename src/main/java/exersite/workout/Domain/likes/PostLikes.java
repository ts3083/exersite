package exersite.workout.Domain.likes;

import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostLikes {

    @Id @GeneratedValue
    @Column(name = "post_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 연관관계 메서드
    public void setPost(Post post) {
        this.post = post;
        post.getLikes().add(this);
    }

    // 생성 메서드 - 생성 시 누가, 어떤 게시글에 좋아요했는지 반드시 필요
    public static PostLikes createPostLikes(Member member, Post post) {
        PostLikes postLikes = new PostLikes();
        postLikes.setMember(member);
        postLikes.setPost(post);
        return postLikes;
    }
}
