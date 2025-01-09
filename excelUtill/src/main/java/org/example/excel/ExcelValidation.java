package org.example.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelValidation {

    boolean isRegex() default true;

    String pattern() default  "";

    String message();

    String columName();

    boolean multi() default false;

    boolean isTrim() default true;

    String multiKey() default ",";

}
