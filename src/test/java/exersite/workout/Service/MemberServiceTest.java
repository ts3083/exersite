package exersite.workout.Service;

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
        Member member = new Member();
        member.setName("A");
        member.setLoginId("abc@naver.com");

        //when
        Long joinMemberId = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberService.findOne(joinMemberId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setLoginId("abc@gmail.com");

        Member member2 = new Member();
        member2.setLoginId("abc@gmail.com");

        //when
        memberService.join(member1);
        memberService.join(member2); // 여기서 예외처리가 일어나야 함

        //then
        fail("예외처리가 발생해야 합니다"); // 코드가 여기까지 오면 에러
    }
}