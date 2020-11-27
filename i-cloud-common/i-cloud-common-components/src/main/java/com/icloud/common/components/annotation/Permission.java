package com.icloud.common.components.annotation;

import java.lang.annotation.*;

/**
 * 权限验证
 *
 * @author 42806
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface Permission {

    String description() default "";

    Operation operation() default Operation.SELECT;

    enum Operation {
        SELECT, DELETE, EDIT, ADD;
    }

}
