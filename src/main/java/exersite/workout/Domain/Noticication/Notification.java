package exersite.workout.Domain.Noticication;

import exersite.workout.Domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    private Member member; // todo : 해쉬태그 설정 알림으로 변경
}
