package exersite.workout.Service;

import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member;
import exersite.workout.Domain.Post;
import exersite.workout.Repository.CommentRepository;
import exersite.workout.Repository.MemberRepository;
import exersite.workout.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 댓글 저장
    @Transactional
    public Long saveComment(Long memberId, Long postId, String content) {
        // 회원 찾기
        Member member = memberRepository.findOne(memberId);

        // 게시글 찾기
        Post post = postRepository.findOne(postId);

        // 댓글 생성
        Comment comment = Comment.createComment(member, post, content);

        // 댓글 저장
        commentRepository.save(comment);
        return comment.getId();
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment findComment = commentRepository.findOne(commentId);
        commentRepository.remove(findComment);
    }

    // Comment id로 조회
    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId);
    }
}
