package domain.responses.gets.lists;

import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ResponseGetListTournamentPosition(UUID id, String name, TournamentState state, LocalDate startDate, LocalDate endDate, Visibility visibility, Language language, List<ResponseGetListTournamentPositionResult> positions) { }
