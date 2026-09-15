package fr.efrei.brackethub.service;

import fr.efrei.brackethub.data.Tournament;
import fr.efrei.brackethub.repository.TournamentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public TournamentService() {
    }

    public List<Tournament> listOfTournaments() {
        return tournamentRepository.findAll();
    }

    public void addTournament(Tournament tournament) {
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