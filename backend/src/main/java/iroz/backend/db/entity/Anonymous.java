package iroz.backend.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Anonymous extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String content;
    private String answer;
}
