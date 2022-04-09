package domain.responses.common.gets;

import domain.responses.gets.lists.CharProximity;

public record ResponseCommonGetGuessCharMatching(char character, CharProximity proximity) { }
