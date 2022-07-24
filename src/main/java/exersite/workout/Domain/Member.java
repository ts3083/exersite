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

    private String name;
    private String nickname;

    @Column(name = "login_email_id")
    private String loginId;
    private String password;

    @Embedded
    private Address address;
    private LocalDateTime memberDate;
    private MemberStatus memberStatus;

    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();


}
