package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Controller.Dtos.myCommentsDto;
import exersite.workout.Controller.Dtos.myPostsDto;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.CommentService;
import exersite.workout.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/myPage")
    public String myPage(@CurrentUser Member member, Model model) {
        MemberDto memberDto = new MemberDto(member);
        model.addAttribute("memberDto", memberDto);
        return "myPages/profile";
    }

    // 내가 쓴 게시글 리스트
    @GetMapping("/myPosts")
    public String myPosts(@CurrentUser Member member, Model model) {
        List<myPostsDto> myPostsDtos = postService.findAllmyPostDtosByUser(member.getId());
        model.addAttribute("myPostsDtos", myPostsDtos);
        model.addAttribute("userNickname", member.getNickname());
        return "myPages/myPosts";
    }

    // 내가 쓴 댓글 리스트
    @GetMapping("/myComments")
    public String myComments(@CurrentUser Member member,
                          Model model) {
        List<myCommentsDto> myCommentsDtos = commentService.findAllmyCommentDtoByUser(member.getId());
        model.addAttribute("myCommentsDtos", myCommentsDtos);
        model.addAttribute("userNickname", member.getNickname());
        return "myPages/myComments";
    }

    // 내 프로필 수정
    @GetMapping("/editProfile")
    public String updateProfileForm(@CurrentUser Member member, Model model) {
        model.addAttribute("memberDto", new MemberDto(member));
        return "myPages/updateProfileForm";
    }

//    @PostMapping("/myPages/updateProfileForm")
//    public String updateProfile(@AuthenticationPrincipal PrincipalDetails details,
//                                @Valid MemberDto memberDto) {
//        memberService.updateMember(details.getId(), memberDto);
//        return "boardHome";
//    }
}
