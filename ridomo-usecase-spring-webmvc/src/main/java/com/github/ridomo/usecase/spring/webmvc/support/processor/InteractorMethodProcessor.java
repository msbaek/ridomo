package com.github.ridomo.usecase.spring.webmvc.support.processor;

import com.github.ridomo.usecase.shared.*;
import com.github.ridomo.usecase.spring.webmvc.support.presenter.*;
import com.github.ridomo.usecase.support.PresentableInteractor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.util.ClassUtils.*;

public class InteractorMethodProcessor extends AbstractMessageConverterMethodProcessor implements BeanFactoryAware {
	private BeanFactory beanFactory;

	public InteractorMethodProcessor(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}

	public InteractorMethodProcessor(List<HttpMessageConverter<?>> messageConverters, ContentNegotiationManager manager) {
		super(messageConverters, manager);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return isAssignable(Interactor.class, parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		ResolveParameter resolveParameter = ResolveParameter.of(parameter, mavContainer);
		return interactor(resolveParameter);
	}

	@SuppressWarnings("unchecked")
	private Interactor<RequestModel> interactor(ResolveParameter resolveParameter) {
		Interactor<RequestModel> result = beanFactory.getBean(resolveParameter.getInteractor(), Interactor.class);
		if (isAssignableValue(PresentableInteractor.class, result) && resolveParameter.hasPresenter()) {
			((PresentableInteractor<RequestModel, ResponseModel>) result).setPresenter(presenter(resolveParameter));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Presenter<ResponseModel> presenter(ResolveParameter resolveParameter) {
		Presenter<ResponseModel> result = beanFactory.getBean(resolveParameter.getPresenter(), Presenter.class);
		if (isAssignableValue(ModelPresenter.class, result)) {
			((ModelPresenter<ResponseModel>) result).setModel(resolveParameter.getModel());
		}
		return result;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return isAssignable(PresentableInteractor.class, returnType.getParameterType());
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		if (returnValue == null) {
			return;
		}
		PresentableInteractor<?, ?> interactor = (PresentableInteractor<?, ?>) returnValue;
		if (isAssignableValue(Targetable.class, interactor.getPresenter())) {
			Targetable<?> targetable = (Targetable<?>) interactor.getPresenter();
			mavContainer.setRequestHandled(true);
			writeWithMessageConverters(targetable.getTarget(), returnType, webRequest);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = checkNotNull(beanFactory, "beanFactory must not be null");
	}
}
