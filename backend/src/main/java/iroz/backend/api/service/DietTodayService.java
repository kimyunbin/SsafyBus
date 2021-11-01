package iroz.backend.api.service;

import iroz.backend.db.entity.DietToday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DietTodayService {
    List<DietToday> getDietTodayByDate(LocalDate start, LocalDate end);

}
