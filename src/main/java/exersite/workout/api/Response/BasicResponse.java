package exersite.workout.api.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter @Setter
public class BasicResponse<T> extends CommonResponse {

    private String code; // 응답 코드
    private int count; // 데이터의 개수
    private T data;

    public BasicResponse(T data) {
        this.data = data;
        this.code = HttpStatus.OK.toString();
        if(data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
    }
}
