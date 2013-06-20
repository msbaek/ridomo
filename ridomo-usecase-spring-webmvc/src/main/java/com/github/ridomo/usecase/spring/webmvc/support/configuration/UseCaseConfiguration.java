package com.github.ridomo.usecase.spring.webmvc.support.configuration;

import com.github.ridomo.usecase.spring.webmvc.support.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class UseCaseConfiguration {
	// Don't like it, but don't have another option
	@Bean
	@Autowired
	public InteractorMethodProcessor interactorMethodProcessor(RequestMappingHandlerAdapter handlerAdapter, ContentNegotiationManager contentNegotiationManager) {
		InteractorMethodProcessor result = new InteractorMethodProcessor(handlerAdapter.getMessageConverters(), contentNegotiationManager);
		register(handlerAdapter, result);
		reinitialze(handlerAdapter);
		return result;
	}

	private void register(RequestMappingHandlerAdapter handlerAdapter, InteractorMethodProcessor processor) {
		handlerAdapter.getCustomArgumentResolvers().add(processor);
		handlerAdapter.getCustomReturnValueHandlers().add(processor);
	}

	private void reinitialze(RequestMappingHandlerAdapter handlerAdapter) {
		handlerAdapter.setArgumentResolvers(null);
		handlerAdapter.setReturnValueHandlers(null);
		handlerAdapter.setInitBinderArgumentResolvers(null);
		handlerAdapter.afterPropertiesSet();
	}

	@Bean
	public BoundaryScopeFactoryPostProcessor boundaryScopeFactoryPostProcessor() {
		return new BoundaryScopeFactoryPostProcessor();
	}
}
