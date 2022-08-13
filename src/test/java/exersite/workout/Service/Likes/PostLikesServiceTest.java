package exersite.workout.Service.Likes;

import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.likes.PostLikes;
import exersite.workout.Repository.Likes.PostLikesRepository;
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
public class PostLikesServiceTest {

    @Autowired PostLikesService postLikesService;
    @Autowired MemberService memberService;
    @Autowired PostService postService;
    @Autowired PostLikesRepository postLikesRepository;

    @Test
    public void 게시글_좋아요_테스트() throws Exception {
        //given
        Long memberId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long postId = postService.savePost(memberId, "자유게시판", "ta1", "ca1");

        //when
        postLikesService.clickPostLikes(memberId, postId); // 좋아요 클릭

        //then
        Optional<PostLikes> optionalPostLikes = postLikesRepository.findOneByMemberAndPost(memberId, postId);
        Assert.assertTrue("DB에 좋아요 저장되었는지 확인", optionalPostLikes.isPresent());

        Post findPost = postService.findOne(postId);
        Assert.assertEquals("좋아요 개수가 증가했는지 확인", 1, findPost.getLikes().size());
    }
    
    @Test
    public void 게시글_좋아요취소_테스트() throws Exception {
        //given
        Long memberId = createAndSaveSampleMemberA(); // 회원 A 저장
        Long postId = postService.savePost(memberId, "자유게시판", "ta1", "ca1");
        postLikesService.clickPostLikes(memberId, postId); // 좋아요 클릭

        //when
        postLikesService.clickPostLikes(memberId, postId); // 좋아요가 눌린 상태에서 한번 더 클릭
        
        //then
        Optional<PostLikes> optionalPostLikes = postLikesRepository.findOneByMemberAndPost(memberId, postId);
        Assert.assertTrue("DB에 좋아요 삭제되었는지 확인", optionalPostLikes.isEmpty());

        Post findPost = postService.findOne(postId);
        Assert.assertEquals("좋아요 개수가 감소했는지 확인", 0, findPost.getLikes().size());
    }

    private Long createAndSaveSampleMemberA() {
        Member member = Member.createMember("a@naver.com",
                new Address("서울", "양천로", "123456"),
                "test1", "A", "123456");
        return memberService.join(member);
    }
}