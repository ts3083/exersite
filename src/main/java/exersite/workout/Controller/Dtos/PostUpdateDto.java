package exersite.workout.Controller.Dtos;

import lombok.Data;

@Data
public class PostUpdateDto {

    private Long id;
    private String title;
    private String content;

    public PostUpdateDto(Long id) {
        this.id = id;
    }
}
