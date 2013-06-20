package com.github.ridomo.usecase.spring.webmvc.support.presenter;

import com.github.ridomo.usecase.shared.ResponseModel;
import com.github.ridomo.usecase.support.AbstractPresenter;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractModelPresenter<T extends ResponseModel> extends AbstractPresenter<T> implements ModelPresenter<T> {
	private Map<String, Object> model;

	protected void put(String key, Object value) {
		checkNotNull(model, "model must not be null").put(checkNotNull(key, "key must not be null"), checkNotNull(value, "value must not be null"));
	}

	@Override
	public final void setModel(Map<String, Object> model) {
		this.model = checkNotNull(model, "model must not be null");
	}
}
