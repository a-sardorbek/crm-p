package com.system.crm.validation.annotation;

import com.system.crm.validation.NumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberValidator.class)
public @interface NumberValidation {

    String message() default "Id is not valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
