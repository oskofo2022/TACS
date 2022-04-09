package domain.responses.posts;

import domain.responses.gets.lists.ResponseGetListGuessCharMatching;

import java.util.List;

public class ResponsePostUserInscriptionGameGuess extends ResponsePostEntityCreation {
    private final List<ResponseGetListGuessCharMatching> charsMatching;

    public ResponsePostUserInscriptionGameGuess(long id, List<ResponseGetListGuessCharMatching> charsMatching) {
        super(id);
        this.charsMatching = charsMatching;
    }

    public List<ResponseGetListGuessCharMatching> getCharsMatching() {
        return this.charsMatching;
    }
}
