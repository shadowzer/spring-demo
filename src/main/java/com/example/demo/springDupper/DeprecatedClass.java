package com.example.demo.springDupper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cod_s on 11.02.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DeprecatedClass {
	Class newImpl();
}
