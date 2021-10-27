package iroz.backend.db.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBook extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String content;
}
