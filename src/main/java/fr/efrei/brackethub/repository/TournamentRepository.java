package fr.efrei.brackethub.repository;

import fr.efrei.brackethub.data.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
