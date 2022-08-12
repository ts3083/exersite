package exersite.workout.Service;

import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Repository.CommentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired MemberService memberService;
    @Autowired PostService postService;
    @Autowired CommentRepository commentRepository;

    @Test
    public void 댓글_저장() throws Exception {
        //given
        Long memberAId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long postId = postService.savePost(memberAId,
                "자유게시판", "t", "c"); // 회원 A가 게시글 저장

        //when
        Long saveCommentId = commentService.saveComment(memberAId, postId, "test1");
        Comment findComment = commentService.findOne(saveCommentId);

        //then
        Assert.assertEquals("a@naver.com", findComment.getMember().getLoginId());
        Assert.assertEquals("A", findComment.getMember().getNickname());
        Assert.assertEquals("t", findComment.getPost().getTitle());
        Assert.assertEquals("c", findComment.getPost().getContent());
        Assert.assertEquals("test1", findComment.getContent());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 댓글_삭제() throws Exception {
        //given
        Long memberAId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long postId = postService.savePost(memberAId,
                "자유게시판", "t", "c"); // 회원 A가 게시글 저장

        //when
        Long saveCommentId1 = commentService.saveComment(memberAId, postId, "test1");
        Long saveCommentId2 = commentService.saveComment(memberAId, postId, "test2");
        commentService.deleteComment(saveCommentId1);

        //then
        Comment comment2 = commentService.findOne(saveCommentId2);
        Assert.assertEquals("test2", comment2.getContent());
        // post의 comment리스트도 삭제되는지 확인
        Post findPost = postService.findOne(postId);
        Assert.assertEquals(1, findPost.getComments().size()); // 목록에 1개가 있어야 함

        commentService.findOne(saveCommentId1);
        fail("예외가 발생해야 합니다.");
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 댓글_존재_게시글삭제() throws Exception {
        //댓글이 달린 게시글을 삭제하면 댓글들이 DB에서 삭제되는지?
        Long memberAId = createAndSaveSampleMemberA();
        Long postId = postService.savePost(memberAId,
                "자유게시판", "t", "c"); // 회원 A가 게시글 저장
        Long saveCommentId1 = commentService.saveComment(memberAId, postId, "test1");

        //when
        postService.deletePost(postId); // 댓글이 달린 게시글 삭제

        //then
        Member member = memberService.findOne(memberAId);
        Assert.assertEquals("회원의 게시글 수는 0", 0, member.getPosts().size());
        Assert.assertEquals("회원의 댓글 수는 0", 0, member.getComments().size());
        commentService.findOne(saveCommentId1);
        fail("예외가 발생해야 합니다.");
    }

    @Test
    public void 댓글_수정() throws Exception {
        //given
        Long memberAId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long postId = postService.savePost(memberAId,
                "자유게시판", "t", "c"); // 회원 A가 게시글 저장
        Long saveCommentId1 = commentService.saveComment(memberAId, postId, "test");

        //when
        commentService.updateComment(saveCommentId1, "newtest");

        //then
        Comment findComment = commentService.findOne(saveCommentId1);
        Assert.assertEquals("수정된 댓글 내용은 newtest", "newtest", findComment.getContent());
    }

    @Test
    public void 회원작성_댓글목록조회() throws Exception {
        //given
        Long memberAId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long memberBId = createAndSaveSampleMemberB(); // 회원 B 저장
        Long postAId = postService.savePost(memberAId,
                "자유게시판", "t", "c"); // 회원 A가 게시글 저장

        //when
        commentService.saveComment(memberAId, postAId, "A comment1");
        commentService.saveComment(memberAId, postAId, "A comment2");
        commentService.saveComment(memberBId, postAId, "B comment1");
        commentService.saveComment(memberBId, postAId, "B comment2");

        //then
        List<Comment> commentsByA = commentRepository.findAllByMember(memberAId);
        List<Comment> commentsByB = commentRepository.findAllByMember(memberBId);
        Assert.assertEquals("A의 댓글 수는 2개", 2, commentsByA.size());
        Assert.assertEquals("B의 댓글 수는 2개", 2, commentsByB.size());
        Assert.assertEquals("A comment2", commentsByA.get(0).getContent());
        Assert.assertEquals("B comment2", commentsByB.get(0).getContent());
    }

    private Long createAndSaveSampleMemberA() {
        Member member = Member.createMember("a@naver.com",
                new Address("서울", "양천로", "123456"),
                "test1", "A", "123456");
        return memberService.join(member);
    }

    private Long createAndSaveSampleMemberB() {
        Member member = Member.createMember("b@naver.com",
                new Address("부산", "해운대", "45782"),
                "test2", "B", "55555");
        return memberService.join(member);
    }
}