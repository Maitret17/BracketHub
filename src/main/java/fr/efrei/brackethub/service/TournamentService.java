package fr.efrei.brackethub.service;

import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.data.Tournament;
import fr.efrei.brackethub.data.TournamentStatus;
import fr.efrei.brackethub.repository.PlayerRepository;
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
    @Autowired
    private PlayerRepository playerRepository;

    public TournamentService() {
    }

    public List<Tournament> listOfTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "This tournament wasn't found"
                ));
    }

    public void addTournament(Tournament tournament) {
        validateDates(tournament);
        tournamentRepository.save(tournament);
    }

    public void updateTournament(Long id, Tournament tournament) {

        validateDates(tournament);

        Tournament existingTournament = getTournament(id);
        if (tournament.getMaxPlayers() < existingTournament.getPlayers().size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum number of players cannot be lower than the number of registered players"
            );
        }

        existingTournament.setName(tournament.getName());
        existingTournament.setStartDate(tournament.getStartDate());
        existingTournament.setEndDate(tournament.getEndDate());
        existingTournament.setMaxPlayers(tournament.getMaxPlayers());
        existingTournament.setStatus(tournament.getStatus());
        existingTournament.setDiscipline(tournament.getDiscipline());

        tournamentRepository.save(existingTournament);
    }

    public void deleteTournament(Long id) {
        Tournament tournament = getTournament(id);
        tournamentRepository.delete(tournament);
    }

    public void addPlayer(Long tournamentId, Long playerId) {

        Tournament tournament = getTournament(tournamentId);

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "This player wasn't found"
                ));

        if (tournament.getStatus() != TournamentStatus.UPCOMING) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Players can only be added to an upcoming tournament"
            );
        }

        boolean alreadyRegistered = tournament.getPlayers()
                .stream()
                .anyMatch(p -> p.getId().equals(playerId));

        if (alreadyRegistered) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This player is already registered in this tournament"
            );
        }

        if (tournament.getPlayers().size() >= tournament.getMaxPlayers()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This tournament is full"
            );
        }

        tournament.getPlayers().add(player);
        tournamentRepository.save(tournament);
    }

    public void removePlayer(Long tournamentId, Long playerId) {

        Tournament tournament = getTournament(tournamentId);

        if (tournament.getStatus() != TournamentStatus.UPCOMING) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Players can only be removed from an upcoming tournament"
            );
        }

        boolean removed = tournament.getPlayers()
                .removeIf(player -> player.getId().equals(playerId));

        if (!removed) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "This player isn't registered in this tournament"
            );
        }

        tournamentRepository.save(tournament);
    }

    private void validateDates(Tournament tournament) {

        if (tournament.getStartDate() != null
                && tournament.getEndDate() != null
                && tournament.getEndDate().isBefore(tournament.getStartDate())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End date cannot be before start date"
            );
        }
    }
}