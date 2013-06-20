package com.github.ridomo.usecase.spring.webmvc.support.integration.presenter;

import com.github.ridomo.usecase.shared.ResponseModel;
import com.github.ridomo.usecase.spring.webmvc.support.presenter.Targetable;
import com.github.ridomo.usecase.support.AbstractPresenter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ListingPresenter extends AbstractPresenter<ResponseModel> implements Targetable<List<Integer>> {
	private List<Integer> target = Lists.newArrayList();
	private Integer count = 0;

	@Override
	protected void execute(ResponseModel response) {
		target.add(++count);
		target.add(++count);
		assertThat(count, is(2));
	}

	@Override
	public List<Integer> getTarget() {
		return target;
	}
}
