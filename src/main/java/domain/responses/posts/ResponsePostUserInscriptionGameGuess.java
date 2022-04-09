package domain.responses.posts;

import domain.responses.common.gets.ResponseCommonGetGuessCharMatching;

import java.util.List;

public class ResponsePostUserInscriptionGameGuess extends ResponsePostEntityCreation {
    private final List<ResponseCommonGetGuessCharMatching> charsMatching;

    public ResponsePostUserInscriptionGameGuess(long id, List<ResponseCommonGetGuessCharMatching> charsMatching) {
        super(id);
        this.charsMatching = charsMatching;
    }

    public List<ResponseCommonGetGuessCharMatching> getCharsMatching() {
        return this.charsMatching;
    }
}
