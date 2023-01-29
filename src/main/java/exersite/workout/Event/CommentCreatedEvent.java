package exersite.workout.Event;

import exersite.workout.Domain.Comment.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentCreatedEvent {

    private Comment comment;

    public CommentCreatedEvent(Comment comment) {
        this.comment = comment;
    }
}
