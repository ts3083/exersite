package exersite.workout.Controller;

import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Controller.Forms.MemberForm;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 로그인 화면으로 이동
    @GetMapping("/loginForm")
    public String loginForm() {
        return "members/login";
    }

    @GetMapping("/members/new") // 회원가입 창으로 이동
    public String createMemberForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) { // 에러가 있으면 다시 회원가입 창으로 보냄 - 에러메시지 띄움
            return "members/createMemberForm";
        }
        memberService.processNewMember(form);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<MemberDto> memberDtos = memberService.findMemberDtos();
        model.addAttribute("members", memberDtos);
        return "/members/memberList";
    }
}
