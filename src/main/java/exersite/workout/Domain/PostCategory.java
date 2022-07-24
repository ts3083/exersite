package exersite.workout.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Post_Category")
@Getter @Setter
public class PostCategory {

    @Id @GeneratedValue
    @Column(name = "post_category_id")
    private Long id;

    @OneToMany(mappedBy = "postCategory")
    private List<Post> posts;

    private String name;
}
