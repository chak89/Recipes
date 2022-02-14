package recipes.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

//Custom annotation interface for checking email
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
    String message() default "Invalid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}