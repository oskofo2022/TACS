package domain.responses.gets.lists;

import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;

import java.time.LocalDate;
import java.util.UUID;

public record ResponseGetListTournament(UUID id,
                                        String name,
                                        Language language,
                                        Visibility visibility,
                                        TournamentState state,
                                        LocalDate startDate,
                                        LocalDate endDate) { }
