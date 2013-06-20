package com.github.ridomo.usecase.spring.webmvc.support.integration.presenter;

import com.github.ridomo.usecase.shared.ResponseModel;
import com.github.ridomo.usecase.spring.webmvc.support.presenter.AbstractModelPresenter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Lazy
public class ShowModelPresenter extends AbstractModelPresenter<ResponseModel> {

	@Override
	protected void execute(ResponseModel response) {
		put("show", 1);
	}
}
