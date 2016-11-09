package com.yuezy.monitor.config.anno;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImportProperties {
	String properties() default "";
	String prefix() default "";
}
