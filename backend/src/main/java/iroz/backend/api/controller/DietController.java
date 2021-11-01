package iroz.backend.api.controller;

import iroz.backend.api.service.DietService;
import iroz.backend.api.service.DietTodayService;
import iroz.backend.common.util.LocalDateParser;
import iroz.backend.db.entity.Diet;
import iroz.backend.db.entity.DietToday;
import iroz.backend.db.repository.DietTodayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api-boot/food")
public class DietController {
    @Autowired
    DietTodayService dietTodayService;
    @Autowired
    DietService dietService;
    @Autowired
    DietTodayRepository dietTodayRepository;
    @GetMapping()
    public ResponseEntity Today() {
        LocalDate localDate = LocalDate.now();
        LocalDateParser localDateParser = new LocalDateParser(localDate.toString());
//        System.out.println(localDateParser.startDate());
//        System.out.println(localDateParser.endDate());
        List<DietToday> dietTodayByDate = dietTodayService.getDietTodayByDate(localDateParser.startDate().toLocalDate(), localDateParser.endDate().toLocalDate());
        HashMap r = new HashMap();
        if (dietTodayByDate.isEmpty()) {
            for (int i = 0; i < 6; i++) {
                Random rand = new Random();
                int id = rand.nextInt(256);
                Long l = new Long(id);
                Optional<Diet> diet = dietService.findByid(l);
                DietToday dietToday = DietToday.builder().diet(diet.get()).createdAt(localDate).build();
                dietTodayRepository.save(dietToday);

            }
            List<DietToday> result = dietTodayService.getDietTodayByDate(localDateParser.startDate().toLocalDate(), localDateParser.endDate().toLocalDate());

            r.put("food",result);
            return ResponseEntity.ok().body(r);
        }
        r.put("food",dietTodayByDate);
        return ResponseEntity.ok().body(r);


    }
}
