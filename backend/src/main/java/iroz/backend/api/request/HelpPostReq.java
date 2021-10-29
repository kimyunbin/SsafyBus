package iroz.backend.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelpPostReq {
    String title;
    String content;
    String code;
    String link;
}
