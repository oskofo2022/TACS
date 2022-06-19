package domain.responses.gets.lists;

import domain.persistence.entities.enums.Language;

import java.time.LocalDate;

public record ResponseGetListUserMatch(LocalDate date, Language language) {
}
