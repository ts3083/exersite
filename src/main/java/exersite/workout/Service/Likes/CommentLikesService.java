package exersite.workout.Service.Likes;

import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.likes.CommentLikes;
import exersite.workout.Repository.Likes.CommentLikesRepository;
import exersite.workout.Service.CommentService;
import exersite.workout.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;
    private final CommentService commentService;
    private final MemberService memberService;

    public void clickCommentLikes(Long memberId, Long commentId) {
        Optional<CommentLikes> optionalCommentLikes =
                commentLikesRepository.findOneByMemberAndComment(memberId, commentId);
        if(optionalCommentLikes.isPresent()) {
            // 이미 좋아요 누른 경우 : 좋아요 삭제
            deleteCommentLikes(optionalCommentLikes.get(), commentId);
        } else {
            // 좋아요를 누르지 않은 경우
            saveCommentLikes(memberId, commentId);
        }
    }

    private void saveCommentLikes(Long memberId, Long commentId) {
        Member member = memberService.findOne(memberId);
        Comment comment = commentService.findOne(commentId);
        CommentLikes commentLikes = CommentLikes.createPostLikes(member, comment);
        commentLikesRepository.save(commentLikes);
    }

    private void deleteCommentLikes(CommentLikes commentLikes, Long commentId) {
        // 삭제 : 게시글이 가진 Likes Set에서 postLikes 삭제
        Comment comment = commentService.findOne(commentId);
        comment.getLikes().remove(commentLikes);
        commentLikesRepository.delete(commentLikes);
    }
}
