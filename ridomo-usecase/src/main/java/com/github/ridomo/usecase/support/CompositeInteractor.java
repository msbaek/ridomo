package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompositeInteractor<T extends RequestModel> implements Interactor<T> {
	private final List<? extends Interactor<T>> interactors;

	public CompositeInteractor(List<? extends Interactor<T>> interactors) {
		this.interactors = checkNotNull(interactors, "interactors must not be null");
	}

	public CompositeInteractor(Interactor<T>... interactors) {
		this(Lists.newArrayList(interactors));
	}

	@Override
	public final void accept(T request) {
		for (Interactor<T> interactor : interactors) {
			interactor.accept(request);
		}
	}

	@Override
	public final void interact() {
		for (Interactor<T> interactor : interactors) {
			interactor.interact();
		}
	}
}
