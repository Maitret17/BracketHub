package fr.efrei.brackethub.data;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Positive
    private int maxPlayers;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany
    private List<Player> players = new ArrayList<>();
    @NotNull
    @Enumerated(EnumType.STRING)
    private TournamentStatus status;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Discipline discipline;


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

    public Discipline getDiscipline() {
        return discipline;
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

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }

    public void setDiscipline(Discipline discipline){
        this.discipline = discipline;
    }
}