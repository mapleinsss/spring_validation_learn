package org.maple.spring_validation_learn.self;

import org.maple.spring_validation_learn.self.IsEnumType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<IsEnumType, String> {

    /**
     * 校验参数是否是如下类型
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.equals("interface") || value.equals("class") || value.equals("enum");
    }

}