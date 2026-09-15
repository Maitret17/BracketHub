package fr.efrei.brackethub.web;

import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<Player> listOfPlayers() {
        return playerService.listOfPlayers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPlayer(@RequestBody Player player) {
        playerService.addPlayer(player);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

}
