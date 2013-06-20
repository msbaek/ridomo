package com.github.ridomo.usecase.spring.webmvc.support.integration.presenter;

import com.github.ridomo.usecase.shared.ResponseModel;
import com.github.ridomo.usecase.spring.webmvc.support.presenter.AbstractModelPresenter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ListingModelPresenter extends AbstractModelPresenter<ResponseModel> {
	private Integer count = 0;

	@Override
	protected void execute(ResponseModel response) {
		put("list", ++count);
		assertThat(count, is(1));
	}
}
