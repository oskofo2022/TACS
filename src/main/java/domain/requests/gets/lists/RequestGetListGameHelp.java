package domain.requests.gets.lists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.validators.RegexSortBy;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RegexSortBy(allowedValues = { "word" })
public class RequestGetListGameHelp extends RequestGetListOnMemoryPagedList<String> {

    @Size(max = 20)
    private String goodLetters;
    @Size(max = 20)
    private String badLetters;
    @Size(max = 20)
    private String greenLetters;

    @JsonIgnore
    private List<RequestGetListGreenLetter> indexedGreenLetters;

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
    private List<RequestGetListGreenLetter> getIndexedGreenLetters() {
        if (this.indexedGreenLetters == null) {
            this.setIndexedGreenLetters();
        }

        return this.indexedGreenLetters;
    }

    @JsonIgnore
    @Override
    public boolean isValid(String word) {
      return !this.hasBadLetters(word) && this.hasGoodLetters(word) && this.hasGreenLetters(word);
    }

    @JsonIgnore
    @Override
    public Map<String, Comparator<String>> getComparatorMap() {
        return new HashMap<>() {
            {
                put("word", Comparator.comparing(Function.identity()));
            }
        };
    }

    private void setIndexedGreenLetters() {
        final var greenLetters = this.getGreenLetters();
        this.indexedGreenLetters = Collections.emptyList();

        if (!greenLetters.isBlank()) {
            this.indexedGreenLetters = IntStream.range(0, greenLetters.length())
                                                .filter(i -> greenLetters.charAt(i) != '-')
                                                .mapToObj(i -> new RequestGetListGreenLetter(i, this.greenLetters.charAt(i)))
                                                .collect(Collectors.toList());
        }
    }

    @Override
    protected String defaultSortBy() {
        return "word";
    }

    private boolean hasBadLetters(String word) {
        final var badLetters = this.getBadLetters();

        return !badLetters.isBlank() && Arrays.stream(badLetters.split(""))
                                                                .anyMatch(word::contains);
    }

    private boolean hasGoodLetters(String word) {
        final var goodLetters = this.getGoodLetters();

        return Arrays.stream(goodLetters.split(""))
                     .allMatch(word::contains);
    }

    private boolean hasGreenLetters(String word) {
        final var requestsGetListGreenLetter = this.getIndexedGreenLetters();

        return requestsGetListGreenLetter.stream()
                                         .allMatch(rglgl -> rglgl.isValid(word));
    }
}
