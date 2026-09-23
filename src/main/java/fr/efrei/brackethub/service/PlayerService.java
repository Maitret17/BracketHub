package fr.efrei.brackethub.service;
import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.efrei.brackethub.data.Tournament;
import fr.efrei.brackethub.repository.TournamentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    public PlayerService() {

    }
    public List<Player> listOfPlayers() {
        return playerRepository.findAll();
    }
    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayer(Long id, Player player) {
        Player existingPlayer = getPlayer(id);

        existingPlayer.setFirst_name(player.getFirst_name());
        existingPlayer.setLast_name(player.getLast_name());
        existingPlayer.setAge(player.getAge());

        playerRepository.save(existingPlayer);
    }

    @Transactional
    public void deletePlayer(Long id) {
        Player player = getPlayer(id);
        for (Tournament tournament : tournamentRepository.findAll()) {
            boolean removed = tournament.getPlayers()
                    .removeIf(p -> p.getId().equals(id));
            if (removed) {
                tournamentRepository.save(tournament);
            }
        }

        playerRepository.delete(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no player found"));
    }
}

