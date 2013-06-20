package com.github.ridomo.usecase.spring.webmvc.support.integration.interactor;

import com.github.ridomo.usecase.shared.*;
import com.github.ridomo.usecase.support.AbstractPresentableInteractor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Lazy
public class ShowInteractor extends AbstractPresentableInteractor<RequestModel, ResponseModel> {
	private int count = 0;

	@Override
	protected void perform(RequestModel request) {
		assertThat(++count, is(1));
	}

	@Override
	protected ResponseModel createResponseModel() {
		return mock(ResponseModel.class);
	}
}
