package exersite.workout.Service.Likes;

import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.likes.CommentLikes;
import exersite.workout.Repository.Likes.CommentLikesRepository;
import exersite.workout.Service.CommentService;
import exersite.workout.Service.MemberService;
import exersite.workout.Service.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentLikesServiceTest {

    @Autowired CommentLikesService commentLikesService;
    @Autowired CommentService commentService;
    @Autowired MemberService memberService;
    @Autowired PostService postService;
    @Autowired CommentLikesRepository commentLikesRepository;

    @Test
    public void 댓글_좋아요_테스트() throws Exception {
        //given
        Long memberId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long postId = postService.savePost(memberId, "자유게시판", "ta1", "ca1");
        Long commentId = commentService.saveComment(memberId, postId, "content");

        //when
        commentLikesService.clickCommentLikes(memberId, commentId);

        //then
//        Optional<CommentLikes> optionalCommentLikes
//                = commentLikesRepository.findOneByMemberAndComment(memberId, commentId);
//        Assert.assertTrue("DB에 좋아요 저장되었는지 확인", optionalCommentLikes.isPresent());

        CommentLikes commentLikes = commentLikesRepository.findOneByMemberAndComment(memberId, commentId);
        Assert.assertNotNull("DB에 좋아요 저장되었는지 확인", commentLikes);

        Comment findComment = commentService.findOne(commentId);
        Assert.assertEquals("좋아요 개수가 증가했는지 확인", 1, findComment.getLikes().size());
    }

    @Test
    public void 댓글_좋아요취소_테스트() throws Exception {
        //given
        Long memberId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long postId = postService.savePost(memberId, "자유게시판", "ta1", "ca1");
        Long commentId = commentService.saveComment(memberId, postId, "content");
        commentLikesService.clickCommentLikes(memberId, commentId); // 좋아요 저장

        //when
        commentLikesService.clickCommentLikes(memberId, commentId); // 좋아요 취소

        //then
//        Optional<CommentLikes> optionalCommentLikes
//                = commentLikesRepository.findOneByMemberAndComment(memberId, commentId);
//        Assert.assertTrue("DB에 좋아요 삭제되었는지 확인", optionalCommentLikes.isEmpty());

        CommentLikes commentLikes = commentLikesRepository.findOneByMemberAndComment(memberId, commentId);
        Assert.assertNull("DB에 좋아요 삭제되었는지 확인", commentLikes);

        Comment findComment = commentService.findOne(commentId);
        Assert.assertEquals("좋아요 개수가 감소했는지 확인", 0, findComment.getLikes().size());
    }

    private Long createAndSaveSampleMemberA() {
        Member member = Member.createMember("a@naver.com",
                new Address("서울", "양천로", "123456"),
                "test1", "A", "123456");
        return memberService.join(member);
    }
}