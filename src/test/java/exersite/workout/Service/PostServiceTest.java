package exersite.workout.Service;

import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.Post.PostCategory;
import exersite.workout.Domain.Post.PostSearch;
import exersite.workout.Repository.PostCategoryRepository;
import exersite.workout.Repository.PostRepository;
import exersite.workout.Repository.post.simplequery.PostDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired PostCategoryRepository postCategoryRepository;
    @Autowired PostRepository postRepository;

    @Test
    public void 게시글_등록() throws Exception {
        //given
        // 회원 생성
        Long saveMemberId = createAndSaveSampleMemberA();

        // 자유게시판 카테고리 불러오기
        PostCategory postCategory = postCategoryRepository
                .findOneByName("자유게시판");

        String title = "제목";
        String content = "본문내용";

        //when
        Long savePostId = postService.savePost(saveMemberId,
                "자유게시판", title, content);

        //then
        Post findPost = postService.findOne(savePostId);
        assertEquals("게시글 작성 회원의 이름 확인", "test1",
                findPost.getMember().getName());
        assertEquals("게시글 제목이 같은지 확인", title,
                findPost.getTitle());
        assertEquals("게시글 내용이 같은지 확인", content,
                findPost.getContent());
    }

    @Test
    public void 게시글_제목_본문내용_수정() throws Exception {
        //given
        // 회원 생성
        Long saveMemberId = createAndSaveSampleMemberA();

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
        postService.findOne(savePostId);
        fail("예외가 발생해야 합니다");
    }

    @Test
    public void 회원작성_게시글목록조회() throws Exception {
        //given
        // 자유게시판 카테고리 불러오기
        PostCategory postCategory = postCategoryRepository.findOneByName("자유게시판");
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        Long memberIdB = createAndSaveSampleMemberB(); // 회원B 저장

        //when
        // A가 게시글 2개 작성
        postService.savePost(memberIdA, "자유게시판", "ta1", "ca1");
        postService.savePost(memberIdA, "자유게시판", "ta2", "ca2");
        // B가 게시글 2개 작성
        postService.savePost(memberIdB, "자유게시판", "tb1", "cb1");
        postService.savePost(memberIdB, "자유게시판", "tb2", "cb2");

        //then
        List<Post> postsByA = postRepository.findAllByMember(memberIdA); // A가 작성한 모든 게시글 리스트
        List<Post> postsByB = postRepository.findAllByMember(memberIdB); // A가 작성한 모든 게시글 리스트
        assertEquals("A의 작성게시글 개수 확인", 2, postsByA.size());
        assertEquals("B의 작성게시글 개수 확인", 2, postsByB.size());
    }

    @Test
    public void 게시글_검색_작성자닉네임() throws Exception {
        //given
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        postService.savePost(memberIdA, "자유게시판", "ta1", "ca1");

        //when
        PostSearch postSearch = new PostSearch();
        postSearch.setNickname("A");

        //then
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        assertEquals(1, postDtos.size());
        assertEquals("ta1", postDtos.get(0).getTitle());
        assertEquals("A", postDtos.get(0).getNickname());
    }

    @Test
    public void 게시글_검색_제목() throws Exception {
        //given
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        postService.savePost(memberIdA, "자유게시판", "ta1", "ca1");

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
        postService.savePost(memberIdA, "자유게시판", "ta1", "ca1");

        //when
        PostSearch postSearch = new PostSearch();
        postSearch.setTitleOrContent("ca");

        //then
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        assertEquals(1, postDtos.size());
        assertEquals("ta1", postDtos.get(0).getTitle());
        assertEquals("A", postDtos.get(0).getNickname());
    }

    @Test
    public void 게시글_검색_제목내용에_겹치는_문자() throws Exception {
        //given
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        postService.savePost(memberIdA, "자유게시판", "ta1", "ca1");
        postService.savePost(memberIdA, "자유게시판", "t2", "c2");

        //when
        PostSearch postSearch = new PostSearch();
        postSearch.setTitleOrContent("a");

        //then
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        assertEquals(1, postDtos.size());
        assertEquals("ta1", postDtos.get(0).getTitle());
        assertEquals("A", postDtos.get(0).getNickname());
    }

    @Test
    public void 게시글_검색_둘다검색() throws Exception {
        //given
        Long memberIdA = createAndSaveSampleMemberA(); // 회원A 저장
        Long memberIdB = createAndSaveSampleMemberB();
        postService.savePost(memberIdA, "자유게시판", "ta1", "ca1");
        postService.savePost(memberIdB, "자유게시판", "t2", "c2");

        //when
        PostSearch postSearch = new PostSearch();
        postSearch.setTitleOrContent("a");
        postSearch.setNickname("B");

        //then
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        assertEquals(0, postDtos.size());
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