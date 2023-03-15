package exersite.workout.Service;

import exersite.workout.Controller.Forms.MemberForm;
import exersite.workout.Controller.Forms.PostForm;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.Post.PostSearch;
import exersite.workout.Repository.PostRepository;
import exersite.workout.Repository.post.simplequery.PostDto;
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
public class PostServiceTest {

    @Autowired PostService postService;
    @Autowired MemberService memberService;
    @Autowired PostRepository postRepository;

    @Test
    public void 게시글_등록() throws Exception {
        //given
        // 회원 생성
        Long saveMemberId = createAndSaveSampleMemberA();

        PostForm postForm = new PostForm();
        postForm.setTitle("제목");
        postForm.setContent("본문내용");

        //when
        Long savePostId = postService.savePost(saveMemberId,
                "free", postForm);

        //then
        Post findPost = postService.findOne(savePostId);
        assertEquals("게시글 작성 회원의 이름 확인", "test1",
                findPost.getMember().getName());
        assertEquals("게시글 제목이 같은지 확인", "제목",
                findPost.getTitle());
        assertEquals("게시글 내용이 같은지 확인", "본문내용",
                findPost.getContent());
    }

    @Test
    public void 게시글_제목_본문내용_수정() throws Exception {
        //given
        // 회원 생성
        Long saveMemberId = createAndSaveSampleMemberA();

        PostForm postForm = new PostForm();
        postForm.setTitle("수정전 제목");
        postForm.setContent("수정전 본문내용");

        //when
        Long savePostId = postService.savePost(saveMemberId,
                "free", postForm);
        // 게시글 수정하기
        postService.updateTitleContent(savePostId,
                "수정후 제목", "수정후 본문");

        //then
        Post findPost = postService.findOne(savePostId);
        assertEquals("게시글 작성 회원의 이름 확인", "test1",
                findPost.getMember().getName());
        assertEquals("게시글 제목이 같은지 확인", "수정후 제목",
                findPost.getTitle());
        assertEquals("게시글 내용이 같은지 확인", "수정후 본문",
                findPost.getContent());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 게시글_삭제() throws Exception {
        //given
        // 회원 생성
        Long saveMemberId = createAndSaveSampleMemberA();

        PostForm postForm = new PostForm();
        postForm.setTitle("제목");
        postForm.setContent("본문내용");

        //when
        Long savePostId = postService.savePost(saveMemberId,
                "free", postForm);

        //then
        postService.deletePost(savePostId);
        postService.findOne(savePostId);
        fail("예외가 발생해야 합니다");
    }

    @Test
    public void 게시글_검색_작성자닉네임() throws Exception {
        //given
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        PostForm postForm1 = new PostForm();
        postForm1.setTitle("ta1");
        postForm1.setContent("ca1");
        postService.savePost(memberIdA, "free", postForm1);

        //when
        PostSearch postSearch = new PostSearch();
        postSearch.setNickname("A");

        //then
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        for (PostDto postDto : postDtos) {
            System.out.println(postDto.getNickname());
        }
    }

    @Test
    public void 게시글_검색_제목() throws Exception {
        //given
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        PostForm postForm1 = new PostForm();
        postForm1.setTitle("ta1");
        postForm1.setContent("ca1");
        postService.savePost(memberIdA, "free", postForm1);

        //when
        PostSearch postSearch = new PostSearch();
        postSearch.setTitleOrContent("ta");

        //then
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        assertEquals(1, postDtos.size());
        assertEquals("ta1", postDtos.get(0).getTitle());
        assertEquals("A", postDtos.get(0).getNickname());
    }

    @Test
    public void 게시글_검색_내용() throws Exception {
        //given
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        PostForm postForm1 = new PostForm();
        postForm1.setTitle("ta1");
        postForm1.setContent("ca1");
        postService.savePost(memberIdA, "free", postForm1);

        //when
        PostSearch postSearch = new PostSearch();
        postSearch.setTitleOrContent("ca");

        //then
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        assertEquals(1, postDtos.size());
        assertEquals("ta1", postDtos.get(0).getTitle());
        assertEquals("A", postDtos.get(0).getNickname());
    }

    private Long createAndSaveSampleMemberA() {
        MemberForm memberForm = MemberForm.createMember("a@naver.com",
                new Address("서울", "양천로", "123456"),
                "test1", "A", "123456");
        return memberService.join(memberForm);
    }

    private Long createAndSaveSampleMemberB() {
        MemberForm memberForm = MemberForm.createMember("a@naver.com",
                new Address("부산", "해운대", "45782"),
                "test2", "B", "55555");
        return memberService.join(memberForm);
    }
}