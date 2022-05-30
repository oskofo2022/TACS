package domain.requests;

import constants.SuppressWarningsConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(SuppressWarningsConstants.ALL)
public abstract class RequestAnnotationTest<TRequest> {
    protected TRequest request;
    private Validator validator;

    @BeforeEach
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.request = this.createValidRequest();
    }

    @Test
    protected void valid() {
        final var constraintViolations = this.validator.validate(this.request);
        assertEquals(0, constraintViolations.size());
    }

    protected void invalid(String propertyPath, String annotationName) {
        final var constraintViolations = this.validator.validate(this.request);
        final var constraintViolation = constraintViolations.stream().findFirst().get();
        final var constraintPropertyPath = constraintViolation.getPropertyPath().toString();
        final var constraintAnnotationName = constraintViolation.getConstraintDescriptor()
                                                                       .getAnnotation()
                                                                       .annotationType()
                                                                       .getSimpleName();

        assertEquals(1, constraintViolations.size());
        assertEquals(propertyPath, constraintPropertyPath);
        assertEquals(annotationName, constraintAnnotationName);
    }

    protected abstract TRequest createValidRequest();
}
