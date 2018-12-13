package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, String> {
}
