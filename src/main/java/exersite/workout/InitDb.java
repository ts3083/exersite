package exersite.workout;

import exersite.workout.Controller.Forms.MemberForm;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final initService initService;

    @PostConstruct
    public void init() {
        initService.dbInit(); // 초기 게시판 카테고리 설정
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class initService {

        private final MemberService memberService;

        public void dbInit() {
            MemberForm memberForm1 = MemberForm.createMember("1@gmail.com", new Address("seoul", "1", "1"),
                    "kim", "london", "11111111");
            MemberForm memberForm2 = MemberForm.createMember("2@gmail.com", new Address("seoul", "2", "2"),
                    "Lee", "tokyo", "22222222");
            memberService.join(memberForm1);
            memberService.join(memberForm2);
        }
    }
}
