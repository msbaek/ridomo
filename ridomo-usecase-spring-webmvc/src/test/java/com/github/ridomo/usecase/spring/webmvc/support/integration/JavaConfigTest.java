package com.github.ridomo.usecase.spring.webmvc.support.integration;

import com.github.ridomo.usecase.spring.webmvc.support.integration.configuration.WebConfiguration;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = WebConfiguration.class)
public class JavaConfigTest extends AbstractConfig {
}
