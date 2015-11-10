package com.kildeen.gv.poll;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.kildeen.gv.DomainEntity;

@Retention(RetentionPolicy.RUNTIME)
public @interface Requires {

	Class<? extends DomainEntity>[] prerequisites() default {};
}
