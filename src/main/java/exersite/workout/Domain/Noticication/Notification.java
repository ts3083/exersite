package exersite.workout.Domain.Noticication;

import exersite.workout.Domain.Member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Notification {

    @Id @GeneratedValue
    private Long id;

    private String title; // 알림 제목

    private String content; // 알림 내용

    private String link; // 링크

    private boolean checked; // 알림 확인 여부

    private LocalDateTime createdTime; // 알림이 만들어진 시간

    @OneToOne(fetch = FetchType.LAZY)
    private Member member; // 글쓴 회원 참조
}
