package iroz.backend.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AnswerPostReq {
    @NotEmpty
    String answer;
}
