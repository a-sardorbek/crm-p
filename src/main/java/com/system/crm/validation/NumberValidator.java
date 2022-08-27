package com.system.crm.validation;

import org.apache.commons.lang3.math.NumberUtils;
import com.system.crm.validation.annotation.NumberValidation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<NumberValidation,Long> {

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        try{
            if(!id.equals("null") || !id.equals(null)){
                if (NumberUtils.isParsable(String.valueOf(id))) {
                    return true;
                }
            }
        }catch (NullPointerException n){
            return false;
        }
        return false;
    }
}
