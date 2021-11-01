package iroz.backend.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    private String menu;
    private String path;

}
