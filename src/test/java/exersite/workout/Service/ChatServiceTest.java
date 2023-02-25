package exersite.workout.Service;

import exersite.workout.Domain.Chat.ChatRoom;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ChatServiceTest {

    @Autowired ChatService chatService;
    @Autowired MemberRepository memberRepository;

    @DisplayName("채팅룸이 이미 존재하는지 체크 테스트")
    @Test
    public void chatRoomExistTest() throws Exception {
        Member member = memberRepository.findByNickname("london");
        ChatRoom chatRoom = chatService.findChatRoomExist(member, 2L);

        assertNull("chatRoom이 null인지 검사", chatRoom);
    }

    @DisplayName("chatRoom 저장 테스트")
    @Test
    public void chatRoomSaveTest() throws Exception {
        Member member = memberRepository.findByNickname("london");
        ChatRoom saveChatRoom = chatService.createChatRoomProcess(member, 2L);

        assertNotNull("chatRoom이 null인지 검사", saveChatRoom);
        assertEquals("london tokyo", saveChatRoom.getRoomName());
    }
}