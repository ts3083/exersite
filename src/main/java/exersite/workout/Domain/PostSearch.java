package exersite.workout.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostSearch {

    private String titleOrContent; // 제목 또는 내용으로 검색
    private String nickname; // 작성자 정보로 검색
}
