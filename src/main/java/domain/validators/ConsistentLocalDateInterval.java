package domain.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConsistentLocalDateIntervalValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentLocalDateInterval {
    String message() default "La fecha de inicio no puede superar a la fecha de fin";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
