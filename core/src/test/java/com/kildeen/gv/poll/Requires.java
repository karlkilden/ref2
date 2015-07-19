package com.kildeen.gv.poll;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.kildeen.ref.BaseEntity;

@Retention(RetentionPolicy.RUNTIME)
public @interface Requires {

	Class<? extends BaseEntity>[] prerequisites() default {};
}
