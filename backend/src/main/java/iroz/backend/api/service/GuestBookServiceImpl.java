package iroz.backend.api.service;

import iroz.backend.api.request.GuestBookSavePostReq;
import iroz.backend.db.Mapping.GuestBookMapping;
import iroz.backend.db.Mapping.UserInfoMapping;
import iroz.backend.db.entity.GuestBook;
import iroz.backend.db.entity.User;
import iroz.backend.db.repository.GuestBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestBookServiceImpl implements GuestBookService{
    @Autowired
    GuestBookRepository guestBookRepository;

    @Override
    public GuestBook saveGuestBook(User user, GuestBookSavePostReq content) {
        GuestBook guestBook = GuestBook.builder().user(user).content(content.getContent()).build();
        return guestBookRepository.save(guestBook);
    }

    @Override
    public List<GuestBookMapping> getGuestBookByDate(LocalDateTime start, LocalDateTime end) {
        return guestBookRepository.findAllByCreatedAtBetween(start,end);
    }
}
