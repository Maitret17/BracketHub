package fr.efrei.brackethub.data;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxPlayers;

    @ManyToMany
    private List<Player> players = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    // TODO Discipline enumerator

    public Tournament() {
    }

    public Tournament(String name, LocalDate startDate, LocalDate endDate, int maxPlayers) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxPlayers = maxPlayers;
    }

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TournamentStatus getStatus(){
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setPlayers(List<Player> players) { // Unsure to keep, we probably should just do a function to addPlayers
        this.players = players;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }
}