package Blind.Sight.Commnunity.dto.validate.birthdate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidate.class)
public @interface ValidBirthDate {
    String message() default "{ValidBirthDay}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

