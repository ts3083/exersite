package exersite.workout.Service;

import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Repository.CommentRepository;
import exersite.workout.Repository.MemberRepository;
import exersite.workout.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 댓글 수정
    @Transactional
    public void updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findOne(commentId);
        comment.setContent(newContent);
    }

    // Comment id로 조회
    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId);
    }

    // 특정 회원이 작성한 모든 댓글 조회
    public List<Comment> findAllByUser(Long memberId) {
        return commentRepository.findAllByMember(memberId);
    }
}
