package com.muwire.seedboxd.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface MuWireNickname {
    public String message() default "Invalid MuWire nickname";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
