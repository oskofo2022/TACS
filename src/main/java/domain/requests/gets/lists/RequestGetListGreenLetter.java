package domain.requests.gets.lists;

public record RequestGetListGreenLetter(int position, char letter) {
    public boolean isValid(String word) {
        return this.position < word.length() && word.charAt(this.position) == this.letter;
    }
}
