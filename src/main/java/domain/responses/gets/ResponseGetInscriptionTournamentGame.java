package domain.responses.gets;

import domain.responses.gets.lists.ResponseGetListGameGuess;

import java.util.List;

public record ResponseGetInscriptionTournamentGame (String word, List<ResponseGetListGameGuess> guesses) { }
