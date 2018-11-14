package ch.supsi.webapp.web;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, String> {
}
