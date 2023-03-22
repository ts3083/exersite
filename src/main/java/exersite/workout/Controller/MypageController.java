package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Controller.Dtos.myCommentsDto;
import exersite.workout.Controller.Dtos.myPostsDto;
import exersite.workout.Controller.Forms.PasswordChangeForm;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.CommentService;
import exersite.workout.Service.MemberService;
import exersite.workout.Service.PostService;
import exersite.workout.Validator.PasswordChangeFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    @InitBinder("passwordChangeForm")
    public void passwordChangeFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordChangeFormValidator());
    }

    @GetMapping("/myPage")
    public String myPage(@CurrentUser Member member, Model model) {
        MemberDto memberDto = new MemberDto(member);
        model.addAttribute("memberDto", memberDto);
        return "myPages/profile";
    }

    @GetMapping("/profile/{nickname}")
    public String profilePage(@CurrentUser Member member, @PathVariable String nickname, Model model) {
        MemberDto memberDto = memberService.profile(nickname);
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("LoginUser", memberDto.getId().equals(member.getId()));
        return "members/otherProfile";
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

    @PostMapping("/myPages/updateProfile")
    public String updateProfile(@CurrentUser Member member, MemberDto memberDto,
                                RedirectAttributes redirectAttributes) {
        memberService.updateMember(member, memberDto);
        redirectAttributes.addFlashAttribute("message", "프로필이 수정되었습니다.");
        return "redirect:/myPage";
    }

    @GetMapping("/passwordChange")
    public String passwordPopUpCheck(Model model) {
        model.addAttribute(new PasswordChangeForm());
        return "myPages/passwordChange";
    }

    @PostMapping("/passwordChange")
    public String passwordChange(@CurrentUser Member member, @Valid PasswordChangeForm passwordChangeForm, Errors errors,
                                 Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("message", "fail");
            return "myPages/passwordChange";
        }
        memberService.updatePassword(member, passwordChangeForm.getNewPassword());
        model.addAttribute("message", "success");
        return "redirect:/passwordChange";
    }
}
