package com.github.ridomo.usecase.spring.webmvc.support.integration.interactor;

import com.github.ridomo.usecase.shared.RequestModel;
import com.github.ridomo.usecase.support.AbstractInteractor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Lazy
public class UpdateInteractor extends AbstractInteractor<RequestModel> {
	private int count = 0;

	@Override
	protected void execute(RequestModel request) {
		assertThat(++count, is(1));
	}
}
