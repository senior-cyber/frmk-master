package com.senior.cyber.frmk.common.base;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface Bookmark {
    String value();
}
