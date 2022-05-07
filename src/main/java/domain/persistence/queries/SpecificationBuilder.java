package domain.persistence.queries;

import constants.RSQLConstants;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Consumer;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

public class SpecificationBuilder {
    private String filter;

    public SpecificationBuilder() {
    }

    public SpecificationBuilder andLike(String propertyPath, Object value) {
        return this.getLike(propertyPath, value, this::andFilter);
    }

    public SpecificationBuilder andEqual(String propertyPath, Object value) {
        return this.getEqual(propertyPath, value, this::andFilter);
    }

    public SpecificationBuilder andEqual(String propertyPath, String value) {
        return this.getEqual(propertyPath, value, this::andFilter);
    }

    public SpecificationBuilder andGreaterThanEqual(String propertyPath, Object value) {
        return this.getGreaterThanEqual(propertyPath, value, this::andFilter);
    }

    public SpecificationBuilder andGreaterThan(String propertyPath, Object value) {
        return this.getGreaterThan(propertyPath, value, this::andFilter);
    }

    public SpecificationBuilder andLowerThanEqual(String propertyPath, Object value) {
        return this.getLowerThanEqual(propertyPath, value, this::andFilter);
    }

    public SpecificationBuilder andLowerThan(String propertyPath, Object value) {
        return this.getLowerThan(propertyPath, value, this::andFilter);
    }

    public SpecificationBuilder orLike(String propertyPath, Object value) {
        return this.getLike(propertyPath, value, this::orFilter);
    }

    public SpecificationBuilder orEqual(String propertyPath, Object value) {
        return this.getEqual(propertyPath, value, this::orFilter);
    }

    public SpecificationBuilder orEqual(String propertyPath, String value) {
        return this.getEqual(propertyPath, value, this::orFilter);
    }

    public SpecificationBuilder orGreaterThanEqual(String propertyPath, Object value) {
        return this.getGreaterThanEqual(propertyPath, value, this::orFilter);
    }

    public SpecificationBuilder orGreaterThan(String propertyPath, Object value) {
        return this.getGreaterThan(propertyPath, value, this::orFilter);
    }

    public SpecificationBuilder orLowerThanEqual(String propertyPath, Object value) {
        return this.getLowerThanEqual(propertyPath, value, this::orFilter);
    }

    public SpecificationBuilder orLowerThan(String propertyPath, Object value) {
        return this.getLowerThan(propertyPath, value, this::orFilter);
    }

    public <T> Specification<T> build() {
        return toSpecification(this.filter);
    }


    private SpecificationBuilder getLike(String propertyPath, Object value, Consumer<String> doOperation) {
        doOperation.accept(RSQLConstants.Filters.getLike(propertyPath).formatted(value));
        return this;
    }

    private SpecificationBuilder getEqual(String propertyPath, Object value, Consumer<String> doOperation) {
        doOperation.accept(RSQLConstants.Filters.getEqual(propertyPath).formatted(value));
        return this;
    }

    private SpecificationBuilder getEqual(String propertyPath, String value, Consumer<String> doOperation) {
        doOperation.accept(RSQLConstants.Filters.getEqualString(propertyPath).formatted(value));
        return this;
    }

    private SpecificationBuilder getGreaterThanEqual(String propertyPath, Object value, Consumer<String> doOperation) {
        doOperation.accept(RSQLConstants.Filters.getGreaterThanEqual(propertyPath).formatted(value));
        return this;
    }

    private SpecificationBuilder getGreaterThan(String propertyPath, Object value, Consumer<String> doOperation) {
        doOperation.accept(RSQLConstants.Filters.getGreaterThan(propertyPath).formatted(value));
        return this;
    }

    private <T> SpecificationBuilder getLowerThanEqual(String propertyPath, T value, Consumer<String> doOperation) {
        doOperation.accept(RSQLConstants.Filters.getLowerThanEqual(propertyPath).formatted(value));
        return this;
    }

    private <T> SpecificationBuilder getLowerThan(String propertyPath, T value, Consumer<String> doOperation) {
        doOperation.accept(RSQLConstants.Filters.getLowerThan(propertyPath).formatted(value));
        return this;
    }

    private void andFilter(String filter) {
        this.setFilter(filter, RSQLConstants.AND);
    }

    private void orFilter(String filter) {
        this.setFilter(filter, RSQLConstants.OR);
    }

    private void setFilter(String filter, String operator) {
        if (this.filter == null) {
            this.filter = filter;
            return;
        }

        this.filter += operator + filter;
    }
}
