package exersite.workout.Controller;

import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Controller.Dtos.MemberUpdateDto;
import exersite.workout.Controller.Dtos.myCommentsDto;
import exersite.workout.Controller.Dtos.myPostsDto;
import exersite.workout.Domain.Comment;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Service.CommentService;
import exersite.workout.Service.MemberService;
import exersite.workout.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal PrincipalDetails Details,
                         Model model) {
        MemberDto memberDto = new MemberDto(Details);
        model.addAttribute("memberDto", memberDto);
        return "myPages/profile";
    }

    // 내가 쓴 게시글 리스트
    @GetMapping("/myPosts")
    public String myPosts(@AuthenticationPrincipal PrincipalDetails details,
                          Model model) {
        List<Post> posts = postService.findAllByUser(details.getId());
        List<myPostsDto> myPostsDtos = posts.stream()
                .map(post -> new myPostsDto(post)).collect(Collectors.toList());

        model.addAttribute("myPostsDtos", myPostsDtos);
        model.addAttribute("userNickname", details.getNickname());
        return "myPages/myPosts";
    }

    // 내가 쓴 댓글 리스트
    @GetMapping("/myComments")
    public String myComments(@AuthenticationPrincipal PrincipalDetails details,
                          Model model) {
        List<Comment> comments = commentService.findAllByUser(details.getId());
        List<myCommentsDto> myCommentsDtos = comments.stream().
                map(comment -> new myCommentsDto(comment)).collect(Collectors.toList());

        model.addAttribute("myCommentsDtos", myCommentsDtos);
        model.addAttribute("userNickname", details.getNickname());
        return "myPages/myComments";
    }

    // 내 프로필 수정
    @GetMapping("/myPage/editProfile")
    public String updateProfileForm(@AuthenticationPrincipal PrincipalDetails details,
                                Model model) {
        model.addAttribute("memberDto", new MemberDto(details));
        model.addAttribute("memberUpdateDto", new MemberUpdateDto(details.getId()));
        return "myPages/updateProfileForm";
    }

    @PostMapping("/myPages/updateProfileForm")
    public String updateProfile(@Valid MemberUpdateDto memberUpdateDto) {
        memberService.updateMember(memberUpdateDto);
        return "redirect:/myPages";
    }
}
