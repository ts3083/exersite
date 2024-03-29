package exersite.workout.Domain.Member;

import exersite.workout.Domain.Comment.Comment;
import exersite.workout.Domain.Post.Post;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "login_email_id", unique = true)
    private String loginId;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus; // 회원 상태 [MEMBER, DELETED]

    private String name;
    @Column(unique = true)
    private String nickname;
    private String password;
    private LocalDateTime memberDate;

    // 생성메서드
    public static Member createMember(String loginId, Address address,
                                      String name, String nickname,
                                      String password) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setAddress(address);
        member.setName(name);
        member.setNickname(nickname);
        member.setMemberStatus(MemberStatus.MEMBER);
        member.setPassword(password);
        member.setMemberDate(LocalDateTime.now());
        return member;
    }
}
