package domain.validators;

import constants.PatternConstants;
import domain.requests.gets.lists.RequestGetPagedList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RegexSortByValidator implements ConstraintValidator<RegexSortBy, RequestGetPagedList> {

    private List<String> allowedValues;

    public void initialize(RegexSortBy regexSortBy) {
        this.allowedValues = Arrays.stream(regexSortBy.allowedValues())
                                   .map(s -> s.replace(".", "\\."))
                                   .toList();
    }

    @Override
    public boolean isValid(RequestGetPagedList value, ConstraintValidatorContext context) {
        final var regex = this.allowedValues.stream()
                                                   .reduce((accumulator, seed) -> accumulator + PatternConstants.OR_OPERATOR + seed)
                                                   .orElseThrow(() -> new IllegalArgumentException("allowedValues debe poseer al menos 1 elemento"));
        final var sortBy = value.getSortBy();
        return Pattern.compile(PatternConstants.getEncasedRegex(regex))
                      .matcher(sortBy)
                      .matches();
    }
}
