package domain.repositories.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Tournament {
    @Id
    private long id;

    @Size(max = 60)
    @NotBlank
    private String name;

    @NotNull
    private Visibility visibility;

    @NotNull
    private Language language;

    @NotNull
    private TournamentState tournamentState;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
