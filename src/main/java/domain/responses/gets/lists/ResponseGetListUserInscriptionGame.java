package domain.responses.gets.lists;

import java.util.List;

public record ResponseGetListUserInscriptionGame(long id, String word, boolean isCompleted, List<ResponseGetListGameGuess> guesses) { }
