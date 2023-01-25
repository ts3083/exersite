package exersite.workout.Service;

import exersite.workout.Controller.Forms.MemberForm;
import exersite.workout.Domain.Member.Member;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        //given
        MemberForm memberForm = new MemberForm();
        memberForm.setName("A");
        memberForm.setLoginId("abc@naver.com");
        memberForm.setPassword("1234");

        //when
        Long joinMemberId = memberService.join(memberForm);

        //then
        Assertions.assertEquals("A", memberService.findOne(joinMemberId).getName());
        Assertions.assertEquals("abc@naver.com", memberService.findOne(joinMemberId).getLoginId());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원예외() throws Exception {
        //given
        MemberForm memberForm1 = new MemberForm();
        memberForm1.setLoginId("abc@gmail.com");
        memberForm1.setNickname("A");
        memberForm1.setPassword("1111");

        MemberForm memberForm2 = new MemberForm();
        memberForm2.setLoginId("abc@gmail.com");
        memberForm2.setNickname("B");
        memberForm2.setPassword("2222");

        //when
        memberService.processNewMember(memberForm1);
        memberService.processNewMember(memberForm2); // 여기서 예외처리가 일어나야 함

        //then
        fail("예외처리가 발생해야 합니다"); // 코드가 여기까지 오면 에러
    }
}