package iroz.backend.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

//    @OneToMany(mappedBy = "help", fetch = FetchType.EAGER)
//    @JsonIgnoreProperties
//    private List<Comment> comments;

}
