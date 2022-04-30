package domain.requests.gets.lists;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestGetListGameHelp {
    private String goodLetters;
    private String badLetters;
    private String greenLetters;

    public String getGoodLetters() {
        return Optional.ofNullable(this.goodLetters).orElse("");
    }

    public void setGoodLetters(String goodLetters) {
        this.goodLetters = goodLetters;
    }

    public String getBadLetters() {
        return Optional.ofNullable(this.badLetters).orElse("");
    }

    public void setBadLetters(String badLetters) {
        this.badLetters = badLetters;
    }

    public String getGreenLetters() {
        return Optional.ofNullable(this.greenLetters).orElse("");
    }

    public void setGreenLetters(String greenLetters) {
        this.greenLetters = greenLetters;
    }


    @JsonIgnore
    public List<RequestGetListGreenLetter> getIndexedGreenLetters() {
        final var greenLetters = this.getGreenLetters();

        if (greenLetters.isBlank()) {
            return new ArrayList<>();
        }

        return IntStream.range(0, greenLetters.length())
                        .filter(i -> greenLetters.charAt(i) != '-')
                        .mapToObj(i -> new RequestGetListGreenLetter(i, this.greenLetters.charAt(i)))
                        .collect(Collectors.toList());
    }

    @JsonIgnore
    public boolean isValid(String word) {
      final var badLetters = this.getBadLetters();
      final var goodLetters = this.getGoodLetters();

      final var hasBadLetters = !badLetters.isBlank() && Arrays.stream(badLetters.split(""))
                                                                                 .anyMatch(word::contains);

      final var hasGoodLetters = Arrays.stream(goodLetters.split(""))
                                                                  .allMatch(word::contains);

      return !hasBadLetters && hasGoodLetters;
    }
}
