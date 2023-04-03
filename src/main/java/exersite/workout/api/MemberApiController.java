package exersite.workout.api;

import exersite.workout.Service.MemberService;
import exersite.workout.api.Response.BasicResponse;
import exersite.workout.api.Response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<? extends CommonResponse> profileData() {
        return ResponseEntity.ok().body(new BasicResponse<>(memberService.findMemberDtos()));
    }
}
