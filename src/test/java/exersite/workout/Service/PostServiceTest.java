package exersite.workout.Service;

import exersite.workout.Domain.Address;
import exersite.workout.Domain.Member;
import exersite.workout.Domain.Post;
import exersite.workout.Domain.PostCategory;
import exersite.workout.Repository.PostCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostCategoryRepository postCategoryRepository;

    @Test
    public void 게시글_등록() throws Exception {
        //given
        // 회원 생성
        Member member = Member.createMember("abc@naver.com",
                new Address("서울", "양천로", "123456"),
                "test1", "A", "123456");
        Long saveMemberId = memberService.join(member);

        // 자유게시판 카테고리 불러오기
        PostCategory postCategory = postCategoryRepository
                .findOneByName("자유게시판");

        String title = "제목";
        String content = "본문내용";

        //when
        Long savePostId = postService.savePost(saveMemberId,
                postCategory.getName(), title, content);

        //then
        Post findPost = postService.findOne(savePostId);
        Assert.assertEquals("게시글 작성 회원의 이름 확인", member.getName(),
                findPost.getMember().getName());
        Assert.assertEquals("게시글 제목이 같은지 확인", title,
                findPost.getTitle());
        Assert.assertEquals("게시글 내용이 같은지 확인", content,
                findPost.getContent());
    }

    @Test
    public void 게시글_제목_본문내용_수정() throws Exception {
        //given
        // 회원 생성
        Member member = Member.createMember("abc@naver.com",
                new Address("서울", "양천로", "123456"),
                "test1", "A", "123456");
        Long saveMemberId = memberService.join(member);

        // 자유게시판 카테고리 불러오기
        PostCategory postCategory = postCategoryRepository
                .findOneByName("자유게시판");

        String title = "수정전 제목";
        String content = "수정전 본문내용";

        //when
        Long savePostId = postService.savePost(saveMemberId,
                postCategory.getName(), title, content);
        // 게시글 수정하기
        postService.updateTitleContent(savePostId,
                "수정후 제목", "수정후 본문");

        //then
        Post findPost = postService.findOne(savePostId);
        Assert.assertEquals("게시글 작성 회원의 이름 확인", member.getName(),
                findPost.getMember().getName());
        Assert.assertEquals("게시글 제목이 같은지 확인", "수정후 제목",
                findPost.getTitle());
        Assert.assertEquals("게시글 내용이 같은지 확인", "수정후 본문",
                findPost.getContent());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 게시글_삭제() throws Exception {
        //given
        // 회원 생성
        Member member = Member.createMember("abc@naver.com",
                new Address("서울", "양천로", "123456"),
                "test1", "A", "123456");
        Long saveMemberId = memberService.join(member);

        // 자유게시판 카테고리 불러오기
        PostCategory postCategory = postCategoryRepository
                .findOneByName("자유게시판");

        String title = "제목";
        String content = "본문내용";

        //when
        Long savePostId = postService.savePost(saveMemberId,
                postCategory.getName(), title, content);

        //then
        postService.deletePost(savePostId);
        Post findDeletedPost = postService.findOne(savePostId);
        fail("예외가 발생해야 합니다");
    }
}