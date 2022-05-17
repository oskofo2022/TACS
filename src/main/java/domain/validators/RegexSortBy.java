package domain.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RegexSortByValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexSortBy {
    String message() default "El campo ingresado no es válido para ordenar los resultados";
    String[] allowedValues();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
