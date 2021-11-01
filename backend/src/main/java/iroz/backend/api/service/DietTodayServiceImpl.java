package iroz.backend.api.service;

import iroz.backend.db.entity.DietToday;
import iroz.backend.db.repository.DietTodayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class DietTodayServiceImpl implements DietTodayService {
    @Autowired
    DietTodayRepository dietTodayRepository;

    @Override
    public List<DietToday> getDietTodayByDate(LocalDate start, LocalDate end) {
        return dietTodayRepository.findAllByCreatedAtBetween(start,end);
    }
}
