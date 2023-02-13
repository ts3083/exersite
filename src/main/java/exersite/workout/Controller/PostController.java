package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Controller.Dtos.CommentDto;
import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Controller.Dtos.PostDetailDto;
import exersite.workout.Controller.Dtos.PostUpdateDto;
import exersite.workout.Controller.Forms.CommentForm;
import exersite.workout.Controller.Forms.PostForm;
import exersite.workout.Domain.Comment.Comment;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.Post.PostCategory;
import exersite.workout.Domain.Post.PostSearch;
import exersite.workout.Repository.CommentRepository;
import exersite.workout.Repository.post.simplequery.PostDto;
import exersite.workout.Service.CommentService;
import exersite.workout.Service.Likes.PostLikesService;
import exersite.workout.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final PostLikesService postLikesService;

    @GetMapping("/posts/freeCategory")
    public String freeCategoryPosts(Model model) {
//        List<PostDto> freePosts = postSearchQueryRepository // dto로 조회하는 방법
//                .findPostDtos("자유게시판");
        List<PostDto> freePosts = postService.findAllPostDtosDesc("free");
        model.addAttribute("freePosts", freePosts);
        return "posts/freeBoard";
    }

    @GetMapping("/posts/secretCategory")
    public String secretCategoryPosts(Model model) {
//        List<PostDto> secretPosts = postSearchQueryRepository // dto로 조회하는 방법
//                .findPostDtos("비밀게시판");
        List<PostDto> secretPosts = postService.findAllPostDtosDesc("secret");
        model.addAttribute("secretPosts", secretPosts);
        return "posts/secretBoard";
    }

    @GetMapping("/posts/createPost")
    public String creatPost(Model model) {
        List<PostCategory> postCategories
                = Arrays.stream(PostCategory.values()).collect(Collectors.toList());

        model.addAttribute("postCategories", postCategories);
        model.addAttribute("postForm", new PostForm());
        return "posts/createPostForm";
    }

    @PostMapping("/posts/createPost")
    public String post(@CurrentUser Member member,
                       @RequestParam("postCategoryName") String postCategoryName,
                       @Valid PostForm postForm, BindingResult result) {
        if (postCategoryName.equals("") || result.hasErrors()) {
            // 에러가 있으면 다시 게시글 작성 창으로
            return "posts/createPostForm";
        }
        postService.savePost(member.getId(), postCategoryName,
                postForm.getTitle(), postForm.getContent());
        return "boardHome";
    }

    @GetMapping("/posts/search") // 검색할 때는 어떤 경우든지 get매핑
    public String postList(@ModelAttribute("postSearch") PostSearch postSearch
            , Model model) {
        // 주의 : 처음 넘겼을 때, postSearch의 필드들은 null로 설정됨
        // 검색 버튼을 누르면 : 아무것도 입력하지 않으면 ""으로 넘어옴(구분 필수)
        List<PostDto> postDtos = postService.findPostsByDsl(postSearch);
        model.addAttribute("postDtos", postDtos);
        return "posts/postList";
    }

    @GetMapping("/posts/{postId}/detail")
    public String detailPost(@PathVariable("postId") Long postId,
                             @CurrentUser Member member, Model model) {
        // 상세보기 클릭
        // 조회수 증가
        postService.updateViewsByClickPost(postId);

        // 해당 게시글의 post를 dto로 변환하여 html에 전달
        PostDetailDto postDto = postService.findOneByPostDetailDto(postId);

        // 해당 게시글에 달린 댓글 리스트 dto로 변환하여 html에 전달
        List<CommentDto> commentDtos = commentService.findAllCommentDtosByPostId(postId);


        // 댓글 작성 시 값을 전달받을 commentForm 객체 전달
        model.addAttribute("user", new MemberDto(member));
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("postDto", postDto);
        model.addAttribute("commentDtos", commentDtos);
        return "posts/detailInformation";
    }

    // 게시글 수정
    @GetMapping("/posts/{id}/edit")
    public String updatePostForm(@PathVariable("id") Long postId, Model model) {
        Post post = postService.findOne(postId);
        model.addAttribute("postDto", new PostDto(post));
        model.addAttribute("postUpdateForm", new PostUpdateDto(postId));
        return "posts/updatePostForm";
    }

    @PostMapping("/posts/updatePostForm")
    public String updatePost(@Valid PostUpdateDto postUpdateDto) {
        postService.updateTitleContent(postUpdateDto.getId(),
                postUpdateDto.getTitle(), postUpdateDto.getContent());
        return "boardHome";
    }

    // 게시글 삭제
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return "boardHome";
    }

    /*@PostMapping("/posts/{postId}/clickPostLikes")
    public RedirectView clickPostLikes(@CurrentUser Member member,
                                       @PathVariable("postId") Long postId) {

        postLikesService.clickPostLikes(member.getId(), postId);

        return new RedirectView("/posts/{postId}/detail");
    }*/

    @PostMapping("/posts/{postId}/clickPostLikes")
    public String clickPostLikes(@CurrentUser Member member,
                                       @PathVariable("postId") Long postId) {

        postLikesService.clickPostLikes(member.getId(), postId);

        return "redirect:/posts/" + postId + "/detail";
    }
}
