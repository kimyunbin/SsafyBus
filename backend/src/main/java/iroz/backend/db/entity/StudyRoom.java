package iroz.backend.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoom extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private int room;
    private LocalDateTime reservation;
    private  String password;

}
