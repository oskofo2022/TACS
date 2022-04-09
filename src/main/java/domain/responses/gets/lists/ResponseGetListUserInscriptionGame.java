package domain.responses.gets.lists;

import java.util.List;

public record ResponseGetListUserInscriptionGame(String word, boolean isCompleted, List<ResponseGetListGameGuess> guesses) { }
