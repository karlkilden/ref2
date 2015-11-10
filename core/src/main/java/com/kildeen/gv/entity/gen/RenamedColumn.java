package com.kildeen.gv.entity.gen;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import javax.persistence.CascadeType;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.persistence.FetchType.EAGER;

@Target({METHOD, FIELD}) 
@Retention(RUNTIME)

public @interface RenamedColumn {   
	
	public String originalName();
}
