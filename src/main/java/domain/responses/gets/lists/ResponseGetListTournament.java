package domain.responses.gets.lists;

import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;

import java.time.LocalDate;

public record ResponseGetListTournament(long id,
                                        String Name,
                                        Language language,
                                        Visibility visibility,
                                        TournamentState tournamentState,
                                        LocalDate startDate,
                                        LocalDate endDate) { }
