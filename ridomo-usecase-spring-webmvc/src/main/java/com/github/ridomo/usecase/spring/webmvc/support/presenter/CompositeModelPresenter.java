package com.github.ridomo.usecase.spring.webmvc.support.presenter;

import com.github.ridomo.usecase.shared.ResponseModel;
import com.github.ridomo.usecase.support.CompositePresenter;
import com.google.common.collect.Lists;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompositeModelPresenter<T extends ResponseModel> extends CompositePresenter<T> implements ModelPresenter<T> {
	private final List<? extends ModelPresenter<T>> presenters;

	public CompositeModelPresenter(List<? extends ModelPresenter<T>> presenters) {
		super(presenters);
		this.presenters = checkNotNull(presenters, "presenters must not be null");
	}

	public CompositeModelPresenter(ModelPresenter<T>... presenters) {
		this(Lists.newArrayList(presenters));
	}

	@Override
	public final void setModel(Map<String, Object> model) {
		for (ModelPresenter<T> presenter : presenters) {
			presenter.setModel(model);
		}
	}
}
