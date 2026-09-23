package fr.efrei.brackethub.web;

import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

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
    public void addPlayer(@Valid @RequestBody Player player) {
        playerService.addPlayer(player);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePlayer(
            @PathVariable Long id,
            @Valid @RequestBody Player player) {
        playerService.updatePlayer(id, player);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(
            ResponseStatusException exception) {

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(exception.getReason());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            MethodArgumentNotValidException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid data");
    }
}
