package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompositePresentableInteractor<T extends RequestModel, S extends ResponseModel> extends CompositeInteractor<T> implements PresentableInteractor<T, S> {
	private final List<? extends PresentableInteractor<T, S>> interactors;
	private Presenter<S> presenter;

	public CompositePresentableInteractor(List<? extends PresentableInteractor<T, S>> interactors) {
		super(interactors);
		this.interactors = checkNotNull(interactors, "interactors must not be null");
	}

	public CompositePresentableInteractor(PresentableInteractor<T, S>... interactors) {
		this(Lists.newArrayList(interactors));
	}

	@Override
	public final Presenter<S> getPresenter() {
		return presenter;
	}

	@Override
	public final void setPresenter(Presenter<S> presenter) {
		this.presenter = checkNotNull(presenter, "presenter must not be null");
		for (PresentableInteractor<T, S> interactor : interactors) {
			interactor.setPresenter(presenter);
		}
	}
}
