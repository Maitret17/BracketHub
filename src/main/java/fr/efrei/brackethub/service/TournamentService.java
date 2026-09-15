package fr.efrei.brackethub.service;

import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.data.Tournament;
import fr.efrei.brackethub.data.TournamentStatus;
import fr.efrei.brackethub.repository.PlayerRepository;
import fr.efrei.brackethub.repository.TournamentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public TournamentService() {
    }

    public List<Tournament> listOfTournaments() {
        return tournamentRepository.findAll();
    }

    public void addTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public void addPlayer(Long tournamentId, Long playerid){

        Tournament tournament = getTournament(tournamentId);
        Player player = playerRepository.findById(playerid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "This player wasn't found"
        ));
        tournament.getPlayers().add(player);

        tournamentRepository.save(tournament);
    }

    public Tournament getTournament(Long id) {

        return tournamentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "This tournament wasn't found"
                ));
    }
}