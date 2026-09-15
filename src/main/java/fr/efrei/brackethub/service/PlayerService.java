package fr.efrei.brackethub.service;
import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    public PlayerService() {

    }
    public List<Player> listOfPlayers() {
        return playerRepository.findAll();
    }
    public void addPlayer(Player player) {
        playerRepository.save(player);
    }
    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no player found"));
    }
}

