package domain.responses.gets.lists;

import domain.persistence.entities.enums.TournamentState;

import java.time.LocalDate;
import java.util.List;

public record ResponseGetListTournamentPosition(String name, TournamentState state, LocalDate startDate, LocalDate endDate, List<ResponseGetListTournamentPositionResult> positions) { }
