package com.github.ridomo.usecase.spring.webmvc.support.configuration;

import com.github.ridomo.usecase.spring.webmvc.support.processor.*;
import com.google.common.collect.Lists;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseConfigurationTest {
	private UseCaseConfiguration configuration;
	@Mock
	private RequestMappingHandlerAdapter handlerAdapter;
	@Mock
	private ContentNegotiationManager contentNegotiationManager;
	@Mock
	private List<HandlerMethodArgumentResolver> argumentResolvers;
	@Mock
	private List<HandlerMethodReturnValueHandler> returnValueHandlers;
	@Mock
	private HttpMessageConverter<?> messageConverter;

	@Before
	public void setUp() throws Exception {
		configuration = new UseCaseConfiguration();
	}

	@Test
	public void testInteractorMethodProcessor() throws Exception {
		List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
		messageConverters.add(messageConverter);
		when(handlerAdapter.getMessageConverters()).thenReturn(messageConverters);
		when(handlerAdapter.getCustomArgumentResolvers()).thenReturn(argumentResolvers);
		when(handlerAdapter.getCustomReturnValueHandlers()).thenReturn(returnValueHandlers);
		assertThat(configuration.interactorMethodProcessor(handlerAdapter, contentNegotiationManager), is(instanceOf(InteractorMethodProcessor.class)));
		verify(argumentResolvers, times(1)).add(Mockito.any(InteractorMethodProcessor.class));
		verify(returnValueHandlers, times(1)).add(Mockito.any(InteractorMethodProcessor.class));
		verify(handlerAdapter, times(1)).setArgumentResolvers(null);
		verify(handlerAdapter, times(1)).setReturnValueHandlers(null);
		verify(handlerAdapter, times(1)).setInitBinderArgumentResolvers(null);
		verify(handlerAdapter, times(1)).afterPropertiesSet();
	}

	@Test
	public void testBoundaryScopeFactoryPostProcessor() throws Exception {
		assertThat(configuration.boundaryScopeFactoryPostProcessor(), is(instanceOf(BoundaryScopeFactoryPostProcessor.class)));
	}
}
