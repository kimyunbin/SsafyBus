package iroz.backend.api.service;

import iroz.backend.api.request.GuestBookSavePostReq;
import iroz.backend.db.Mapping.GuestBookMapping;
import iroz.backend.db.Mapping.UserInfoMapping;
import iroz.backend.db.entity.GuestBook;
import iroz.backend.db.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface GuestBookService {
    GuestBook saveGuestBook(User user, GuestBookSavePostReq content);
    List<GuestBookMapping> getGuestBookByDate(LocalDateTime start, LocalDateTime end);

}
