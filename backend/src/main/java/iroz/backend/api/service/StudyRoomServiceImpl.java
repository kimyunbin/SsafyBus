package iroz.backend.api.service;

import iroz.backend.api.request.StudyRoomRegisterPostReq;
import iroz.backend.db.Mapping.StudyRoomRegiMapping;
import iroz.backend.db.entity.StudyRoom;
import iroz.backend.db.entity.User;
import iroz.backend.db.entity.UserStudyRoom;
import iroz.backend.db.repository.StudyRoomRepository;
import iroz.backend.db.repository.UserRepository;
import iroz.backend.db.repository.UserStudyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudyRoomServiceImpl implements StudyRoomService {
    @Autowired
    StudyRoomRepository studyRoomRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserStudyRoomRepository userStudyRoomRepository;

    @Override
    public StudyRoom register(User user, StudyRoomRegisterPostReq studyRoomRegisterPostReq) {
        StudyRoom studyroom = StudyRoom.builder()
                .user(user)
                .reservation(studyRoomRegisterPostReq.getDate())
                .room(studyRoomRegisterPostReq.getRoom())
                .password(studyRoomRegisterPostReq.getPassword())
                .build();
        studyRoomRepository.save(studyroom);
        List list = studyRoomRegisterPostReq.getUserid();

        for (int i = 0; i < list.size(); i++) {
            Optional<User> byUserId = userRepository.findByUserId(list.get(i).toString());
            UserStudyRoom us = UserStudyRoom.builder().studyRoom(studyroom).user(byUserId.get()).build();
            userStudyRoomRepository.save(us);
        }
        return studyroom;

    }

    @Override
    public List<StudyRoom> getListGroupBy(int year, int month, int day, int hour, int room) {
        return studyRoomRepository.getListGroupBy(year,month,day,hour,room);
    }

    @Override
    public List<Object[]> getListGroupByDay(int year, int month, int day, int room) {
        return studyRoomRepository.getListGroupByDay(year,month,day,room);
    }

    @Override
    public List<StudyRoomRegiMapping> findByRoomAndReservationBetween(int room, LocalDateTime start, LocalDateTime end) {
        return studyRoomRepository.findByRoomAndReservationBetween(room,start,end);
    }
}
