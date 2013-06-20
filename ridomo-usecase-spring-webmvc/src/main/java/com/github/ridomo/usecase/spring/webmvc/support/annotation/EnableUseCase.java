package com.github.ridomo.usecase.spring.webmvc.support.annotation;

import com.github.ridomo.usecase.spring.webmvc.support.configuration.UseCaseConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(UseCaseConfiguration.class)
public @interface EnableUseCase {
}
