package domain.responses.gets.lists;

public class ResponseGetListTournamentPositionResult {
    private String name;
    private int guessesCount;
    private long cardinal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGuessesCount() {
        return guessesCount;
    }

    public void setGuessesCount(int guessesCount) {
        this.guessesCount = guessesCount;
    }

    public long getCardinal() {
        return cardinal;
    }

    public void setCardinal(long cardinal) {
        this.cardinal = cardinal;
    }
}
