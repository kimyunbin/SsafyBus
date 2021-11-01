package iroz.backend.api.service;

import iroz.backend.db.entity.Diet;
import iroz.backend.db.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DietServiceImpl implements DietService {
    @Autowired
    DietRepository dietRepository;

    @Override
    public Optional<Diet> findByid(Long id) {
        return dietRepository.findById(id);
    }
}
