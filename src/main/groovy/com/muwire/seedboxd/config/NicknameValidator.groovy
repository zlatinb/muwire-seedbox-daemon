package com.muwire.seedboxd.config

import com.muwire.core.util.DataUtil

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NicknameValidator implements ConstraintValidator<MuWireNickname, String>{

    @Override
    boolean isValid(String value, ConstraintValidatorContext context) {
        DataUtil.isValidName(value)
    }
}
