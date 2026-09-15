package fr.efrei.brackethub.web;

import fr.efrei.brackethub.data.Tournament;
import fr.efrei.brackethub.service.TournamentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    public TournamentController() {
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tournament> listOfTournaments() {
        return tournamentService.listOfTournaments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tournament getTournament(@PathVariable Long id) {
        return tournamentService.getTournament(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTournament(@RequestBody Tournament tournament) {
        tournamentService.addTournament(tournament);
    }

    @PutMapping("/{tournamentId}/players/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public void addPlayer(
            @PathVariable Long tournamentId,
            @PathVariable Long playerId){
        tournamentService.addPlayer(tournamentId, playerId);
    }
}