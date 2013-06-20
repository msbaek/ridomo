package com.github.ridomo.usecase.spring.webmvc.support.processor;

import com.github.ridomo.usecase.shared.*;
import com.github.ridomo.usecase.spring.webmvc.support.annotation.Interaction;
import com.github.ridomo.usecase.spring.webmvc.support.presenter.*;
import com.github.ridomo.usecase.support.PresentableInteractor;
import com.google.common.collect.Lists;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InteractorMethodProcessorTest {
	private InteractorMethodProcessor processor;
	private List<HttpMessageConverter<?>> messageConverters;
	@Mock
	private HttpMessageConverter<?> httpMessageConverter;
	@Mock
	private BeanFactory beanFactory;
	@Mock
	private MethodParameter parameter;
	@Mock
	private ModelAndViewContainer mavContainer;
	@Mock
	private NativeWebRequest webRequest;
	@Mock
	private WebDataBinderFactory binderFactory;
	@Mock
	private Interactor<RequestModel> interactor;
	@Mock
	private PresentableInteractor<?, ResponseModel> presentableInteractor;
	@Mock
	private Presenter<ResponseModel> presenter;
	@Mock
	private ModelPresenter<ResponseModel> modelPresenter;
	@Mock
	private TargetablePresenter<Integer> targetablePresenter;
	@Mock
	private Interaction interaction;
	@Mock
	private ModelMap modelMap;

	@Before
	public void setUp() throws Exception {
		messageConverters = Lists.newArrayList();
		messageConverters.add(httpMessageConverter);
		processor = new InteractorMethodProcessor(messageConverters);
		processor.setBeanFactory(beanFactory);
	}

	@Test
	public void testSupportsParameter() throws Exception {
		when(parameter.getParameterType()).then(new Answer<Class<?>>() {
			@Override
			public Class<?> answer(InvocationOnMock invocation) throws Throwable {
				return Interactor.class;
			}
		});
		assertThat(processor.supportsParameter(parameter), is(true));
		verify(parameter, times(1)).getParameterType();
	}

	@Test
	public void testResolveArgument() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.interactor()).thenReturn(name);
		when(beanFactory.getBean(name, Interactor.class)).thenReturn(interactor);
		assertThat(processor.resolveArgument(parameter, mavContainer, webRequest, binderFactory), is((Object) interactor));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testResolveArgumentWhenPresentableInteractorButHasNoPresenter() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.interactor()).thenReturn(name);
		when(beanFactory.getBean(name, Interactor.class)).thenReturn(presentableInteractor);
		assertThat(processor.resolveArgument(parameter, mavContainer, webRequest, binderFactory), is((Object) presentableInteractor));
		verify(presentableInteractor, never()).setPresenter(Mockito.any(Presenter.class));
	}

	@Test
	public void testResolveArgumentWhenPresentableInteractorAndPresenter() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.interactor()).thenReturn(name);
		when(interaction.presenter()).thenReturn(name);
		when(beanFactory.getBean(name, Interactor.class)).thenReturn(presentableInteractor);
		when(beanFactory.getBean(name, Presenter.class)).thenReturn(presenter);
		assertThat(processor.resolveArgument(parameter, mavContainer, webRequest, binderFactory), is((Object) presentableInteractor));
		verify(presentableInteractor, times(1)).setPresenter(presenter);
	}

	@Test
	public void testResolveArgumentWhenPresentableInteractorAndModelPresenter() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.interactor()).thenReturn(name);
		when(interaction.presenter()).thenReturn(name);
		when(beanFactory.getBean(name, Interactor.class)).thenReturn(presentableInteractor);
		when(beanFactory.getBean(name, Presenter.class)).thenReturn(modelPresenter);
		when(mavContainer.getModel()).thenReturn(modelMap);
		assertThat(processor.resolveArgument(parameter, mavContainer, webRequest, binderFactory), is((Object) presentableInteractor));
		verify(presentableInteractor, times(1)).setPresenter(modelPresenter);
		verify(modelPresenter, times(1)).setModel(modelMap);
	}

	@Test
	public void testSupportsReturnType() throws Exception {
		when(parameter.getParameterType()).then(new Answer<Class<?>>() {
			@Override
			public Class<?> answer(InvocationOnMock invocation) throws Throwable {
				return PresentableInteractor.class;
			}
		});
		assertThat(processor.supportsReturnType(parameter), is(true));
		verify(parameter, times(1)).getParameterType();
	}

	@Test
	public void testHandleReturnValue() throws Exception {
		final Integer target = 1;
		processor = new InteractorMethodProcessor(messageConverters) {
			@Override
			protected <T> void writeWithMessageConverters(T returnValue, MethodParameter returnType, NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException {
				assertThat((Integer) returnValue, is(target));
				assertThat(returnType, is(parameter));
			}
		};
		when(presentableInteractor.getPresenter()).thenReturn(targetablePresenter);
		when(targetablePresenter.getTarget()).thenReturn(target);
		processor.handleReturnValue(presentableInteractor, parameter, mavContainer, webRequest);
		verify(presentableInteractor, times(2)).getPresenter();
		verify(targetablePresenter, times(1)).getTarget();
		verify(mavContainer, times(1)).setRequestHandled(true);
	}

	@Test
	public void testHandleReturnValueWhenReturnValueIsNull() throws Exception {
		processor.handleReturnValue(null, parameter, mavContainer, webRequest);
		verify(mavContainer, never()).setRequestHandled(Mockito.any(boolean.class));
	}

	@Test
	public void testHandleReturnValueWhenReturnValueIsNotTargetable() throws Exception {
		when(presentableInteractor.getPresenter()).thenReturn(presenter);
		processor.handleReturnValue(presentableInteractor, parameter, mavContainer, webRequest);
		verify(mavContainer, never()).setRequestHandled(Mockito.any(boolean.class));
	}

	private interface TargetablePresenter<T> extends Targetable<T>, Presenter<ResponseModel> {
	}
}
