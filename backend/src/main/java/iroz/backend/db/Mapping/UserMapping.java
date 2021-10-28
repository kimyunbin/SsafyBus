package iroz.backend.db.Mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import iroz.backend.db.entity.Roletype;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public interface UserMapping {

    String getUserId();
    String getNickname();
    int getGeneration();
    int getClassNum();
    Roletype getPosition();

}
