package exersite.workout.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "login_email_id")
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
