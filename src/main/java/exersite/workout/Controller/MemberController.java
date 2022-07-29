package exersite.workout.Controller;

import exersite.workout.Domain.Address;
import exersite.workout.Domain.Member;
import exersite.workout.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
        Member member = Member.createMember(form.getLoginId(),
                new Address(form.getCity(), form.getStreet(), form.getZipcode()),
                form.getName(), form.getNickname(), form.getPassword());

        memberService.join(member);
        return "redirect:/";
    }
}
