package fr.efrei.brackethub.repository;

import fr.efrei.brackethub.data.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}