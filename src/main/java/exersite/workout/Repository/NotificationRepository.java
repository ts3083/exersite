package exersite.workout.Repository;

import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Noticication.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countByMemberAndChecked(Member member, boolean checked);
}
