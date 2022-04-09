package domain.responses.gets.lists;

import java.util.List;

public record ResponseGetListGameGuess(String word, List<ResponseGetListGuessCharMatching> charsMatching) { }
