package com.github.ridomo.usecase.spring.webmvc.support.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interaction {
	/**
	 * Interactor Spring Bean name
	 * If value is empty then will use parameter name
	 *
	 * @return
	 */
	String interactor() default "";

	/**
	 * Presenter Spring Bean name
	 * If value is empty then skipping
	 *
	 * @return
	 */
	String presenter() default "";
}
