package iroz.backend.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Help extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;
    private String content;
    private String code;
    private String link;

    @OneToMany(mappedBy = "help", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties
    private List<Comment> comments = new ArrayList<Comment>();

}
