package iroz.backend.api.service;

import iroz.backend.api.request.StudyRoomRegisterPostReq;
import iroz.backend.db.Mapping.StudyRoomRegiMapping;
import iroz.backend.db.entity.StudyRoom;
import iroz.backend.db.entity.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public interface StudyRoomService {
    StudyRoom register(User user,StudyRoomRegisterPostReq studyRoomRegisterPostReq);
    List<StudyRoom> getListGroupBy(int year, int month, int day, int hour, int room);
    List<Object[]> getListGroupByDay(int year, int month, int day, int room);
    List<StudyRoomRegiMapping> findByRoomAndReservationBetweenOrderBOrderByReservation(int room, LocalDateTime start, LocalDateTime end);
}
