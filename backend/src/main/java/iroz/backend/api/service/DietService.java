package iroz.backend.api.service;

import iroz.backend.db.entity.Diet;

import java.util.Optional;

public interface DietService {

    Optional<Diet> findByid(Long id);
}
