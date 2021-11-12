package home.work.homework.api.validation;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for {@link ValidEnum} annotation.
 *
 */
public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        enumClass = constraintAnnotation.targetedEnum();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }

        value = value.trim().toUpperCase();

        try {
            Enum.valueOf(enumClass, value);
            return true;
        } catch (IllegalArgumentException iaeEx) {
            return false;
        }
    }
}
