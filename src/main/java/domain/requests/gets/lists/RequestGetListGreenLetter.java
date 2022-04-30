package domain.requests.gets.lists;

public record RequestGetListGreenLetter(int position, char letter) {
    public boolean isValid(String word) {
        return word.indexOf(this.letter) == this.position;
    }
}
