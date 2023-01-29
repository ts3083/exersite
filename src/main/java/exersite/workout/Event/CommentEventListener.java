package exersite.workout.Event;

import exersite.workout.Domain.Comment.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Noticication.Notification;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Async
@Component
@Transactional
@RequiredArgsConstructor
public class CommentEventListener {

    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleCommentCreatedEvent(CommentCreatedEvent commentCreatedEvent) {
        /**
         * DB에 notification 정보를 저장
         * 1. 게시글을 생성한 member 정보를 조회해야 함
         * 2. 게시글 작성자에 대한 notification을 생성하여 저장
         * */
        Comment comment = commentCreatedEvent.getComment(); // 작성한 댓글
        Post post = comment.getPost(); // 댓글다는 게시글
        Member member = post.getMember(); // 게시글 작성자
        // 알림 저장
        saveNotification(post, member);
    }

    private void saveNotification(Post post, Member member) {
        Notification notification = new Notification();
        notification.setTitle("댓글이 달렸습니다");
        notification.setContent("내가 쓴 " + post.getTitle() + "에 댓글이 달렸어요");
        notification.setLink("/posts/"+ post.getId() +"/detail");
        notification.setChecked(false);
        notification.setCreatedTime(LocalDateTime.now());
        notification.setMember(member);
        notificationRepository.save(notification);
    }
}
