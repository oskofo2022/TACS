package domain.responses.gets.lists;

import domain.responses.common.gets.ResponseCommonGetGuessCharMatching;

import java.util.List;

public record ResponseGetListGameGuess(String word, List<ResponseCommonGetGuessCharMatching> charsMatching) { }
