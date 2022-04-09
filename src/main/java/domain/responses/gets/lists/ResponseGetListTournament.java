package domain.responses.gets.lists;

import domain.repositories.entities.Language;
import domain.repositories.entities.TournamentState;
import domain.repositories.entities.Visibility;

import java.time.LocalDate;

public record ResponseGetListTournament(long id,
                                        String Name,
                                        Language language,
                                        Visibility visibility,
                                        TournamentState tournamentState,
                                        LocalDate startDate,
                                        LocalDate endDate) { }
